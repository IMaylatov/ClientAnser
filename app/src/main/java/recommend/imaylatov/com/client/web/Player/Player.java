package recommend.imaylatov.com.client.web.Player;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import recommend.imaylatov.com.client.web.Player.MetaData.SongMetaData;
import recommend.imaylatov.com.client.web.Player.Scale.ScaleRate;
import recommend.imaylatov.com.client.web.Player.SeekBar.SeekBarUpdate;
import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;
import recommend.imaylatov.com.client.web.Player.State.StatePlayer;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public interface Player {
    void goNextState(Player player);
    void setState(StatePlayer state);
    void nextSong();
    void prevSong();
    MediaPlayer getMediaPlayer();

    Button getStartPause();
    void setStartPause(Button startPause);

    SeekBar getSeekBar();
    void setSeekBar(SeekBar seekBar);

    void setStartTimeDuration(TextView txtStartTimeDuration);
    void setEndTimeDuration(TextView txtEndTimeDuration);

    void setStackSong(StackSong stackSong);
    StackSong getStackSong();

    void setNextSongButton(Button nextSongButton);
    void setPrevSongButton(Button prevSongButton);

    String getState();

    SeekBarUpdate getSeekBarUpdate();
}
