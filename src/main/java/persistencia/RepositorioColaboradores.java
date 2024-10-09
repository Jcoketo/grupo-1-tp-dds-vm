package persistencia;

import lombok.Getter;
import modelo.personas.Colaborador;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositorioColaboradores {
    private static RepositorioColaboradores instancia = null;

    @Getter
    private static List<Colaborador> colaboradores;

    private RepositorioColaboradores() {
        colaboradores = new ArrayList<Colaborador>();
    }
    public static RepositorioColaboradores getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioColaboradores();
        }
        return instancia;
    }

    public Colaborador existeColaborador(String uniqueIdentifier) {

        for (Colaborador colab : colaboradores) {
            String currentDocumento = colab.getUniqueIdentifier();

            if (Objects.equals(currentDocumento, uniqueIdentifier)) {
                return colab;  // devuelve el colaborador
            }
        }

        return null;  // Devuelve null si no se encuentra un colaborador con el mismo tipo y número de documento
    }

    public void agregar(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public void darDeBaja(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

}

