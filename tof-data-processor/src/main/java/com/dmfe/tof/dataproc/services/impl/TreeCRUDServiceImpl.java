package com.dmfe.tof.dataproc.services.impl;

import com.dmfe.tof.dataproc.data.PersonEntity;
import com.dmfe.tof.dataproc.repositories.PersonRepository;
import com.dmfe.tof.dataproc.services.api.TreeCRUDService;
import com.dmfe.tof.model.tree.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class TreeCRUDServiceImpl implements TreeCRUDService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> getPersons() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .map(PersonEntity::toPerson)
                .collect(Collectors.toList());
    }

    @Override
    public Person getPersonById(String id) {
        PersonEntity personEntity = personRepository.findOneByExternalId(id);

        return personEntity.toPerson();
    }

    @Override
    public String savePerson(Person person) {
        PersonEntity personEntity = PersonEntity.ofPerson(person);
        String externalId = UUID.randomUUID().toString();
        personEntity.setExternalId(externalId);

        personRepository.save(personEntity);

        return externalId;
    }

    @Override
    public void modifyPerson(String id, Person person) {
        PersonEntity personEntity = personRepository.findOneByExternalId(id);
        PersonEntity updatedPersonEntity = PersonEntity.ofPerson(person);
        updatedPersonEntity.setExternalId(personEntity.getExternalId());
        updatedPersonEntity.setId(personEntity.getId());

        personRepository.save(updatedPersonEntity);
    }

    @Override
    public void deletePerson(String id) {
        PersonEntity personEntity = personRepository.findOneByExternalId(id);
        personRepository.delete(personEntity);
    }
}
