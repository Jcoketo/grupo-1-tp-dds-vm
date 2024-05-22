package aplicacion.apis.dto;

import java.util.List;

public class PokeMoveResponse {
    private List<NameMove> learned_by_pokemon;

    public List<NameMove> getLearned_by_pokemon() {
        return learned_by_pokemon;
    }

    public void setLearned_by_pokemon(List<NameMove> learned_by_pokemon) {
        this.learned_by_pokemon = learned_by_pokemon;
    }
}
