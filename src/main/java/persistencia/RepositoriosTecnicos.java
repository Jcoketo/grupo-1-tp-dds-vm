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
import modelo.personas.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        List<Tecnico> tecnicos = em.createQuery("SELECT u FROM Tecnico u WHERE u.areaCobertura = :area", Tecnico.class)
                .setParameter("area", area)
                .getResultList();

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
            throw new RuntimeException("Failed : HTTP error code : " + code);
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

  }