package com.facturacion.facturacion.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name="invoice_details")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoice_detail_id;
    //private Long invoice_id;
    private Long amounts;
    //private Long product_id;
    private Double price;

   //Muchos detalles de factura tiene una factura
    @ManyToOne
    @JoinColumn(name="invoice_id")
    private Invoice invoice;

    //Muchos detalles de factura tiene un producto
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

}
