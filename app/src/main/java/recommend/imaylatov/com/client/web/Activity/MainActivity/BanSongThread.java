package recommend.imaylatov.com.client.web.Activity.MainActivity;

import recommend.imaylatov.com.client.web.Rest.SongsRest.SongRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class BanSongThread implements Runnable {
    private String songUrl;

    public BanSongThread(String songUrl) {
        this.songUrl = songUrl;
    }

    @Override
    public void run() {
        SongRest songRest = new SongRest();
        songRest.addToBlackList(songUrl);
    }
}
