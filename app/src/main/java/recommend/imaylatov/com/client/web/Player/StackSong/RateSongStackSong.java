package recommend.imaylatov.com.client.web.Player.StackSong;

import java.util.List;

import recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic.SongRateInfo;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class RateSongStackSong implements StackSong {
    private List<SongRateInfo> songs;
    private int currentPosition = 0;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public RateSongStackSong(List<SongRateInfo> songs) {
        this.songs = songs;
    }

    @Override
    public String nextSong() {
        if(currentPosition + 1 == songs.size())
            currentPosition = 0;
        else
            currentPosition++;
        return songs.get(currentPosition).getUrl();
    }

    @Override
    public boolean hasPrevSong() {
        return true;
    }

    @Override
    public String prevSong() {
        if(songs.size() == 0)
            return "";
        if(currentPosition - 1 < 0)
            currentPosition = songs.size() - 1;
        else
            currentPosition--;
        return songs.get(currentPosition).getUrl();
    }

    @Override
    public String getCurrentSongUrl() {
        return songs.get(currentPosition).getUrl();
    }
}
