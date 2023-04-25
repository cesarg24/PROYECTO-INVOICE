package com.facturacion.facturacion.entities;
/* Realizado por Cesar Grijalva */
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="clients")
public class Clients {

   /*Las siguientes variables representan los campos de la tabla clients en la BDD
   * @GeneratedValue(strategy = GenerationType.IDENTITY) para que gestione el ID la BDD */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id")
    private Long id;

    @Column(name="name")
    private String nombre;

    @Column(name="lastname")
    private String lastname;

    @Column(name="docnumber")
    private String numerodocumento;

    @OneToMany(mappedBy = "clients")
    @JsonIgnore
    private List<Invoice> lst_invoice;

 public Clients() {}

 public Clients(Long id, String nombre, String lastname, String numerodocumento) {
  this.id = id;
  this.nombre = nombre;
  this.lastname = lastname;
  this.numerodocumento = numerodocumento;
 }
}
