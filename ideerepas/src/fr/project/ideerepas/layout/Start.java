package fr.project.ideerepas.layout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.layout.meals.ListMeal;


public class Start extends ListActivity {

	private static String TAG = Start.class.getName();

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		String[] values = new String[] { getString(R.string.liste_repas),
				getString(R.string.liste_menu),
				getString(R.string.liste_course), getString(R.string.quitter) };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Log.i(TAG,"Info : menu choisie.");


		switch (position) {
		case 0: // Mes repas.
			Intent intent = new Intent(getApplicationContext(), ListMeal.class);
			startActivity(intent);
			break;

		case 1: // Menus.
			break;

		case 2: // Liste de course.
			break;

		case 3: // Quitter.
			finish();
			//System.exit(0);
			break;
		default:
			break;
		}


	}
}