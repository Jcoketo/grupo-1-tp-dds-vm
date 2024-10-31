package modelo.personas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Documento {

    @Id
    @GeneratedValue
    private int id;

    private String numeroDoc;
    private TipoDocumento tipoDoc;

    public Documento(String nro_doc, TipoDocumento tipo_doc){
        this.numeroDoc = nro_doc;
        this.tipoDoc = tipo_doc;
    }

    public String getUniqueIdentifier(){
        return this.tipoDoc + this.numeroDoc;
    }
}


