package modelo.consumosAPIs.recomendadorDePuntos.apiMock;

import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDireccion;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class ApiMockCall {
    private static ApiMockCall instancia = null;
    private static final Logger logger = LoggerFactory.getLogger(ApiMockCall.class);


    public static ApiMockCall getInstancia(){
        if(instancia == null){
            instancia = new ApiMockCall();
        }
        return instancia;
    }

    public PuntoDeColocacion[] obtenerPuntosDeColocacion(Double latitud, Double longitud, Double radio) throws Exception {
        WebClient clientUsers = WebClient.create("https://05fdedf2-260a-4266-a358-c85cb1f7e468.mock.pstmn.io/api/puntosEstrategicos");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Response response = clientUsers
                .header("Content-Type", "application/json")
                .get();

        int status = response.getStatus();
        String responseBody = response.readEntity(String.class);

        //try {
            if (status == 200) {
                PuntoDeColocacion[] puntosDeColocacion = objectMapper.readValue(responseBody, PuntoDeColocacion[].class);
                return puntosDeColocacion;
            } else {
                logger.info("Error response = " + responseBody);
                throw new Exception("Error en la llamada a /api/puntosEstrategicos con estado: " + status);
            }
        /*} catch (JsonProcessingException e) {
            // Captura errores de deserialización JSON
            logger.info("Error al deserializar la respuesta JSON: " + e.getMessage());
            throw new Exception("Error de deserialización JSON", e);
        } catch (Exception e) {
            // Captura cualquier otro error
            logger.info("Error general en la llamada a la API: " + e.getMessage());
            throw e; // Relanza la excepción para propagarla
        }*/

    }

    public PuntoDireccion[] obtenerPuntosXdireccion(String direccion) {
        WebClient clientUsers = WebClient.create("https://nominatim.openstreetmap.org/search.php?q=" + direccion + "&format=jsonv2");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Response response = clientUsers
                .header("Content-Type", "application/json")
                .get();
        int status = response.getStatus();
        String responseBody = response.readEntity(String.class);
        if (status == 200) {
            try {
                PuntoDireccion[] puntosDeColocacion = objectMapper.readValue(responseBody, PuntoDireccion[].class);
                return puntosDeColocacion;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("Error response = " + responseBody);
            return null;
        }
    }

}
