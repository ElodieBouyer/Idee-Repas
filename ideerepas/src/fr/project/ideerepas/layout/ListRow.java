package fr.project.ideerepas.layout;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.project.ideerepas.R;

public class ListRow extends ArrayAdapter<String> {

	private static String TAG = ListRow.class.getName();
	private Uri[] picture;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.row_layout, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

		textView.setText(getItem(position));

		if(convertView == null ) {
			if( picture[position] == null ) {
				imageView.setImageResource(R.drawable.interrogation);
			}
			else {
				imageView.setImageURI(picture[position]);
			}
			
		}
		else {
			rowView = (View)convertView;
		}

		return rowView;
	}


	public ListRow(Context context, String[] names, String[] pictures)  {
		super(context, R.layout.row_layout, names);

		this.picture = new Uri[pictures.length];
		int i = 0;

		for(String path : pictures) {
			Uri uri = null;
			if( path != null ) {
				Log.i(TAG, path);
				uri = Uri.parse(path);
			}
			picture[i] = uri;
			i++;
		}
	}
}