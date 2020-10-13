import java.util.*;

public class healer extends player
{
    private static ArrayList<healer> healer_list = new ArrayList<>();
    private static int alive_healers =0;
    public healer(int id)
    {
        super(id, 800);
      //  healer_list = new HashMap<>();
    }

    //getters 

    public static ArrayList<healer> gethealer_list() {
        return healer_list;
    }

    public healer get_healer(int n)
    {
        return gethealer_list().get(n);
    }

    public static int getAlive_healers() {
        return alive_healers;
    }

    //setter

    public static void add_healer(healer m)
    {
        gethealer_list().add(m);
        setAlive_healers(1);
    }


    public static void setAlive_healers(int n) {
        alive_healers += n;
    }

    public static void display_healer(int n)
    {
        for(int i=0;i<gethealer_list().size();i++)
        {
            if(gethealer_list().get(i).getPlayer_number()==n)
            {
                System.out.print("Player"+gethealer_list().get(i).getPlayer_number()+"[User] ");
                continue;
            }
            System.out.print("Player"+gethealer_list().get(i).getPlayer_number()+" ");

        }
        System.out.println("were healers");
    }


    public static player choose_one(HashMap<Integer, player> all_alive_players)  //will be a healer type object
    {
        Random rand = new Random();
        List<Integer> all_alive_keys = new ArrayList<>(all_alive_players.keySet());
        all_alive_players.get(all_alive_keys.get(rand.nextInt(all_alive_keys.size())));
        int index = rand.nextInt(all_alive_keys.size());
        while(all_alive_players.get(all_alive_keys.get(index)).getStatus()=='d')
        {
            index = rand.nextInt(all_alive_keys.size());
        }
        return all_alive_players.get(all_alive_keys.get(index));
    }


    @Override
    public void remove(player p)
    {
        p = (healer)p;
        setAlive_healers(-1);
        p.setStatus();

    }

    @Override
    public player choose_one_as_per_user(HashMap<Integer, player> map, player obj)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Choose a player to heal: ");
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

            if(map.containsKey(n) && map.get(n).getStatus()=='a')
            {
                return map.get(n);
            }
        }
    }
    @Override
    public void message()
    {
        System.out.println("You are Player"+this.getPlayer_number());
        System.out.print("You are healer. Other healers are: [");
        for(int i=0;i<gethealer_list().size();i++)
        {
            if(gethealer_list().get(i).getPlayer_number()!=this.getPlayer_number())
                System.out.print("Player"+gethealer_list().get(i).getPlayer_number()+" ");
        }
        System.out.println("]");
    }

}
