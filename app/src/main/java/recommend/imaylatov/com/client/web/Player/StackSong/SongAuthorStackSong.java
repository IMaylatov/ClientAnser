package recommend.imaylatov.com.client.web.Player.StackSong;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 14.05.2015.
 */
public class SongAuthorStackSong implements StackSong {
    private List<String> songs = new ArrayList<>();
    private int currentPosition = 0;

    public SongAuthorStackSong(String authorName, long userId){
        try {
            SearchSongsByAuthorThread searchListThread =
                    new SearchSongsByAuthorThread(authorName, userId, songs);
            Thread searchThread = new Thread(searchListThread);
            searchThread.start();
            searchThread.join();

            songs = searchListThread.getSongs();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String nextSong() {
        if(currentPosition + 1 >= songs.size())
            currentPosition = 0;
        else
            currentPosition++;
        return songs.get(currentPosition);
    }

    @Override
    public boolean hasPrevSong() {
        return currentPosition > 0;
    }

    @Override
    public String prevSong() {
        if(songs.size() == 0)
            return "";
        if(hasPrevSong()){
            currentPosition--;
        }
        return songs.get(currentPosition);
    }

    @Override
    public String getCurrentSongUrl() {
        return songs.get(currentPosition);
    }
}
