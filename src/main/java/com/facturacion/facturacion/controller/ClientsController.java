package com.facturacion.facturacion.controller;


import com.facturacion.facturacion.entities.Clients;
import com.facturacion.facturacion.exception.ClientsAlreadyExistException;
import com.facturacion.facturacion.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/cliente")
public class ClientsController {

    @Autowired
    private ClientsService clienteService;

   @PostMapping(path = "/")
    public ResponseEntity<Clients> create(@RequestBody Clients clients) throws ClientsAlreadyExistException {
        return  new ResponseEntity<>(this.clienteService.create(clients), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Clients> update(@RequestBody Clients clients, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.clienteService.update(clients,id), HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Clients> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.clienteService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Clients>> findAll(){
        return new ResponseEntity<>(this.clienteService.findAll(), HttpStatus.OK);
    }

}
