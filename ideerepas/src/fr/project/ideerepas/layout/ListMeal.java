package fr.project.ideerepas.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class ListMeal extends Activity {

	private Meals m_list = null;
	private static String TAG = Start.class.getName();

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.list_meal);
		Log.i(TAG, "Activity ListMeal créée.");

		// Activity title.
		setTitle(getString(R.string.liste_repas));
		
		if (this.m_list == null) {
			this.m_list = new Meals(getApplicationContext());
		}

		// We retrieve name meals.
		String[] values = this.m_list.getNames();

		// If the list is empty.
		if (values == null) {
			TextView message = (TextView) findViewById(R.id.emptyText);
			message.setText(getString(R.string.empty_meals));
			return;
		}

		// Else, to do a meal or menu name list.
		LinearLayout list = (LinearLayout) findViewById(R.id.listView);
		ListView lview = new ListView(getApplicationContext());

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		lview.setAdapter(adapter);

		list.addView(lview);
	}

	public void add(View view) {
		Intent intent = new Intent(getApplicationContext(), AddMeal.class);
		startActivity(intent);
		finish();
	}

}
