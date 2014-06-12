package fr.project.ideerepas.layout;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.database.IngredientMeal;

public class IngredientLayout {

	private TableLayout tableIgd = null;
	private Context context;
	private IngredientMeal igdMeal;
	private Button adding = null;
	private EditText edit = null;

	public IngredientLayout(Context context, String mealName, Boolean add) {
		this.context = context;
		this.igdMeal = new IngredientMeal(context);


		List<String> names = igdMeal.getIngredient(mealName);
		if( names != null) {
			for( String name : names) {
				addIngredient(name);
			}
		}
		else addEmpty();
		if( add ) {
			adding = new Button(context);
			edit = new EditText(context);
			addButton();
		}
	}

	private void addIngredient(String igdName) {
		if( tableIgd == null ) tableIgd = new TableLayout(context);
		TableRow    row    = new TableRow(context);
		TextView    name   = new TextView(context);
		ImageButton delete = new ImageButton(context);

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		name.setText(igdName);
		name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		name.setPadding(10, 10, 10, 10);

		delete.setBackgroundResource(R.drawable.dark_ic_action_discard);
		delete.setPadding(10, 5, 10, 10);


		row.addView(name);
		row.addView(delete);

		tableIgd.addView(row);
	}

	private void addEmpty() {
		if( tableIgd == null ) tableIgd = new TableLayout(context);
		TableRow    row    = new TableRow(context);
		TextView    name   = new TextView(context);

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		name.setText(R.string.empty_ingredient);
		name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		name.setPadding(10, 5, 10, 10);

		row.addView(name);
		tableIgd.addView(row);
	}

	private void addButton() {
		if( tableIgd == null ) tableIgd = new TableLayout(context);
		TableRow row    = new TableRow(context);

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		edit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		edit.setFocusable(true);

		adding.setText(R.string.add);
		adding.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));

		row.addView(edit);
		row.addView(adding);

		tableIgd.addView(row);
	}

	public TableLayout getTableLayout() {
		return tableIgd;
	}

	public Button getButtonAdd() {
		return adding;
	}

	public EditText getEditView() {
		return edit;
	}
}
