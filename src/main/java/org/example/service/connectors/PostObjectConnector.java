package org.example.service.connectors;

import org.example.aspectj.annotations.GhostTracerReader;
import org.example.aspectj.annotations.GhostTracerSaver;
import org.example.aspectj.model.RestConnectorRequest;
import org.example.aspectj.model.RestConnectorResponse;
import org.example.model.*;
import org.example.service.transformers.PostObjectConnectorRequestTransformer;
import org.example.service.transformers.PostObjectConnectorResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostObjectConnector {

    @Autowired
    private RestTemplate restTemplate;

    @GhostTracerReader(connectorClass = PostObjectConnector.class, restConnectorReturnType = ObjectPostDTO.class)
    @GhostTracerSaver(connectorClass = PostObjectConnector.class)
    public PostObjectResponse postCall(ObjectPostRequest request, PostObjectConnectorRequestTransformer requestTransformer, PostObjectConnectorResponseTransformer responseTransformer) {
        RestConnectorRequest<ObjectPostRequest> restConnectorRequest = requestTransformer.transform(request);
        ObjectPostDTO objectPostDTO = restTemplate.postForObject("https://api.restful-api.dev/objects", restConnectorRequest.getRequest(), ObjectPostDTO.class);
        RestConnectorResponse<ObjectPostDTO> restConnectorResponse = new RestConnectorResponse<>();
        restConnectorResponse.setResponse(ResponseEntity.ok(objectPostDTO));
        return responseTransformer.transform(restConnectorResponse);
    }

}
