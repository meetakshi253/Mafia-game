import java.util.*;

public abstract class player implements Users, Cloneable
{
    private final int player_number;
    private float HP;
    private char status;
    private int votes;

    public player(int id, float hp)
    {
        this.player_number = id;
        this.HP = hp;
        this.status = 'a'; //alive
        this.votes = 0;
    }
    // getters

    public float getHP() {
        return HP;
    }

    public int getPlayer_number() {
        return player_number;
    }

    public char getStatus() {
        return status;
    }

    public int getVotes() {
        return votes;
    }

    //setters

    public void setHP(float HP) {
        this.HP = HP;
    }

    public void setStatus() {
        this.status = 'd';  //dead
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public static void resetvotes(HashMap<Integer, player> all_alive_players)
    {
        for(Map.Entry e : all_alive_players.entrySet())
        {
            ((player)(e.getValue())).setVotes(0);
        }
    }

    public static player max_votes(HashMap<Integer, player> all_alive_players, boolean b, int player_id) {
        Random rand = new Random();
        List<Integer> all_alive_keys = new ArrayList<>(all_alive_players.keySet());
        all_alive_players.get(all_alive_keys.get(rand.nextInt(all_alive_keys.size())));
        for (int i = 0; i < all_alive_players.size(); i++) {
            if (all_alive_players.get(i+1).getStatus() != 'd') {
                //select one
                if((i+1)==player_id && !b)
                    continue;
                int index = rand.nextInt(all_alive_keys.size()); //selected a random index (alive)
                all_alive_players.get(all_alive_keys.get(index)).setVotes(all_alive_players.get(all_alive_keys.get(index)).getVotes() + 1);
            }
        }
        player p = null;
        int max = 0;
        for (int i = 0; i < all_alive_players.size(); i++)
        {
            if (all_alive_players.get(all_alive_keys.get(i )).getStatus() != 'd')
            {
                if(all_alive_players.get(all_alive_keys.get(i)).getVotes()>=max)
                {
                    max = all_alive_players.get(all_alive_keys.get(i)).getVotes();
                    p = all_alive_players.get(all_alive_keys.get(i));
                }

            }
        }
        resetvotes(all_alive_players);
        return p;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj!=null)
        {
            return (this.getClass()==obj.getClass());
        }
        else
            return false;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException { //shallow copy
        return super.clone();
    }

    @Override
    public void choose_a_player_to_vote_out(HashMap<Integer, player> map)
    {
        System.out.print("Choose a player to vote out: ");
        Scanner in = new Scanner(System.in);
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
                map.get(n).setVotes(1);
                return;
            }
        }
    }

    public abstract void remove(player p);


}



