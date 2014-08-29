package fr.project.ideerepas.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.adapter.ListMenuAdapter;
import fr.project.ideerepas.database.MealsDatabase;
import fr.project.ideerepas.database.MenusDatabase;

public class MenuFragment extends Fragment {

	private static int NB_DAYS = 7;
	private static String []MONTHS = {
		"Janvier", 
		"Fevrier", 
		"Mars", 
		"Avril", 
		"Mai", 
		"Juin", 
		"Juillet", 
		"Août",
		"Septembre",
		"Octobre",
		"Novembre",
	"Decembre"};

	private ListView lview;
	private View rootView;

	private MenusDatabase menuDatabase  = null;
	private MealsDatabase mealsDatabase = null;

	private String []firstMealList = new String[NB_DAYS];
	private String []seconMealList = new String[NB_DAYS];
	private String [] listMealName;
	private List<String> meals = new ArrayList<String>();


	/**
	 * Called when the menu fragment displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get the layout to display.
		rootView = inflater.inflate(R.layout.menu, container, false);

		// Get the current menu.
		if( menuDatabase  == null ) menuDatabase  = new MenusDatabase(getActivity().getApplicationContext());
		if( mealsDatabase == null ) mealsDatabase = new MealsDatabase(getActivity().getApplicationContext());
		firstMealList = menuDatabase.getFirstMealList();
		seconMealList = menuDatabase.getSecondMealList();
		listMealName = mealsDatabase.getNames();

		TextView message = (TextView) rootView.findViewById(R.id.emptyText);
		if( listMealName != null && listMealName.length > 0) {
			message.setVisibility(View.GONE);
			setListView(rootView);
		}
		else message.setVisibility(View.VISIBLE);

		// Return the view.
		return rootView;
	}

	/**
	 * Called when the user want a new menu.
	 */
	public void generateMenu() {

		if( listMealName == null || listMealName.length < 1) {
			return; // The user hasn't meals.
		}

		// Delete the current menu.
		menuDatabase.deleteCourrentMenu();

		firstMealList = new String[NB_DAYS];
		seconMealList = new String[NB_DAYS];

		fillList();
		
		for(int i = 0 ; i < NB_DAYS ; i++) {
			Random rand = new Random();
			int meal1 = 0;
			int meal2 = 0;

			while( meals.get(meal1).equals(meals.get(meal2)) ) {
				meal1 = rand.nextInt(meals.size());
				meal2 = rand.nextInt(meals.size());
				Log.i("BOUH", meals.get(meal1) + " et " + meals.get(meal2));
			}
			if( firstMealList[i] == null || firstMealList[i].isEmpty() ) firstMealList[i] = meals.get(meal1);
			if( seconMealList[i] == null || seconMealList[i].isEmpty() ) seconMealList[i] = meals.get(meal2);

			//Log.i("BOUH", "Repas n°"+firstMealList[i]+" et n°" + seconMealList[i]);
			addMeal(i, firstMealList[i], seconMealList[i]);
		}
		setListView(rootView);
	}

	public int getNbMeals() {
		if( listMealName == null || listMealName.length <= 0) return 0;
		return listMealName.length;
	}


	private void fillList() {
		for( String name : listMealName) {
			int frequency = mealsDatabase.getFrequency(name);

			if( frequency == 0 ) { // Often.
				for(int i = 0 ; i < 20; i++) {
					meals.add(name);
				}
			}
			else if ( frequency == 1 ) { // Regularly.
				for(int i = 0 ; i < 5; i++) {
					meals.add(name);
				}
			}
			else if ( frequency == 2 ) { // Occasionally.
				for(int i = 0 ; i < 2; i++) {
					meals.add(name);
				}
			}	
			else if ( frequency == 3 ) { // Rarely.
				meals.add(name);
			}
			else if( frequency%10 == 1) {// Noon.
				firstMealList[frequency/10 -4] = name;
			}
			else if( frequency%10 == 2) {// Evenning.
				seconMealList[frequency/10 -4] = name;
			}

		}
		/*for(String n : meals) {
			Log.i("Bouh", n);
		}*/
		
		Collections.shuffle(meals); 
		/*for(String n : meals) {
			Log.i("Bouh", n);
		}*/
	}

	/**
	 * To add two meal of the database (for one day).
	 * @param day Day of the week (0 to monday, 6 to sunday).
	 * @param id1 First meal id.
	 * @param id2 Second meal id.
	 */
	private void addMeal(int day, String name1, String name2) {
		menuDatabase.addMenu(name1, name2, day);
	}

	/**
	 * Set the menu list.
	 * @param menuView
	 */
	private void setListView(View menuView) {

		ListMenuAdapter adapter = new ListMenuAdapter(
				getActivity().getApplicationContext(), getDays(), firstMealList, seconMealList);

		lview = (ListView) menuView.findViewById(R.id.menu);
		lview.setAdapter(adapter);	
		lview.setClickable(false);
	}

	/**
	 * Get the week's date.
	 * @return A date array.
	 */
	private String [] getDays() {

		Calendar calendar = Calendar.getInstance(); 
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		switch (day) {
		case Calendar.TUESDAY:
			calendar.add(Calendar.DATE, -1);
			break;
		case Calendar.WEDNESDAY:
			calendar.add(Calendar.DATE, -2);
			break;
		case Calendar.THURSDAY:
			calendar.add(Calendar.DATE, -3);
			break;
		case Calendar.FRIDAY:
			calendar.add(Calendar.DATE, -4);
			break;
		case Calendar.SATURDAY:
			calendar.add(Calendar.DATE, -5);
			break;
		case Calendar.SUNDAY:
			calendar.add(Calendar.DATE, -6);
			break;
		default:
			break;
		}

		String [] days = {
				getActivity().getResources().getString(R.string.monday),
				getActivity().getResources().getString(R.string.tuesday) ,
				getActivity().getResources().getString(R.string.wednesday),
				getActivity().getResources().getString(R.string.thursday),
				getActivity().getResources().getString(R.string.friday),
				getActivity().getResources().getString(R.string.saturday),
				getActivity().getResources().getString(R.string.sunday)
		};

		for(int i = 0 ; i < days.length ; i++) {
			days[i] += " ";
			days[i] += calendar.get(Calendar.DAY_OF_MONTH);
			days[i] += " ";
			days[i] += MONTHS[calendar.get(Calendar.MONTH)];
			calendar.add(Calendar.DATE, 1);
		}
		return days;
	}


}