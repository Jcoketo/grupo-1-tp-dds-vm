package modelo.autenticacion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(unique = true)
    private String mail;

    @Setter
    @Getter
    @Column
    private String Usuario;

    @Setter
    @Getter
    @Column
    private String hashedPassword;


    public Usuario(String mail, String hashedPassword) {
        this.mail = mail;
        this.hashedPassword = hashedPassword;
    }

}