package com.dmfe.tof.dataproc.components;

import com.dmfe.tof.dataproc.services.api.DbInitService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DbInitComponent implements ApplicationListener<ApplicationReadyEvent> {
    private final DbInitService dbInitService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        dbInitService.createCollections();
    }
}
