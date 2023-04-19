package com.sm.client.log;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

public class LogReqBodyClientHttpConnectorDecorator implements ClientHttpConnector {

    private final ClientHttpConnector delegate;

    public LogReqBodyClientHttpConnectorDecorator(ClientHttpConnector delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<ClientHttpResponse> connect(HttpMethod httpMethod,
                                            URI uri,
                                            Function<? super ClientHttpRequest, Mono<Void>> callback) {
        return this.delegate.connect(httpMethod, uri, clientHttpRequest ->
                callback.apply(new LogReqBodyClientHttpRequestDecorator(clientHttpRequest)));
    }
}