package com.Via.market;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class MarketTimeLine extends FragmentActivity {
	 static final int NUM_ITEMS = 3;

		private PagerAdapter mPagerAdapter;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			super.setContentView(R.layout.activity_market_time_line);

			// Création de la liste de Fragments que fera défiler le PagerAdapter
			List fragments = new Vector();

			// Ajout des Fragments dans la liste
			fragments.add(Fragment.instantiate(this,SearchActivity.class.getName()));
			fragments.add(Fragment.instantiate(this,ItemDisplayList.class.getName()));
			//fragments.add(Fragment.instantiate(this,PageDroiteFragment.class.getName()));

			// Création de l'adapter qui s'occupera de l'affichage de la liste de
			// Fragments
			this.mPagerAdapter = new PageAdapter(super.getSupportFragmentManager(), fragments);

			ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
			// Affectation de l'adapter au ViewPager
			pager.setAdapter(this.mPagerAdapter);
		}
	}