package fr.project.ideerepas.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.meal.Functionality;
import fr.project.ideerepas.meal.Meals;
import fr.project.ideerepas.meal.Menus;

public class Adding extends Activity {

	private static String TAG = Adding.class.getName();
	private Functionality func;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.add);

		TextView add = (TextView) findViewById(R.id.name);

		switch (getIntent().getExtras().getInt("type")) {
		case 1:
			setTitle(getString(R.string.new_meal));
			add.setText(R.string.name_meal);
			break;

		case 2:
			setTitle(getString(R.string.new_menu));
			add.setText(R.string.name_menu);
			break;

		default:
			break;
		}
	}

	public void addNew(View view) {
		String message = null;

		switch (getIntent().getExtras().getInt("type")) {
		case 1:
			message = getString(R.string.popup_adding_meal);
			this.func = new Meals(getApplicationContext());
			break;

		case 2:
			message = getString(R.string.popup_adding_menu);
			this.func = new Menus(getApplicationContext());
			break;

		default:
			break;
		}

		// Field verification.
		EditText editName = (EditText) findViewById(R.id.name_edit);
		
		if (editName.getText().toString().isEmpty()) {
            editName.setError(this.getResources().getString(R.string.error_name));
            func = null;
            return;
        }
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage(message)
		
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				EditText editName = (EditText) findViewById(R.id.name_edit);
				String name = editName.getText().toString();
				func.add(name, null, -1);
				Log.i(TAG, "Ajout de "+name+" dans la base de données.");
				finish();
				Intent intent = new Intent(getApplicationContext(), List.class);
				intent.putExtra("type", getIntent().getExtras().getInt("type"));
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
