package fr.project.ideerepas.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import fr.project.ideerepas.R;
import fr.project.ideerepas.adapter.TabsPagerAdapter;
import fr.project.ideerepas.fragment.AddMealFragment;
import fr.project.ideerepas.fragment.EditMealFragment;
import fr.project.ideerepas.fragment.MealFragment;
import fr.project.ideerepas.fragment.MealListFragment;
import fr.project.ideerepas.fragment.MenuFragment;

/**
 * @author elodie.bouyer@hotmail.fr
 *
 */
public class MainActivity extends FragmentActivity implements TabListener {

	private final int MENU = 0;
	private final int MEAL_LIST = 1;
	private final int ADD  = 2;
	private final int MEAL = 3;
	private final int EDIT = 4;

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private int position = MENU;
	private Menu menu = null;
	private Fragment currentFragment = new MealListFragment();
	private Uri photo=null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_layout);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       

		String[] tabs = { 
				getApplicationContext().getResources().getString(R.string.liste_menu),
				getApplicationContext().getResources().getString(R.string.liste_repas)};

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu m) {
		if( m == null ) return false;
		
		MenuInflater inflater = getMenuInflater();
		m.clear();

		switch (position) {
		case MENU:	
			inflater.inflate(R.menu.action_generate_delete, m);
			break;

		case MEAL_LIST:
			inflater.inflate(R.menu.action_add, m);

			break;

		case ADD:
			inflater.inflate(R.menu.actions_back_camera_save, m);
			break;

		case MEAL:
			inflater.inflate(R.menu.actions_back_edit_delete,m);
			break;

		case EDIT:
			inflater.inflate(R.menu.actions_back_camera_save_delete, m);
			break;
		default:
			break;
		}
		menu = m;
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		switch (item.getItemId()) {

		case R.id.action_back:
			returnInMealList();
			return true;

		case R.id.action_add:
			currentFragment = new AddMealFragment();
			transaction.replace(R.id.fragment_container, currentFragment);
			transaction.commit();
			position = ADD;
			onCreateOptionsMenu(menu);
			return true;

		case R.id.action_camera:
			addPicture();
			return true;

		case R.id.action_save:
			addNew();
			return true;

		case R.id.action_delete:
			deleteMeal();
			return true;

		case R.id.action_modif:
			String name = ((MealFragment) currentFragment).getName();
			currentFragment = new EditMealFragment(name);
			transaction.replace(R.id.fragment_container, currentFragment);
			transaction.commit();
			position = EDIT;
			onCreateOptionsMenu(menu);
			return true;

		case R.id.action_update:
			updateMeal();
			return true;

		case R.id.action_back_meal:
			String nameMeal = ((EditMealFragment) currentFragment).getName();
			clickOnList(nameMeal);
			return true;
			
		case R.id.action_generate:
			generate();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

		if(  tab.getPosition() == MENU ) {
			position = MENU;
		}
		else if( currentFragment.getClass().getName().equals(MealListFragment.class.getName()))  {
			position = MEAL_LIST;
			ft.replace(R.id.fragment_container,currentFragment);
		}
		else if( currentFragment.getClass().getName().equals(AddMealFragment.class.getName()))  {
			position = ADD;
			ft.replace(R.id.fragment_container,currentFragment);
		}
		else if( currentFragment.getClass().getName().equals(MealFragment.class.getName())) {
			position = MEAL;
			ft.replace(R.id.fragment_container,currentFragment);
		}
		else if( currentFragment.getClass().getName().equals(EditMealFragment.class.getName())) {
			position = EDIT;
			ft.replace(R.id.fragment_container,currentFragment);
		}

		onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		menu.clear();
	}

	public void clickOnList(String meal) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		currentFragment = new MealFragment(meal);
		transaction.replace(R.id.fragment_container, currentFragment);
		transaction.commit();
		position = MEAL;
		onCreateOptionsMenu(menu);
	}

	/**
	 * Called when user click in the picture.
	 * @param v
	 */
	public void addPicture(View v) {
		addPicture();
	}

	/**
	 * Called when user click in the button to add a picture.
	 */
	public void addPicture() {
		// Create a file for a photo.
		photo = Uri.fromFile(createImageFile());

		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

		// start the image capture Intent
		startActivityForResult(intent, 1);
	}

	/**
	 * To add an ingredient to a meal.
	 * @param v
	 */
	public void addIngredient(View v) {
		EditText igdEdit   = (EditText) findViewById(R.id.newIngredient);
		String igdName = igdEdit.getText().toString();
		IngredientLayout igd = null;

		switch(position) {
		case ADD:
			igd = ((AddMealFragment) currentFragment).getIngredientLayout();
			break;

		case EDIT:
			igd = ((EditMealFragment) currentFragment).getIngredientLayout();
		}

		if( igdName.isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal_name, Toast.LENGTH_SHORT).show();
			igdEdit.setText("");
			return ;
		}

		if( !igd.getIngredientList().isEmpty() && igd.getIngredientList().contains(igdName) ) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_igd, Toast.LENGTH_SHORT).show();
			igdEdit.setText("");
			return;
		}
		igd.getIngredientDatabase().add(igdName);
		igd.getIngredientList().add(igdName);
		igd.newIngredient(igdName);
		LinearLayout tableIgd  = (LinearLayout) findViewById(R.id.list_ingredient); 
		tableIgd.removeAllViews();
		tableIgd.addView(igd.getTableLayout());
		igdEdit.setText("");
	}


	/**
	 * Called to prepare picture.
	 * @return The picture as a file format.
	 */
	private File createImageFile() {

		File storageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), "IdeeRepas");

		// Create the storage directory if it does not exist
		if (! storageDir.exists()){
			if (! storageDir.mkdirs()){
				return null;
			}
		}

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File image = new File(storageDir.getPath() + File.separator +
				"IMG_"+ timeStamp + ".jpg");

		return image;
	}

	/**
	 * Called when the user finished to take his picture.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,requestCode,data);

		try {
			// *** On la met dans l'ImageView.
			setPicture();
		}
		catch(Exception e) {
			Log.i("MainActivity", "onActivityResutl() "+ e.toString());
		}
	}

	/**
	 * Update the ImageView.
	 */
	private void setPicture() {
		ImageView img = null;

		img = (ImageView) findViewById(R.id.mealPicture);

		if( img == null ) return;
		if( photo == null ) {
			img.setImageResource(R.drawable.light_ic_unknow);
		}
		else {
			File test = new File(photo.getPath());
			if( test.exists() ) img.setImageURI(photo);
			else img.setImageResource(R.drawable.light_ic_unknow);
		}
	}

	private void updateMeal() {
		// Field verification.
		if (((EditMealFragment) currentFragment).getName().isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal_name, Toast.LENGTH_SHORT).show();
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.popup_save_meal);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				String pcr=null;
				String nameMeal = ((EditMealFragment) currentFragment).getName();
				int idMeal = ((EditMealFragment) currentFragment).getIdMeal();
				photo = ((EditMealFragment) currentFragment).getMealsDatabase().getPicture(nameMeal);
				TextView newName = (TextView) findViewById(R.id.mealName);

				if( photo != null ) {
					File test = new File(photo.getPath());
					if( test.exists() ) pcr =  photo.getPath();
				}

				((EditMealFragment) currentFragment).getMealsDatabase().update(
						idMeal, newName.getText().toString(), pcr, -1);
				((EditMealFragment) currentFragment).getIngredientLayout().addInDatabase(idMeal);
				((EditMealFragment) currentFragment).getIngredientLayout().deleteInDatabase(idMeal);
				returnInMealList();
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		builder.show();
	}

	/**
	 * To save the new meal.
	 */
	private void addNew() {
		// Field verification.
		EditText editName = (EditText) findViewById(R.id.name_edit);
		String name = editName.getText().toString();
		IngredientLayout igd = ((AddMealFragment) currentFragment).getIngredientLayout(); 

		if (name.isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal_name, Toast.LENGTH_SHORT).show();
			return;
		}
		if( igd.getMealsDatabase().exist(name) ) {
			Toast.makeText(getApplicationContext(), R.string.popup_bad_meal, Toast.LENGTH_SHORT).show();
			editName.setText("");
			return;
		}


		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_adding_meal)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				EditText editName = (EditText) findViewById(R.id.name_edit);
				IngredientLayout igd = null;

				switch(position) {
				case ADD:
					igd = ((AddMealFragment) currentFragment).getIngredientLayout();
				}

				String name = editName.getText().toString();
				String picture=null;

				if( photo != null ) {
					File test = new File(photo.getPath());
					if( test.exists() ) picture =  photo.getPath();
				}
				int idMeal = igd.getMealsDatabase().add(name, picture, -1);

				igd.addInDatabase(idMeal);
				igd.deleteInDatabase(idMeal);

				returnInMealList();
			}
		})

		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				setPicture();
			}
		});

		builder.show();
	}

	private void returnInMealList() {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		currentFragment = new MealListFragment();
		transaction.replace(R.id.fragment_container, currentFragment);
		transaction.commit();
		position = MEAL_LIST;
		onCreateOptionsMenu(menu);
	}

	private void deleteMeal() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.popup_deleting_meal);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				switch(position) {
				case MEAL:
					((MealFragment) currentFragment).deleteMeal();
					break;
				case EDIT:
					((EditMealFragment) currentFragment).deleteMeal();
					break;
				}

				returnInMealList();
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		builder.show();
	}
	
	private void generate() {
		( (MenuFragment) mAdapter.getItem(MENU)).generateMenu();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}