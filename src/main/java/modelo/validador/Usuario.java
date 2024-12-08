package modelo.validador;

import accessManagment.Roles;
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
    private String username;

    @Setter
    @Getter
    @Column
    private String hashedPassword;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private Roles rol;

    public Usuario(String mail, String username, String password) {
        this.mail = mail;
        this.hashedPassword = password;
        this.username = username;
    }

    public Usuario() {

    }
}