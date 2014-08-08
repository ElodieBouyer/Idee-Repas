package fr.project.ideerepas.fragment;

import java.io.File;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import fr.project.ideerepas.R;
import fr.project.ideerepas.activity.IngredientLayout;
import fr.project.ideerepas.database.MealsDatabase;

public class EditMealFragment extends Fragment {

	private String name;
	private MealsDatabase mealsDatabase = null;
	private IngredientLayout igd;
	private int idMeal;

	public EditMealFragment(String meal) {
		name = meal;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View editView = inflater.inflate(R.layout.modif_meal, container, false);
		mealsDatabase = new MealsDatabase(getActivity().getApplicationContext());
		idMeal = mealsDatabase.getId(name);

		// Set meal name.
		EditText nameMeal = (EditText)  editView.findViewById(R.id.mealName);
		nameMeal.setText(name);
		// ***

		// Set picture.
		ImageView picture = (ImageView) editView.findViewById(R.id.mealPicture);
		Uri photo = mealsDatabase.getPicture(name);
		if( photo == null ) {
			picture.setImageResource(R.drawable.light_ic_unknow);
		}
		else {
			File test = new File(photo.getPath());
			if( test.exists() ) picture.setImageURI(photo);
			else picture.setImageResource(R.drawable.light_ic_unknow);
		}
		// ***

		// Set Ingredient.
		LinearLayout tableIgd  = (LinearLayout) editView.findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getActivity().getApplicationContext(), name, true);
		tableIgd.addView(igd.getTableLayout());
		// ***

		return editView;
	}

	public String getName() {
		return name;
	}

	public void deleteMeal() {
		mealsDatabase.delete(name);
	}

	public IngredientLayout getIngredientLayout() {
		return igd;
	}

	public MealsDatabase getMealsDatabase() {
		return mealsDatabase;
	}

	public int getIdMeal() {
		return idMeal;
	}
}
