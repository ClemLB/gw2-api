# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Projet

Librairie Java (Spring Boot 3.0.12) fournissant un client REST pour l'API Guild Wars 2. Ce n'est **pas** un serveur web (`web-application-type: none`) — c'est une librairie consommée par d'autres projets via GitHub Packages.

API GW2 de référence : https://wiki.guildwars2.com/wiki/API:2

## Commandes de build

```bash
./mvnw clean install                    # Build complet avec tests
./mvnw clean install -DskipTests        # Build sans tests
./mvnw test                             # Tests unitaires uniquement (*Test.java)
./mvnw verify                           # Tests unitaires + intégration (*IT.java, *IntTest.java)
./mvnw test -Dtest=RaidsTest            # Lancer un test spécifique
./mvnw org.jacoco:jacoco-maven-plugin:prepare-agent install   # Build avec couverture JaCoCo
```

## Architecture

### Pattern Facade
Le point d'entrée principal est `Gw2Client` (`client/Gw2Client.java`), un `@Component` Spring qui expose tous les services via des méthodes d'accès : `client.account()`, `client.achievements()`, `client.raids()`, etc.

### Hiérarchie de services
Tous les services héritent de `AbstractService` qui fournit :
- Les méthodes HTTP `get()` et `getWithAuthentification()` (WebClient réactif avec `.block()`)
- Les builders d'URI : `buildURI(endpoint)`, `buildURI(endpoint, id)`, `buildURI(endpoint, ids)`, `buildURIAllIds(endpoint)`
- La gestion d'erreurs HTTP (401, 403, 404, 503) → `TechnicalException`
- La limite de pagination (max 200 IDs par requête) → `TooManyArgumentsException`

Certains services sont composites avec des sous-services (ex : `AccountService` → `account().achievements()`, `account().bank()`, `account().raids()`).

### Modèles générés
Les modèles dans `web/rest/models/` sont auto-générés par le plugin `openapi-generator-maven-plugin` à partir du fichier Swagger `src/main/resources/swagger/api-gw2.yaml`. Ne pas les modifier manuellement.

### Auto-configuration Spring
La librairie s'enregistre via `spring.factories` → `AutoConfig` qui scan le package `fr.kuremento.gw2`.

### Données embarquées
Les fichiers JSON dans `src/main/resources/data/` (mappings et instabilities fractales) sont chargés au démarrage via `JsonFractalsConfig`.

## Configuration

Tous les endpoints et timeouts sont externalisés dans `src/main/resources/application.yaml` sous `application.rest.config` et `application.rest.endpoints`.

## Conventions

- **Lombok** : `@Data`, `@RequiredArgsConstructor`, `@Slf4j`, `@FieldDefaults(level = AccessLevel.PRIVATE)`
- **Injection** : constructeur via `@RequiredArgsConstructor` pour les composants, setter `@Autowired` dans `AbstractService`
- **JSON** : annotations `@JsonProperty` explicites, snake_case → camelCase
- **Tests** : `@SpringBootTest`, `@DisplayName`, assertions JUnit 5 statiques. Les tests mirrorent la structure des packages main
- **Java** : 17, encodage UTF-8

## Versionnement et CI/CD

- Semantic versioning avec tags `v{version}`
- PR template : indiquer le type de bump (`#minor`, `#patch`)
- Workflows GitHub Actions : build/test sur push et PR, publish sur release, bump automatique
