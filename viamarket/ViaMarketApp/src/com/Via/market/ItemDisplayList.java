package com.Via.market;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

	private List<Item> items;
	private int amount;
	private int startPos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_item_display_list, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Call the 20 latest items added and display them into the market
		// timeline
		 TimeLineHttpRequest latest = new TimeLineHttpRequest();
		 latest.execute(20,0);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		System.out.println("item clicked ");
	}

	public Item ItemFromJson(JSONObject j) throws JSONException {
		if (j != null) {
			// Retrieving the different nodes
			JSONObject cat = new JSONObject(j.getString("Category").toString());
			JSONObject user = new JSONObject(j.getString("ApplicationUser")
					.toString());
			JSONObject cur = new JSONObject(j.getString("Currency").toString());
			String[] image;
			JSONArray imageArray = j.getJSONArray("Image");
			image = new String[imageArray.length()];
			for (int i = 0; i < imageArray.length(); i++) {
				JSONObject jsonObject = imageArray.getJSONObject(i);
				
				image[i] =  jsonObject.toString();
			}
			Item i = new Item(j.getString("Id").toString(), j.get("Price")
					.toString(), cur.getString("Code").toString(), cat
					.getString("Name").toString(), j.getString("Title")
					.toString(), j.getString("Description").toString(),user.getString("Id").toString(), j
					.getString("Created").toString(), image);
			return i;
		}
		return null;
	}

	public class TimeLineHttpRequest extends AsyncTask<Integer, Void, Boolean> {

		private JSONArray json;

		@Override
		protected Boolean doInBackground(Integer... range) {
			JSONParser jsonParser = new JSONParser();
			 String loadURL = "http://viamarket-001-site1.myasp.net/api/item/latest/"
						+ range[0] + "/" + range[1];
			try {
				json = jsonParser.request(loadURL);

				if (json != null)
			//		System.out.println(json);
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
				items = new ArrayList<Item>();
				for (int i = 0; i < json.length(); i++) {
					try {
						JSONObject jObj = json.getJSONObject(i);
						items.add(ItemFromJson(jObj));
						System.out.println(items);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
}
