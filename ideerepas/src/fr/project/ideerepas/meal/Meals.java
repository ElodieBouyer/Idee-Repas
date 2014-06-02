package fr.project.ideerepas.meal;

import java.util.ArrayList;
import java.util.List;

public class Meals implements Functionality{

	private List<Meal> m_meals;
	
	
	public Meals() {
		this.m_meals = new ArrayList<Meal>();
		this.m_meals = null;
	}
	
	public String[] getNames() {
		if( this.m_meals == null || this.m_meals.size() == 0 ) {
			return null;
		}
		String[] nameList = new String[this.m_meals.size()];
		int i = 0;
		for (Meal m : this.m_meals) {
			nameList[i] = m.getName();
			i++;
		}
		return nameList;
	}
}
