package recommend.imaylatov.com.client.web.Player.StackSong;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 08.05.2015.
 */

/**
 * Получает url следующих и предыдущих песен
 */
public interface StackSong {
    String nextSong();
    boolean hasPrevSong();
    String prevSong();
    String getCurrentSongUrl();
}
