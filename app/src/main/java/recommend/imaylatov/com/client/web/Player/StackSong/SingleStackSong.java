package recommend.imaylatov.com.client.web.Player.StackSong;

import recommend.imaylatov.com.client.web.Common.Common;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class SingleStackSong implements StackSong {
    private String song;

    public SingleStackSong(String song) {
        this.song = song;
    }

    @Override
    public String nextSong() {
        return String.format("%s/songs/%s.mp3", Common.host, song);
    }

    @Override
    public boolean hasPrevSong() {
        return true;
    }

    @Override
    public String prevSong() {
        if(song.isEmpty())
            return "";
        return String.format("%s/songs/%s.mp3", Common.host, song);
    }

    @Override
    public String getCurrentSongUrl() {
        return String.format("%s/songs/%s.mp3", Common.host, song);
    }
}
