package recommend.imaylatov.com.client.web.Player.SeekBar;

import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 15.05.2015.
 */
public class SaveSongInHistoryThread implements Runnable{
    private String songUrl;

    public SaveSongInHistoryThread(String songUrl) {
        this.songUrl = songUrl;
    }

    @Override
    public void run() {
        PersonRest personRest = new PersonRest();
        personRest.saveSongInHistory(songUrl);
    }
}
