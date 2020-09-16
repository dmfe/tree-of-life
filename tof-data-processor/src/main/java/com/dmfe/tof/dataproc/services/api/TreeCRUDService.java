package com.dmfe.tof.dataproc.services.api;

import com.dmfe.tof.model.tree.Person;
import com.dmfe.tof.model.tree.Persons;

public interface TreeCRUDService {
    Persons getPersons();
    Person getPersonById(String id);
    String savePerson(Person person);
    void modifyPerson(String id, Person person);
    void deletePerson(String id);
}
