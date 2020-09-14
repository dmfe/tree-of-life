package com.dmfe.tof.dataproc.services.impl;

import com.dmfe.tof.dataproc.data.PersonEntity;
import com.dmfe.tof.dataproc.repositories.PersonRepository;
import com.dmfe.tof.dataproc.services.api.TreeCRUDService;
import com.dmfe.tof.model.tree.Person;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Log4j2
public class TreeCRUDServiceImpl implements TreeCRUDService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> getPersons() {
        List<Person> persons = StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .map(PersonEntity::toPerson)
                .collect(Collectors.toList());
        log.trace("(getPersons): Found {} person(s)", persons.size());

        return persons;
    }

    @Override
    public Person getPersonById(String id) {
        PersonEntity personEntity = personRepository.findOneByExternalId(id);
        log.debug("(getPersonById): Found person entity:\n{}", personEntity);

        return personEntity.toPerson();
    }

    @Override
    public String savePerson(Person person) {
        PersonEntity personEntity = PersonEntity.ofPerson(person);
        String externalId = UUID.randomUUID().toString();
        personEntity.setExternalId(externalId);
        log.debug("(savePerson): Person entity to save:\n{}", personEntity);

        PersonEntity savedEntity = personRepository.save(personEntity);
        log.trace("(savePerson): Person entity with id={} created", savedEntity.getId());

        return externalId;
    }

    @Override
    public void modifyPerson(String id, Person person) {
        PersonEntity personEntity = personRepository.findOneByExternalId(id);
        log.debug("(modifyPerson): Found person entity:\n{}", personEntity);

        PersonEntity updatedPersonEntity = PersonEntity.ofPerson(person);
        updatedPersonEntity.setExternalId(personEntity.getExternalId());
        updatedPersonEntity.setId(personEntity.getId());
        log.debug("(modifyPerson): Person entity to modify:\n{}", updatedPersonEntity);

        personRepository.save(updatedPersonEntity);
        log.trace("(modifyPerson): Person entity with id={} updated", updatedPersonEntity.getId());
    }

    @Override
    public void deletePerson(String id) {
        PersonEntity personEntity = personRepository.findOneByExternalId(id);
        log.debug("(deletePerson): Found person entity:\n{}", personEntity);

        personRepository.delete(personEntity);
        log.trace("(deletePerson): Person entity with id={} deleted", personEntity.getId());
    }
}
