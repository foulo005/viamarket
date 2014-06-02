package com.Via.market;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemAdapter extends ArrayAdapter<Item>{
	private String[] item;
	ImageLoader imageLoader;
	private Context context;
	//String imgSrc; 
	
	public ItemAdapter(Context context,	String[] item) {
		super(context, R.layout.item_row);
		this.item =  item;
		 this.context = context;
		 imageLoader = ImageLoader.getInstance();
	}
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.item_row, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(item[position]);
	    // Change the icon for Windows and iPhone
	    String s = item[position];

	    return rowView;
	  }
}