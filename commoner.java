import java.util.*;

public class commoner extends player
{
    private static int alive_commoners =0;

    public commoner(int id)
    {
        super(id, 1000);
    }

    //getters


    public static int getAlive_commoners() {
        return alive_commoners;
    }

    //setters

    public static void setAlive_commoners(int n) {
        commoner.alive_commoners +=n;
    }



    public static void display_commoners(HashMap<Integer, player> map, int n)
    {
        if(!map.isEmpty()) {
            Iterator it = map.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry obj = (Map.Entry)it.next();
                if(obj.getValue().equals(new commoner(-1)))
                {
                    if((int)obj.getKey()==n)
                    {
                        System.out.print("Player"+obj.getKey()+"[User] ");
                        continue;
                    }
                    System.out.print("Player"+obj.getKey()+" ");
                }

            }
            System.out.println("were commoners");
        }
    }


    @Override
    public void remove(player p)
    {
        p = (commoner)p;
        p.setStatus();
        setAlive_commoners(-1);
    }

    @Override
    public player choose_one_as_per_user(HashMap<Integer, player> map, player obj)
    {
        return null;
    }

    @Override
    public void message() {
        System.out.println("You are Player"+this.getPlayer_number());
        System.out.println("You are commoner");
    }
}
