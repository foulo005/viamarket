package com.Via.market;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ItemDisplayList extends ListFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_item_display_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final String[] items = new String[]{"salut","cava"};
		final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, items);
		
		setListAdapter(ArrayAdapter);
	}

}
