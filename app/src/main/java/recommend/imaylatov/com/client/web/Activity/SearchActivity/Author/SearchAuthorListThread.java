package recommend.imaylatov.com.client.web.Activity.SearchActivity.Author;

import java.util.List;

import recommend.imaylatov.com.client.web.Rest.SongsRest.SongRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 14.05.2015.
 */
public class SearchAuthorListThread implements Runnable{
    private List<String> authorName;
    private String patternString;

    public List<String> getAuthorList() {
        return authorName;
    }

    public SearchAuthorListThread(String patternString, List<String> authorName) {
        this.authorName = authorName;
        this.patternString = patternString;
    }

    @Override
    public void run() {
        SongRest songRest = new SongRest();
        authorName = songRest.getAuthorListByName(patternString);
    }
}
