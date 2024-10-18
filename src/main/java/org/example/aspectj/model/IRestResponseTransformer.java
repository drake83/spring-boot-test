package org.example.aspectj.model;

public interface IRestResponseTransformer<RESOURCE, OUTPUT> {
    OUTPUT transform(RestConnectorResponse<RESOURCE> var1);
}
