package pegorov.lesson4.game;

public class GamePlayer {
    private char playerSign;
    private boolean realPlayer = true;

    public GamePlayer(boolean isRealPlayer, char playerSign){
        this.playerSign = playerSign;
        this.realPlayer = isRealPlayer;
    }

    public char getPlayerSign(){
        return this.playerSign;
    }
}
