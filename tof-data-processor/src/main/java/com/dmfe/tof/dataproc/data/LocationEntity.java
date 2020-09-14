package com.dmfe.tof.dataproc.data;

import com.dmfe.tof.dataproc.Generators;
import com.dmfe.tof.model.tree.Country;
import com.dmfe.tof.model.tree.Location;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationEntity {
    public static LocationEntity ofLocation(Location location) {
        return LocationEntity.builder()
                .externalId(Generators.genrateUuidIfEmpty(location.getId()))
                .country(location.getCountry().name())
                .city(location.getCity())
                .district(location.getDistrict())
                .street(location.getStreet())
                .building(location.getBuilding()).build();
    }

    public Location toLocation() {
        return Location.newBuilder()
                .setId(externalId)
                .setCountry(Country.valueOf(country))
                .setCity(city)
                .setDistrict(district)
                .setStreet(street)
                .setBuilding(building).build();
    }

    private String externalId;
    private String country;
    private String city;
    private String district;
    private String street;
    private String building;
}
