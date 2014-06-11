package fr.project.ideerepas.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.database.Meals;

public class MealLayout extends Activity {

	private static String TAG = MealLayout.class.getName();
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

		name.setText(extra.getString("meal"));

		m_list = new Meals(getApplicationContext());

		Uri pathPicture = null;
		if( m_list.getPicture(extra.getString("meal")) != null) {
			pathPicture = m_list.getPicture(extra.getString("meal"));
		}
		if(pathPicture != null) {
			picture.setImageURI(pathPicture);
		}

		setIngredient();

	}

	private void setIngredient() {
		LinearLayout tableIgd  = (LinearLayout) findViewById(R.id.list_ingredient); 
		IngredientLayout igd = new IngredientLayout(getApplicationContext(), name.getText().toString(), false);
		tableIgd.addView(igd.getTableLayout());
	}

	public void modifMeal(View view) {
		Intent intent = new Intent(getApplicationContext(), ModifMeal.class);
		intent.putExtra("meal", getIntent().getExtras().getString("meal"));
		startActivity(intent);
		finish();
	}

	public void deleteMeal(View view) {
		if( m_list == null) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_deleting_meal)

		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				m_list.delete(name.getText().toString());
				Intent intent = new Intent(getApplicationContext(), ListMealLayout.class);
				startActivity(intent);
				finish();
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		builder.show();
	}


}
