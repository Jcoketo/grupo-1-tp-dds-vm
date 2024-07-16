package pruebas;

import importador.CargarCSV;
import importador.ProcesarCSV;
import importador.RegistroLeido;
import personas.PersonaJuridica;
import repositorios.RepositorioArchivos;

import java.io.IOException;
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


        /* ----------- PROBAR RECOMENDADOR DE PUNTOS ----------- */
        //recomendadorDePuntos.RecomendadorDePuntos recomendador = recomendadorDePuntos.RecomendadorDePuntos.getInstancia();
        //System.out.println(recomendador.obtenerPuntosRecomendados(-34.603722, -58.381592, 1000.0));

        /* ----------- PROBAR CSV ----------- */


        CargarCSV.CargarSCV();

        // en caso de enviarle por parametro el path
        //CargarCSV.cargarCSV("src/main/resources/cargaMasivaNuevosUsuarios.csv");

        RepositorioArchivos repositorio = RepositorioArchivos.getInstancia();

        List<RegistroLeido> registros = repositorio.tomarPorIndice(0);

        ProcesarCSV.ProcesarCSV(registros);

        repositorio.cambiarEstadoAProcesado(registros);


    }
}