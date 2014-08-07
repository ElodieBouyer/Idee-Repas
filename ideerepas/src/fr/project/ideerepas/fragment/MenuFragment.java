package fr.project.ideerepas.fragment;

import java.util.Calendar;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.adapter.ListMenuAdapter;
import fr.project.ideerepas.database.Meals;
import fr.project.ideerepas.database.Menus;

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

	private Menus menuDatabase  = null;
	private Meals mealsDatabase = null;

	private String []firstMealList = new String[NB_DAYS];
	private String []seconMealList = new String[NB_DAYS];
	private int [] listMealId;


	/**
	 * Called when the menu fragment displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get the layout to display.
		rootView = inflater.inflate(R.layout.menu, container, false);

		// Get the current menu.
		if( menuDatabase  == null ) menuDatabase  = new Menus(getActivity().getApplicationContext());
		if( mealsDatabase == null ) mealsDatabase = new Meals(getActivity().getApplicationContext());
		firstMealList = menuDatabase.getFirstMealList();
		seconMealList = menuDatabase.getSecondMealList();
		listMealId = mealsDatabase.getIds();

		TextView message = (TextView) rootView.findViewById(R.id.emptyText);
		if( listMealId != null && listMealId.length > 0) {
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

		if( listMealId == null || listMealId.length < 1) {
			return; // The user hasn't meals.
		}

		// Delete the current menu.
		menuDatabase.deleteCourrentMenu();

		firstMealList = new String [NB_DAYS];
		seconMealList = new String[NB_DAYS];

		for(int i = 0 ; i < NB_DAYS ; i++) {
			Random rand = new Random();
			int meal1 = 0;
			int meal2 = 0;

			while(meal1 == meal2) {
				meal1 = rand.nextInt(listMealId.length);
				meal2 = rand.nextInt(listMealId.length);
				firstMealList[i] = mealsDatabase.getName(meal1);
				seconMealList[i] = mealsDatabase.getName(meal2);
			}
			Log.i("BOUH", "Repas n°"+listMealId[meal1]+" et n°" + listMealId[meal2]);
			addMeal(i, meal1, meal2);
		}
		setListView(rootView);
	}


	/**
	 * To add two meal of the database (for one day).
	 * @param day Day of the week (0 to monday, 6 to sunday).
	 * @param id1 First meal id.
	 * @param id2 Second meal id.
	 */
	private void addMeal(int day, int id1, int id2) {
		menuDatabase.addMenu(id1, id2, day);
	}

	private void setListView(View menuView) {

		ListMenuAdapter adapter = new ListMenuAdapter(
				getActivity().getApplicationContext(), getDays(), firstMealList, seconMealList);

		lview = (ListView) menuView.findViewById(R.id.menu);
		lview.setAdapter(adapter);	
		lview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				//( (MainActivity) getActivity()).clickOnList(names[position]);
			}
		});
	}

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