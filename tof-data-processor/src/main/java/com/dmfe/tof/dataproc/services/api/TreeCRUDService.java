package com.dmfe.tof.dataproc.services.api;

import com.dmfe.tof.model.tree.Person;

import java.util.List;

public interface TreeCRUDService {
    List<Person> getPersons();
    Person getPersonById(String id);
    String savePerson(Person person);
    void modifyPerson(String id, Person person);
    void deletePerson(String id);
}
