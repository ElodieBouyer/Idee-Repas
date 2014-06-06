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
import fr.project.ideerepas.meal.Meals;

public class ListRow extends ArrayAdapter<String> {

	private static String TAG = ListRow.class.getName();
	private Meals meals;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.row_layout, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

		String name    = getItem(position);
		Uri picture = meals.getPicture(name);

		textView.setText(name);

		if( picture == null ) {
			imageView.setImageResource(R.drawable.interrogation);
			Log.i(TAG, name+" -> "+" ? ");
		}
		else {
			imageView.setImageURI(picture);
			Log.i(TAG, name+" -> "+picture.getPath());
		}

		return rowView;
	}


	public ListRow(Context context, String[] names)  {
		super(context, R.layout.row_layout, names);
		Log.i(TAG, "nb name = "+names.length);
		meals = new Meals(context);
	}
}