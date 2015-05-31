package recommend.imaylatov.com.client.web.Player.Scale;

import android.widget.ImageButton;

import java.util.List;

import recommend.imaylatov.com.client.R;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class AddRateButtonsThread implements Runnable {
    private List<ImageButton> buttons;
    private int r;

    public AddRateButtonsThread(List<ImageButton> buttons, int r) {
        this.buttons = buttons;
        this.r = r;
    }

    @Override
    public void run() {
        for(int i = 0; i < buttons.size() && i < r; i++)
            buttons.get(i).setBackgroundResource(R.drawable.starfull);
        for(int i = r; i < buttons.size(); i++)
            buttons.get(i).setBackgroundResource(R.drawable.starempty);
    }
}
