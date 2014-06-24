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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.adapter.ListMenuAdapter;
import fr.project.ideerepas.database.Meals;
import fr.project.ideerepas.database.Menus;

public class MenuFragment extends Fragment {

	private ListView lview;
	private Menus menuDatabase  = null;
	private Meals mealsDatabase = null;
	private static int NB_DAYS = 7;
	private View rootView;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.menu, container, false);

		setListView(rootView);

		return rootView;
	}

	public ListView getListView() {
		return lview;
	}

	public void generateMenu() {
		if( menuDatabase  == null ) menuDatabase  = new Menus(getActivity().getApplicationContext());
		if( mealsDatabase == null ) mealsDatabase = new Meals(getActivity().getApplicationContext());

		int [] listID = mealsDatabase.getIds();
		if( listID == null ) {
			return; // L'utilisateur n'a pas de repas.
		}
		
		// TODO : Supprimer le menu enregistré dans la base.
		
		for(int i = 0 ; i < NB_DAYS ; i++) {
			Random rand = new Random();
			int meal1 = 0;
			int meal2 = 0;

			while(meal1 == meal2) {
				meal1 = rand.nextInt(listID.length);
				meal2 = rand.nextInt(listID.length);
			}
			Log.i("BOUH", "Repas n°"+listID[meal1]+" et n°" + listID[meal2]);
			addMeal(i, meal1, meal2);
		}
		
		// TODO : Mettre à jour l'affichage.
		setListView(rootView);
	}

	
	private void addMeal(int day, int id1, int id2) {
		// TODO : Ajouter le menu dans la base.
	}
	
	private void setListView(View menuView) {

		ListMenuAdapter adapter = new ListMenuAdapter(
				getActivity().getApplicationContext(), 
				getDays());
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