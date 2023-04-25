package com.facturacion.facturacion.controller;

import com.facturacion.facturacion.entities.Invoice;
import com.facturacion.facturacion.request.InvoiceRequest;
import com.facturacion.facturacion.service.InvoiceService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/* Realizado por Cesar Grijalva */

@RestController
@RequestMapping(path = "/api/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;


    @PostMapping(path = "/crearfact")
    public ResponseEntity<Invoice> create(@RequestBody @NotNull InvoiceRequest request) throws Exception {
        return new ResponseEntity<>(this.invoiceService.create(request), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Invoice> update(@RequestBody Invoice invoice, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.invoiceService.update(invoice,id), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.invoiceService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Invoice>> findAll(){
        return new ResponseEntity<>(this.invoiceService.findAll(), HttpStatus.OK);
    }

}
