package org.example.service.connectors;

import org.example.aspectj.annotations.GhostTracerReader;
import org.example.aspectj.annotations.GhostTracerSaver;
import org.example.aspectj.model.RestConnectorRequest;
import org.example.aspectj.model.RestConnectorResponse;
import org.example.model.GetObjectResponse;
import org.example.model.ObjectDTO;
import org.example.service.transformers.GetObjectConnectorRequestTransformer;
import org.example.service.transformers.GetObjectConnectorResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetObjectConnector {

    @Autowired
    private RestTemplate restTemplate;

    @GhostTracerReader(connectorClass = GetObjectConnector.class, restConnectorReturnType = ObjectDTO.class)
    @GhostTracerSaver(connectorClass = GetObjectConnector.class)
    public GetObjectResponse call(Long id, GetObjectConnectorRequestTransformer requestTransformer, GetObjectConnectorResponseTransformer responseTransformer) {
        RestConnectorRequest<Long> restConnectorRequest = requestTransformer.transform(id);
        ObjectDTO objectDTO = restTemplate.getForObject("https://api.restful-api.dev/objects/" + restConnectorRequest.getRequest(), ObjectDTO.class);
        RestConnectorResponse<ObjectDTO> restConnectorResponse = new RestConnectorResponse<>();
        restConnectorResponse.setResponse(ResponseEntity.ok(objectDTO));
        return responseTransformer.transform(restConnectorResponse);
    }

}
