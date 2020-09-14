package com.dmfe.tof.dataproc.data;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Field;
import com.dmfe.tof.dataproc.utils.Generators;
import com.dmfe.tof.model.tree.Person;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@Document("person")
public class PersonEntity {

    public static PersonEntity ofPerson(Person person) {
        return PersonEntity.builder()
                .externalId(Generators.genrateUuidIfEmpty(person.getId()))
                .name(person.getName())
                .surname(person.getSurname())
                .oldSurnames(person.getOldSurnamesList())
                .bornLocation(LocationEntity.ofLocation(person.getBornLocation()))
                .livingLocation(LocationEntity.ofLocation(person.getLivingLocation())).build();
    }

    public Person toPerson() {
        return Person.newBuilder()
                .setId(externalId)
                .setName(name)
                .setSurname(surname)
                .addAllOldSurnames(oldSurnames)
                .setBornLocation(bornLocation.toLocation())
                .setLivingLocation(livingLocation.toLocation()).build();
    }

    @Id
    private String id;

    @Field("ext_id")
    private String externalId;

    private String name;
    private String surname;

    @Field("old_surnames")
    private List<String> oldSurnames;

    @Field("born_location")
    private LocationEntity bornLocation;

    @Field("living_location")
    private LocationEntity livingLocation;
}
