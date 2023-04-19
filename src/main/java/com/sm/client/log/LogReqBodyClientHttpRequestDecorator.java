package com.sm.client.log;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class LogReqBodyClientHttpRequestDecorator extends ClientHttpRequestDecorator {
    private final Logger logger = LoggerFactory.getLogger(LogReqBodyClientHttpRequestDecorator.class);

    public LogReqBodyClientHttpRequestDecorator(ClientHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        Mono<DataBuffer> buffer = Mono.from(body);
        return super.writeWith(buffer.doOnNext(dataBuffer -> {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                Channels.newChannel(baos).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                logger.debug("Request Body" + baos.toString(StandardCharsets.UTF_8));
                super.getHeaders();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error applying filter to response");
            }
        }));
    }
}