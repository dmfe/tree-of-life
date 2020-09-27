package com.dmfe.tof.dataproc.components.request.data;

import com.dmfe.tof.dataproc.dao.dto.TransitionObject;
import com.dmfe.tof.dataproc.dao.dto.TransitionObjectFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public class RequestProperties {
    private final String id;
    private final String entityName;
    private final TransitionObject transitionObject;

    public static RequestProperties fromRequestData(RequestData data) {
        return new RequestProperties(data.getId(), data.getEntity(), TransitionObjectFactory.fromRequestData(data));
    }

    public boolean isListRequest() {
        return StringUtils.isEmpty(id);
    }
}
