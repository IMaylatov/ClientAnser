package recommend.imaylatov.com.client.web.Player.Scale;

import recommend.imaylatov.com.client.web.Rest.SongsRest.SongRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class AddRateThread implements Runnable {
    private String songUrl;
    int r;

    public AddRateThread(String songUrl, int r) {
        this.songUrl = songUrl;
        this.r = r;
    }

    @Override
    public void run() {
        SongRest songRest = new SongRest();
        songRest.addRate(songUrl, r);
    }
}
