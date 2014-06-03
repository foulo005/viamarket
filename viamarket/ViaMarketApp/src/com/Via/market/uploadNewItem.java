
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

public class uploadNewItem extends Fragment implements OnItemSelectedListener{
	
	// TODO : DROPDOWN LIST POUR LA CURRENCY GROS SAC !!!!!! SINON ON NE PEUT PAS LA RECUPERER !!! 
	// PUSH ENSUITE
	private TextView tv;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private Button bt;
	private String cat="";
	private Spinner spinner;
	private int catId;
	private List<String> categoriesList = new ArrayList<String>();
	private List<String> currencyList= new ArrayList<String>();
	
	
	

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        
        // creation of the form elements
        tv2 = (TextView) mainView.findViewById(R.id.textView2);
        tv3 = (TextView)  mainView.findViewById(R.id.textView3);
        tv4 = (TextView)  mainView.findViewById(R.id.textView3);
        tv5 = (TextView)  mainView.findViewById(R.id.textView4);
        et1 = (EditText)  mainView.findViewById(R.id.editText1);
        et2 = (EditText)  mainView.findViewById(R.id.editText2);
        et3 = (EditText)  mainView.findViewById(R.id.editText3);
        bt = (Button)  mainView.findViewById(R.id.button1);
        bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToNextActivity();
				
			}
		});
        
        spinner = (Spinner) mainView.findViewById(R.id.spinner1);
     // Create an ArrayAdapter using category list with a custom spinner R.layout.custom_spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.custom_spinner, categoriesList);
     // Specify the layout to use when the list of choices appears
     
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);
     spinner.setOnItemSelectedListener(this);
     
    	    return mainView;
    	   
       
        
       
    }
    
    public void goToNextActivity() {
    	
    	/*
    	if(!et1.getText().toString().equals("")&&!et2.getText().toString().equals("")&&!et3.getText().toString().equals("")&&!cat.equals("") ){
    	Intent intent = new Intent(this.getActivity(), ChoosePictures.class);
    	
    	intent.putExtra("TITLE",et1.getText().toString());
    	intent.putExtra("DESCRIPTION", et2.getText().toString());
    	intent.putExtra("PRICE", et3.getText().toString());
    	intent.putExtra("CAT", cat);
        startActivity(intent);
        
    	}
    	else {
    		String error="";
    		if(et1.getText().toString().equals("")){
    			error += "You have to give a title" + '\n';
    		}
    		if(et2.getText().toString().equals("")){
    			error +="You have to write a description" + '\n';
    		}
    		if(et3.getText().toString().equals("")){
    			error += "You have set a price";
    		}
    		
    		Toast.makeText(this.getActivity(),error,Toast.LENGTH_SHORT).show();
    		
    		
    		
    		
    	}*/
    	
    	et1.setError(null);
    	et2.setError(null);
    	et3.setError(null);
    	
    	boolean cancel = false;
    	View focusView = null;
    	
    	if(TextUtils.isEmpty(et1.getText().toString())){
    		et1.setError(getString(R.string.error_field_required));
    		focusView = et1;
    		cancel = true;
    	}
    	
    	if(TextUtils.isEmpty(et2.getText().toString())){
    		et2.setError(getString(R.string.error_field_required));
    		focusView = et2;
    		cancel = true;
    	}
    	
    	if(TextUtils.isEmpty(et3.getText().toString())){
    		et3.setError(getString(R.string.error_field_required));
    		focusView = et3;
    		cancel = true;
    	}
    	
    	if (cancel){
    		focusView.requestFocus();
    	}
    	else{
    		Intent intent = new Intent(this.getActivity(), ChoosePictures.class);
        	
        	intent.putExtra("TITLE",et1.getText().toString());
        	intent.putExtra("DESCRIPTION", et2.getText().toString());
        	intent.putExtra("PRICE", et3.getText().toString());
        	intent.putExtra("CAT", cat);
        	intent.putExtra("IDCAT",String.valueOf(catId));
        	System.out.println(String.valueOf(catId));
            startActivity(intent);
    	}
	}
    
    
 // what happened when you click on one element on the categoriesList
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long id) 
	{
		catId = pos;
		cat = (String)parent.getItemAtPosition(pos);
		
	}
//
 @Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
 
 @Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		categoryHttpRequest catReq = new categoryHttpRequest();
		categoriesList.add(0, "Choose Category");
		catReq.execute();

	}

	public class categoryHttpRequest extends AsyncTask<Void, Void, Boolean> {
		private String categoryURL = "http://viamarket-001-site1.myasp.net/api/category";
		// private String currencyURL = "http://viamarket-001-site1.myasp.net/api/currency";
		private JSONArray catjson;
		//private JSONArray curjson;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			JSONParser jsonParser = new JSONParser();
			try {
				catjson = jsonParser.request(categoryURL);
				//curjson= = jsonParser.request(currencyURL);
				if (catjson != null)
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
				for (int i = 0; i < catjson.length(); i++) {
					try {
						JSONObject jObjCat = catjson.getJSONObject(i);
						//JSONObject jObjCur = curjson.getJSONObject(i);

						categoriesList.add(jObjCat.getString("Name").toString());
						// currencyList.add(jObjCat.getString("Code").toString());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
    
    
}
