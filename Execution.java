package Dice_Game;
/* executive main function */
import java.util.Scanner;
import java.util.ArrayList;
public class Execution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Give the number of player: ");
        int n = sc.nextInt();
        sc.close();
        ArrayList<Players>list = new ArrayList<>();
        for(int i =0; i < n; i++){
            System.out.println("Player"+(i+1)+" name :");
            String name = sc.nextLine();
            list.add(new Players(name));
        }
        MainEngine me = new MainEngine(list);
        while (true) 
        {
         System.out.println("You wanna play the game?(1/0) :");   
         int ip = sc.nextInt();
         if(ip == 1){
            me.playRound();
            me.showResults();
         }
         else break;
        }
        System.out.println("Thanks for playing! See you next time... o7");

    }        
}
