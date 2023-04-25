package com.facturacion.facturacion.controller;

import com.facturacion.facturacion.entities.InvoiceDetails;
import com.facturacion.facturacion.exception.InvoiceDetailsAlreadyExistException;
import com.facturacion.facturacion.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/* ELIMNAR */
@RestController
@RequestMapping(path = "/api/invoice_details")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @PostMapping(path = "/")
    public ResponseEntity<InvoiceDetails> create(@RequestBody InvoiceDetails invoice_details) throws InvoiceDetailsAlreadyExistException {
        return  new ResponseEntity<>(this.invoiceDetailsService.create(invoice_details), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDetails> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.invoiceDetailsService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<InvoiceDetails>> findAll(){
        return new ResponseEntity<>(this.invoiceDetailsService.findAll(), HttpStatus.OK);
    }

}
