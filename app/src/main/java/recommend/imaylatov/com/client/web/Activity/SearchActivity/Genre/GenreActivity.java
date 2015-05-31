package recommend.imaylatov.com.client.web.Activity.SearchActivity.Genre;

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

import java.util.ArrayList;
import java.util.List;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Activity.MainActivity.MainActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Author.AuthorActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Search.SearchActivity;
import recommend.imaylatov.com.client.web.Player.PlayerService;
import recommend.imaylatov.com.client.web.Player.StackSong.GenreStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.RecommendStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class GenreActivity extends Activity {
    private ListView genreListView;
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
        setContentView(R.layout.genre_layout);

        initComponent();

        loadField();
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
                Intent goToSearch = new Intent(GenreActivity.this, SearchActivity.class);
                startActivity(goToSearch);
                break;
            case R.id.authorMenu:
                Intent goToAuthors = new Intent(GenreActivity.this, AuthorActivity.class);
                startActivity(goToAuthors);
                break;
            case R.id.recommendMenu:
                StackSong stackSong = new RecommendStackSong(PersonToken.Instance.getPersonId());
                playerService.getPlayer().setStackSong(stackSong);
                Intent goToRecommend = new Intent(GenreActivity.this, MainActivity.class);
                startActivity(goToRecommend);
                playerService.getPlayer().nextSong();
                if(!playerService.getPlayer().getMediaPlayer().isPlaying()) {
                    playerService.getPlayer().goNextState(playerService.getPlayer());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initComponent(){
        genreListView = (ListView) findViewById(R.id.GenreListView);
        genreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSelectedGenre((String) parent.getItemAtPosition(position));
            }
        });
    }

    private void loadField(){
        try {
            List<String> genreName = new ArrayList<>();
            GenreListThread genreListThread = new GenreListThread(genreName);
            Thread searchThread = new Thread(genreListThread);
            searchThread.start();
            searchThread.join();

            genreName = genreListThread.getSongsInfo();
            ArrayAdapter<String> listAdapter = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.list_item, genreName);
            genreListView.setAdapter(listAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playSelectedGenre(String genreName){
        if(mBound) {
            StackSong stackSong = new GenreStackSong(genreName, PersonToken.Instance.getPersonId());
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
