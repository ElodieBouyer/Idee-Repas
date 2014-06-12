package fr.project.ideerepas.layout;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
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

	public IngredientLayout(Context context, String mealName, Boolean add) {
		this.context  = context;
		this.igdMeal  = new IngredientMeal(context);
		this.tableIgd = new TableLayout(context);

		List<String> names = igdMeal.getIngredient(mealName);
		if( names != null) {
			for( String name : names) {
				addIngredient(name);
			}
		}
		else {
			if( !add) addEmpty();
		}
	}

	public void newIngredient(String igdName) {
		addIngredient(igdName);
	}
	
	private void addIngredient(String igdName) {
		TableRow    row    = new TableRow(context);
		TextView    name   = new TextView(context);
		ImageButton delete = new ImageButton(context);

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		name.setText(igdName);
		name.setTextAppearance(context, R.style.layoutText);
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
		TableRow    row    = new TableRow(context);
		TextView    name   = new TextView(context);

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		name.setText(R.string.empty_ingredient);
		name.setTextAppearance(context, R.style.emptyText);
		name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		name.setPadding(10, 5, 10, 10);

		row.addView(name);
		tableIgd.addView(row);
	}

	public TableLayout getTableLayout() {
		return tableIgd;
	}
}
