import java.util.*;

public class detective extends player
{

    private static ArrayList<detective> detective_list = new ArrayList<>();
    private static int alive_detectives = 0;

    public detective(int id)
    {
        super(id, 800);
    
    }

    //getters

    public static ArrayList<detective> getdetective_list() {
        return detective_list;
    }

    public detective get_detective(int n)
    {
        return getdetective_list().get(n);
    }

    public static int getAlive_detectives()
    {
        return alive_detectives;
    }

    //setter

    public static void add_detective(detective m)
    {
        getdetective_list().add(m);
        setAlive_detectives(1);
    }

    @Override
    public void remove(player p)
    {
        p = (detective)p;
        setAlive_detectives(-1);
        p.setStatus();

    }

    public static void setAlive_detectives(int n) {
        alive_detectives+=n;
    }

    public static void display_detective(int n)
    {
        for(int i=0;i<getdetective_list().size();i++)
        {
            if(getdetective_list().get(i).getPlayer_number()==n)
            {
                System.out.print("Player"+getdetective_list().get(i).getPlayer_number()+"[User] ");
                continue;
            }
            System.out.print("Player"+getdetective_list().get(i).getPlayer_number()+" ");
        }
        System.out.println("were detectives");
    }


    public static player choose_one(HashMap<Integer, player> all_alive_players, detective obj)  //will be a detective type object
    {
        Random rand = new Random();
        List<Integer> all_alive_keys = new ArrayList<>(all_alive_players.keySet());
        all_alive_players.get(all_alive_keys.get(rand.nextInt(all_alive_keys.size())));
        int index = rand.nextInt(all_alive_keys.size());
        while(all_alive_players.get(all_alive_keys.get(index)).equals(obj) || all_alive_players.get(all_alive_keys.get(index)).getStatus()=='d')
        {
            index = rand.nextInt(all_alive_keys.size());
        }
        return (all_alive_players.get(all_alive_keys.get(index)));
    }

    @Override
    public player choose_one_as_per_user(HashMap<Integer, player> map, player obj)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Choose a player to test: ");
        int n = in.nextInt();
        while(true)
        {
            if(!map.containsKey(n))
            {
                System.out.print("Invalid player number. Re-enter: ");
                n=in.nextInt();
            }
            if(map.get(n).getStatus()=='d')
            {
                System.out.print("The player is already dead. Re-enter: ");
                n=in.nextInt();
            }

            if(map.get(n).equals((detective)obj))
            {
                System.out.print("You cannot test a detective. Choose a player to test: ");
                n=in.nextInt();
            }

            if(map.containsKey(n) && map.get(n).getStatus()=='a' && !map.get(n).equals((detective)obj))
            {
                if(map.get(n).equals(new mafia(-1)))
                {
                    System.out.println("Player"+map.get(n).getPlayer_number()+" is mafia");
                }
                else
                {
                    System.out.println("Player"+map.get(n).getPlayer_number()+" is not mafia");
                }
                return map.get(n);
            }
        }
    }
    @Override
    public void message()
    {
        System.out.println("You are Player"+this.getPlayer_number());
        System.out.print("You are detective. Other detectives are: [");
        for(int i=0;i<getdetective_list().size();i++)
        {
            if(getdetective_list().get(i).getPlayer_number()!=this.getPlayer_number())
                System.out.print("Player"+getdetective_list().get(i).getPlayer_number()+" ");
        }
        System.out.println("\b]");
    }

}
