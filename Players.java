package Dice_Game;
/* this holds the player Info
 * 1. Name
 * 2. Wins
 */
public class Players {
    private String names;
    private int wins;
    public Players(String names){
        this.names = names;
    }
    
    public int showWins(){
        return this.wins;
    }
    public String showNames(){
        return this.names;
    }
    public void incrementWin(){
        this.wins++;
    }
}
