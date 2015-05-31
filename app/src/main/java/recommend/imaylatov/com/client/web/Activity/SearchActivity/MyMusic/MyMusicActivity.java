package recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Activity.MainActivity.MainActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Author.AuthorActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Genre.GenreActivity;
import recommend.imaylatov.com.client.web.Player.PlayerService;
import recommend.imaylatov.com.client.web.Player.StackSong.RateSongStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.RecommendStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;
import recommend.imaylatov.com.client.web.Rest.SongsRest.SongInfo;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class MyMusicActivity extends Activity {
    private ListView listView;
    private Spinner spinner;
    private PlayerService playerService;
    private boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            playerService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymusic_activity);

        initComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initComponent(){
        listView = (ListView) findViewById(R.id.songsListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSelectedSong(position);
            }
        });
        try {
            List<SongRateInfo> songsInfo = null;
            LoadUserSongsThread loadUserSongs = new LoadUserSongsThread(0, songsInfo);
            Thread loadUserSongsThread = new Thread(loadUserSongs);
            loadUserSongsThread.start();
            loadUserSongsThread.join();

            songsInfo = loadUserSongs.getSongs();
            AdapterSongRate listAdapter = new AdapterSongRate(getApplicationContext(), songsInfo);
            listView.setAdapter(listAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        String[] dataSpinner = new String[]{"Все", "Рейтинг больше 1", "Рейтинг больше 2"
                , "Рейтинг больше 3", "Рейтинг больше 4", "Рейтинг 5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.list_item, dataSpinner);
        adapter.setDropDownViewResource(R.layout.list_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Рейтинг");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterListAdapter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.genreMenu:
                Intent goToGenres = new Intent(MyMusicActivity.this, GenreActivity.class);
                startActivity(goToGenres);
                break;
            case R.id.authorMenu:
                Intent goToAuthors = new Intent(MyMusicActivity.this, AuthorActivity.class);
                startActivity(goToAuthors);
                break;
            case R.id.recommendMenu:
                StackSong stackSong = new RecommendStackSong(PersonToken.Instance.getPersonId());
                playerService.getPlayer().setStackSong(stackSong);
                Intent goToRecommend = new Intent(MyMusicActivity.this, MainActivity.class);
                startActivity(goToRecommend);
                playerService.getPlayer().nextSong();
                if(!playerService.getPlayer().getMediaPlayer().isPlaying()) {
                    playerService.getPlayer().goNextState(playerService.getPlayer());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterListAdapter(int position){
        try {
            List<SongRateInfo> songsInfo = null;
            LoadUserSongsThread loadUserSongs = new LoadUserSongsThread(position, songsInfo);
            Thread loadUserSongsThread = new Thread(loadUserSongs);
            loadUserSongsThread.start();
            loadUserSongsThread.join();

            songsInfo = loadUserSongs.getSongs();
            AdapterSongRate listAdapter = new AdapterSongRate(getApplicationContext(), songsInfo);
            listView.setAdapter(listAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playSelectedSong(int position){
        if(mBound){
            List<SongRateInfo> songs = new ArrayList<>();
            for(int i = 0; i < listView.getAdapter().getCount(); i++)
                songs.add((SongRateInfo) listView.getAdapter().getItem(i));
            RateSongStackSong stackSong = new RateSongStackSong(songs);
            stackSong.setCurrentPosition(position);
            playerService.getPlayer().setStackSong(stackSong);

            Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToMain);

            playerService.getPlayer().nextSong();
            playerService.getPlayer().getStartPause().setBackgroundResource(R.drawable.pause);
            if(!playerService.getPlayer().getMediaPlayer().isPlaying()) {
                playerService.getPlayer().goNextState(playerService.getPlayer());
            }
        }
    }
}
