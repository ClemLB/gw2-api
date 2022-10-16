package fr.kuremento.gw2.web.rest.models;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;

import java.util.List;

public interface GetService<Id, Output> {

	List<Id> get();

	List<Output> getAll();

	List<Output> get(List<Id> ids) throws TooManyArgumentsException;

	Output get(Id id);
}
