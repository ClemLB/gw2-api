#!/bin/bash
set -euo pipefail

BASE_URL="https://api.guildwars2.com/v2"
LANG="fr"
SCHEMA_VERSION="2019-12-19T00:00:00.000Z"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUTPUT_DIR="$SCRIPT_DIR/../src/main/resources/data/referential"
MAX_IDS_PER_REQUEST=200

mkdir -p "$OUTPUT_DIR"

# Télécharge un endpoint paginé (max 200 IDs par requête)
download_paginated() {
    local endpoint="$1"
    local output_file="$2"

    echo "Téléchargement de $endpoint..."

    # Récupère la liste de tous les IDs
    local id_list
    id_list=$(curl -s "$BASE_URL/$endpoint" | jq -r '.[]')
    local total
    total=$(echo "$id_list" | wc -l | tr -d ' ')
    echo "  $total IDs trouvés"

    local temp_dir
    temp_dir=$(mktemp -d)
    local batch_num=0
    local ids_array=()

    while IFS= read -r id; do
        [ -z "$id" ] && continue
        ids_array+=("$id")
    done <<< "$id_list"

    # Découpe en lots de MAX_IDS_PER_REQUEST
    local i=0
    while [ "$i" -lt "${#ids_array[@]}" ]; do
        local batch_ids=""
        local j=0
        while [ "$j" -lt "$MAX_IDS_PER_REQUEST" ] && [ "$((i + j))" -lt "${#ids_array[@]}" ]; do
            if [ -n "$batch_ids" ]; then
                batch_ids="$batch_ids,${ids_array[$((i + j))]}"
            else
                batch_ids="${ids_array[$((i + j))]}"
            fi
            j=$((j + 1))
        done

        echo "  Lot $batch_num ($j IDs)..."
        curl -sf "$BASE_URL/$endpoint?ids=$batch_ids&lang=$LANG&v=$SCHEMA_VERSION" > "$temp_dir/batch_$(printf '%04d' $batch_num).json" || {
            echo "    ERREUR sur le lot $batch_num, nouvel essai..."
            sleep 2
            curl -sf "$BASE_URL/$endpoint?ids=$batch_ids&lang=$LANG&v=$SCHEMA_VERSION" > "$temp_dir/batch_$(printf '%04d' $batch_num).json"
        }

        i=$((i + j))
        batch_num=$((batch_num + 1))
        sleep 0.5
    done

    # Fusionne tous les lots en un seul JSON array
    jq -s 'add' "$temp_dir"/batch_*.json > "$output_file"

    rm -rf "$temp_dir"
    local result_count
    result_count=$(jq 'length' "$output_file")
    echo "  $result_count éléments écrits dans $(basename "$output_file")"
}

# Télécharge un endpoint simple (peu d'éléments, pas besoin de pagination)
download_simple() {
    local endpoint="$1"
    local output_file="$2"

    echo "Téléchargement de $endpoint..."
    curl -sf "$BASE_URL/$endpoint?ids=all&lang=$LANG&v=$SCHEMA_VERSION" > "$output_file"
    local result_count
    result_count=$(jq 'length' "$output_file")
    echo "  $result_count éléments écrits dans $(basename "$output_file")"
}

echo "=== Mise à jour du référentiel GW2 ==="
echo ""

download_paginated "traits" "$OUTPUT_DIR/traits.json"
echo ""

download_paginated "skills" "$OUTPUT_DIR/skills.json"
echo ""

download_simple "specializations" "$OUTPUT_DIR/specializations.json"
echo ""

download_simple "professions" "$OUTPUT_DIR/professions.json"
echo ""

echo "=== Référentiel mis à jour avec succès ==="
