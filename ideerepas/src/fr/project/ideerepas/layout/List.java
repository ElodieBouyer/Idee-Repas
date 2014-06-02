package fr.project.ideerepas.layout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Functionality;
import fr.project.ideerepas.meal.Meals;
import fr.project.ideerepas.meal.Menus;

public class List extends Activity {

	private Functionality m_list = null;
	private static String TAG = Start.class.getName();

	private void createFunctionality(Bundle bundle) {
		Log.i(TAG,"Info : start activity.");
		if( bundle == null ) Log.i(TAG,"Info : bundle null.");
		if (this.m_list == null) {
			int type = bundle.getInt("type");
			switch (type) {
			case 1:
				this.m_list = new Meals();
				break;

			case 2:
				this.m_list = new Menus();
				break;

			default:
				break;
			}
		}
	}

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.list);

		// Determine which functionality need.
		createFunctionality(getIntent().getExtras());

		// We retrieve name meals.
		String[] values = this.m_list.getNames();

		// If the list is empty.
		if (values == null) {
			TextView message = (TextView) findViewById(R.id.emptyText);
			message.setText(getString(R.string.empty_meals));
			return;
		}

		// Else, to do a meal name list.*/

	}

}
