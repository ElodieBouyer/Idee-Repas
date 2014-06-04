package fr.project.ideerepas.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Meals;

public class AddMeal extends Activity {

	private static String TAG = AddMeal.class.getName();
	private Meals func;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.add_meal);
		setTitle(getString(R.string.new_meal));
	}

	public void addNew(View view) {
		this.func = new Meals(getApplicationContext());

		// Field verification.
		EditText editName = (EditText) findViewById(R.id.name_edit);

		if (editName.getText().toString().isEmpty()) {
			editName.setError(this.getResources().getString(R.string.error_name));
			func = null;
			return;
		}


		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_adding_meal)

		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				EditText editName = (EditText) findViewById(R.id.name_edit);
				String name = editName.getText().toString();
				func.add(name, null, -1);
				Log.i(TAG, "Ajout de "+name+" dans la base de données.");
				finish();
				Intent intent = new Intent(getApplicationContext(), ListMeal.class);
				startActivity(intent);
				finish();
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});

		builder.show();
	}

}
