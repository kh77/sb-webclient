package com.sm.controller;

import com.sm.api.InvoiceRequest;
import com.sm.api.InvoiceResponse;
import com.sm.service.BillInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillInvoiceController {

    private final BillInvoiceService billInvoiceService;

    @GetMapping("/{id}")
    public InvoiceResponse findById(@PathVariable Long id) {
        return billInvoiceService.readInvoice(id);
    }

    @PostMapping
    public InvoiceResponse create(@RequestBody InvoiceRequest request) {
        return billInvoiceService.createInvoice(request);
    }

    @PutMapping("/{id}")
    public InvoiceResponse update(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        request.setId(id);
        return billInvoiceService.updateInvoice(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        billInvoiceService.deleteInvoice(id);
    }

    @PostMapping("/async")
    public Mono<InvoiceResponse> asyncCreate(@RequestBody InvoiceRequest request) {
        return billInvoiceService.createInvoiceAsync(request);
    }

    @GetMapping("/async/{id}")
    public Mono<InvoiceResponse> asycFindById(@PathVariable Long id) {
        return billInvoiceService.readInvoiceAsync(id);
    }

    @PutMapping("/async/{id}")
    public Mono<InvoiceResponse> asyncUpdate(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        request.setId(id);
        return billInvoiceService.updateInvoiceAsync(id, request);
    }

    @DeleteMapping("/async/{id}")
    public void asyncDelete(@PathVariable Long id) {
        billInvoiceService.deleteInvoiceAsync(id);
    }
}

