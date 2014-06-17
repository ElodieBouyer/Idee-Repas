package fr.project.ideerepas.activity;

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

public class ModifMeal extends Activity {

	private EditText name;
	private ImageView picture;
	private Uri photo=null;
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private int mealID;
	private IngredientLayout igd;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.modif_meal);
		setTitle(getString(R.string.liste_repas));

		Bundle extra = getIntent().getExtras();

		if( extra.getString("meal") == null) {
			return;
		}

		name    = (EditText)  findViewById(R.id.name_modif);
		picture = (ImageView) findViewById(R.id.picture_modif);

		photo  = DatabaseController.getInstanceMeals(getApplicationContext()).getPicture(extra.getString("meal"));
		mealID = DatabaseController.getInstanceMeals(getApplicationContext()).getId(extra.getString("meal")); 

		setPicture();

		name.setText(extra.getString("meal"));
		setIngredient();
	}

	private void setPicture() {
		if( photo == null ) {
			picture.setImageResource(R.drawable.light_ic_unknow);
		}
		else {
			File test = new File(photo.getPath());
			if( test.exists() ) picture.setImageURI(photo);
			else picture.setImageResource(R.drawable.light_ic_unknow);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		setPicture();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_camera_save_delete, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_modif:
			editPicture();
			return true;
		case R.id.action_delete:
			deleteMeal();
			return true;
		case R.id.action_save:
			saveMeal();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void editPicture() {
		// Create a file for a photo.
		photo = Uri.fromFile(createImageFile());

		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

		// start the image capture Intent
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}

	public void editPicture(View view) {
		editPicture();
	}

	private void deleteMeal() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_deleting_meal)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				DatabaseController.getInstanceMeals(getApplicationContext()).delete(name.getText().toString());
				//Intent intent = new Intent(getApplicationContext(), ListMealActivity.class);
				//startActivity(intent);
				finish();
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		builder.show();
	}

	private void setIngredient() {
		LinearLayout tableIgd  = (LinearLayout) findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getApplicationContext(), name.getText().toString(), true);
		tableIgd.addView(igd.getTableLayout());
	}

	private void saveMeal() {

		// Field verification.
		if (name.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal_name, Toast.LENGTH_SHORT).show();
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_save_meal)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				String pcr=null;

				if( photo != null ) {
					File test = new File(photo.getPath());
					if( test.exists() ) pcr =  photo.getPath();
				}

				DatabaseController.getInstanceMeals(getApplicationContext()).update(mealID, name.getText().toString(), pcr, -1);
				igd.addInDatabase(mealID);
				igd.deleteInDatabase(mealID);

				finish();
				//Intent intent = new Intent(getApplicationContext(), ListMealActivity.class);
				//startActivity(intent);
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		builder.show();

	}

	/**
	 * Called when the user finished to take his picture.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,requestCode,data);

		try {
			setPicture();
		}
		catch(Exception e) {

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
				return null;
			}
		}

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File image = new File(storageDir.getPath() + File.separator +
				"IMG_"+ timeStamp + ".jpg");

		return image;
	}


	public void addIngredient(View v) {
		EditText igdEdit   = (EditText) findViewById(R.id.newIngredient);
		String igdName = igdEdit.getText().toString();

		if( igdName.isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal_name, Toast.LENGTH_SHORT).show();
			igdEdit.setText("");
			return ;
		}

		if( !igd.getIngredientList().isEmpty() && igd.getIngredientList().contains(igdName) ) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_igd, Toast.LENGTH_SHORT).show();
			igdEdit.setText("");
			return;
		}

		DatabaseController.getInstanceIngredient(getApplicationContext()).add(igdName);
		igd.getIngredientList().add(igdName);
		igd.newIngredient(igdName);
		LinearLayout tableIgd  = (LinearLayout) findViewById(R.id.list_ingredient); 
		tableIgd.removeAllViews();
		tableIgd.addView(igd.getTableLayout());
		igdEdit.setText("");
	}
}
