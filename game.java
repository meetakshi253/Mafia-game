import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class game
{
    private static HashMap<Integer, player> all_alive_players;
    private static int no_of_alive_players;
    private static Users _user;

    public game(int n)
    {
        all_alive_players = new HashMap<>();
        no_of_alive_players = n;
    }

    //getters


    public static int getNo_of_alive_players() {
        return no_of_alive_players;
    }

    public static HashMap<Integer, player> getAll_alive_players() {
        return all_alive_players;
    }

    public static player get_user() {
        return (player) _user;
    }

    //setters

    public void setNo_of_alive_players()
    {
        no_of_alive_players-=1;
    }

    public void add_player( player _player)
    {
        getAll_alive_players().put(_player.getPlayer_number(), _player);
    }

    public static void set_user(Users _user) {
        game._user = _user;
    }

    public void remove_player(int i)
    {
        getAll_alive_players().get(i).setStatus();
    }

    public void display_alive_players()
    {
        System.out.print(getNo_of_alive_players()+" players remain: ");
        for(int i=0;i<getAll_alive_players().size();i++)
        {
            if(getAll_alive_players().get(i+1).getStatus()=='a')
                System.out.print("Player"+(i+1)+" ");

        }
        System.out.println("are alive");
    }

    public void display_roles()
    {
        mafia.display_mafia(get_user().getPlayer_number());
        detective.display_detective(get_user().getPlayer_number());
        healer.display_healer(get_user().getPlayer_number());
        commoner.display_commoners(getAll_alive_players(), get_user().getPlayer_number());
    }


    public void random_allotment(int num, int choice) throws CloneNotSupportedException {
        int n = num;
        int m = Math.floorDiv(n, 5);
        int d = Math.floorDiv(n, 5);
        int h = Math.max(1, Math.floorDiv(n, 10));
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <n; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        for (int i = 0; i < m; i++) {
            mafia _p = new mafia(list.get(i)+1);
            mafia.add_mafia(_p);
            add_player(_p);

        }

        for (int i = 0; i < d; i++) {
            detective det = new detective(list.get(i+m)+1);
            add_player(det);
            detective.add_detective(det);

        }

        for (int i = 0; i < h; i++) {
            healer he = new healer(list.get(i+m+d)+1);
            add_player(he);
            healer.add_healer(he);

        }

        for (int i = h + d + m; i < n; i++) {
            commoner cm = new commoner(list.get(i)+1);
            commoner.setAlive_commoners(1);

            add_player(cm);

        }

        if(choice == 5)
        {
            Random rand = new Random();
            int ran = rand.nextInt(4)+1;
            choice = ran;
        }
        switch (choice)
        {
            case 1:
                 try
                 {
                     set_user((mafia)(mafia.getMafia_list().get(0).clone()));
                 }
                 catch (CloneNotSupportedException e){};
                //mafia
                break;
            case 3:
                try
                {
                    set_user((healer)(healer.gethealer_list().get(0).clone()));
                }
                catch (CloneNotSupportedException e){};
                //healer
                break;
            case 2:
                try
                {
                    set_user((detective)(detective.getdetective_list().get(0).clone()));
                }
                catch (CloneNotSupportedException e){};
                //detective
                break;
            case 4:
                int i;
                for(i=1;i<getAll_alive_players().size();i++)
                {
                    if(getAll_alive_players().get(i).equals(new commoner(-1)))
                    {
                    try
                    {
                        set_user((commoner)(getAll_alive_players().get(i)).clone());
                    }
                    catch (CloneNotSupportedException e){};
                    //commoner
                    break;}
                }
                break;

        }
    }

    public void simulate_game()
    {
        int round =1;
        player m = null, d,h;
        get_user().message();
        while(true)
        {
            d = null;
            h = null;
            if(2*mafia.getAlive_mafias()>=getNo_of_alive_players() || mafia.getAlive_mafias()==0)
            {
                System.out.println("Game Over.");
                if(mafia.getAlive_mafias()==0)
                {
                    System.out.println("The Mafias have lost");
                }
                else
                {
                    System.out.println("The Mafias have won");
                }
                display_roles();
                System.exit(0);
            }

            System.out.println("Round "+round+":");
            display_alive_players();
            if(get_user().equals(new mafia(-1)) && get_user().getStatus()!='d')
            {
                m = get_user().choose_one_as_per_user(getAll_alive_players(), new mafia(-1));
            }
            else
            {
                m = mafia.choose_one(getAll_alive_players(), new mafia(-1)); //mafias target
                System.out.println("The mafias have chosen their target ");
            }

            if(detective.getAlive_detectives()!=0)
            {
                if(get_user().equals(new detective(-1)) && get_user().getStatus()=='a')
                {
                    d = get_user().choose_one_as_per_user(getAll_alive_players(), new detective(-1));
                }
                else
                {
                    d = detective.choose_one(getAll_alive_players(), new detective(-1)); //detectives test
                    System.out.println("Detectives have chosen a player to test ");
                }
            }
            else
            {
                System.out.println("Detectives have chosen a player to test ");
            }

            if(healer.getAlive_healers()!=0) //healers
            {
                if(get_user().equals(new healer(-1)) && get_user().getStatus()=='a')
                {
                    h = get_user().choose_one_as_per_user(getAll_alive_players(), null);
                }
                else
                {
                    h = healer.choose_one(getAll_alive_players());
                    System.out.println("Healers have chosen a player to heal ");
                }

            }
            else
            {
                System.out.println("Healers have chosen a player to heal ");
            }

            System.out.println("---End of actions---");

            if(mafia.getAlive_mafias()!=0)
                mafia.kill_HP(m); //reduce HP of the mafias target
            if(healer.getAlive_healers()!=0) //healers
            {
                h.setHP(h.getHP()+500);
            }
             //add 500 to the healers choice HP

            if(m.getHP()==0)
            {
                System.out.println("Player"+m.getPlayer_number()+" has died");
                remove_player(m.getPlayer_number());
                setNo_of_alive_players();
                m.remove(m);
                m.setStatus();
                if(m.getPlayer_number()==get_user().getPlayer_number())
                {
                    get_user().setStatus();
                }
            }
            else
            {
                System.out.println("No one died");
            }

            if(d!=null && mafia.getMafia_list().contains(d))
            {
                System.out.println("Player"+d.getPlayer_number()+" has been voted out.");
                remove_player(d.getPlayer_number());
                setNo_of_alive_players();
                d.remove(d);
                if(d.getPlayer_number()==get_user().getPlayer_number())
                {
                    get_user().setStatus();
                }
            }
            else
                {
                    boolean b=true;
                    if(get_user().getStatus()=='a')
                    {
                        get_user().choose_a_player_to_vote_out(getAll_alive_players());
                        b= false;
                    }
                    d = player.max_votes(getAll_alive_players(), b, get_user().getPlayer_number());
                    System.out.println("Player"+d.getPlayer_number()+" has been voted out.");
                    remove_player(d.getPlayer_number());
                    setNo_of_alive_players();
                    d.remove(d);
                    if(d.getPlayer_number()==get_user().getPlayer_number())
                    {
                        get_user().setStatus();
                    }

                }

            System.out.println("---End of Round "+round+"---");
            round++;
            System.out.println();

        }


    }


}



