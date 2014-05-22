package fr.project.ideerepas.layout;

import fr.project.ideerepas.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Start extends ListActivity {
	
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    
    String[] values = new String[] { getString(R.string.liste_repas),
	         						 getString(R.string.liste_menu),
	         						 getString(R.string.liste_course),
	         						 getString(R.string.quitter)};
    
    ArrayAdapter<String> adapter = 
    		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
  }
} 