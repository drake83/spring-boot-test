package org.example.aspectj.model;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RestConnectorRequest<INPUT> extends BaseConnectorRequest {
    private HttpHeaders httpHeaders;
    private INPUT payload;
    private Map<String, String> params = new HashMap();
    private MultiValueMap<String, String> queryParams = new LinkedMultiValueMap();
    private HttpMethod method;
    private String dynamicUri;
    private HttpMethod dynamicMethod;
    private String username;
    private String password;

    public RestConnectorRequest() {
    }

    public RestConnectorRequest(HttpHeaders httpHeaders, @Nullable INPUT payload, Map<String, String> params, Map<String, String> queryParams) {
        this.httpHeaders = httpHeaders;
        this.payload = payload;
        this.params = params;
        this.queryParams = this.toMultiValueMap(queryParams);
    }

    /** @deprecated */
    @Deprecated
    public RestConnectorRequest(HttpHeaders httpHeaders, @Nullable INPUT payload, Map<String, String> params, Map<String, String> queryParams, HttpMethod method) {
        this.httpHeaders = httpHeaders;
        this.payload = payload;
        this.params = params;
        this.method = method;
        this.queryParams = this.toMultiValueMap(queryParams);
    }

    /** @deprecated */
    @Deprecated
    public RestConnectorRequest(HttpHeaders httpHeaders, @Nullable INPUT payload, Map<String, String> params, MultiValueMap<String, String> queryParams, HttpMethod method) {
        this.httpHeaders = httpHeaders;
        this.payload = payload;
        this.params = params;
        this.method = method;
        this.queryParams = queryParams;
    }

    public RestConnectorRequest(HttpHeaders httpHeaders, @Nullable INPUT payload, Map<String, String> params, MultiValueMap<String, String> queryParams) {
        this.httpHeaders = httpHeaders;
        this.payload = payload;
        this.params = params;
        this.queryParams = queryParams;
    }

    public HttpHeaders getHttpHeaders() {
        return this.httpHeaders;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public INPUT getRequest() {
        return this.payload;
    }

    public void setRequest(INPUT request) {
        this.payload = request;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParams(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap();
        }

        this.params.put(key, value);
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    /** @deprecated */
    @Deprecated
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getQueryParams() {
        return this.queryParams == null ? null : this.queryParams.toSingleValueMap();
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = this.toMultiValueMap(queryParams);
    }

    public void setQueryParams(MultiValueMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public MultiValueMap<String, String> getQueryParamsMultiValue() {
        return this.queryParams;
    }

    public String getDynamicUri() {
        return this.dynamicUri;
    }

    public void setDynamicUri(String dynamicUri) {
        this.dynamicUri = dynamicUri;
    }

    public HttpMethod getDynamicMethod() {
        return this.dynamicMethod;
    }

    public void setDynamicMethod(HttpMethod dynamicMethod) {
        this.dynamicMethod = dynamicMethod;
    }

    public void addHeader(String key, String value) {
        if (this.httpHeaders == null) {
            this.httpHeaders = new HttpHeaders();
        }

        this.httpHeaders.add(key, value);
    }

    public boolean addHeaderIfExists(String key, String value) {
        if (this.httpHeaders == null) {
            this.httpHeaders = new HttpHeaders();
        }

        if (value != null) {
            this.httpHeaders.add(key, value);
            return true;
        } else {
            return false;
        }
    }

    private MultiValueMap<String, String> toMultiValueMap(Map<String, String> map) {
        if (map == null) {
            return null;
        } else {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.setAll(map);
            return multiValueMap;
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
