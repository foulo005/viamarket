package com.Via.market;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
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

public class uploadNewItem extends Fragment implements OnItemSelectedListener{
	
	TextView tv;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	EditText et1;
	EditText et2;
	EditText et3;
	Button bt;
	String cat="";
	
	
	

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        
        tv = (TextView) getActivity().findViewById(R.id.textView1);
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
        
        Spinner spinner = (Spinner) mainView.findViewById(R.id.spinner1);
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
             R.array.categories, android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);
     spinner.setOnItemSelectedListener(this);
     
    	    return mainView;
    	   
       
        
       
    }
    
    public void goToNextActivity() {
    	
    	
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
    		
    		
    		
    		
    	}
	}
    
    

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long id) {
		cat = (String)parent.getItemAtPosition(pos);
		System.out.println(cat);
	}

 @Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
    
    
}
