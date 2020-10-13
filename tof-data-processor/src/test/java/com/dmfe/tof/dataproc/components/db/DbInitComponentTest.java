package com.dmfe.tof.dataproc.components.db;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dmfe.tof.dataproc.services.api.DbInitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class DbInitComponentTest {

    @Test
    void testOnApplicationEvent(@Mock DbInitService dbInitService) {
        DbInitComponent dbInitComponent = new DbInitComponent(dbInitService);
        ApplicationReadyEvent applicationReadyEvent = mock(ApplicationReadyEvent.class);

        dbInitComponent.onApplicationEvent(applicationReadyEvent);

        verify(dbInitService, times(1)).createCollections();
    }
}
