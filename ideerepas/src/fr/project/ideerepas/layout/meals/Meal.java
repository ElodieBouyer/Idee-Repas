package fr.project.ideerepas.layout.meals;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class Meal extends Activity {

	private static String TAG = Meal.class.getName();
	private Meals m_list      = null;
	private TextView name;
	private ImageView picture;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.meal);

		Log.i(TAG, "Activity meal créée.");

		Bundle extra = getIntent().getExtras();

		if( extra.getString("meal") == null) {
			return;
		}

		name    = (TextView)  findViewById(R.id.mealName);
		picture = (ImageView) findViewById(R.id.mealPicture);

		m_list = new Meals(getApplicationContext());
		Uri pathPicture = null;
		if( m_list.getPicture(extra.getString("meal")) != null) {
			pathPicture = m_list.getPicture(extra.getString("meal"));
		}

		Log.i(TAG, "Name="+extra.getString("meal"));
		Log.i(TAG, "Picture="+pathPicture.toString());

		if(pathPicture != null) {
			picture.setImageURI(pathPicture);
		}

		name.setText(extra.getString("meal"));
	}

	public void modifMeal(View view) {
		Intent intent = new Intent(getApplicationContext(), ModifMeal.class);
		intent.putExtra("meal", getIntent().getExtras().getString("meal"));
		startActivity(intent);
		finish();
	}

}
