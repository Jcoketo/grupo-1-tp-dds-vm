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

    @Column
    @Getter @Setter
    private Boolean bajaLogica = Boolean.FALSE;

    public Usuario(String mail, String username, String password, Roles rol) {
        this.mail = mail;
        this.hashedPassword = password;
        this.username = username;
        this.rol = rol;
    }
    public Usuario(String mail, String password, Roles rol) {
        this.mail = mail;
        this.hashedPassword = password;
        this.rol = rol;
    }

    public Usuario() {

    }
}