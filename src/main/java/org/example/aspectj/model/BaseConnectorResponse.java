package org.example.aspectj.model;

public abstract class BaseConnectorResponse<O> {
    public BaseConnectorResponse() {
    }

    public abstract O getResult();
}
