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
import fr.project.ideerepas.view.FrequencyView;

public class EditMealFragment extends Fragment {

	private MealsDatabase mealsDatabase = null;
	private IngredientLayout igd;

	private String mMealName;
	private int mMealId;
	private String mMealPicturePath;
	private FrequencyView fqView;

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

		// *** Set frequency. ***
		// Get frequency in the database.
		int frequency = mealsDatabase.getFrequency(mMealName);

		// Update picker.
		fqView = new FrequencyView(getActivity());
		fqView.updatePicker(editView);

		// Update radio button.
		RadioButton btn = null;
		if( frequency < 4 ) { // Predefine.
			btn = (RadioButton) editView.findViewById(R.id.frequency_predefine_label);
			fqView.predefine();
		}
		else { // Personalize.
			btn = (RadioButton) editView.findViewById(R.id.frequency_perso_label);
			fqView.personalize();
		}
		btn.setChecked(true);
		// ***

		return editView;
	}

	public void personalize() {
		if( fqView != null ) fqView.personalize();
	}
	
	public void predefine() {
		if( fqView != null ) fqView.predefine();
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
