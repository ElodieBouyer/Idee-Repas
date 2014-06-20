package fr.project.ideerepas.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import fr.project.ideerepas.R;
import fr.project.ideerepas.database.IngredientMeal;
import fr.project.ideerepas.database.Ingredients;
import fr.project.ideerepas.database.Meals;

public class IngredientLayout {

	private TableLayout tableIgd = null;
	private Context context;
	private IngredientMeal igdMeal;
	private Ingredients igdDatabase;
	private Meals mealsDatabase;
	private List<String> igdList;
	private List<String> igdDelete;

	public IngredientLayout(Context context, String mealName, Boolean add) {
		this.context     = context;
		this.igdMeal     = new IngredientMeal(context);
		this.igdDatabase = new Ingredients(context);
		this.tableIgd    = new TableLayout(context);
		this.mealsDatabase = new Meals(context);
		this.igdList     = new ArrayList<String>();
		this.igdDelete   = new ArrayList<String>();

		if(mealName == null) return ;
		List<String> names = igdMeal.getIngredient(mealName);
		if( names != null) {
			for( String name : names) {
				addIngredient(name, add);
			}
		}
		else {
			if( !add) addEmpty();
		}
	}

	public void newIngredient(String igdName) {
		addIngredient(igdName, true);
	}

	private void addIngredient(String igdName, boolean modeAdd) {
		igdList.add(igdName);
		TableRow    row    = new TableRow(context);
		TextView    name   = new TextView(context);

		row.setGravity(Gravity.CENTER);
		row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		name.setEnabled(false);
		name.setFocusable(false);
		name.setBackgroundResource(R.drawable.edit_style);
		name.setText(igdName);
		name.setTextAppearance(context, R.style.layoutText);
		name.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		name.setPadding(10, 10, 10, 10);

		row.addView(name);

		if( modeAdd ) {
			ImageButton delete = new ImageButton(context);

			delete.setBackgroundResource(R.drawable.light_ic_action_discard);
			delete.setPadding(30, 5, 10, 10);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					TableRow r = (TableRow) v.getParent();
					TextView e = (TextView) r.getVirtualChildAt(0);
					igdList.remove(e.getText().toString());
					igdDelete.add(e.getText().toString());
					tableIgd.removeView(r);
					Toast.makeText(context, "Ingredient supprimé", Toast.LENGTH_SHORT).show();
				}
			});

			row.addView(delete);
		}

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

	public List<String> getIngredientList() {
		return igdList;
	}


	public void addInDatabase(int mealId) {
		for(String n : igdList ) {
			int idIgd  = igdDatabase.getID(n);
			igdMeal.add(mealId, idIgd);
		}
	}

	public void deleteInDatabase(int mealId) {
		for(String n : igdDelete ) {
			int idIgd  = igdDatabase.getID(n);
			igdMeal.delete(mealId, idIgd);
		}
	}

	public Ingredients getIngredientDatabase() {
		return igdDatabase;
	}

	public Meals getMealsDatabase() {
		return mealsDatabase;
	}
}
