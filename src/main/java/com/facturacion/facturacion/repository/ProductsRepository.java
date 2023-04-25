package com.facturacion.facturacion.repository;

import com.facturacion.facturacion.entities.Invoice;
import com.facturacion.facturacion.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findByCode(String code);

    Optional<Products> findById(Long id);
}
