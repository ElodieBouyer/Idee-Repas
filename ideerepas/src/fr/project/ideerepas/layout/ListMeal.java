package fr.project.ideerepas.layout;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class ListMeal extends Activity {

	private static String TAG = Start.class.getName();

	private Meals m_list      = null;
	private String[] pictures = null;
	private String[] names    = null;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.list_meal);
		Log.i(TAG, "Activity ListMeal créée.");

		// Activity title.
		setTitle(getString(R.string.liste_repas));

		if (this.m_list == null) {
			this.m_list = new Meals(getApplicationContext());
		}

		Map<String,String> rows = new HashMap<String, String>();
		rows = this.m_list.getCoupleNamePicture();
		names    = new String[rows.size()];
		pictures = new String[rows.size()];

		if( rows != null) {
			int i = 0;
			for (Map.Entry<String, String> entry : rows.entrySet()) {
				names[i]    = entry.getKey();
				pictures[i] = entry.getValue();
				i++;
			}
		}

		// If the list is empty.
		if (names == null) {
			TextView message = (TextView) findViewById(R.id.emptyText);
			message.setText(getString(R.string.empty_meals));
			return;
		}

		ListRow adapter = 
				new ListRow(this, names, pictures);
		ListView lview = (ListView) findViewById(R.id.listView);
		lview.setAdapter(adapter);
	}

	public void add(View view) {
		Intent intent = new Intent(getApplicationContext(), AddMeal.class);
		startActivity(intent);
		finish();
	}

}
