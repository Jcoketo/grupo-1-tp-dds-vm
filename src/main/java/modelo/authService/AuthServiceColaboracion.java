package modelo.authService;

import modelo.colaboracion.*;
import modelo.consumosAPIs.api_direccion.PuntoEstrategicoXdireccion;
import modelo.consumosAPIs.servicioGeoLocalizacion.LatLong;
import modelo.consumosAPIs.servicioGeoLocalizacion.LocalizadorLatLong;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import modelo.elementos.TarjetaPlastica;
import modelo.excepciones.ExcepcionValidacion;
import modelo.notificador.Notificador;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.Persona;
import modelo.personas.PersonaJuridica;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioOfertas;
import persistencia.RepositorioTarjetas;

import java.net.DatagramPacket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceColaboracion {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioTarjetas repoTarjetas = RepositorioTarjetas.getInstancia();
    private static RepositorioOfertas reposOfertas = RepositorioOfertas.getInstancia();

    public static void registrarColaboracionDinero(Integer idPersona, String monto, FrecuenciaDonacion freq){
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);
        DonarDinero donacion = new DonarDinero(LocalDate.now(), Double.parseDouble(monto), freq);
        donacion.hacerColaboracion(colab);
        repoColab.nuevaColaboracion(colab, donacion);
    }

    public static void registrarColaboracionVianda(Integer idPersona, Integer heladeraId, String comida, String fechaCaducidad, String pesoVianda, String calorias, LocalDateTime fechaDonacion) {
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);
        Heladera heladera = repoHeladeras.buscarHeladera(heladeraId);

        Integer caloriasAux = null;
        Integer pesoAux = null;
        if ( pesoVianda == null || pesoVianda.equals("") ){
            pesoAux = 0; }
        if ( calorias == null || calorias.equals("") ){
            caloriasAux = 0; }

        Vianda vianda = new Vianda(comida, LocalDate.parse(fechaCaducidad), LocalDate.now(), colab, heladera, caloriasAux, pesoAux);

        DonarVianda donacion = new DonarVianda(vianda, heladera, fechaDonacion.toLocalDate());
        donacion.hacerColaboracion(colab);
        repoHeladeras.actualizarHeladera(heladera);
        repoColab.nuevaColaboracion(colab, donacion);

    }

    public static void registrarColaboracionDistribuirViandas(Integer idPersona, Integer idHeladeraOrigen, Integer idHeladeraDestino, MotivoDistribucion motivoDistribucion, Integer cantidadViandas) {
        Heladera origen = repoHeladeras.buscarHeladera(idHeladeraOrigen);
        Heladera destino = repoHeladeras.buscarHeladera(idHeladeraDestino);
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);

        if (!destino.entranXViandasMas(cantidadViandas)){
            throw new ExcepcionValidacion("No hay espacio suficiente en la heladera de destino para esa cantidad de viandas!");
        }
        if (!origen.tieneNViandasDisponibles(cantidadViandas)){
            throw new ExcepcionValidacion("No hay suficientes viandas en la heladera de origen!");
        }

        List<Vianda> viandas = new ArrayList<>();
        for (int i = 0; i < cantidadViandas; i++){
            System.out.println(i + ' ');
            Vianda vianda = origen.conocerVianda(i);
            viandas.add(vianda);
        }

        DistribucionDeViandas distribucion = new DistribucionDeViandas(origen, destino, motivoDistribucion, LocalDate.now());
        distribucion.setViandas(viandas);
        distribucion.hacerColaboracion(colab);
        repoColab.persistirViandas(viandas);
        repoHeladeras.actualizarHeladera(origen);
        repoHeladeras.actualizarHeladera(destino);
        repoColab.nuevaColaboracion(colab, distribucion);
    }

    public static void registrarPersonasVulnerables(Integer idPersona, MedioDeContacto medioDeContacto) {
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);

        RegistroPersonasSituVulnerable colaboracion = repoColab.traerColaboradoresXColaboradorPersonaSitu(colab);

        if (colaboracion != null) {
            if ((colaboracion.getTarjetas().size() - colaboracion.getCantidadRepartida() > 0))
                throw new ExcepcionValidacion("Tienes tarjetas para repartir!");
        }

        List<TarjetaPlastica> tarjetas = repoTarjetas.crearNTarjetasPlasticas(2);

        RegistroPersonasSituVulnerable registroPersonasSituVulnerable = new RegistroPersonasSituVulnerable(2, tarjetas, LocalDate.now());

        colab.agregarColaboracion(registroPersonasSituVulnerable);

        repoColab.nuevaColaboracion(colab, registroPersonasSituVulnerable);

        Notificador.notificar("Has solicitado tarjetas para repartir .\n" +
                "Te las estaremos enviado en las proximas 72hs habiles. \n\n" +
                "Los numeros de tarjeta entregados son: \n" +
                "Nro. tarjeta: " + tarjetas.get(0).getNro_tarjeta() + "\n" +
                "Nro. tarjeta: "+ tarjetas.get(1).getNro_tarjeta(), "Tarjetas repartidas" ,medioDeContacto);
    }

    public static void registrarColaboracionRecompensa(Integer idPersona, String nombre,String descripcion,TipoOferta tipoOferta,Double puntos,String imagen) {

        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);

        PersonaJuridica persona = repoColab.traerPersonaPorIdJuridica(idPersona);

        Oferta oferta = new Oferta(nombre,descripcion,tipoOferta,persona.getRubro(),Boolean.TRUE,puntos,imagen);

        OfrecerRecompensa colaboracion = new OfrecerRecompensa(oferta);

        colaboracion.hacerColaboracion(colab);

        repoColab.nuevaColaboracion(colab, colaboracion);

    }

    public static void registrarHeladera(Integer idPersona, String nombre, String capacidad, Boolean activo, String direccion, LocalDate fechaInicio, Boolean checkBoxDireccion, Float tempMax, Float tempMin, Boolean esRecomendada, String direcRecomendada, String latRecomendada, String longRecomendada) {

        Colaborador colaborador = repoColab.buscarColaboradorXIdPersona(idPersona);
        PersonaJuridica persona = (PersonaJuridica) colaborador.getPersona();

        PuntoEstrategico puntoEstrategico = new PuntoEstrategico();

        if ( checkBoxDireccion ){
            String direcAux = colaborador.getPersona().getDireccion();
            if ( direcAux == null || direcAux.equals("") ){
                throw new ExcepcionValidacion("La dirección de la persona jurídica no fue cargada!");
            }
            puntoEstrategico.setDireccion(direcAux);
            LatLong latLong = LocalizadorLatLong.obtenerLatitudYLongitud(direcAux);
            puntoEstrategico.setLatitud(latLong.getLatitud());
            puntoEstrategico.setLongitud(latLong.getLongitud());
            puntoEstrategico.setAreas(LocalizadorLatLong.obtenerArea(direcAux));
        }else if ( esRecomendada ){
            Double lat = Double.parseDouble(latRecomendada);
            Double lon = Double.parseDouble(longRecomendada);
            puntoEstrategico.setDireccion(direcRecomendada);
            puntoEstrategico.setLatitud(lat);
            puntoEstrategico.setLongitud(lon);
            puntoEstrategico.setAreas(LocalizadorLatLong.obtenerArea(direcRecomendada));
        } else {
            puntoEstrategico.setDireccion(direccion);
            LatLong latLong = LocalizadorLatLong.obtenerLatitudYLongitud(direccion);
            puntoEstrategico.setLatitud(latLong.getLatitud());
            puntoEstrategico.setLongitud(latLong.getLongitud());
            puntoEstrategico.setAreas(LocalizadorLatLong.obtenerArea(direccion));
        }

        Heladera heladera = new Heladera(nombre, Integer.parseInt(capacidad), puntoEstrategico, activo, fechaInicio, tempMax, tempMin);
        HacerseCargoHeladera colaboracion = new HacerseCargoHeladera(heladera);
        persona.agregarHeladera(heladera);
        colaboracion.hacerColaboracion(colaborador);

        repoColab.actualizarColaborador(colaborador);

    }
}

/*
* CRONE TASK DIARIO
* que se ejecute todos los días a las 00:00 hs
* VA IR A TABLA PUNTO ESTRATEGICO
* Y VA A DECIR
* CHE LATITUD Y LONGITUD SON VACIA? BUENO
* DAME LA DIRECCION, HABLO CON GOOGLEMAPS
* Y TE DEVUELVO LATITUD Y LONGITUD
* Y ACTUALIZAS LA BASE DE DATOS
* */