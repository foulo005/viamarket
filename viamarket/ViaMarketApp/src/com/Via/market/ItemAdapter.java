package com.Via.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ItemAdapter extends ArrayAdapter<Item> {
	private String[] item;
	ImageLoader imageLoader;
	private Context context;

	// String imgSrc;

	public ItemAdapter(Context context, String[] item) {
		super(context, R.layout.item_row);
		this.item = item;
		this.context = context;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context

		));
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View view = convertView;
		Item item = getItem(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_row, parent, false);
			holder.txtTitle = (TextView) view.findViewById(R.id.label);
			holder.imageView = (ImageView) view.findViewById(R.id.icon);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		holder.txtTitle.setText(item.getTitle());

        if(!item.getImage(0).equals("null")){
            ImageLoader imageLoader = ImageLoader.getInstance();
        	imageLoader.displayImage(item.getImage(0), holder.imageView);
        }

		return view;
	}
}