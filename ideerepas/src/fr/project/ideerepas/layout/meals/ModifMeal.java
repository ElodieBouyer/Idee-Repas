package fr.project.ideerepas.layout.meals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class ModifMeal extends Activity {

	private Meals m_list      = null;
	private EditText name;
	private ImageView picture;
	private Uri photo=null;
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private int mealID;


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

		m_list = new Meals(getApplicationContext());
		photo  = m_list.getPicture(extra.getString("meal"));
		mealID = m_list.getId(extra.getString("meal")); 

		if( photo != null ) picture.setImageURI(photo);
		else picture.setImageResource(R.drawable.interrogation);
		
		name.setText(extra.getString("meal"));
	}

	public void modifPicture(View view) {
		// Create a file for a photo.
		photo = Uri.fromFile(createImageFile());

		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

		// start the image capture Intent
		startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
	}

	public void save(View view) {

		// Field verification.
		if (name.getText().toString().isEmpty()) {
			name.setError(this.getResources().getString(R.string.error_name));
			return;
		}

		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.saves);
		builder.show();*/

		String picture;

		if( photo != null ) picture =  photo.toString();
		else picture = null;

		m_list.update(mealID, name.getText().toString(), picture, -1);

		finish();
		Intent intent = new Intent(getApplicationContext(), ListMeal.class);
		startActivity(intent);
	}

	/**
	 * Called when the user finished to take his picture.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,requestCode,data);

		try {
			// *** On la met dans l'ImageView.
			if( photo != null ) picture.setImageURI(photo);
			else picture.setImageResource(R.drawable.interrogation);
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
}
