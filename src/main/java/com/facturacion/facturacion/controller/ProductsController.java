package com.facturacion.facturacion.controller;

import com.facturacion.facturacion.entities.Products;
import com.facturacion.facturacion.exception.ProductsAlreadyExistException;
import com.facturacion.facturacion.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/producto")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @PostMapping(path = "/")
    public ResponseEntity<Products> create(@RequestBody Products producto) throws ProductsAlreadyExistException {
        return  new ResponseEntity<>(this.productsService.create(producto), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Products> update(@RequestBody Products producto, @PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.productsService.update(producto,id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Products> delete(@RequestBody Products producto, @PathVariable Long id) throws Exception {
        this.productsService.delete(producto,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Products> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(this.productsService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Products>> findAll(){
        return new ResponseEntity<>(this.productsService.findAll(), HttpStatus.OK);
    }


}
