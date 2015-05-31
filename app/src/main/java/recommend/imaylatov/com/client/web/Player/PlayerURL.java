package recommend.imaylatov.com.client.web.Player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import recommend.imaylatov.com.client.web.Player.MetaData.SongMetaData;
import recommend.imaylatov.com.client.web.Player.Scale.ScaleRate;
import recommend.imaylatov.com.client.web.Player.SeekBar.SeekBarChageListener;
import recommend.imaylatov.com.client.web.Player.SeekBar.SeekBarUpdate;
import recommend.imaylatov.com.client.web.Player.State.StatePlayer;
import recommend.imaylatov.com.client.web.Player.State.StatePlayerStop;
import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public class PlayerURL implements Player{
    private MediaPlayer mediaPlayer;
    private StatePlayer statePlayer;
    private Button startPause;
    private Context context;
    private SeekBar seekBar;
    private StackSong stackSong;
    private long currentSongId;
    private NextSongRunnable nextSongRunnable;
    private PrevSongRunnable prevSongRunnable;
    private Button nextSongButton;
    private Button prevSongButton;
    private TextView txtStartTimeDuration;
    private TextView txtEndTimeDuration;
    private ScaleRate scaleRate;

    private SeekBarUpdate seekBarUpdate;
    private SeekBarChageListener seekBarChageListener;

    public SeekBarUpdate getSeekBarUpdate() {
        return seekBarUpdate;
    }

    public PlayerURL(Context context,
                     Button startPause,
                     Button nextSongButton,
                     Button prevSongButton,
                     SeekBar seekBar,
                     TextView txtStartTimeDuration,
                     TextView txtEndTimeDuration,
                     StackSong stackSong) {
        this.startPause = startPause;
        this.context = context;
        this.seekBar = seekBar;
        this.stackSong = stackSong;
        this.nextSongButton = nextSongButton;
        this.prevSongButton = prevSongButton;
        this.txtStartTimeDuration = txtStartTimeDuration;
        this.txtEndTimeDuration = txtEndTimeDuration;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);

        statePlayer = new StatePlayerStop();

        seekBarChageListener = new SeekBarChageListener(mediaPlayer);
        seekBar.setOnSeekBarChangeListener(seekBarChageListener);

        seekBarUpdate = new SeekBarUpdate(this, seekBar, txtStartTimeDuration, txtEndTimeDuration);
        Thread updateSeekBarThread = new Thread(seekBarUpdate);
        updateSeekBarThread.start();

        nextSongRunnable = new NextSongRunnable(this, stackSong);
        prevSongRunnable = new PrevSongRunnable(this, stackSong);
    }

    @Override
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public Button getStartPause() {
        return startPause;
    }

    @Override
    public SeekBar getSeekBar() {
        return seekBar;
    }

    @Override
    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
        seekBar.setMax(mediaPlayer.getDuration());
        seekBarUpdate.setSeekBar(seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChageListener);
    }

    @Override
    public void goNextState(Player player) {
        statePlayer.goNextState(this);
        statePlayer.playStop(this);
    }

    @Override
    public void setState(StatePlayer state) {
        this.statePlayer = state;
    }

    @Override
    public void nextSong() {
        try {
            setEnabledButton(false);
            Thread nextSongThread = new Thread(nextSongRunnable);
            nextSongThread.start();
            nextSongThread.join();
            setEnabledButton(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prevSong() {
        try{
            setEnabledButton(false);
            Thread prevSongThread = new Thread(prevSongRunnable);
            prevSongThread.start();
            prevSongThread.join();
            setEnabledButton(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStackSong(StackSong stackSong) {
        setEnabledButton(false);
        this.stackSong = stackSong;
        nextSongRunnable = new NextSongRunnable(this, stackSong);
        prevSongRunnable = new PrevSongRunnable(this, stackSong);
        setEnabledButton(true);
    }

    @Override
    public StackSong getStackSong() {
        return stackSong;
    }

    @Override
    public void setStartPause(Button startPause) {
        this.startPause = startPause;
    }

    @Override
    public void setNextSongButton(Button nextSongButton) {
        this.nextSongButton = nextSongButton;
    }

    @Override
    public void setPrevSongButton(Button prevSongButton) {
        this.prevSongButton = prevSongButton;
    }

    @Override
    public void setStartTimeDuration(TextView txtStartTimeDuration) {
        this.txtStartTimeDuration = txtStartTimeDuration;
        seekBarUpdate.setStartTimeDuration(txtStartTimeDuration);
    }

    @Override
    public void setEndTimeDuration(TextView txtEndTimeDuration) {
        this.txtEndTimeDuration = txtEndTimeDuration;
        seekBarUpdate.setEndTimeDuration(txtEndTimeDuration);
    }

    @Override
    public String getState() {
        return statePlayer.getState();
    }

    private void setEnabledButton(boolean enable){
        startPause.setEnabled(enable);
        nextSongButton.setEnabled(enable);
        prevSongButton.setEnabled(enable);
    }
}
