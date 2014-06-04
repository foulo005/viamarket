

package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;



public class uploadNewItem extends Fragment  {


	// TODO : DROPDOWN LIST POUR LA CURRENCY
	//TODO: spinner.getText et tu recupère aussi la position dans le onclick de ton spinner que tu store dans cur(le Code) et curID(position)
	
	private TextView tv;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private Button bt;
	private String cat = "";

	private Spinner spinnerCat;
	private Spinner spinnerCurrency;

	private int catId;
	private String cur; 
	private int curId;
	//ArrayList for category and currency dropdown list
	private List<String> categoriesList = new ArrayList<String>();
	private List<String> currencyList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		View mainView = inflater.inflate(R.layout.fragment_main, container,
				false);

		// creation of the form elements
		tv2 = (TextView) mainView.findViewById(R.id.textView2);
		tv3 = (TextView) mainView.findViewById(R.id.textView3);
		tv4 = (TextView) mainView.findViewById(R.id.textView3);
		tv5 = (TextView) mainView.findViewById(R.id.textView4);
		et1 = (EditText) mainView.findViewById(R.id.editText1);
		et2 = (EditText) mainView.findViewById(R.id.editText2);
		et3 = (EditText) mainView.findViewById(R.id.editText3);
		bt = (Button) mainView.findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goToNextActivity();

			}
		});


		spinnerCat = (Spinner) mainView.findViewById(R.id.spinner1);

		// Create an ArrayAdapter using category list with a custom spinner
		// R.layout.custom_spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.custom_spinner, categoriesList);
		// Specify the layout to use when the list of choices appears

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner

		spinnerCat.setAdapter(adapter);
		spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long id) {

				catId = pos;
				cat = (String) parent.getItemAtPosition(pos);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
		});
		
		
		spinnerCurrency = (Spinner) mainView.findViewById((R.id.spinner2));
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner,currencyList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCurrency.setAdapter(adapter2);
		
		spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
					long id) {

				curId = pos;
				cur = (String)parent.getItemAtPosition(pos);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		return mainView;

	}

	public void goToNextActivity() {
		et1.setError(null);
		et2.setError(null);
		et3.setError(null);

		boolean cancel = false;

		View focusView = null;
		// verification function for the fields of the upload form
		if (TextUtils.isEmpty(et1.getText().toString())) {
			et1.setError(getString(R.string.error_field_required));
			focusView = et1;
			cancel = true;
		}

		if (TextUtils.isEmpty(et2.getText().toString())) {
			et2.setError(getString(R.string.error_field_required));
			focusView = et2;
			cancel = true;
		}

		if (TextUtils.isEmpty(et3.getText().toString())) {
			et3.setError(getString(R.string.error_field_required));
			focusView = et3;
			cancel = true;
		}


		if (cancel) {
			focusView.requestFocus();
		} else {

		String error = "";
		if (curId == 0 ){
			
			focusView = spinnerCurrency;
			cancel = true;
			error = error + "You need to choose a currency"+ "\n";
			
		}
		if (catId == 0){
			focusView = spinnerCat;
			cancel = true;
			error = error + "You need to choose a category"+ "\n";
		}
		
		
		if (cancel) {
			focusView.requestFocus();
			
		}
		if (!error.equals("")) Toast.makeText(this.getActivity().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
		else {

			//Launching new intent with the informations TITLE,DESCRIPTION,PRICE,CATEGORY,IDCATEGORY
			Intent intent = new Intent(this.getActivity(), ChoosePictures.class);

			intent.putExtra("TITLE", et1.getText().toString());
			intent.putExtra("DESCRIPTION", et2.getText().toString());
			intent.putExtra("PRICE", et3.getText().toString());
			intent.putExtra("CAT", cat);
			intent.putExtra("IDCAT", String.valueOf(catId));
			intent.putExtra("CUR", cur);

			intent.putExtra("IDCUR",String.valueOf(curId));

			intent.putExtra("IDCUT",String.valueOf(curId));

			startActivity(intent);
		}
		}
	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		// Load the categories and Currency
		loadData loadCC = new loadData();
		categoriesList.add(0, "Choose Category");
		currencyList.add(0,"Choose Currency");
		loadCC.execute();

	}

	public class loadData extends AsyncTask<Void, Void, Boolean> {
		private String categoryURL = "http://viamarket-001-site1.myasp.net/api/category";
		private String currencyURL = "http://viamarket-001-site1.myasp.net/api/currency/list";
		private JSONArray catjson;
		private JSONArray curjson;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			JSONParser jsonParser = new JSONParser();
			try {
				catjson = jsonParser.request(categoryURL);
				curjson = jsonParser.request(currencyURL);
				if (catjson != null && curjson != null)

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
				for (int e = 0; e < curjson.length(); e++) {

					try {
						JSONObject jObjCur = curjson.getJSONObject(e);
						currencyList.add(jObjCur.getString("Code").toString());

					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				for (int i = 0; i < catjson.length(); i++) {
					try {
						JSONObject jObjCat = catjson.getJSONObject(i);

						categoriesList
								.add(jObjCat.getString("Name").toString());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
