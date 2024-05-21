package aplicacion.apis;

import aplicacion.apis.dto.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class PokemonApiCall {

    public void buscarPokemon(String pokemon) throws Exception {

        WebClient clientUsers = WebClient.create("https://pokeapi.co/api/v2/pokemon/"+pokemon);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Response response = clientUsers
                .header("Content-Type", "application/json")
                .get();

        int status = response.getStatus();
        //System.out.println("Status: " + status);
        String responseBody = response.readEntity(String.class);
        if (status == 200) {
            //System.out.println("response = " + responseBody);

            PokemonResponse pokemonResponse = objectMapper.readValue(responseBody, PokemonResponse.class);

            System.out.println("Habilidades del pokemon "+ pokemon+ " :");

            for (int i=0; i<pokemonResponse.getMoves().size(); i++) {
                Move move = pokemonResponse.getMoves().get(i);
                System.out.println(i+"."+move.getMove().getName());

            }

            System.out.println("Imagen del pokemon: " + pokemonResponse.getSprites().getFront_default());


        } else {
            System.out.println("Error response = " + responseBody);
            throw new Exception("Error en la llamada a /api/user");
        }
    }

    public void buscarMovimiento(String movimiento) throws Exception {

        WebClient clientUsers = WebClient.create("https://pokeapi.co/api/v2/move/"+movimiento);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Response response = clientUsers
                .header("Content-Type", "application/json")
                .get();

        int status = response.getStatus();
        //System.out.println("Status: " + status);
        String responseBody = response.readEntity(String.class);
        if (status == 200) {
            //System.out.println("response = " + responseBody);

            PokeMoveResponse move = objectMapper.readValue(responseBody, PokeMoveResponse.class);

            System.out.println("Pokemon que aprenden el movimiento "+ movimiento+ " :");

            for (int i=0; i<move.getLearned_by_pokemon().size(); i++) {
                NameMove nameMove = move.getLearned_by_pokemon().get(i);
                System.out.println(i+"."+nameMove.getName());
            }


        } else {
            System.out.println("Error response = " + responseBody);
            throw new Exception("Error en la llamada a /api/user");
        }
    }
}
