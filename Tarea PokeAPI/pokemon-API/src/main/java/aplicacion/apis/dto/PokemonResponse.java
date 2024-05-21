package aplicacion.apis.dto;

import java.util.List;

public class PokemonResponse {

    private List<Move> moves;

    private Sprites sprites;

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

}
