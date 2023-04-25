package com.facturacion.facturacion.repository;

import com.facturacion.facturacion.entities.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {


    Optional<InvoiceDetails> findById(Long id);
}
