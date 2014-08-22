package fr.project.ideerepas.adapter;

import java.security.acl.Owner;

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

public class ListMealAdapter extends ArrayAdapter<String> {

	private MealsDatabase mealsDatabase = null;
	
	private Uri[] picture;

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
		
		String text;
		
		String evening = " ";
		evening += rowView.getResources().getString(R.string.evening);
		
		String noon = " ";
		noon += rowView.getResources().getString(R.string.noon);
		
		switch (frequency) {
		case 0:
			frequencyView.setText(R.string.often);
			break;
		case 1:
			frequencyView.setText(R.string.regularly);
			break;
		case 2:
			frequencyView.setText(R.string.occasionally);
			break;
		case 3 :
			frequencyView.setText(R.string.rarely);
			break;

		// Lundi midi.	
		case 41:
			text = rowView.getResources().getString(R.string.monday);
			text += noon;
			frequencyView.setText(text);
			break;
			
		// Lundi soir.
		case 42:
			text = rowView.getResources().getString(R.string.monday);
			text += evening;
			frequencyView.setText(text);
			break;
			
		// Mardi midi.
		case 51:
			text = rowView.getResources().getString(R.string.tuesday);
			text += noon;
			frequencyView.setText(text);
			break;
			
		// Mardi soir.	
		case 52:
			text = rowView.getResources().getString(R.string.tuesday);
			text += evening;
			frequencyView.setText(text);
			break;
			
		case 61:
			text = rowView.getResources().getString(R.string.wednesday);
			text += noon;
			frequencyView.setText(text);
			break;
			
		case 62:
			text = rowView.getResources().getString(R.string.wednesday);
			text += evening;
			frequencyView.setText(text);
			break;
			
		case 71:
			text = rowView.getResources().getString(R.string.thursday);
			text += noon;
			frequencyView.setText(text);
			break;
		case 72:
			text = rowView.getResources().getString(R.string.thursday);
			text += evening;
			frequencyView.setText(text);
			break;
		case 81:
			text = rowView.getResources().getString(R.string.friday);
			text += noon;
			frequencyView.setText(text);
			break;
		case 82:
			text = rowView.getResources().getString(R.string.friday);
			text += evening;
			frequencyView.setText(text);
			break;
		case 91:
			text = rowView.getResources().getString(R.string.saturday);
			text += noon;
			frequencyView.setText(text);
			break;
		case 92:
			text = rowView.getResources().getString(R.string.saturday);
			text += evening;
			frequencyView.setText(text);
			break;
		case 101:
			text = rowView.getResources().getString(R.string.sunday);
			text += noon;
			frequencyView.setText(text);
			break;
		case 102:
			text = rowView.getResources().getString(R.string.sunday);
			text += evening;
			frequencyView.setText(text);
			break;
			
		default:
			frequencyView.setText(R.string.frequency_empty);
			break;
		}

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

	public ListMealAdapter(Context context, String[] names, String[] pictures)  {
		super(context, R.layout.row_list_meal, names);

		mealsDatabase = new MealsDatabase(context);
		picture   = new Uri[pictures.length];
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