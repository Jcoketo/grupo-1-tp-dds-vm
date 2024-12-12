package modelo.personas;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class Documento {

    @Getter @Setter private String numeroDoc;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private TipoDocumento tipoDoc;

    public Documento(String nro_doc, TipoDocumento tipo_doc){
        this.numeroDoc = nro_doc;
        this.tipoDoc = tipo_doc;
    }


    public Documento (){

    }

    public String getUniqueIdentifier(){
        return this.tipoDoc + this.numeroDoc;
    }
}