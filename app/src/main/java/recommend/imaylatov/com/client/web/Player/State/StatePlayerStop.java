package recommend.imaylatov.com.client.web.Player.State;

import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Player.Player;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public class StatePlayerStop implements StatePlayer {
    @Override
    public void goNextState(Player player) {
        StatePlayer nextState = new StatePlayerStart();
        player.setState(nextState);
    }

    @Override
    public void playStop(Player player){
        player.getMediaPlayer().pause();
        (new Handler(Looper.getMainLooper())).postDelayed(new ThreadPlayerStop(player.getStartPause()), 1);
    }

    class ThreadPlayerStop implements Runnable{
        private Button button;

        ThreadPlayerStop(Button button) {
            this.button = button;
        }

        @Override
        public void run() {
            button.setBackgroundResource(R.drawable.play);
        }
    }

    @Override
    public String getState() {
        return "Stop";
    }
}
