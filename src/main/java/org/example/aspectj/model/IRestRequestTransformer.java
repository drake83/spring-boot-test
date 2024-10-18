package org.example.aspectj.model;

public interface IRestRequestTransformer<INPUT, DTO> {
    RestConnectorRequest<DTO> transform(INPUT var1, Object... var2);
}
