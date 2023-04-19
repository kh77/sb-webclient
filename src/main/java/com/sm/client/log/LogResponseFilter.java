package com.sm.client.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class LogResponseFilter implements ExchangeFilterFunction {

    private final Logger logger = LoggerFactory.getLogger(LogResponseFilter.class);

    @Override
    public ExchangeFilterFunction andThen(ExchangeFilterFunction afterFilter) {
        return ExchangeFilterFunction.super.andThen(afterFilter);
    }

    @Override
    public ExchangeFunction apply(ExchangeFunction exchange) {
        return ExchangeFilterFunction.super.apply(exchange);
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        logger.info("Request URI: {}", request.url());
        logger.info("Request Method: {}", request.method());
        logger.info("Request Headers: {}", request.headers());


        return next.exchange(request).flatMap(response -> {
            logger.info("Response Status Code: {}", response.statusCode());
            logger.info("Response Headers: {}", response.headers().asHttpHeaders());

            return response.bodyToMono(String.class).flatMap(responseBody -> {
                logger.info("Response Body: {}", responseBody);

                byte[] bytes = responseBody.getBytes(Charset.defaultCharset());
                DataBuffer restoredBuffer = new DefaultDataBufferFactory().wrap(bytes);

                // Release the original response body to prevent memory leaks
                response.bodyToMono(Void.class).subscribe();

                // Return a new ClientResponse with the restored response body
                return Mono.just(ClientResponse.create(response.statusCode()).headers(headers -> headers.putAll(response.headers().asHttpHeaders())).body(Flux.just(restoredBuffer)).build());
            }).onErrorResume(throwable -> {
                // Log response exceptions
                if (throwable instanceof WebClientResponseException) {
                    WebClientResponseException responseException = (WebClientResponseException) throwable;
                    HttpStatus statusCode = responseException.getStatusCode();
                    String responseBody = new String(responseException.getResponseBodyAsByteArray(), Charset.defaultCharset());
                    HttpHeaders responseHeaders = responseException.getHeaders();
                    logger.error("Response Exception - Status Code: {}, Headers: {}, Body: {}", statusCode, responseHeaders, responseBody);
                } else {
                    logger.error("Response Exception: {}", throwable.getMessage(), throwable);
                }
                return Mono.error(throwable);
            });
        });
    }
}
