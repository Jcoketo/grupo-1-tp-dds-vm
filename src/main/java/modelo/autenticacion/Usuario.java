package modelo.autenticacion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Getter @Setter
    private String mail;

    @Column
    @Getter @Setter
    private String hashedPassword;

    // Getters y setters
}