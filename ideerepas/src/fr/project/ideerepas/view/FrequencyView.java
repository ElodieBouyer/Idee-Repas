package fr.project.ideerepas.view;

import fr.project.ideerepas.R;
import android.app.Activity;
import android.view.View;
import android.widget.NumberPicker;

public class FrequencyView {

	private NumberPicker picker0;
	private NumberPicker picker1;
	private NumberPicker picker2;
	private Activity activity;

	public FrequencyView(Activity a) {
		activity = a;
	}

	public void updatePicker(View v) {
		picker0 = (NumberPicker) v.findViewById(R.id.picker_frequency);
		picker0.setMinValue(0);
		picker0.setMaxValue(3);
		picker0.setDisplayedValues( new String[] {
				activity.getResources().getString(R.string.often),
				activity.getResources().getString(R.string.regularly) ,
				activity.getResources().getString(R.string.occasionally),
				activity.getResources().getString(R.string.rarely)
		} );

		picker1 = (NumberPicker) v.findViewById(R.id.picker_days);
		picker1.setMinValue(0);
		picker1.setMaxValue(6);
		picker1.setDisplayedValues( new String[] {
				activity.getResources().getString(R.string.monday),
				activity.getResources().getString(R.string.tuesday) ,
				activity.getResources().getString(R.string.wednesday),
				activity.getResources().getString(R.string.thursday),
				activity.getResources().getString(R.string.friday),
				activity.getResources().getString(R.string.saturday),
				activity.getResources().getString(R.string.sunday)
		} );

		picker2 = (NumberPicker) v.findViewById(R.id.picker_dinner);
		picker2.setMinValue(0);
		picker2.setMaxValue(1);
		picker2.setDisplayedValues( new String[] {
				activity.getResources().getString(R.string.noon),
				activity.getResources().getString(R.string.evening)
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

	public String getFrequency(int fqr) {
		String text;

		String evening = " ";
		evening += activity.getResources().getString(R.string.evening);

		String noon = " ";
		noon += activity.getResources().getString(R.string.noon);

		switch (fqr) {
		case 0:
			return activity.getResources().getString(R.string.often);
		case 1:
			return activity.getResources().getString(R.string.regularly);
		case 2:
			return activity.getResources().getString(R.string.occasionally);
		case 3:
			return activity.getResources().getString(R.string.rarely);

			// Lundi midi.	
		case 41:
			text = activity.getResources().getString(R.string.monday);
			text += noon;
			return text;

			// Lundi soir.
		case 42:
			text = activity.getResources().getString(R.string.monday);
			text += evening;
			return text;

			// Mardi midi.
		case 51:
			text = activity.getResources().getString(R.string.tuesday);
			text += noon;
			return text;

			// Mardi soir.	
		case 52:
			text = activity.getResources().getString(R.string.tuesday);
			text += evening;
			return text;

		case 61:
			text = activity.getResources().getString(R.string.wednesday);
			text += noon;
			return text;

		case 62:
			text = activity.getResources().getString(R.string.wednesday);
			text += evening;
			return text;

		case 71:
			text = activity.getResources().getString(R.string.thursday);
			text += noon;
			return text;

		case 72:
			text = activity.getResources().getString(R.string.thursday);
			text += evening;
			return text;
			
		case 81:
			text = activity.getResources().getString(R.string.friday);
			text += noon;
			return text;
			
		case 82:
			text = activity.getResources().getString(R.string.friday);
			text += evening;
			return text;
			
		case 91:
			text = activity.getResources().getString(R.string.saturday);
			text += noon;
			return text;
			
		case 92:
			text = activity.getResources().getString(R.string.saturday);
			text += evening;
			return text;
			
		case 101:
			text = activity.getResources().getString(R.string.sunday);
			text += noon;
			return text;
			
		case 102:
			text = activity.getResources().getString(R.string.sunday);
			text += evening;
			return text;
			

		default:
			return activity.getResources().getString(R.string.frequency_empty);
		}

	}
}
