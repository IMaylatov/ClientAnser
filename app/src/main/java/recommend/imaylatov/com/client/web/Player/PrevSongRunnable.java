package recommend.imaylatov.com.client.web.Player;

import java.io.IOException;

import recommend.imaylatov.com.client.web.Player.StackSong.StackSong;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 10.05.2015.
 */
public class PrevSongRunnable implements Runnable {
    private Player player;
    private StackSong stackSong;

    public PrevSongRunnable(Player player, StackSong stackSong) {
        this.player = player;
        this.stackSong = stackSong;
    }

    @Override
    public void run() {
        try {
            boolean playGo = false;
            if(player.getMediaPlayer().isPlaying()) {
                playGo = true;
                player.getMediaPlayer().stop();
            }
            player.getMediaPlayer().reset();
            String songUrl = stackSong.prevSong();
            if(!songUrl.isEmpty()) {
                player.getMediaPlayer().setDataSource(songUrl);
                player.getMediaPlayer().prepare();

                player.getSeekBar().setMax(player.getMediaPlayer().getDuration());

                if (playGo) {
                    player.getMediaPlayer().start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
