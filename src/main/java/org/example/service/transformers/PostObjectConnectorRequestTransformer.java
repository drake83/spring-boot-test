package org.example.service.transformers;


import org.example.aspectj.model.IRestRequestTransformer;
import org.example.aspectj.model.RestConnectorRequest;
import org.example.model.ObjectPostRequest;
import org.springframework.stereotype.Service;

@Service
public class PostObjectConnectorRequestTransformer implements IRestRequestTransformer<ObjectPostRequest, ObjectPostRequest> {

    @Override
    public RestConnectorRequest<ObjectPostRequest> transform(ObjectPostRequest om, Object... args) {
        RestConnectorRequest<ObjectPostRequest> restConnectorRequest = new RestConnectorRequest<>();
        restConnectorRequest.setRequest(om);
        return restConnectorRequest;
    }

}
