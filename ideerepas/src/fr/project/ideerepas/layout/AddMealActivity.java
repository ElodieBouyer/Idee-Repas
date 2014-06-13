package fr.project.ideerepas.layout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import fr.project.ideerepas.R;
import fr.project.ideerepas.controller.DatabaseController;

public class AddMealActivity extends Activity {

	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static String TAG = AddMealActivity.class.getName();
	private Uri photo=null;
	private ImageView imgView;
	private IngredientLayout igd;
	private List<String> igdList = null;
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

		setIngredient();
	}

	@Override
	public void onResume() {
		super.onResume();
		setPicture();
	}

	private void setIngredient() {
		LinearLayout tableIgd  = (LinearLayout) findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getApplicationContext(), null, true);
		tableIgd.addView(igd.getTableLayout());
	}

	/**
	 * Called when user click in the picture.
	 * @param v
	 */
	public void addPicture(View v) {
		addPicture();
	}

	/**
	 * Called when user click in the button to add a picture.
	 */
	public void addPicture() {
		// Create a file for a photo.
		photo = Uri.fromFile(createImageFile());

		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

		Log.i(TAG,"Path to the picture="+photo);
		// start the image capture Intent
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_camera_save, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_camera:
			addPicture();
			return true;
		case R.id.action_save:
			addNew();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
			setPicture();
		}
		catch(Exception e) {
			Log.i(TAG, "onActivityResutl() "+ e.toString());
		}
	}

	private void setPicture() {
		if( photo == null ) {
			imgView.setImageResource(R.drawable.light_ic_unknow);
		}
		else {
			File test = new File(photo.getPath());
			if( test.exists() ) imgView.setImageURI(photo);
			else imgView.setImageResource(R.drawable.light_ic_unknow);
		}
	}

	public void addNew() {
		// Field verification.
		EditText editName = (EditText) findViewById(R.id.name_edit);
		String name = editName.getText().toString();

		if (name.isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal_name, Toast.LENGTH_SHORT).show();
			return;
		}
		if( DatabaseController.getInstanceMeals(getApplicationContext()).exist(name) ) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal, Toast.LENGTH_SHORT).show();
			editName.setText("");
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
					File test = new File(photo.getPath());
					if( test.exists() ) picture =  photo.getPath();
				}
				int idMeal = DatabaseController.getInstanceMeals(getApplicationContext())
				.add(name, picture, -1);
				Log.i(TAG, "Ajout de "+name+" dans la base de données.");

				for(String n : igdList) {
					DatabaseController.getInstanceIngredientMeal(getApplicationContext())
					.add(idMeal, DatabaseController.getInstanceIngredient(getApplicationContext()).getID(n));
				}

				finish();
				Intent intent = new Intent(getApplicationContext(), ListMealLayout.class);
				startActivity(intent);
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				setPicture();
			}
		});

		builder.show();
	}

	public void addIngredient(View v) {
		EditText igdEdit   = (EditText) findViewById(R.id.newIngredient);
		String igdName = igdEdit.getText().toString();

		if( igdName.isEmpty()) return ;
		if( igdList == null ) igdList = new ArrayList<String>();

		if( !igdList.isEmpty() && igdList.contains(igdName)) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_igd, Toast.LENGTH_SHORT).show();
			igdEdit.setText("");
			return;
		}

		DatabaseController.getInstanceIngredient(getApplicationContext()).add(igdName);
		igdList.add(igdName);
		igd.newIngredient(igdName);
		LinearLayout tableIgd  = (LinearLayout) findViewById(R.id.list_ingredient); 
		tableIgd.removeAllViews();
		tableIgd.addView(igd.getTableLayout());
		igdEdit.setText("");
	}

}
