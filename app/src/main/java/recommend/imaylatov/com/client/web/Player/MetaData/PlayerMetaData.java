package recommend.imaylatov.com.client.web.Player.MetaData;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import recommend.imaylatov.com.client.R;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 19.05.2015.
 */
public class PlayerMetaData implements SongMetaData {
    private ImageView coverImage;
    private TextView artist;
    private TextView title;

    private Bitmap bitmap;
    private MediaMetadataRetriever mmr;

    public PlayerMetaData(ImageView coverImage, TextView artist, TextView title) {
        this.coverImage = coverImage;
        this.artist = artist;
        this.title = title;
    }

    @Override
    public void setMetaData(String songUrl) {
        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(songUrl, new HashMap<String, String>());
        byte[] data = mmr.getEmbeddedPicture();
        if(data != null)
        {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                @Override
                public void run() {
                    coverImage.setImageBitmap(bitmap);
                    coverImage.setAdjustViewBounds(true);
                }
            }, 10);
        }else{
            (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                @Override
                public void run() {
                    coverImage.setImageResource(R.drawable.fox);
                    coverImage.setAdjustViewBounds(true);
                }
            }, 10);
        }

        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            @Override
            public void run() {
                artist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                title.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            }
        }, 10);
    }
}
