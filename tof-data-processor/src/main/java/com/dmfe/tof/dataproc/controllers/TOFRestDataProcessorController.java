package com.dmfe.tof.dataproc.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TOFRestDataProcessorController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from TOF Data Processor!";
    }
}
