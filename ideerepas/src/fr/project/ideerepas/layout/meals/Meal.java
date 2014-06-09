package fr.project.ideerepas.layout.meals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class Meal extends Activity {

	private static String TAG = Meal.class.getName();
	private Meals m_list      = null;
	private TextView name;
	private ImageView picture;
	private TableLayout tableIgd;

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
		
		tableIgd  = (TableLayout) findViewById(R.id.list_ingredient); 
		setIngredient("test");
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
				Intent intent = new Intent(getApplicationContext(), ListMeal.class);
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
	
	private void setIngredient(String igdName) {
		TableRow    row    = new TableRow(getApplicationContext());
		TextView    name   = new TextView(getApplicationContext());
		ImageButton delete = new ImageButton(getApplicationContext());

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
		name.setText(igdName);
		name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		name.setPadding(10, 10, 10, 10);

		delete.setBackgroundResource(R.drawable.delete);
		delete.setPadding(10, 10, 10, 10);
		
		
		row.addView(name);
		row.addView(delete);

		tableIgd.addView(row);
	}

}
