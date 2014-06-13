package fr.project.ideerepas.layout;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.database.Meals;

public class ListMealLayout extends Activity {

	private Meals m_list      = null;
	private String[] pictures = null;
	private String[] names    = null;
	private ListView lview;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.list_meal);
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

		TextView message = (TextView) findViewById(R.id.emptyText);
		// If the list is empty.
		if (names == null) {
			message.setVisibility(View.VISIBLE);
			return;
		}
		message.setVisibility(View.GONE);
		setListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_add, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_add:
			Intent intent = new Intent(getApplicationContext(), AddMealActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setListView() {
		ListRow adapter = 
				new ListRow(this, names, pictures);
		lview = (ListView) findViewById(R.id.listView);
		lview.setAdapter(adapter);

		lview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				Intent intent = new Intent(getApplicationContext(), MealLayout.class);
				intent.putExtra("meal", names[position]);
				startActivity(intent);
				m_list = null;
			} 
		});
	}

}
