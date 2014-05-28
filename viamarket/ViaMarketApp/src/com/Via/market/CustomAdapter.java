package com.Via.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<String> {
	private Integer[] imageTab = {R.drawable.icon_144};
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
          getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
 
   /*   //  TextView textView = (TextView) rowView.findViewById(R.id.label);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
 
        textView.setText(getItem(position));
 
        if(convertView == null )
          imageView.setImageResource(imageTab[position]);
        else
          rowView = (View)convertView;
 */
        return rowView; 
    }

    public CustomAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
    }
}


