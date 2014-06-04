package com.Via.market;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ItemAdapter extends BaseAdapter {
	private List<Item> item;
	ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	private DisplayImageOptions options;

	// String imgSrc;

	public ItemAdapter(Context context, List<Item> items,
			DisplayImageOptions opt) {
		super();
		this.item = items;
		this.options = opt;
		this.context = context;
	}

	/* private view holder class */

	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		Item itemNow = getItem(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_row, parent, false);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.label);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTitle.setText(itemNow.getTitle());
		imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(itemNow.getImagePreview(),holder.imageView, options, animateFirstListener);

		return convertView;
	}

	@Override
	public int getCount() {
		return item.size();
	}

	@Override
	public Item getItem(int position) {
		return item.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setItemList(List<Item> itemList) {
		this.item = itemList;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}