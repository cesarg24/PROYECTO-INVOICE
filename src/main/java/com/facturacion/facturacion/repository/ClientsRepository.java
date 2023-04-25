package com.facturacion.facturacion.repository;

import com.facturacion.facturacion.entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Optional<Clients> findByNumerodocumento(String numdoc);

    boolean existsByNumerodocumento(String numdoc);
}
