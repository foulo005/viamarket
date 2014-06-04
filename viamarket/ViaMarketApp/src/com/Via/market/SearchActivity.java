
package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends Fragment {
	// UI componennts
	private EditText searchField;
	private Button searchButton;
	private Spinner categoriesSpinner;
	private List<String> categoriesList = new ArrayList<String>();
	//yeah its api/item/category/{id:int}/{search}
	

	// HttpRequest

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.activity_search, container, false);
		Intent i = getActivity().getIntent();
		String str = i.getStringExtra("lol");
		if (str !=null){
			
		} else{
		searchField = (EditText) view.findViewById(R.id.searchEditText);
		searchButton = (Button) view.findViewById(R.id.searchButton);
		categoriesSpinner = (Spinner) view.findViewById(R.id.categoriesSpinner);
		ArrayAdapter<String> cat = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, categoriesList);
		categoriesSpinner.setAdapter(cat);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
	
				
			}
		});}
		return view;
		
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		categoryHttpRequest catReq = new categoryHttpRequest();
		categoriesList.add(0, "Choose Category");
		catReq.execute();

	}

	public class categoryHttpRequest extends AsyncTask<Void, Void, Boolean> {
		private String loginURL = "http://viamarket-001-site1.myasp.net/api/category";
		private JSONArray json;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			JSONParser jsonParser = new JSONParser();
			try {
				json = jsonParser.request(loginURL);

				if (json != null)
					return true;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				for (int i = 0; i < json.length(); i++) {
					try {
						JSONObject jObj = json.getJSONObject(i);

						categoriesList.add(jObj.getString("Name").toString());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
