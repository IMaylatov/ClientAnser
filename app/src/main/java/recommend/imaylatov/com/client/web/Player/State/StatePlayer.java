package recommend.imaylatov.com.client.web.Player.State;

import recommend.imaylatov.com.client.web.Player.Player;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public interface StatePlayer {
    void goNextState(Player player);
    void playStop(Player player);
    String getState();
}
