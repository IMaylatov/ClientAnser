package recommend.imaylatov.com.client.web.Activity.SearchActivity.Search;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 27.05.2015.
 */
public class SongAuthorInfo {
    private String name;
    private String Author;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public SongAuthorInfo(String name, String author, String url) {
        this.name = name;
        Author = author;
        this.url = url;
    }

    @Override
    public String toString() {
        return name.replace('_',' ');
    }
}
