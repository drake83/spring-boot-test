package org.example.service;

import org.example.model.GetObjectResponse;
import org.example.service.connectors.GetObjectConnector;
import org.example.service.transformers.GetObjectConnectorRequestTransformer;
import org.example.service.transformers.GetObjectConnectorResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectService {

    @Autowired
    private GetObjectConnector getObjectConnector;
    @Autowired
    private GetObjectConnectorRequestTransformer getObjectConnectorRequestTransformer;
    @Autowired
    private GetObjectConnectorResponseTransformer getObjectConnectorResponseTransformer;


    public GetObjectResponse getObjectById(Long id) {
        return getUserTasks(id, getObjectConnectorRequestTransformer, getObjectConnectorResponseTransformer);
    }

    public GetObjectResponse getUserTasks(Long input, GetObjectConnectorRequestTransformer getObjectConnectorRequestTransformer, GetObjectConnectorResponseTransformer getObjectConnectorResponseTransformer) {
        return getObjectConnector
                .call(input, getObjectConnectorRequestTransformer, getObjectConnectorResponseTransformer);
    }




}
