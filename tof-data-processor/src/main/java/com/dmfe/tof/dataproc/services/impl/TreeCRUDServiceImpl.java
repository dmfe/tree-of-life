package com.dmfe.tof.dataproc.services.impl;

import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.dao.api.RwDao;
import com.dmfe.tof.dataproc.dao.dto.TransitionObject;
import com.dmfe.tof.dataproc.services.api.TreeCreateService;
import com.dmfe.tof.dataproc.services.api.TreeDeleteService;
import com.dmfe.tof.dataproc.services.api.TreeModifyService;
import com.dmfe.tof.dataproc.services.api.TreeReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TreeCRUDServiceImpl implements
        TreeReadService,
        TreeCreateService,
        TreeModifyService,
        TreeDeleteService {

    private final RwDao rwDao;

    @Override
    public String getEntities(RequestProperties requestProperties) {

        String result;
        if (StringUtils.isNotEmpty(requestProperties.getId())) {
            TransitionObject object = rwDao.getFirstEntity(requestProperties);
            log.debug("(getEntities): Found entity:\n{}", object);

            result = object.toJsonObject();
        } else {
            List<TransitionObject> entities = rwDao.getEntities(requestProperties);
            log.trace("(getEntities): Found {} entity(ies)", entities.size());

            result = entities.stream()
                    .map(TransitionObject::toJsonObject)
                    .collect(Collectors.joining(",", "[", "]"));
        }

        return result;
    }

    @Override
    public String saveEntity(RequestProperties requestProperties) {
        String externalId = rwDao.createEntity(requestProperties);
        log.trace("(saveEntity): Entity with id={} created", externalId);

        return externalId;
    }

    @Override
    public void modifyEntity(RequestProperties requestProperties) {
        rwDao.replaceEntity(requestProperties);
        log.trace("(modifyEntity): Entity with id={} updated", requestProperties.getId());
    }

    @Override
    public void deleteEntity(RequestProperties requestProperties) {
        rwDao.deleteEntity(requestProperties);
        log.trace("(deleteEntity): Entity with id={} deleted", requestProperties.getId());
    }
}
