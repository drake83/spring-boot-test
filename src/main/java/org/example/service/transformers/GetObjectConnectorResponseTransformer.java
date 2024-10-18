package org.example.service.transformers;


import org.example.aspectj.model.IRestResponseTransformer;
import org.example.aspectj.model.RestConnectorResponse;
import org.example.model.GetObjectResponse;
import org.example.model.ObjectDTO;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetObjectConnectorResponseTransformer implements IRestResponseTransformer<ObjectDTO, GetObjectResponse> {

    @Override
    public GetObjectResponse transform(RestConnectorResponse<ObjectDTO> response) {

        Optional<ObjectDTO> res = Optional.ofNullable(response.getResponse()).map(HttpEntity::getBody);
        return res.map(a -> {
            GetObjectResponse out = new GetObjectResponse();
            out.setId(a.getId());
            out.setName(a.getName());
            out.setData(a.getData());
            out.setDate(new Date());
            return out;
        }).orElseThrow(RuntimeException::new);
    }

}
