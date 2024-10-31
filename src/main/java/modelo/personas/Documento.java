package modelo.personas;

import javax.persistence.*;

@Embeddable
public class Documento {

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

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Documento (){

    }

}


