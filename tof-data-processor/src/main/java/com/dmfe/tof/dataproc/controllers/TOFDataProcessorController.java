package com.dmfe.tof.dataproc.controllers;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.ResponseData;
import com.dmfe.tof.dataproc.components.request.handlers.RequestHandler;
import com.dmfe.tof.dataproc.components.request.handlers.RequestHandlerProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Log4j2
public class TOFDataProcessorController {

    private static final String PERSONS = "persons";
    private static final String LOCATIONS = "locations";

    private final RequestHandlerProvider requestHandlerProvider;

    @Operation(summary = "Get persons list by some filtering criteria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found some persons")
    })
    @GetMapping(value = "/" + PERSONS, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String getPersons() {
        log.trace("GET request /api/tof/dp/tree/persons");

        RequestHandler<String> handler = requestHandlerProvider.getGetRequestHandler();
        RequestData requestData = RequestData.builder()
                .entity(PERSONS)
                .build();

        return handler.handle(requestData);
    }

    @Operation(summary = "Get person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found person")
    })
    @GetMapping(value = "/" + PERSONS + "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String getPersonById(@PathVariable("id") String id) {
        log.trace("GET request /api/tof/dp/tree/persons/{}", id);

        RequestHandler<String> requestHandler = requestHandlerProvider.getGetRequestHandler();
        RequestData requestData = RequestData.builder()
                .entity(PERSONS)
                .id(id)
                .build();

        return requestHandler.handle(requestData);
    }

    @Operation(summary = "Create new person")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Person created")
    })
    @PostMapping(value = "/" + PERSONS, consumes = "application/json")
    public ResponseEntity<ResponseData> addPerson(@RequestBody String person) {
        log.trace("POST request /api/tof/dp/tree/persons\n{}", person);

        RequestHandler<String> requestHandler = requestHandlerProvider.getPostRequestHandler();
        RequestData requestData = RequestData.builder()
                .entity(PERSONS)
                .data(person)
                .build();

        String id = requestHandler.handle(requestData);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseData("Created Person with id = " + id));
    }

    @Operation(summary = "Change existing person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person modified")
    })
    @PutMapping(value = "/" + PERSONS + "/{id}", consumes = "application/json")
    public ResponseEntity<ResponseData> modifyPerson(@PathVariable("id") String id,
                                                     @RequestBody String person) {
        log.trace("PUT request /api/tof/dp/tree/persons/{}\n{}", id, person);

        RequestHandler<Void> requestHandler = requestHandlerProvider.getPutRequestHandler();
        RequestData requestData = RequestData.builder()
                .id(id)
                .entity(PERSONS)
                .data(person)
                .build();

        requestHandler.handle(requestData);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseData("Modified Person with id = " + id));
    }

    @Operation(summary = "Delete existing person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person deleted")
    })
    @DeleteMapping("/" + PERSONS + "/{id}")
    public ResponseEntity<ResponseData> deletePerson(@PathVariable("id") String id) {
        log.trace("DELETE request /api/tof/dp/tree/persons/{}", id);

        RequestHandler<Void> requestHandler = requestHandlerProvider.getDeleteRequestHandler();
        RequestData requestData = RequestData.builder()
                .id(id)
                .entity(PERSONS)
                .build();

        requestHandler.handle(requestData);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseData("Deleted Person with id = " + id));
    }
}
