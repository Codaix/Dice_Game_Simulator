package Dice_Game;
/*
 * 1. PlayGround
 * 2. Shows Results
 */
import java.util.ArrayList;
public class MainEngine {
    ArrayList<Players>arr;
    Dice dice;
    public MainEngine(ArrayList<Players>list){
        arr = list;
        dice = new Dice();
    }
    public void playRound(){
        int highestOP = 0;
        Players winPlayer = null;
        for(Players P : arr){
            int n = dice.roll();
            if(n > highestOP){
                highestOP = n;
                winPlayer = P;
            }else if(n == highestOP){
                winPlayer = null;
            }
        }
        if(winPlayer != null){
            System.out.println("Winner: "+winPlayer.showNames()+" won this round. The output we got: "+ highestOP);
            winPlayer.incrementWin();
        }
        else{
            System.out.println("No winner this round due to a tie at: " + highestOP);
        }

    }
    public void showResults(){
        for(Players P:arr){
            System.out.println(P.showNames()+" have won "+P.showWins()+" rounds");
        }
    }
}
