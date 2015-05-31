package recommend.imaylatov.com.client.web.Activity.SearchActivity.Search;

import java.util.List;

import recommend.imaylatov.com.client.web.Rest.PersonRest.SongsQuery;
import recommend.imaylatov.com.client.web.Rest.SongsRest.SongInfo;
import recommend.imaylatov.com.client.web.Rest.SongsRest.SongRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class SearchListThread implements Runnable{
    private List<SongAuthorInfo> songsInfo;
    private String patternSong;

    public List<SongAuthorInfo> getSongsInfo() {
        return songsInfo;
    }

    public SearchListThread(String patternSong, List<SongAuthorInfo> songsInfo) {
        this.songsInfo = songsInfo;
        this.patternSong = patternSong;
    }

    @Override
    public void run() {
        SongRest songRest = new SongRest();
        songsInfo = songRest.getListSong(new SongsQuery(patternSong));
    }
}
