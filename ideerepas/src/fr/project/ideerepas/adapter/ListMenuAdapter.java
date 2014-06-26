package fr.project.ideerepas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.project.ideerepas.R;

public class ListMenuAdapter extends ArrayAdapter<String> {

	private String [] firstMealList;
	private String [] secondMealList;
	
	public ListMenuAdapter(Context context, String [] days, String [] firstMeal, String []secondMeal) {
		super(context, R.layout.row_list_menu, days);
		firstMealList  = firstMeal;
		secondMealList = secondMeal;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = null;

		LayoutInflater inflater = (LayoutInflater)
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.row_list_menu, parent, false);

		TextView date       = (TextView) rowView.findViewById(R.id.date_text);
		TextView firstMeal  = (TextView) rowView.findViewById(R.id.firstMeal_text);
		TextView secondMeal = (TextView) rowView.findViewById(R.id.secondMeal_text);

		date.setText(getItem(position));
		if( firstMealList  != null ) firstMeal.setText(firstMealList[position]);
		if( secondMealList != null ) secondMeal.setText(secondMealList[position]);

		return rowView;
	}
}

