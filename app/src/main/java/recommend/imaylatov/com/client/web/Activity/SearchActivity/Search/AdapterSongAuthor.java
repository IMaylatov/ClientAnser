package recommend.imaylatov.com.client.web.Activity.SearchActivity.Search;

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
 * date: 27.05.2015.
 */
public class AdapterSongAuthor extends ArrayAdapter<SongAuthorInfo> {
    private Context context;
    private List<SongAuthorInfo> songAuthorInfos;

    public AdapterSongAuthor(Context context, List<SongAuthorInfo> songAuthorInfos) {
        super(context, R.layout.item_layout_song_author, songAuthorInfos);
        this.context = context;
        this.songAuthorInfos = songAuthorInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_layout_song_author, parent, false);

        TextView songName = (TextView) rowView.findViewById(R.id.songName);
        TextView songAuthor = (TextView) rowView.findViewById(R.id.songAuthor);

        songName.setText(songAuthorInfos.get(position).getName());
        songAuthor.setText(songAuthorInfos.get(position).getAuthor());

        return rowView;
    }
}
