package com.facturacion.facturacion.service;

import com.facturacion.facturacion.entities.Clients;
import com.facturacion.facturacion.entities.Invoice;
import com.facturacion.facturacion.entities.InvoiceDetails;
import com.facturacion.facturacion.entities.Products;
import com.facturacion.facturacion.exception.*;
import com.facturacion.facturacion.request.*;
import com.facturacion.facturacion.repository.ClientsRepository;
import com.facturacion.facturacion.repository.InvoiceDetailsRepository;
import com.facturacion.facturacion.repository.InvoiceRepository;
import com.facturacion.facturacion.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.SchemaOutputResolver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ProductsRepository productsRepository;

       public Invoice create(@NotNull InvoiceRequest request) throws Exception {

        Clients client = clientsRepository.findById(request.getClientId()).orElseThrow(() -> new ClientsNotFoundException("Cliente no encontrado"));

        Products productoss = new Products();

        Invoice invoice = new Invoice();
        invoice.setClients(client);
        invoice.setCreated_at(LocalDateTime.now());

           // El siguiente código permite obtener la fecha del servicio externo, pero al ser inestable dicho
           // servicio se ha optado por comentarlo, ya que ralentiza las peticiones.
        /*   RestTemplate restTemplate = new RestTemplate();
           String url = "http://worldclockapi.com/api/json/utc/now/";
           try {
               Map<String, Object> result = restTemplate.getForObject(url, Map.class);
               String dateString = (String) result.get("currentDateTime");
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
               LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
               invoice.setClients(client);
               invoice.setCreated_at(dateTime);
           } catch (Exception e) {
               System.out.println("Error al obtener la fecha del servicio externo: " + e.getMessage());
               invoice.setClients(client);
               invoice.setCreated_at(LocalDateTime.now());
           } */

        List<InvoiceRequest.InvoiceDetail> details = new ArrayList<>();
        for (InvoiceRequest.InvoiceDetail detailRequest : request.getDetailsList()) {
            Products product = productsRepository.findById(detailRequest.getProductId()).orElseThrow(() -> new ProductsNotFoundException("Producto no encontrado"));
            InvoiceRequest.InvoiceDetail detail = new InvoiceRequest.InvoiceDetail();
            detail.setProductId(product.getId());
            detail.setAmounts(detailRequest.getAmounts());
            details.add(detail);
        }

        invoice.setTotal(0.0); // inicializamos en cero el total de la factura
        int acumulador =  0; // inicializamos el acumulador en cero, para luego calcular la cantidad de productos que se venden
        // Agregamos los detalles de la factura
        List<InvoiceDetails> invoiceDetails = new ArrayList<>();

        for (InvoiceRequest.InvoiceDetail detailRequest : request.getDetailsList()) {
            Products product = productsRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new ProductsNotFoundException("Producto no encontrado"));
            Long amounts = detailRequest.getAmounts();
            double price = product.getPrice();
            double detailTotal = amounts * price;

            // Verificamos que hay suficiente stock del producto para la venta
            if (product.getStock() < amounts) {
                throw new InsufficientStockException("Stock insuficiente para el producto con ID " + product.getId());
            }

            // Actualizamos el stock del producto
            product.setStock(product.getStock() - amounts);
            productsRepository.save(product);


            // Creamos el detalle de la factura y lo agregamos a la lista
             InvoiceDetails invoiceDetail = new InvoiceDetails();

            invoiceDetail.setProducts(product);
            invoiceDetail.setAmounts(amounts);
            invoiceDetail.setPrice(price);
            invoiceDetails.add(invoiceDetail);

             // Actualizamos el total de la factura
            invoice.setTotal(invoice.getTotal() + detailTotal);
            acumulador = acumulador + amounts.intValue();
            System.out.println("CANT. " + amounts + " " + product.getDescription() + " EN: " + invoice.getTotal() + " TOTAL EN STOCK: " + product.getStock());

             Long invoice_id = invoice.getId();
            if(invoice_id==null){
                invoiceRepository.save(invoice);
            }
            invoiceDetail.setInvoice(invoice);
            invoiceDetailsRepository.save(invoiceDetail);
        }

        System.out.println("SE VENDIERON UN TOTAL DE " + acumulador + " PRODUCTOS");
        System.out.println("PRECIO TOTAL DE LA VENTA ES: " + invoice.getTotal());

           List<InvoiceRequest.InvoiceProduct> prod = new ArrayList<>();
           for (InvoiceRequest.InvoiceDetail detailRequest : request.getDetailsList()) {
               // Obtenemos el producto de la base de datos
               Products product = productsRepository.findById(detailRequest.getProductId())
                       .orElseThrow(() -> new ProductsNotFoundException("Producto no encontrado"));

               // Creamos el objeto InvoiceProduct con la información del producto
               InvoiceRequest.InvoiceProduct productInfo = new InvoiceRequest.InvoiceProduct();
               productInfo.setDescription(product.getDescription());
               productInfo.setCode(product.getCode());
               productInfo.setStock(product.getStock());
               productInfo.setPrice(product.getPrice());

               // Agregamos el objeto InvoiceProduct a la lista
               prod.add(productInfo);
           }
           List<InvoiceDetails> invoiceDetails2 = convertToInvoiceDetails(prod);
           invoice.setLst_invoiceDetails(invoiceDetails2);

        return invoice;
    }

    private List<InvoiceDetails> convertToInvoiceDetails(List<InvoiceRequest.InvoiceProduct> products) throws ProductsNotFoundException {
        List<InvoiceDetails> invoiceDetails = new ArrayList<>();

        for (InvoiceRequest.InvoiceProduct product : products) {
          Products productEntity = productsRepository.findByCode(product.getCode()).orElseThrow(() -> new ProductsNotFoundException("Producto no encontrado"));

            // Creamos el detalle de la factura y lo agregamos a la lista
            InvoiceDetails invoiceDetail = new InvoiceDetails();
            invoiceDetail.setProducts(productEntity);
            invoiceDetails.add(invoiceDetail);
        }

        return invoiceDetails;
    }

    public Invoice update(Invoice newInvoice, Long id) throws Exception {
        Optional<Invoice> invoiceOp = invoiceRepository.findById(id);

        if (invoiceOp.isEmpty()) {
            throw new Exception("No se encontró la factura con el id " + id);
        }

        if (invoiceOp.isPresent()) {
            Invoice invoice = invoiceOp.get();
            invoice.setId(newInvoice.getClients().getId());
            invoice.setClients(newInvoice.getClients());
            invoice.setCreated_at(newInvoice.getCreated_at());
            invoice.setTotal(newInvoice.getTotal());
            return invoiceRepository.save(invoice);
        } else {
            throw new Exception("No se puede actualizar Invoice porque no se encuentra en la base de datos");
        }
    }

    public Invoice findById(Long id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<Invoice> invoiceOp = this.invoiceRepository.findById(id);

        if (invoiceOp.isEmpty()){
            log.info("La factura con el id brindado no existe en la base de datos : " + id);
            throw new InvoiceNotFoundException("La factura que intenta solicitar no existe");
        }else {
            return invoiceOp.get();
        }
    }

    public List<Invoice> findAll(){
        return this.invoiceRepository.findAll();
    }

}
