package pruebas;

import importador.CargarCSV;
import importador.ProcesarCSV;
import importador.RegistroLeido;
import personas.PersonaJuridica;
import repositorios.RepositorioArchivos;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


        //CargarCSV.CargarSCV();

        // en caso de enviarle por parametro el path
        //CargarCSV.cargarCSV("src/main/resources/cargaMasivaNuevosUsuarios.csv");

        //RepositorioArchivos repositorio = RepositorioArchivos.getInstancia();

        //List<RegistroLeido> registros = repositorio.tomarPorIndice(0);

        //ProcesarCSV.ProcesarCSV(registros);

        ///repositorio.cambiarEstadoAProcesado(registros);


        /* ----------- PRUEBA DE CRONE TASK ----------- */
        /*
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        System.out.println("Permitiendo ingreso de viandas por 3 horas...");
        scheduler.schedule(() -> {
            System.out.println("El tiempo de ingreso de viandas ha terminado.");
            scheduler.shutdown();
            // Aquí puedes agregar la lógica que debe ejecutarse después de 3 horas
        }, 3, TimeUnit.SECONDS);
        */

        //System.out.println (UUID.randomUUID().toString().toUpperCase().subSequence(0,11));

        // genera una prueba para verificar el beetwen de la fecha de solicitud y la fecha de apertura
        LocalDateTime start = LocalDateTime.of(2023, 10, 1, 14, 30);
        LocalDateTime end = LocalDateTime.of(2023, 10, 2, 18, 45);

        System.out.println(Duration.between(start, end).toHours());


    }
}
