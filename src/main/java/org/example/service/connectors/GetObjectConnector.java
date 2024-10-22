package org.example.service.connectors;

import org.example.aspectj.annotations.GhostTracerReader;
import org.example.aspectj.annotations.GhostTracerSaver;
import org.example.aspectj.model.RestConnectorRequest;
import org.example.aspectj.model.RestConnectorResponse;
import org.example.model.GetObjectResponse;
import org.example.model.ObjectGetDTO;
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

    @GhostTracerReader(connectorClass = GetObjectConnector.class, restConnectorReturnType = ObjectGetDTO.class)
    @GhostTracerSaver(connectorClass = GetObjectConnector.class)
    public GetObjectResponse getCall(Long id, GetObjectConnectorRequestTransformer requestTransformer, GetObjectConnectorResponseTransformer responseTransformer) {
        RestConnectorRequest<Long> restConnectorRequest = requestTransformer.transform(id);
        ObjectGetDTO objectGetDTO = restTemplate.getForObject("https://api.restful-api.dev/objects/" + restConnectorRequest.getRequest(), ObjectGetDTO.class);
        RestConnectorResponse<ObjectGetDTO> restConnectorResponse = new RestConnectorResponse<>();
        restConnectorResponse.setResponse(ResponseEntity.ok(objectGetDTO));
        return responseTransformer.transform(restConnectorResponse);
    }


}
