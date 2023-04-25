package com.facturacion.facturacion.service;

import com.facturacion.facturacion.entities.InvoiceDetails;
import com.facturacion.facturacion.exception.InvoiceDetailsAlreadyExistException;
import com.facturacion.facturacion.exception.InvoiceDetailsNotFoundException;
import com.facturacion.facturacion.repository.InvoiceDetailsRepository;
import com.facturacion.facturacion.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceDetails create(InvoiceDetails newInvoiceDetails) throws InvoiceDetailsAlreadyExistException {

          Optional<InvoiceDetails> invoicedetailsOp = this.invoiceDetailsRepository.findById(newInvoiceDetails.getInvoice().getId());

        if (invoicedetailsOp.isPresent()){
            log.info("La factura que intenta agregar ya existe en la base de datos : " + newInvoiceDetails);
            throw new InvoiceDetailsAlreadyExistException("La factura que intenta agregar ya existe en la BDD");
        }else {
            return this.invoiceDetailsRepository.save(newInvoiceDetails);
        }
    }

    public InvoiceDetails findById(Long id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<InvoiceDetails> invoicedetailsOp = this.invoiceDetailsRepository.findById(id);

        if (invoicedetailsOp.isEmpty()){
            log.info("El detalle de factura con el id brindado no existe en la base de datos : " + id);
            throw new InvoiceDetailsNotFoundException("El detalle de factura que intenta solicitar no existe");
        }else {
            return invoicedetailsOp.get();
        }
    }

    public List<InvoiceDetails> findAll(){
        return this.invoiceDetailsRepository.findAll();
    }

}
