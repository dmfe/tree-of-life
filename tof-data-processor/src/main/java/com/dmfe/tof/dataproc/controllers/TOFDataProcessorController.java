package com.dmfe.tof.dataproc.controllers;

import com.dmfe.tof.dataproc.services.api.TreeCRUDService;
import com.dmfe.tof.model.tree.Person;
import com.dmfe.tof.model.tree.Persons;
import com.google.protobuf.util.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get persons list by some filtering criteria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found some persons")
    })
    @GetMapping(value = "/persons", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String getPersons() {
        log.trace("GET request /api/tof/dp/tree/persons");

        return InvalidProtobufMsgHandler.handle(() -> JsonFormat.printer().print(treeCRUDService.getPersons()));
    }

    @Operation(summary = "Get person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found person")
    })
    @GetMapping(value = "/persons/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String getPersonById(@PathVariable("id") String id) {
        log.trace("GET request /api/tof/dp/tree/persons/{}", id);

        return InvalidProtobufMsgHandler.handle(() ->
                JsonFormat.printer().print(treeCRUDService.getPersonById(id)));
    }

    @Operation(summary = "Create new person")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Person created")
    })
    @PostMapping(value = "/persons", consumes = "application/json")
    public ResponseEntity<ResponseData> addPerson(@RequestBody String person) {
        log.trace("POST request /api/tof/dp/tree/persons\n{}", person);

        Person.Builder personBuilder = Person.newBuilder();
        InvalidProtobufMsgHandler.handle(() -> {
            JsonFormat.parser().merge(person, personBuilder);
            return null;
        });

        String id = treeCRUDService.savePerson(personBuilder.build());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseData("Created Person with id = " + id));
    }

    @Operation(summary = "Change existing person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person modified")
    })
    @PutMapping("persons/{id}")
    public ResponseEntity<ResponseData> modifyPerson(@PathVariable("id") String id,
                                                     @RequestBody String person) {
        log.trace("PUT request /api/tof/dp/tree/persons/{}\n{}", id, person);

        Person.Builder personBuilder = Person.newBuilder();
        InvalidProtobufMsgHandler.handle(() -> {
            JsonFormat.parser().merge(person, personBuilder);
            return null;
        });

        treeCRUDService.modifyPerson(id, personBuilder.build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseData("Modified Person with id = " + id));
    }

    @Operation(summary = "Delete existing person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person deleted")
    })
    @DeleteMapping("persons/{id}")
    public ResponseEntity<ResponseData> deletePerson(@PathVariable("id") String id) {
        log.trace("DELETE request /api/tof/dp/tree/persons/{}", id);

        treeCRUDService.deletePerson(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseData("Deleted Person with id = " + id));
    }
}
