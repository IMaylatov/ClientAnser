package recommend.imaylatov.com.client.web.Player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class PlayerService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        player.getMediaPlayer().start();
    }

    @Override
    public void onDestroy() {
        player.getMediaPlayer().stop();
    }
}
