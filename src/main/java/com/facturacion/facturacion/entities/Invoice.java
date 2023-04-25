package com.facturacion.facturacion.entities;
/* Realizado por Cesar Grijalva */
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name="invoice")
public class Invoice {

    /*Las siguientes variables representan los campos de la tabla invoice en la BDD*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="invoice_id")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created_at;

    private Double total;
    /*muchas facturas tiene un cliente*/
    @ManyToOne
    @JoinColumn(name="client_id")
    private Clients clients;
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetails> lst_invoiceDetails;

}
