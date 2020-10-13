package com.dmfe.tof.dataproc.components.validation.protobuf;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.validation.Validator;
import com.dmfe.tof.dataproc.exceptions.ModelException;
import com.dmfe.tof.model.metadata.DbCollectionsUtil;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

@Component
public class ProtobufValidator implements Validator {

    private static final JsonFormat.Parser parser = JsonFormat.parser();

    @Override
    public void validate(RequestData data) {
        Message protoMessage = DbCollectionsUtil.getProtoMessage(data.getEntity())
                .orElseThrow(() ->
                        new ModelException("Entity: " + data.getEntity() + " is not supported in the current model.")
                );

        InvalidProtobufMsgHandler.handle(() -> {
            parser.merge(data.getData(), protoMessage.toBuilder());
            return null;
        });
    }
}
