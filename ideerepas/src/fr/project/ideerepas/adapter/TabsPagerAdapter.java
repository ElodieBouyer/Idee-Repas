package fr.project.ideerepas.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fr.project.ideerepas.fragment.ContainerFragment;
import fr.project.ideerepas.fragment.MenuFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private MenuFragment menuFragent = null;
	private ContainerFragment containerFragment = null;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			if( menuFragent == null) {
				menuFragent =  new MenuFragment();
			}
			return menuFragent;
		case 1:
			if( containerFragment == null) {
				containerFragment = new ContainerFragment();
			}
			return containerFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
