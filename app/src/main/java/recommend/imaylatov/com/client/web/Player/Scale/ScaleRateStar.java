package recommend.imaylatov.com.client.web.Player.Scale;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;

import java.util.List;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Player.Player;
import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class ScaleRateStar implements ScaleRate {
    private List<ImageButton> buttons;
    private Player player;

    public ScaleRateStar(List<ImageButton> buttons, Player player) {
        this.buttons = buttons;
        this.player = player;
    }

    @Override
    public void setRate(int r) {


        try {
            String songUrl = player.getStackSong().getCurrentSongUrl();
            AddRateThread addRate = new AddRateThread(songUrl, r);
            Thread addRateThread = new Thread(addRate);
            addRateThread.start();
            addRateThread.join();


            AddRateButtonsThread addRateButtonsThread = new AddRateButtonsThread(buttons,r);
            (new Handler(Looper.getMainLooper())).postDelayed(addRateButtonsThread, 100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadRate() {
        try {
            String songUrl = player.getStackSong().getCurrentSongUrl();
            LoadRateThread loadRate = new LoadRateThread(buttons, songUrl);
            Thread loadRateThread = new Thread(loadRate);
            loadRateThread.start();
            loadRateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
