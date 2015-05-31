package recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import recommend.imaylatov.com.client.R;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 17.05.2015.
 */
public class AdapterSongRate extends ArrayAdapter<SongRateInfo> {
    private Context context;
    private List<SongRateInfo> songRateInfos;

    public AdapterSongRate(Context context, List<SongRateInfo> songRateInfos) {
        super(context, R.layout.item_layout_song_rate, songRateInfos);
        this.context = context;
        this.songRateInfos = songRateInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_layout_song_rate, parent, false);

        TextView songName = (TextView) rowView.findViewById(R.id.songName);
        TextView songRate = (TextView) rowView.findViewById(R.id.songRate);
        TextView songAuthor = (TextView) rowView.findViewById(R.id.songAuthor);

        songName.setText(songRateInfos.get(position).getName());
        songRate.setText(Integer.toString(songRateInfos.get(position).getRate()));
        songAuthor.setText(songRateInfos.get(position).getAuthor());

        return rowView;
    }
}
