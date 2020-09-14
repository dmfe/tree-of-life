package com.dmfe.tof.dataproc.controllers;

import com.dmfe.tof.dataproc.services.api.TreeCRUDService;
import com.dmfe.tof.model.tree.Person;
import com.dmfe.tof.model.tree.Persons;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tof/dp/tree")
@AllArgsConstructor
@Log4j2
public class TOFDataProcessorController {

    private TreeCRUDService treeCRUDService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from TOF InputData Processor!";
    }

    @GetMapping("/persons")
    @ResponseStatus(HttpStatus.OK)
    public Persons getPersons() {
        log.trace("GET request /api/tof/dp/tree/persons");

        return Persons.newBuilder()
                .addAllPersonList(treeCRUDService.getPersons()).build();
    }

    @GetMapping("/persons/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person getPersonById(@PathVariable("id") String id) {
        log.trace("GET request /api/tof/dp/tree/persons/{}", id);

        return treeCRUDService.getPersonById(id);
    }

    @PostMapping("/persons")
    public ResponseEntity<ResponseData> addPerson(@RequestBody Person person) {
        log.trace("POST request /api/tof/dp/tree/persons\n{}", person);

        String id = treeCRUDService.savePerson(person);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseData("Created Person with id = " + id));
    }

    @PutMapping("persons/{id}")
    public ResponseEntity<ResponseData> modifyPerson(@PathVariable("id") String id,
                                                     @RequestBody Person person) {
        log.trace("PUT request /api/tof/dp/tree/persons/{}\n{}", id, person);

        treeCRUDService.modifyPerson(id, person);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseData("Modified Person with id = " + id));
    }

    @DeleteMapping("persons/{id}")
    public ResponseEntity<ResponseData> deletePerson(@PathVariable("id") String id) {
        log.trace("DELETE request /api/tof/dp/tree/persons/{}", id);

        treeCRUDService.deletePerson(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseData("Deleted Person with id = " + id));
    }
}
