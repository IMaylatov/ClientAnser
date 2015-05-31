package recommend.imaylatov.com.client.web.Activity.SearchActivity.Genre;

import java.util.List;

import recommend.imaylatov.com.client.web.Rest.SongsRest.SongRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 14.05.2015.
 */
public class GenreListThread implements Runnable{
    private List<String> songsInfo;

    public List<String> getSongsInfo() {
        return songsInfo;
    }

    public GenreListThread(List<String> songsInfo) {
        this.songsInfo = songsInfo;
    }

    @Override
    public void run() {
        SongRest songRest = new SongRest();
        songsInfo = songRest.getAllGenre();
    }
}
