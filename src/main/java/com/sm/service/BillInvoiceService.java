package com.sm.service;

import com.sm.api.InvoiceRequest;
import com.sm.api.InvoiceResponse;
import com.sm.client.service.InvoiceWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillInvoiceService {

    private final InvoiceWebClientService invoiceWebClientService;

    public InvoiceResponse createInvoice(InvoiceRequest request) {
        return invoiceWebClientService.createInvoice(request);
    }

    public InvoiceResponse readInvoice(Long invoiceId) {
        return invoiceWebClientService.readInvoice(invoiceId);
    }

    public InvoiceResponse updateInvoice(Long invoiceId, InvoiceRequest request) {
        return invoiceWebClientService.updateInvoice(invoiceId, request);
    }

    public void deleteInvoice(Long invoiceId) {
        invoiceWebClientService.deleteInvoice(invoiceId);
    }

    public Mono<InvoiceResponse> createInvoiceAsync(InvoiceRequest request) {
        return invoiceWebClientService.createInvoiceAsync(request);
    }

    public Mono<InvoiceResponse> readInvoiceAsync(Long invoiceId) {
        return invoiceWebClientService.readInvoiceAsync(invoiceId);
    }

    public Mono<InvoiceResponse> updateInvoiceAsync(Long invoiceId, InvoiceRequest request) {
        return invoiceWebClientService.updateInvoiceAsync(invoiceId, request);
    }

    public Mono<Void> deleteInvoiceAsync(Long invoiceId) {
        return invoiceWebClientService.deleteInvoiceAsync(invoiceId);
    }

    public Flux<InvoiceResponse> findAllInvoiceAsync() {
        return invoiceWebClientService.findAllInvoiceAsync();
    }

    public List<InvoiceResponse> findAllInvoice() {
        return invoiceWebClientService.findAllInvoice();
    }
}
