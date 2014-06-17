package fr.project.ideerepas.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import fr.project.ideerepas.R;
import fr.project.ideerepas.activity.MealLayout;
import fr.project.ideerepas.adapter.ListRowAdapter;
import fr.project.ideerepas.database.Meals;

public class MealListFragment extends Fragment {

	private Meals m_list      = null;
	private String[] pictures = null;
	private String[] names    = null;
	private ListView lview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mealView = inflater.inflate(R.layout.list_meal, container, false);

		if (this.m_list == null) {
			this.m_list = new Meals(getActivity().getApplicationContext());
		}

		Map<String,String> rows = new HashMap<String, String>();
		rows = this.m_list.getCoupleNamePicture();

		if( rows != null) {
			int i = 0;
			names    = new String[rows.size()];
			pictures = new String[rows.size()];
			for (Map.Entry<String, String> entry : rows.entrySet()) {
				names[i]    = entry.getKey();
				pictures[i] = entry.getValue();
				i++;
			}
		}

		TextView message = (TextView) mealView.findViewById(R.id.emptyText);
		// If the list is empty.
		if (names != null) {
			message.setVisibility(View.GONE);
			setListView(mealView);
		}
		else message.setVisibility(View.VISIBLE);

		return mealView;
	}

	private void setListView(View mealView) {
		ListRowAdapter adapter = new ListRowAdapter(getActivity().getApplicationContext(), names, pictures);
		lview = (ListView) mealView.findViewById(R.id.listView);
		lview.setAdapter(adapter);

		lview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
				Intent intent = new Intent(getActivity().getApplicationContext(), MealLayout.class);
				intent.putExtra("meal", names[position]);
				startActivity(intent);
				m_list = null;
			} 
		});
	}

}