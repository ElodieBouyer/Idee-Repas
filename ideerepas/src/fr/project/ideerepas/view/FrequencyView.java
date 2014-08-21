package fr.project.ideerepas.view;

import fr.project.ideerepas.R;
import android.app.Activity;
import android.view.View;
import android.widget.NumberPicker;

public class FrequencyView {

	private NumberPicker picker0;
	private NumberPicker picker1;
	private NumberPicker picker2;
	
	public void updatePicker(View v, Activity a) {
		picker0 = (NumberPicker) v.findViewById(R.id.picker_frequency);
		picker0.setMinValue(0);
		picker0.setMaxValue(3);
		picker0.setDisplayedValues( new String[] {
				a.getResources().getString(R.string.often),
				a.getResources().getString(R.string.regularly) ,
				a.getResources().getString(R.string.occasionally),
				a.getResources().getString(R.string.rarely)
		} );
		
		picker1 = (NumberPicker) v.findViewById(R.id.picker_days);
		picker1.setMinValue(0);
		picker1.setMaxValue(6);
		picker1.setDisplayedValues( new String[] {
				a.getResources().getString(R.string.monday),
				a.getResources().getString(R.string.tuesday) ,
				a.getResources().getString(R.string.wednesday),
				a.getResources().getString(R.string.thursday),
				a.getResources().getString(R.string.friday),
				a.getResources().getString(R.string.saturday),
				a.getResources().getString(R.string.sunday)
		} );
		
		picker2 = (NumberPicker) v.findViewById(R.id.picker_dinner);
		picker2.setMinValue(0);
		picker2.setMaxValue(1);
		picker2.setDisplayedValues( new String[] {
				a.getResources().getString(R.string.noon),
				a.getResources().getString(R.string.evening)
		} );
	}
	
	public void predefine() {
		if( picker0 == null && picker1 == null && picker2 == null) return;
		
		picker0.setEnabled(true);
		
		picker1.setEnabled(false);
		picker2.setEnabled(false);
		
	}
	
	public void personalize() {
		if( picker0 == null && picker1 == null && picker2 == null) return;
		
		picker0.setEnabled(false);
		
		picker1.setEnabled(true);
		picker2.setEnabled(true);
	}
	
}
