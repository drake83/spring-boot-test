package org.example.service.transformers;


import org.example.aspectj.model.IRestRequestTransformer;
import org.example.aspectj.model.RestConnectorRequest;
import org.springframework.stereotype.Service;

@Service
public class GetObjectConnectorRequestTransformer implements IRestRequestTransformer<Long, Long> {

    @Override
    public RestConnectorRequest<Long> transform(Long om, Object... args) {
        RestConnectorRequest<Long> restConnectorRequest = new RestConnectorRequest<>();
        restConnectorRequest.setRequest(om);
        return restConnectorRequest;
    }

}
