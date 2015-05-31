package recommend.imaylatov.com.client.web.Activity.SearchActivity.Search;

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
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Activity.MainActivity.MainActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Author.AuthorActivity;
import recommend.imaylatov.com.client.web.Activity.SearchActivity.Genre.GenreActivity;
import recommend.imaylatov.com.client.web.Player.PlayerService;
import recommend.imaylatov.com.client.web.Player.StackSong.RecommendStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.SingleStackSong;
import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;
import recommend.imaylatov.com.client.web.Rest.SongsRest.SongInfo;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class SearchActivity extends Activity {
    private SearchView searchView;
    private ListView listView;
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
        setContentView(R.layout.search_layout);

        initComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initComponent(){
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSongs();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(searchView.getQuery().toString().isEmpty())
                    listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, new ArrayList<SongInfo>()));
                return false;
            }
        });
        listView = (ListView) findViewById(R.id.SongsSearchList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSelectedSong((SongAuthorInfo)parent.getItemAtPosition(position));
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
                Intent goToGenres = new Intent(SearchActivity.this, GenreActivity.class);
                startActivity(goToGenres);
                break;
            case R.id.authorMenu:
                Intent goToAuthors = new Intent(SearchActivity.this, AuthorActivity.class);
                startActivity(goToAuthors);
                break;
            case R.id.recommendMenu:
                StackSong stackSong = new RecommendStackSong(PersonToken.Instance.getPersonId());
                playerService.getPlayer().setStackSong(stackSong);
                Intent goToRecommend = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(goToRecommend);
                playerService.getPlayer().nextSong();
                if(!playerService.getPlayer().getMediaPlayer().isPlaying()) {
                    playerService.getPlayer().goNextState(playerService.getPlayer());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchSongs(){
        try {
            List<SongAuthorInfo> songsInfo = null;
            SearchListThread searchListThread = new SearchListThread(searchView.getQuery().toString(), songsInfo);
            Thread searchThread = new Thread(searchListThread);
            searchThread.start();
            searchThread.join();

            songsInfo = searchListThread.getSongsInfo();
            AdapterSongAuthor adapterSongAuthor = new AdapterSongAuthor(getApplicationContext(), songsInfo);
            listView.setAdapter(adapterSongAuthor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playSelectedSong(SongAuthorInfo song){
        if(mBound){
            StackSong stackSong = new SingleStackSong(song.getUrl());
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
