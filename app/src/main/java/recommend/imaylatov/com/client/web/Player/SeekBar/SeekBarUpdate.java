package recommend.imaylatov.com.client.web.Player.SeekBar;

import android.os.Handler;
import android.os.Looper;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import recommend.imaylatov.com.client.web.Common.Common;
import recommend.imaylatov.com.client.web.Player.MetaData.SongMetaData;
import recommend.imaylatov.com.client.web.Player.Player;
import recommend.imaylatov.com.client.web.Player.Scale.ScaleRate;
import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 07.05.2015.
 */
public class SeekBarUpdate implements Runnable {
    private Handler handler = new Handler();
    private Player player;
    private SeekBar seekBar;
    private TextView txtStartTimeDuration;
    private TextView txtEndTimeDuration;

    private SongMetaData songMetaData;
    private boolean isMetaData = false;
    private ScaleRate scaleRate;
    private boolean isScaleRate = false;

    public SeekBarUpdate(Player player, SeekBar seekBar,
                         TextView txtStartTimeDuration, TextView txtEndTimeDuration) {
        this.player = player;
        this.seekBar = seekBar;
        this.txtStartTimeDuration = txtStartTimeDuration;
        this.txtEndTimeDuration = txtEndTimeDuration;
    }

    public void setMetaData(SongMetaData songMetaData){
        this.songMetaData = songMetaData;
        isMetaData = true;
    }

    public void setScaleRate(ScaleRate scaleRate){
        this.scaleRate = scaleRate;
        isScaleRate = true;
    }

    @Override
    public void run() {
        if(player.getMediaPlayer() != null){
            int timeFinal = player.getMediaPlayer().getDuration();
            int timeElapsed = player.getMediaPlayer().getCurrentPosition();
            int timeRemaining = timeFinal - timeElapsed;

            if((timeElapsed + 1000 >= timeFinal)&&(player.getState().equals("play"))){
                player.goNextState(player);
                try {
                    SaveSongInHistoryThread saveSongInHistoryThread = new SaveSongInHistoryThread(player.getStackSong().getCurrentSongUrl());
                    Thread searchThread = new Thread(saveSongInHistoryThread);
                    searchThread.start();
                    searchThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isMetaData = true;
                isScaleRate = true;
                player.nextSong();
                player.goNextState(player);
            }else if(player.getMediaPlayer().getDuration() > 0){
                if(isMetaData && songMetaData != null){
                    isMetaData = false;
                    String songUrl = player.getStackSong().getCurrentSongUrl();
                    if(!songUrl.isEmpty())
                        songMetaData.setMetaData(songUrl);
                }
                if(isScaleRate && scaleRate != null){
                    isScaleRate = false;
                    scaleRate.loadRate();
                }

                seekBar.setProgress(timeElapsed);

                int minuteElapsed = (int) TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed);
                int secondElapsed = (int) TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed) - 60 * minuteElapsed;
                int minuteRemaining = (int) TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining);
                int secondRemaining = (int) TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - 60 * minuteRemaining;

                (new Handler(Looper.getMainLooper())).postDelayed(new ThreadTimeDuration(txtStartTimeDuration,
                        String.format("%d:%02d", minuteElapsed, secondElapsed)), 1);
                (new Handler(Looper.getMainLooper())).postDelayed(new ThreadTimeDuration(txtEndTimeDuration,
                        String.format("%d:%02d", minuteRemaining, secondRemaining)), 1);
            }
        }
        handler.postDelayed(this, 100);
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public void setStartTimeDuration(TextView txtStartTimeDuration) {
        this.txtStartTimeDuration = txtStartTimeDuration;
    }

    public void setEndTimeDuration(TextView endTimeDuration) {
        this.txtEndTimeDuration = endTimeDuration;
    }

    private class ThreadTimeDuration implements Runnable{
        TextView textView;
        String text;

        private ThreadTimeDuration(TextView textView, String text) {
            this.textView = textView;
            this.text = text;
        }

        @Override
        public void run() {
            textView.setText(text);
        }
    }
}
