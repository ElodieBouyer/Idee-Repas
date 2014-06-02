package fr.project.ideerepas.meal;

import java.util.ArrayList;
import java.util.List;

public class Menus implements Functionality {
private List<Menu> m_menu;
	
	public Menus() {
		this.m_menu = new ArrayList<Menu>();
		this.m_menu = null;
	}
	
	public String[] getNames() {
		if( this.m_menu == null || this.m_menu.size() == 0 ) {
			return null;
		}
		String[] nameList = new String[this.m_menu.size()];
		int i = 0;
		for (Menu m : this.m_menu) {
			nameList[i] = m.getName();
			i++;
		}
		return nameList;
	}
}
