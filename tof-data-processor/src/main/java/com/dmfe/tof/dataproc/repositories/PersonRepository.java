package com.dmfe.tof.dataproc.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.dmfe.tof.dataproc.data.PersonEntity;

public interface PersonRepository extends ArangoRepository<PersonEntity, String> {

    PersonEntity findOneByExternalId(String id);
}
