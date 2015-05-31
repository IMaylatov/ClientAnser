package recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class SongRateInfo {
    private String name;
    private String Author;
    private int rate;
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

    public int getRate() {
        return rate;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public SongRateInfo(String name, String author, int rate, String url) {
        this.name = name;
        Author = author;
        this.rate = rate;
        this.url = url;
    }

    @Override
    public String toString() {
        return name.replace('_',' ');
    }
}
