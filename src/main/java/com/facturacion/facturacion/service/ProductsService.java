package com.facturacion.facturacion.service;

import com.facturacion.facturacion.entities.Clients;
import com.facturacion.facturacion.entities.Products;
import com.facturacion.facturacion.exception.ProductsAlreadyExistException;
import com.facturacion.facturacion.exception.ProductsNotFoundException;
import com.facturacion.facturacion.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public Products create(Products newProducto) throws ProductsAlreadyExistException {
        if(newProducto.getDescription().isEmpty() || newProducto.getCode().isEmpty() || ((newProducto.getStock()<=0) || (newProducto.getPrice()<=0)))
        {
            log.info("No se cumple con los requerimientos, campos vacíos o valores menores a cero");
            throw new IllegalArgumentException("No se cumple con los requerimientos, campos vacíos o valores menores a cero");
        }

        Optional<Products> productoOp = this.productsRepository.findByCode(newProducto.getCode());

        if (productoOp.isPresent()){
            log.info("El producto que intenta agregar ya existe en la base de datos : " + newProducto);
            throw new ProductsAlreadyExistException("El producto que intenta agregar ya existe en la BDD");
        }else {
            return this.productsRepository.save(newProducto);
        }
    }

    /* Este método fue creado con fines didácticos*/
    public void delete(Products newProductos, Long id) throws  Exception{
        log.info("ID INGRESADO : " + id);
        if (id <= 0){
            throw new Exception("El id brindado no es valido");
        }
        Optional<Products> productOp = this.productsRepository.findById(id);

        if (productOp.isEmpty()){
            log.info("El producto que intenta eliminar no existe en la base de datos : " + newProductos);
            throw new ProductsNotFoundException("El producto que intenta eliminar no existe en la base de datos");
        }else{
            log.info("producto encontrado para ser eliminado");
            try {
                this.productsRepository.deleteById(id);
            }catch (DataIntegrityViolationException e){
                log.error("Violación de integridad referencial al eliminar el producto con ID " + id);
                throw new Exception("El producto no puede ser eliminado debido a que existen registros relacionados en la tabla invoice_details, si desea eliminar elimine primero todos los registros en la tabla invoice_details relacionados con el producto con ID " + id);
            }
        }
    }
    public Products update(Products newProductos, Long id) throws Exception {
        log.info("ID INGRESADO : " + id);
        if (id <= 0){
            throw new Exception("El id brindado no es valido");
        }

        Optional<Products> productOp = this.productsRepository.findById(id);

        if (productOp.isEmpty()){
            log.info("El producto que intenta modificar no existe en la base de datos : " + newProductos);
            throw new ProductsNotFoundException("El producto que intenta modificar no existe en la base de datos");
        }else {
            log.info("el producto fue encontrado");
            Products productoBd = productOp.get();

            productoBd.setDescription(newProductos.getDescription());
            productoBd.setCode(newProductos.getCode());
            productoBd.setStock(newProductos.getStock());
            productoBd.setPrice(newProductos.getPrice());

            log.info("producto actualizado : " + productoBd);

            return this.productsRepository.save(productoBd);
        }
    }
    public Products findById(Long id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<Products> productOp = this.productsRepository.findById(id);

        if (productOp.isEmpty()){
            log.info("El producto con el id brindado no existe en la base de datos : " + id);
            throw new ProductsNotFoundException("El producto que intenta solicitar no existe");
        }else {
            Products  products = productOp.get();
            String description = products.getDescription();
            String code = products.getCode();
            Long stock = products.getStock();
            Double price = products.getPrice();
            return new Products(id, description , code, stock, price);
            //return productOp.get();
        }
    }

    public List<Products> findAll(){
        return this.productsRepository.findAll();
    }

}