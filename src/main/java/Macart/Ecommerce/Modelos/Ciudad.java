package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private String codigoPostal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Ciudad_id")
    private Departamento departamento;
}
