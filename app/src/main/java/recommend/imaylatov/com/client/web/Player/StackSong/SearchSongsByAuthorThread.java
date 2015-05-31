package recommend.imaylatov.com.client.web.Player.StackSong;

import java.util.List;

import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 14.05.2015.
 */
public class SearchSongsByAuthorThread implements Runnable {
    private String authorName;
    private long userId;
    private List<String> songs;

    public List<String> getSongs() {
        return songs;
    }

    public SearchSongsByAuthorThread(String authorName, long userId, List<String> songs) {
        this.authorName = authorName;
        this.userId = userId;
        this.songs = songs;
    }

    @Override
    public void run() {
        PersonRest personRest = new PersonRest();
        songs = personRest.getSongsByAuthor(authorName, userId);
    }
}
