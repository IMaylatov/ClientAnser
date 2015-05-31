package recommend.imaylatov.com.client.web.Rest.SongsRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class SongInfo {
    private String name;
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

    public SongInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return name.replace('_',' ');
    }
}
