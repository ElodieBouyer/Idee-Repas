package fr.project.ideerepas.layout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Functionality;
import fr.project.ideerepas.meal.Meals;
import fr.project.ideerepas.meal.Menus;

public class List extends Activity {

	private Functionality m_list = null;
	private static String TAG = Start.class.getName();

	private void createFunctionality() {
		if (this.m_list == null) {
			switch (getIntent().getExtras().getInt("type")) {
			case 1:
				this.m_list = new Meals();
				setTitle(getString(R.string.liste_repas));
				break;

			case 2:
				this.m_list = new Menus();
				setTitle(getString(R.string.liste_menu));
				break;

			default:
				break;
			}
		}
	}

	private String displayEmptyText() {
		switch( getIntent().getExtras().getInt("type") ) {
		case 1:
			return getString(R.string.empty_meals);

		case 2:
			return getString(R.string.empty_menu);

		default:
			return "";
		}
	}

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.list);
		Log.i(TAG, "Activity créée.");

		// Determine which functionality need.
		createFunctionality();

		// *** Test ***
		for(int i = 0 ; i < 20 ; i++ ) {
			this.m_list.add("Test"+i);
		}
		// ***
		
		// We retrieve name meals.
		String[] values = this.m_list.getNames();

		// If the list is empty.
		if (values == null) {
			TextView message = (TextView) findViewById(R.id.emptyText);
			message.setText(displayEmptyText());
			return;
		}
		
		// Else, to do a meal or menu name list.*/
		ListView list = (ListView) findViewById(R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		list.setAdapter(adapter);
	}

}
