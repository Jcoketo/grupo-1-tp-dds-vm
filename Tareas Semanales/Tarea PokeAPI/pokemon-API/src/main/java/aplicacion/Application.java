package aplicacion;

import aplicacion.apis.PokemonApiCall;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws Exception {

        PokemonApiCall pokemonApiCall = new PokemonApiCall();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Elija una Opcion : ");
        System.out.println("1. Buscar Pokemon");
        System.out.println("2. Buscar Movimiento");
        String respuesta = scanner.nextLine();

        switch (respuesta) {
            case "1":
                System.out.println("Ingrese el Pokemon: ");
                String nombrePokemon = scanner.nextLine();
                pokemonApiCall.buscarPokemon(nombrePokemon);
                break;
            case "2":
                System.out.println("Ingrese el Movimiento: ");
                String nombreMovimiento = scanner.nextLine();
                pokemonApiCall.buscarMovimiento(nombreMovimiento);
                break;
            default:
                System.out.println("Opcion no valida");
                break;
        }

    }

}
