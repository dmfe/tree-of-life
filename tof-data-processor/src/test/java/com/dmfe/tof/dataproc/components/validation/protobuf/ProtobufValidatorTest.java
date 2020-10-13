package com.dmfe.tof.dataproc.components.validation.protobuf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.exceptions.ModelException;
import com.dmfe.tof.dataproc.exceptions.ProtobufFormatException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class ProtobufValidatorTest {

    private final ProtobufValidator protobufValidator = new ProtobufValidator();

    @Test
    void testValidateSunnyDayScenario() {
        RequestData requestData = RequestData.builder()
                .id("1")
                .entity("persons")
                .data("{\n" +
                        "    \"name\" : \"Ivan\",\n" +
                        "    \"surname\" : \"Ivanov\",\n" +
                        "    \"oldSurnames\" : [\"Smirnov\", \"Sidorov\"],\n" +
                        "    \"bornLocation\" : {\n" +
                        "        \"country\" : \"RUSSIA\",\n" +
                        "        \"city\" : \"Gorky\",\n" +
                        "        \"district\" : \"Avtozavodsky\",\n" +
                        "        \"street\" : \"Lenina\",\n" +
                        "        \"building\" : \"10\"\n" +
                        "    },\n" +
                        "    \"livingLocation\" : {\n" +
                        "        \"country\" : \"RUSSIA\",\n" +
                        "        \"city\" : \"Moscow\",\n" +
                        "        \"district\" : \"Moscowskii\",\n" +
                        "        \"street\" : \"Moscowskaya\",\n" +
                        "        \"building\" : \"14\"\n" +
                        "    }\n" +
                        "}")
                .build();

        protobufValidator.validate(requestData);

        log.info("Data is valid");
    }

    @Test
    void testValidateUnsupportedEntity() {
        String unsupportedEntity = "person";
        RequestData requestData = RequestData.builder()
                .entity(unsupportedEntity)
                .build();
        String expectedErrMsg = "Entity: " + unsupportedEntity + " is not supported in the current model.";

        Throwable exception = assertThrows(ModelException.class, () -> protobufValidator.validate(requestData));

        assertEquals(expectedErrMsg, exception.getMessage());
    }

    @Test
    void testValidateInvalidEntityField() {
        String invalidField = "names";
        RequestData requestData = RequestData.builder()
                .id("1")
                .entity("persons")
                .data("{\n" +
                        "    \"" + invalidField + "\" : \"Ivan\"\n" +
                        "}")
                .build();
        String expectedErrMsg = "Error while parsing protobuf message: Cannot find field: " + invalidField +
                " in message Person";

        Throwable exception = assertThrows(ProtobufFormatException.class, () ->
                protobufValidator.validate(requestData));

        assertEquals(expectedErrMsg, exception.getMessage());
    }
}
