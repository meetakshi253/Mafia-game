import java.util.*;

public interface Users
{
   public abstract void choose_a_player_to_vote_out(HashMap<Integer, player> map);

   public abstract player choose_one_as_per_user(HashMap<Integer, player> map, player obj);

   public abstract void message();
}
