package modelo.personas;

import javax.persistence.*;

@Entity
@Table
public class Documento {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String numeroDoc;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDoc;

    public Documento(String nro_doc, TipoDocumento tipo_doc){
        this.numeroDoc = nro_doc;
        this.tipoDoc = tipo_doc;
    }

    public String getUniqueIdentifier(){
        return this.tipoDoc + this.numeroDoc;
    }
}


