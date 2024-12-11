package modelo.consumosAPIs.recomendadorDePuntos.apiMock;

import modelo.consumosAPIs.recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.Response;

public class ApiMockCall {
    private static ApiMockCall instancia = null;

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
        if (status == 200) {
            PuntoDeColocacion[] puntosDeColocacion = objectMapper.readValue(responseBody, PuntoDeColocacion[].class);
            return puntosDeColocacion;

        } else {
            System.out.println("Error response = " + responseBody);
            throw new Exception("Error en la llamada a /api/user");
        }
    }
}
