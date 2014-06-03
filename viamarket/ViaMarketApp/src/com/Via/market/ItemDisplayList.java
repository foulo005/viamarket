package com.Via.market;

import java.io.IOException;
import java.sql.Date;

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
	
	private  Item[] items;
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
	 public Item ItemFromJson(JSONObject j)
	 {
		 if(j != null )
		 {
		//	 int price, int idCurrency, int idCategory,
			//	String title, String description, String iDAdoNetUsers,
				//String applicationUser_Id, Date date, Boolean sold, String[] imagesURLs)
			Item i = new Item(j.getString("Id").toString(),j.get("Price").toString(),j.getString("Category").)
		 }
		 return null;
	 }
	 
	 public class TimeLineHttpRequest extends AsyncTask<Integer, Void, Boolean> {
			private String loadURL = "http://viamarket-001-site1.myasp.net/api/item/latest/"+amount+"/"+startPos;
			private JSONArray json;

			@Override
			protected Boolean doInBackground(Integer... arg0) {
				JSONParser jsonParser = new JSONParser();
				try {
					json = jsonParser.request(loadURL);

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
							

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			

	 }}
