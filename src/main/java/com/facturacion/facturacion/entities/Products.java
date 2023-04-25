package com.facturacion.facturacion.entities;
/* Realizado por Cesar Grijalva */
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="products")
public class Products {

    /*Las siguientes variables representan los campos de la tabla products en la BDD*/
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String code;
    private Long stock;
    private Double price;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    private List<InvoiceDetails> lst_invoiceDetails;


    public Products() {}

    public Products(Long id, String description, String code, Long stock, Double price) {
        this.id = id;
        this.description = description;
        this.code = code;
        this.stock = stock;
        this.price = price;
    }
}
