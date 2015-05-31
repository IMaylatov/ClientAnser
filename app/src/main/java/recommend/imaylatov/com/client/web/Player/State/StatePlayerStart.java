package recommend.imaylatov.com.client.web.Player.State;

import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Player.Player;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public class StatePlayerStart implements StatePlayer {
    @Override
    public void goNextState(Player player) {
        StatePlayer nextState = new StatePlayerStop();
        player.setState(nextState);
    }

    @Override
    public void playStop(Player player){
        player.getMediaPlayer().start();
        (new Handler(Looper.getMainLooper())).postDelayed(new ThreadPlayerStart(player.getStartPause()), 1);
    }

    class ThreadPlayerStart implements Runnable{
        private Button button;

        ThreadPlayerStart(Button button) {
            this.button = button;
        }

        @Override
        public void run() {
            button.setBackgroundResource(R.drawable.pause);
        }
    }

    @Override
    public String getState() {
        return "play";
    }
}
