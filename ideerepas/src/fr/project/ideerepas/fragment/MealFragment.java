package fr.project.ideerepas.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.activity.IngredientLayout;
import fr.project.ideerepas.database.MealsDatabase;
import fr.project.ideerepas.view.FrequencyView;

public class MealFragment extends Fragment {

	private IngredientLayout igd;
	private String mName;
	private MealsDatabase mealsDatabase = null;


	public MealFragment(String meal) {
		mName = meal;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mealView = inflater.inflate(R.layout.fragment_meal, container, false);
		mealsDatabase = new MealsDatabase(getActivity().getApplicationContext());

		// Meal mName.
		TextView mNameMeal = (TextView) mealView.findViewById(R.id.name);
		mNameMeal.setText(mName);
		// ***

		// Meal picture.
		ImageView picture = (ImageView) mealView.findViewById(R.id.picture);
		Uri pathPicture = null;
		if( mealsDatabase.getPicture(mName) != null) {
			pathPicture = mealsDatabase.getPicture(mName);
		}
		if(pathPicture != null) {
			picture.setImageURI(pathPicture);
		}
		// ***

		setIngredient(mealView);
		setFrequency(mealView);

		return mealView;
	}

	private void setIngredient(View v) {
		LinearLayout tableIgd  = (LinearLayout) v.findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getActivity().getApplicationContext(), mName, false);
		tableIgd.removeAllViews();
		tableIgd.addView(igd.getTableLayout());
	}

	private void setFrequency(View v) {
		TextView frequencyView = (TextView) v.findViewById(R.id.frequency);
		int numFrequency = mealsDatabase.getFrequency(mName); 

		FrequencyView fqr = new FrequencyView(getActivity());
		frequencyView.setText(fqr.getFrequency(numFrequency));
	}

	public IngredientLayout getIngredientLayout() {
		return igd;
	}

	public void deleteMeal() {
		mealsDatabase.delete(mName);
	}

	public String getName() {
		return mName;
	}
}
