import java.util.Comparator;

public class mafia_Comparator implements Comparator<mafia>
{
    @Override
    public int compare(mafia o1, mafia o2) {
        if(o1.getStatus()=='d'|| o1.getHP()==0.0f || o2.getStatus()=='d' || o2.getHP()==0.0f)
            return Integer.MIN_VALUE;
        return (int)(o1.getHP()-o2.getHP());
    }
}
