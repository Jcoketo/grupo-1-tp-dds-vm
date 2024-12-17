package modelo.authService;


import modelo.colaboracion.RegistroPersonasSituVulnerable;
import modelo.elementos.TarjetaPlastica;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import modelo.personas.PersonaHumana;
import modelo.personas.PersonaVulnerable;
import modelo.personas.TipoDocumento;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioPersonasVulnerables;


public class AuthServicePersonaVulnerable {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositorioPersonasVulnerables repoPersVuln = RepositorioPersonasVulnerables.getInstancia();

    public static void procesarAltaPersonaVulnerable(Integer idPersona, String nombre, TipoDocumento tipoDoc, String numeroDocumento, String domicilio, String nroTarj, Integer cantidadMenores) {

        //Con esto sacamos los 0s del id autogenerado
        Integer nroTarj2 = Integer.parseInt(nroTarj);
        String nroTarjeta = nroTarj2.toString();

        PersonaHumana persona = repoColab.buscarPersonaPorId(idPersona);
        Colaborador colaborador = repoColab.buscarColaboradorXIdPersona(idPersona);

        RegistroPersonasSituVulnerable colaboracion = repoColab.traerColaboradoresXColaboradorPersonaSitu(colaborador);

        if ( colaboracion == null )
            throw new ExcepcionValidacion("No tiene ninguna tarjeta disponible para repartir!");

        if (colaboracion.getTarjetas().isEmpty())
            throw new ExcepcionValidacion("No hay tarjetas disponibles");

        if (colaboracion.getTarjetas().stream().noneMatch(tarjeta -> tarjeta.getNro_tarjeta().equals(nroTarjeta)))
            throw new ExcepcionValidacion("La tarjeta no es válida");

        if ( (colaboracion.getTarjetas().size() - colaboracion.getCantidadRepartida() <= 0 ) )
            throw new ExcepcionValidacion("No hay tarjetas disponibles para asignar. Solicite mas!");

        TarjetaPlastica tarjetaPlasticaxAsignar = colaboracion.getTarjetas().stream()
                .filter(tarjeta -> tarjeta.getNro_tarjeta().equals(nroTarjeta)).findFirst().get();

        tarjetaPlasticaxAsignar.setRecibida(true);

        PersonaVulnerable personaVulnerable = new PersonaVulnerable(persona, nombre, tipoDoc, numeroDocumento, domicilio, tarjetaPlasticaxAsignar, cantidadMenores);

        colaboracion.setCantidadRepartida(colaboracion.getCantidadRepartida() + 1);
        colaboracion.hacerColaboracion(colaborador);

        repoColab.nuevaColaboracion(colaborador, colaboracion);
        repoPersVuln.agregarPersonaVulnerable(personaVulnerable);






    }



}