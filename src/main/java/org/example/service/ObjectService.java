package org.example.service;

import org.example.model.GetObjectResponse;
import org.example.model.ObjectPostRequest;
import org.example.model.PostObjectResponse;
import org.example.service.connectors.GetObjectConnector;
import org.example.service.connectors.PostObjectConnector;
import org.example.service.transformers.GetObjectConnectorRequestTransformer;
import org.example.service.transformers.GetObjectConnectorResponseTransformer;
import org.example.service.transformers.PostObjectConnectorRequestTransformer;
import org.example.service.transformers.PostObjectConnectorResponseTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectService {

    @Autowired
    private GetObjectConnector getObjectConnector;
    @Autowired
    private PostObjectConnector postObjectConnector;
    @Autowired
    private GetObjectConnectorRequestTransformer getObjectConnectorRequestTransformer;
    @Autowired
    private GetObjectConnectorResponseTransformer getObjectConnectorResponseTransformer;

    @Autowired
    private PostObjectConnectorRequestTransformer postObjectConnectorRequestTransformer;

    @Autowired
    private PostObjectConnectorResponseTransformer postObjectConnectorResponseTransformer;

    public GetObjectResponse getObjectById(Long id) {
        return getObjectById(id, getObjectConnectorRequestTransformer, getObjectConnectorResponseTransformer);
    }

    public PostObjectResponse postObject(ObjectPostRequest request) {
        return postObject(request, postObjectConnectorRequestTransformer, postObjectConnectorResponseTransformer);
    }

    public GetObjectResponse getObjectById(Long input, GetObjectConnectorRequestTransformer getObjectConnectorRequestTransformer, GetObjectConnectorResponseTransformer getObjectConnectorResponseTransformer) {
        return getObjectConnector
                .getCall(input, getObjectConnectorRequestTransformer, getObjectConnectorResponseTransformer);
    }

    public PostObjectResponse postObject(ObjectPostRequest request, PostObjectConnectorRequestTransformer getObjectConnectorRequestTransformer, PostObjectConnectorResponseTransformer getObjectConnectorResponseTransformer) {
        return postObjectConnector
                .postCall(request, getObjectConnectorRequestTransformer, getObjectConnectorResponseTransformer);
    }


}
