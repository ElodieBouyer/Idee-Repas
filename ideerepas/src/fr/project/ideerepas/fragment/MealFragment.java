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
import fr.project.ideerepas.database.Meals;

public class MealFragment extends Fragment {

	private IngredientLayout igd;
	private String name;
	private Meals m_list = null;

	public MealFragment(String meal) {
		name = meal;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mealView = inflater.inflate(R.layout.meal, container, false);
		m_list = new Meals(getActivity().getApplicationContext());
		
		// Meal name.
		TextView n = (TextView) mealView.findViewById(R.id.mealName);
		n.setText(name);
		// ***

		// Meal picture.
		ImageView picture = (ImageView) mealView.findViewById(R.id.mealPicture);
		Uri pathPicture = null;
		if( m_list.getPicture(name) != null) {
			pathPicture = m_list.getPicture(name);
		}
		if(pathPicture != null) {
			picture.setImageURI(pathPicture);
		}
		// ***
		
		setIngredient(mealView);

		return mealView;
	}


	private void setIngredient(View v) {
		LinearLayout tableIgd  = (LinearLayout) v.findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getActivity().getApplicationContext(), name, false);
		tableIgd.removeAllViews();
		tableIgd.addView(igd.getTableLayout());
	}

	public IngredientLayout getIngredientLayout() {
		return igd;
	}
	
	public void deleteMeal() {
		m_list.delete(name);
	}
	
	public String getName() {
		return name;
	}
}
