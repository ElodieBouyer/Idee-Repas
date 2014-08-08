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
import android.widget.RadioButton;
import fr.project.ideerepas.R;
import fr.project.ideerepas.activity.IngredientLayout;
import fr.project.ideerepas.database.MealsDatabase;

public class EditMealFragment extends Fragment {

	private MealsDatabase mealsDatabase = null;
	private IngredientLayout igd;

	private String mMealName;
	private int mMealId;
	private String mMealPicturePath;

	public EditMealFragment(String meal) {
		mMealName = meal;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View editView = inflater.inflate(R.layout.fragment_edit_meal, container, false);
		mealsDatabase = new MealsDatabase(getActivity().getApplicationContext());
		mMealId = mealsDatabase.getId(mMealName);

		// Set meal mMealName.
		EditText mMealNameMeal = (EditText)  editView.findViewById(R.id.name);
		mMealNameMeal.setText(mMealName);
		// ***

		// Set picture.
		ImageView picture = (ImageView) editView.findViewById(R.id.picture);
		Uri photo = mealsDatabase.getPicture(mMealName);
		if( photo == null ) {
			picture.setImageResource(R.drawable.light_ic_unknow);
		}
		else {
			File test = new File(photo.getPath());
			if( test.exists() ) {
				picture.setImageURI(photo);
				mMealPicturePath = photo.getPath();
			}
			else picture.setImageResource(R.drawable.light_ic_unknow);

		}
		// ***

		// Set Ingredient.
		LinearLayout tableIgd  = (LinearLayout) editView.findViewById(R.id.list_ingredient); 
		igd = new IngredientLayout(getActivity().getApplicationContext(), mMealName, true);
		tableIgd.addView(igd.getTableLayout());
		// ***

		// Set frequency.
		int frequency = mealsDatabase.getFrequency(mMealName);
		RadioButton btn = null;
		switch(frequency) {
		case 0:
			btn = (RadioButton) editView.findViewById(R.id.souvent);
			break;
		case 1:
			btn = (RadioButton) editView.findViewById(R.id.occasionnellement);
			break;
		case 2:
			btn = (RadioButton) editView.findViewById(R.id.regulierement);
			break;
		case 3:
			btn = (RadioButton) editView.findViewById(R.id.rare);
			break;
		}
		btn.setChecked(true);
		// ***

		return editView;
	}

	public String getName() {
		return mMealName;
	}
	
	public String getMealPicturePath() {
		return mMealPicturePath;
	}

	public void deleteMeal() {
		mealsDatabase.delete(mMealName);
	}

	public IngredientLayout getIngredientLayout() {
		return igd;
	}

	public MealsDatabase getMealsDatabase() {
		return mealsDatabase;
	}

	public int getMealId() {
		return mMealId;
	}
}
