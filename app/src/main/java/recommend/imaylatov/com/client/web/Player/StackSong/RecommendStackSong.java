package recommend.imaylatov.com.client.web.Player.StackSong;

import java.util.ArrayList;
import java.util.List;

import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 08.05.2015.
 */
public class RecommendStackSong implements StackSong {
    private final int sizeStack = 10;
    private List<String> songs = new ArrayList<>();
    private long userId;
    private int currentPosition = 0;

    public RecommendStackSong(long userId) {
        this.userId = userId;
    }

    @Override
    public String nextSong() {
        if(songs.size() == 0){
            PersonRest personRest = new PersonRest();
            songs = personRest.getUrlRecommendSongs(userId);
            return songs.get(0);
        }

        if(currentPosition + 1 < songs.size()){
            currentPosition++;
            return songs.get(currentPosition);
        }

        PersonRest personRest = new PersonRest();
        List<String> songUrl = personRest.getUrlRecommendSongs(userId);

        if(songUrl.size() > 0) {
            if(songs.size() > 5)
                songs = songs.subList(5, sizeStack);
            songs.addAll(songUrl);
            currentPosition = 5;
        }
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
        if((songs.size() == 0) || (currentPosition<0 || currentPosition>songs.size()))
            return "";
        return songs.get(currentPosition);
    }
}
