package fr.project.ideerepas.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.database.MealsDatabase;
import fr.project.ideerepas.view.FrequencyView;

public class ListMealAdapter extends ArrayAdapter<String> {

	private MealsDatabase mealsDatabase = null;

	private Uri[] picture;
	private Activity activity;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = null;

		LayoutInflater inflater = (LayoutInflater)
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.row_list_meal, parent, false);

		TextView nameMealView  = (TextView)  rowView.findViewById(R.id.name);
		ImageView imageView    = (ImageView) rowView.findViewById(R.id.picture);
		TextView frequencyView = (TextView)  rowView.findViewById(R.id.frequency);

		nameMealView.setText(getItem(position));

		// Get frequency.
		int frequency = mealsDatabase.getFrequency(getItem(position));

		FrequencyView fqr = new FrequencyView(activity);
		frequencyView.setText(fqr.getFrequency(frequency));

		if( picture[position] == null ) {
			imageView.setImageResource(R.drawable.light_ic_unknow);
		}
		else {
			setPic(imageView, picture[position]);
		}

		return rowView;
	}

	private void setPic(ImageView img, Uri uri) {
		// Get the dimensions of the View
		int targetW = 60;//img.getWidth();
		int targetH = 60;//img.getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(uri.getPath(), bmOptions);

		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(), bmOptions);
		img.setImageBitmap(bitmap);
	}

	public ListMealAdapter(Context context, String[] names, String[] pictures, Activity a)  {
		super(context, R.layout.row_list_meal, names);

		mealsDatabase = new MealsDatabase(context);
		picture   = new Uri[pictures.length];
		activity = a;
		int i = 0;

		for(String path : pictures) {
			Uri uri = null;
			if( path != null ) {
				uri = Uri.parse(path);
			}
			picture[i] = uri;
			i++;
		}
	}
}