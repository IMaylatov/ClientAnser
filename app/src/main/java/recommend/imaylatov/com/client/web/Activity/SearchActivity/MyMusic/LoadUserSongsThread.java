package recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic;

import java.util.List;

import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class LoadUserSongsThread implements Runnable {
    private int rateValue;
    private List<SongRateInfo> songs;

    public List<SongRateInfo> getSongs() {
        return songs;
    }

    public LoadUserSongsThread(int rateValue, List<SongRateInfo> songs) {
        this.rateValue = rateValue;
        this.songs = songs;
    }

    @Override
    public void run() {
        PersonRest personRest = new PersonRest();
        songs = personRest.getSongsByPerson(rateValue);
    }
}
