package recommend.imaylatov.com.client.web.Player.SeekBar;

import android.media.MediaPlayer;
import android.widget.SeekBar;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public class SeekBarChageListener implements SeekBar.OnSeekBarChangeListener {
    private MediaPlayer mediaPlayer;

    public SeekBarChageListener(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(mediaPlayer != null && fromUser){
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
