package com.Via.market;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ItemDisplayList extends ListFragment {
	
	private  String[] items;
	private int amount; 
	private int startPos;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_item_display_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Call the 20 latest items added and display them into the market timeline
		//TimeLineHttpRequest latest = new TimeLineHttpRequest();
		//latest.execute(20,0);
	}
	
	 @Override
     public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("item clicked ");
     }
	 
	 public class TimeLineHttpRequest extends AsyncTask<Integer, Void, Boolean> {
			private String loginURL = "http://viamarket-001-site1.myasp.net/api/item/latest/"+amount+"/"+startPos;
			private JSONArray json;

			@Override
			protected Boolean doInBackground(Integer... arg0) {
				JSONParser jsonParser = new JSONParser();
				try {
					json = jsonParser.makeHttpRequest(loginURL,"GET",null);

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

							//categoriesList.add(jObj.getString("Name").toString());

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			

	 }}
