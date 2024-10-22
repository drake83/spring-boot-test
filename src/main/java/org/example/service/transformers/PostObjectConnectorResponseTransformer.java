package org.example.service.transformers;


import org.example.aspectj.model.IRestResponseTransformer;
import org.example.aspectj.model.RestConnectorResponse;
import org.example.model.GetObjectResponse;
import org.example.model.ObjectPostDTO;
import org.example.model.ObjectPostData;
import org.example.model.PostObjectResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostObjectConnectorResponseTransformer implements IRestResponseTransformer<ObjectPostDTO, PostObjectResponse> {

    @Override
    public PostObjectResponse transform(RestConnectorResponse<ObjectPostDTO> response) {

        Optional<ObjectPostDTO> res = Optional.ofNullable(response.getResponse()).map(HttpEntity::getBody);
        return res.map(a -> {
            PostObjectResponse out = new PostObjectResponse();
            out.setId(a.getId());
            out.setName(a.getName());
            out.setData(a.getData());
            out.setDate(new Date());
            out.setCreatedAt(a.getCreatedAt());
            return out;
        }).orElseThrow(RuntimeException::new);
    }

}
