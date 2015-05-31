package recommend.imaylatov.com.client.web.Activity.MainActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Author.AuthorActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Genre.GenreActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic.MyMusicActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Search.SearchActivity;
import recommend.imaylatov.com.client.web.Common.Common;
import recommend.imaylatov.com.client.web.Player.MetaData.PlayerMetaData;
import recommend.imaylatov.com.client.web.Player.MetaData.SongMetaData;
import recommend.imaylatov.com.client.web.Player.PlayerService;
import recommend.imaylatov.com.client.web.Player.PlayerURL;
import recommend.imaylatov.com.client.web.Player.Scale.ScaleRate;
import recommend.imaylatov.com.client.web.Player.Scale.ScaleRateStar;
import recommend.imaylatov.com.client.web.Player.StackSong.RecommendStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public class MainActivity extends Activity {
    private Button startPause;
    private Button nextSongButton;
    private Button prevSongButton;
    private TextView txtStartTimeDuration;
    private TextView txtEndTimeDuration;
    private SeekBar sbSong;
    private ScaleRate scaleRate;
    private ImageView imageView;
    private TextView titleSong;
    private TextView artistSong;

    private ImageButton rate1Button;
    private ImageButton rate2Button;
    private ImageButton rate3Button;
    private ImageButton rate4Button;
    private ImageButton rate5Button;

    private SongMetaData songMetaData;

    private PlayerService playerService;
    boolean mBound = false;

    private List<ImageButton> imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        initComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.searchMenu:
                Intent goToSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(goToSearch);
                break;
            case R.id.genreMenu:
                Intent goToGenres = new Intent(MainActivity.this, GenreActivity.class);
                startActivity(goToGenres);
                break;
            case R.id.authorMenu:
                Intent goToAuthors = new Intent(MainActivity.this, AuthorActivity.class);
                startActivity(goToAuthors);
                break;
            case R.id.recommendMenu:
                StackSong stackSong = new RecommendStackSong(PersonToken.Instance.getPersonId());
                playerService.getPlayer().setStackSong(stackSong);
                Intent goToRecommend = new Intent(MainActivity.this, MainActivity.class);
                startActivity(goToRecommend);
                playerService.getPlayer().nextSong();
                playerService.getPlayer().getStartPause().setBackgroundResource(R.drawable.pause);
                if(!playerService.getPlayer().getMediaPlayer().isPlaying()) {
                    playerService.getPlayer().goNextState(playerService.getPlayer());
                }
                break;
            case R.id.myMusicMenu:
                Intent goToMyMusic = new Intent(MainActivity.this, MyMusicActivity.class);
                startActivity(goToMyMusic);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponent(){
        startPause = (Button) findViewById(R.id.btnStart);
        nextSongButton = (Button) findViewById(R.id.btnNext);
        prevSongButton = (Button) findViewById(R.id.btnPrevious);
        sbSong =(SeekBar) findViewById(R.id.sbSong);
        txtStartTimeDuration = (TextView) findViewById(R.id.txtStartTimeDuration);
        txtEndTimeDuration = (TextView) findViewById(R.id.txtEndTimeDuration);
        imageView = (ImageView) findViewById(R.id.imageView);

        rate1Button = (ImageButton) findViewById(R.id.rate1Button);
        rate2Button = (ImageButton) findViewById(R.id.rate2Button);
        rate3Button = (ImageButton) findViewById(R.id.rate3Button);
        rate4Button = (ImageButton) findViewById(R.id.rate4Button);
        rate5Button = (ImageButton) findViewById(R.id.rate5Button);

        imageButtons = new ArrayList<>();
        imageButtons.add(rate1Button);
        imageButtons.add(rate2Button);
        imageButtons.add(rate3Button);
        imageButtons.add(rate4Button);
        imageButtons.add(rate5Button);

        titleSong = (TextView) findViewById(R.id.titleSong);
        artistSong = (TextView) findViewById(R.id.artistSong);

        songMetaData = new PlayerMetaData(imageView, artistSong, titleSong);
    }

    public void onStartClick(View view){
        if(mBound){
            if(playerService.getPlayer().getMediaPlayer().getDuration() <= 0)
                playerService.getPlayer().nextSong();
            playerService.getPlayer().goNextState(playerService.getPlayer());
        }
    }

    public void onNextSongClick(View view){
        if(mBound) {
            playerService.getPlayer().nextSong();
            playerService.getPlayer().getSeekBarUpdate().setMetaData(songMetaData);
            playerService.getPlayer().getSeekBarUpdate().setScaleRate(scaleRate);
        }
    }

    public void onPrevSong(View view){
        if(mBound) {
            playerService.getPlayer().prevSong();
            playerService.getPlayer().getSeekBarUpdate().setMetaData(songMetaData);
            playerService.getPlayer().getSeekBarUpdate().setScaleRate(scaleRate);
        }
    }

    public void banButtonClick(View view)
    {
        try {
            if(mBound){
                String currentSongUrl = playerService.getPlayer().getStackSong().getCurrentSongUrl();
                BanSongThread banSong = new BanSongThread(currentSongUrl);
                Thread banSongThread = new Thread(banSong);
                banSongThread.start();
                banSongThread.join();

                playerService.getPlayer().nextSong();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void rateButton1Cick(View view){
        if(mBound)
            scaleRate.setRate(1);
    }
    public void rateButton2Cick(View view){
        if(mBound)
            scaleRate.setRate(2);
    }
    public void rateButton3Cick(View view){
        if(mBound)
            scaleRate.setRate(3);
    }
    public void rateButton4Cick(View view){
        if(mBound)
            scaleRate.setRate(4);
    }
    public void rateButton5Cick(View view){
        if(mBound)
            scaleRate.setRate(5);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            playerService = binder.getService();

            if (playerService.getPlayer() == null){
                playerService.setPlayer(new PlayerURL(getApplicationContext(),
                        startPause,
                        nextSongButton,
                        prevSongButton,
                        sbSong,
                        txtStartTimeDuration,
                        txtEndTimeDuration,
                        new RecommendStackSong(PersonToken.Instance.getPersonId())));
                playerService.getPlayer().getSeekBarUpdate().setMetaData(songMetaData);
            }else{
                startPause.setText(playerService.getPlayer().getStartPause().getText());
                playerService.getPlayer().setStartPause(startPause);
                playerService.getPlayer().setNextSongButton(nextSongButton);
                playerService.getPlayer().setPrevSongButton(prevSongButton);
                playerService.getPlayer().setSeekBar(sbSong);
                playerService.getPlayer().setEndTimeDuration(txtEndTimeDuration);
                playerService.getPlayer().setStartTimeDuration(txtStartTimeDuration);
                playerService.getPlayer().getSeekBarUpdate().setMetaData(songMetaData);
            }

            scaleRate = new ScaleRateStar(imageButtons, playerService.getPlayer());
            playerService.getPlayer().getSeekBarUpdate().setScaleRate(scaleRate);

            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
