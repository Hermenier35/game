import java.util.List;

public class Guest {
    int id;
    String pseudo;
    Player player;

    public Guest(int id, String pseudo, Player player) {
        this.id = id;
        this.pseudo = pseudo;
        this.player = player;
    }
}
