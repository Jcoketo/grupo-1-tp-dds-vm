package persistencia;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import modelo.elementos.Areas;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import modelo.elementos.Heladera;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoriosTecnicos{
    @Getter
    private static RepositoriosTecnicos instancia = null;

    private static EntityManager em;

    public static RepositoriosTecnicos getInstancia(EntityManager entityManager) {
        if (instancia == null) {
                instancia = new RepositoriosTecnicos(entityManager);
        }
        return instancia;
    }

    public static RepositoriosTecnicos getInstancia() {
        if(instancia == null) {
            throw new ExcepcionValidacion("No fue instanciado en el repositorio!");
        }
        return instancia;
    }

    private RepositoriosTecnicos(EntityManager entityManager) {
        em = entityManager;
    }

    public void agregar(Tecnico tecnico){
        this.validarInsertTecnico(tecnico);
        em.getTransaction().begin();
        em.persist(tecnico);
        em.getTransaction().commit();
        //em.close();
        //emf.close();
    }


    // hacemos un try catch en el controller
    public void validarInsertTecnico(Tecnico tecnico){
        if(tecnico.getPersona().getNombre() == null){
            throw new RuntimeException("El técnico no tiene una persona asociada");
        }
        if(tecnico.getPersona().getApellido() == null){
            throw new RuntimeException("El técnico no tiene una persona asociada");
        }
        if (tecnico.getAreaCobertura() == null){
            throw new RuntimeException("El técnico no tiene un área de cobertura asociada");
        }
        if(tecnico.getPersona().getDocumento().getNumeroDoc() == null || tecnico.getPersona().getDocumento().getTipoDoc() == null){
            throw new RuntimeException("El técnico no tiene un Documento completo");
        }
        if (tecnico.getNroCUIL() == null){
            throw new RuntimeException("El técnico no tiene un CUIL asociado");
        }
    }

    public void eliminar(Tecnico tecnico) {
        em.getTransaction().begin();
        Tecnico managedTecnico = em.find(Tecnico.class, tecnico.getId());
        if (managedTecnico != null) {
            em.remove(managedTecnico);
            em.getTransaction().commit();
        }
    }

    public Tecnico obtenerTecnicoCercano(Areas area, Heladera heladera) throws Exception {
        TypedQuery<Tecnico> query = em.createQuery("SELECT u FROM Tecnico u WHERE u.areaCobertura = :area", Tecnico.class)
                .setParameter("area", area);
        List<Tecnico> tecnicos;
        try {
            tecnicos = query.getResultList();
        } catch (NoResultException e) {
            tecnicos = new ArrayList<>();
        }

        if (tecnicos.isEmpty()) {
            throw new Exception("No hay técnicos disponibles en el área de cobertura");
        }


        // Serializar las latitudes y longitudes de los técnicos y la heladera en un JSON
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Double> centro = new HashMap<>();
        centro.put("latitud", heladera.getPuntoEstrategico().getLatitud());
        centro.put("longitud", heladera.getPuntoEstrategico().getLongitud());
        requestBody.put("centro", centro);

        List<Map<String, Double>> ubicaciones = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            Map<String, Double> ubicacion = new HashMap<>();
            ubicacion.put("latitud", tecnico.getPuntoEstrategico().getLatitud());
            ubicacion.put("longitud", tecnico.getPuntoEstrategico().getLongitud());
            ubicaciones.add(ubicacion);
        }
        requestBody.put("ubicaciones", ubicaciones);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInputString = objectMapper.writeValueAsString(requestBody);

        // Realizar la petición HTTP GET
        URL url = new URL("http://localhost:8080/api/recomendacion/tecnicoCercano"); // <------------------- URL
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        if (code != 200) {
            throw new Exception("Failed : HTTP error code : " + code);
        }

        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8")) {
            Map<String, Double> response = objectMapper.readValue(reader, Map.class);
            double latitud = response.get("latitud");
            double longitud = response.get("longitud");

            // Encontrar el técnico más cercano basado en la respuesta
            for (Tecnico tecnico : tecnicos) {
                if (tecnico.getPuntoEstrategico().getLatitud() == latitud && tecnico.getPuntoEstrategico().getLongitud() == longitud) {
                    return tecnico;
                }
            }
        }

        return null; // En caso de no encontrar un técnico cercano
    }

    public void registrarTecnico(Tecnico tecnico) {
        em.getTransaction().begin();
        em.persist(tecnico);
        em.getTransaction().commit();

    }

    public Tecnico obtenerTecnico(Integer id) {
        return em.find(Tecnico.class, id);
    }

    public void registrarVisita(Tecnico tecnico) {
        em.getTransaction().begin();
        em.persist(tecnico);
        em.getTransaction().commit();
    }

    public Tecnico existeTecnico(String nroCuil) {
        TypedQuery<Tecnico> query = em.createQuery(
                "SELECT t FROM Tecnico t WHERE t.nroCUIL = :nroCuil ",
                Tecnico.class
        );
        query.setParameter("nroCuil", nroCuil);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Tecnico buscarTecnicoXIdPersona(Integer idPersona) {
        TypedQuery<Tecnico> query = em.createQuery(
                "SELECT t FROM Tecnico t WHERE t.persona.id = :idPersona ",
                Tecnico.class
        );
        query.setParameter("idPersona", idPersona);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}