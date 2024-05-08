package pruebas;

import importador.ImportCSV;
import personas.Colaborador;
import enums.MedioContacto;
import Notificador.Notificador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class probando {
    public static void main(String[] args) throws IOException {

        /* ----------- PROBAR NOTIFICAR ----------- */
        /*
        // Crear un colaborador de prueba
        List<MedioContacto> contactos = new ArrayList<>();
        contactos.add(MedioContacto.MAIL);
        // si comentamos la linea de arriba deberia haber una excepcion

        Colaborador colaborador = new Colaborador(contactos,
                                "Calle Falsa 123",
                                    "gsantucho@frba.utn.edu.ar");
        // Crear un mensaje de prueba
        String mensaje = "puto el que lee.";

        //hay que ver si queremos que la clase notiticador tenga el metodo notificar estatico o no
        Notificador notificador = new Notificador();
        // Llamar al método notificar
        notificador.notificar(mensaje, colaborador);
        */

        /* ----------- PROBAR IMPORTAR SCV ----------- */
        ImportCSV.importCSV();

    }
}