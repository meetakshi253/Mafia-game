import java.util.*;

public class mafia extends player //implements Comparator<mafia>
{
    private static ArrayList<mafia> mafia_list = new ArrayList<>();
    static mafia_Comparator maf = new mafia_Comparator();
    private static int alive_mafias=0;
    public mafia(int id)
    {
        super(id, 2500 );

    }

    //getters

    public static ArrayList<mafia> getMafia_list()
    {

        return mafia_list;
    }

    public mafia get_mafia(int n)
    {
        return getMafia_list().get(n);
    }

    public static int getAlive_mafias() {
        return alive_mafias;
    }

    //setter

    public static mafia add_mafia(mafia m)
    {
        getMafia_list().add(m);
        setAlive_mafias(1);
        return m;
    }



    public static void setAlive_mafias(int n) {
        mafia.alive_mafias +=n;
    }

    public static void display_mafia(int n)
    {
        for(int i=0;i<getMafia_list().size();i++)
        {
            if(getMafia_list().get(i).getPlayer_number()==n)
            {
                System.out.print("Player"+getMafia_list().get(i).getPlayer_number()+"[User] ");
                continue;
            }
            System.out.print("Player"+getMafia_list().get(i).getPlayer_number()+" ");
        }
        System.out.println("were mafia");
    }

    public static player choose_one(HashMap<Integer, player> all_alive_players, mafia obj)  //will be a mafia type object
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

    public static float sum_HP()
    {
        float sum=0;
        for(int i=0;i<getMafia_list().size();i++)
        {
            if(getMafia_list().get(i).getStatus()=='a')
                sum+=getMafia_list().get(i).getHP();
        }
        return sum;
    }

    public static int get_active_mafias()
    {
        int sum=0;
        for(int i=0;i<getMafia_list().size();i++)
        {
            if(getMafia_list().get(i).getStatus()=='a' && getMafia_list().get(i).getHP()>0)
                sum+=1;
        }
        return sum;
    }

   public static void kill_HP(player p)
    {
        Collections.sort(getMafia_list(), maf);
        float sum = sum_HP();

        if(sum>p.getHP())
        {
            float supposed_reduction = p.getHP()/get_active_mafias();
            while(true)
            {
                if(p.getHP()==0)
                    return;
               //first entry of the mafia list would contain the smallest non-zero HP wala object
                if(getMafia_list().get(0).getHP()<supposed_reduction)
                {
                    p.setHP(p.getHP()-getMafia_list().get(0).getHP());  //reduce it by the amount
                    getMafia_list().get(0).setHP(0);
                    Collections.sort(getMafia_list(), maf );
                }
                supposed_reduction = p.getHP()/get_active_mafias();
                if(getMafia_list().get(0).getHP()>=supposed_reduction)
                {
                    //all subsequent active mafias have HP greater than thiss
                    for(int i=0;i<get_active_mafias();i++)
                    {
                        getMafia_list().get(i).setHP(getMafia_list().get(i).getHP()-supposed_reduction);
                    }
                    p.setHP(0);
                    return;
                }

            }

        }
        else if(sum<=p.getHP())
        {
            for(int i=0;i<getMafia_list().size();i++)
            {
                p.setHP(p.getHP()-getMafia_list().get(i).getHP());
                getMafia_list().get(i).setHP(0);
            }
            return;
        }

    }

    @Override
    public player choose_one_as_per_user(HashMap<Integer, player> map, player obj)
    {
        Scanner in = new Scanner(System.in);
        System.out.print("Choose a target: ");
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

            if(map.get(n).equals((mafia)obj))
            {
                System.out.print("You cannot kill a mafia. Re-enter: ");
                n=in.nextInt();
            }

            if(map.containsKey(n) && map.get(n).getStatus()=='a' && !map.get(n).equals((mafia)obj))
            {
                return map.get(n);
            }
        }
    }


    @Override
    public void remove(player p)
    {
        p = (mafia)p;
        setAlive_mafias(-1);
        p.setStatus();
        p.setHP(0);
    }

    @Override
    public void message()
    {
        System.out.println("You are Player"+this.getPlayer_number());
        System.out.print("You are mafia. Other mafias are: [");
        for(int i=0;i<getMafia_list().size();i++)
        {
            if(getMafia_list().get(i).getPlayer_number()!=this.getPlayer_number())
                System.out.print("Player"+getMafia_list().get(i).getPlayer_number()+" ");
        }
        System.out.println("\b]");
    }
}
