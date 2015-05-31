package recommend.imaylatov.com.client.web.Player.Scale;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class LoadRateThread implements Runnable {
    private List<ImageButton> buttons;
    private String songUrl;

    public LoadRateThread(List<ImageButton> buttons, String songUrl) {
        this.buttons = buttons;
        this.songUrl = songUrl;
    }

    @Override
    public void run() {
        PersonRest personRest = new PersonRest();
        int r = personRest.loadRateForSong(songUrl);

        AddRateButtonsThread addRateButtonsThread = new AddRateButtonsThread(buttons,r);
        (new Handler(Looper.getMainLooper())).postDelayed(addRateButtonsThread, 10);
    }
}
