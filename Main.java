
import java.util.Scanner;

public class Main
{
    public static void print_welcome_message()
    {
        System.out.println("Choose a character");
        System.out.println("1) Mafia\n2) Detective\n3) Healer\n4) Commoner\n5) Assign randomly");

    }
    public static void main(String args[]) throws CloneNotSupportedException {
        Scanner in = new Scanner(System.in);
        int choice, no_of_players;
        System.out.println("Welcome to Mafia");
        System.out.print("Enter number of players (at least 10): ");
        no_of_players = in.nextInt();
        while(no_of_players<10)
        {
            System.out.print("Number of players must be at least 10. Re-enter: ");
            no_of_players = in.nextInt();
        }
        print_welcome_message();
        choice = in.nextInt();
        while(choice>5 || choice<1)
        {
            System.out.print("Wrong choice. Re-enter: ");
            choice = in.nextInt();
        }
        game _game = new game(no_of_players);
        _game.random_allotment(no_of_players, choice);
        _game.simulate_game();

    }
}
