package fr.project.ideerepas.layout.meals;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.layout.ListRow;
import fr.project.ideerepas.layout.Start;
import fr.project.ideerepas.meal.Meals;

public class ListMeal extends Activity {

	private static String TAG = Start.class.getName();

	private Meals m_list      = null;
	private String[] pictures = null;
	private String[] names    = null;
	private ListView lview;

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

		if( rows != null) {
			int i = 0;
			names    = new String[rows.size()];
			pictures = new String[rows.size()];
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

		setListView();
	}

	private void setListView() {
		ListRow adapter = 
				new ListRow(this, names, pictures);
		lview = (ListView) findViewById(R.id.listView);
		lview.setAdapter(adapter);

		lview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				Intent intent = new Intent(getApplicationContext(), Meal.class);
				intent.putExtra("meal", names[position]);
				startActivity(intent);
				m_list = null;
				finish();
			} 
		});
	}

	public void add(View view) {
		Intent intent = new Intent(getApplicationContext(), AddMeal.class);
		startActivity(intent);
		finish();
	}

}
