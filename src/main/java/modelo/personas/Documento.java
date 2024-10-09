package modelo.personas;

public class Documento {
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


