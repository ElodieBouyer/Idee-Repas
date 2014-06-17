package fr.project.ideerepas.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import fr.project.ideerepas.R;
import fr.project.ideerepas.activity.IngredientLayout;

public class AddMealFragment extends Fragment {

	private IngredientLayout igd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View addMealView = inflater.inflate(R.layout.add_meal, container, false);
		setIngredient(addMealView);;

		return addMealView;
	}


	private void setIngredient(View v) {
		LinearLayout tableIgd  = (LinearLayout) v.findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getActivity().getApplicationContext(), null, true);
		tableIgd.addView(igd.getTableLayout());
	}

	public IngredientLayout getIngredientLayout() {
		return igd;
	}
	
}
