package fr.project.ideerepas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.project.ideerepas.R;

public class ContainerFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View containerView = inflater.inflate(R.layout.container, container, false);

		return containerView;
	}
}
