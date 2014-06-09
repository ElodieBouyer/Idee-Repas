package fr.project.ideerepas.layout.meals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class AddMeal extends Activity {

	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static String TAG = AddMeal.class.getName();
	private Meals func;
	private Uri photo=null;
	private ImageView imgView;

	/**
	 * Called when the activity launched.
	 */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.i(TAG, "Activity AddMeal créée.");

		setContentView(R.layout.add_meal);
		setTitle(getString(R.string.new_meal));

		imgView  = (ImageView) findViewById(R.id.picture);
	}

	@Override
	public void onResume() {
		super.onResume();
		imgView = (ImageView) findViewById(R.id.picture);
		if( photo == null ) imgView.setImageResource(R.drawable.interrogation);
		else imgView.setImageURI(photo);
	}

	/**
	 * Called when user click in the button to add a picture.
	 * @param view Button to add a picture.
	 */
	public void addPicture(View view) {
		// Create a file for a photo.
		photo = Uri.fromFile(createImageFile());

		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

		Log.i(TAG,"Path to the picture="+photo);
		// start the image capture Intent
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}



	/**
	 * Called to prepare picture.
	 * @return The picture as a file format.
	 */
	private File createImageFile() {

		File storageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), "IdeeRepas");

		// Create the storage directory if it does not exist
		if (! storageDir.exists()){
			if (! storageDir.mkdirs()){
				Log.i(TAG, "failed to create directory");
				return null;
			}
		}

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File image = new File(storageDir.getPath() + File.separator +
				"IMG_"+ timeStamp + ".jpg");

		return image;
	}

	/**
	 * Called when the user finished to take his picture.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,requestCode,data);
		Log.i(TAG,"Path to the picture="+photo);

		try {
			// *** On la met dans l'ImageView.
			if( photo == null ) imgView.setImageResource(R.drawable.interrogation);
			else imgView.setImageURI(photo);
		}
		catch(Exception e) {
			Log.i(TAG, "onActivityResutl() "+ e.toString());
		}
	}

	public void addNew(View view) {
		this.func = new Meals(getApplicationContext());

		// Field verification.
		EditText editName = (EditText) findViewById(R.id.name_edit);


		if (editName.getText().toString().isEmpty()) {
			editName.setError(this.getResources().getString(R.string.error_name));
			func = null;
			return;
		}


		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_adding_meal)

		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				EditText editName = (EditText) findViewById(R.id.name_edit);

				String name = editName.getText().toString();
				String picture=null;

				if( photo != null ) {
					picture =  photo.getPath();
				}

				func.add(name, picture, -1);
				Log.i(TAG, "Ajout de "+name+" dans la base de données.");

				finish();
				Intent intent = new Intent(getApplicationContext(), ListMeal.class);
				startActivity(intent);
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				photo = null;
				imgView = (ImageView) findViewById(R.id.picture);
				if( photo == null ) imgView.setImageResource(R.drawable.interrogation);
				else imgView.setImageURI(photo);
			}
		});

		builder.show();
	}

}
