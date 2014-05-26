package com.Via.market;

import java.util.List;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class MarketTimeLine extends FragmentActivity {
	static final int NUM_ITEMS = 3;

	private PagerAdapter mPagerAdapter;
	private String idUser;
	private String personName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_market_time_line);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		idUser = b.getString("idUser");
		System.out.println(idUser);
		personName = b.getString("personName");
		
		Toast.makeText(getApplicationContext(), "welcome "+personName, Toast.LENGTH_LONG).show();
		// getting the action bar
		final ActionBar actionBar = getActionBar();

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Creating the fragmentList
		List<Fragment> fragments = new Vector<Fragment>();

		// Ajout des Fragments dans la liste
		fragments
				.add(Fragment.instantiate(this, ItemDisplayList.class.getName()));
		fragments.add(Fragment.instantiate(this,
				SearchActivity.class.getName()));
		// fragments.add(Fragment.instantiate(this,PageDroiteFragment.class.getName()));

		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new PageAdapter(super.getSupportFragmentManager(),
				fragments);

		final ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.mPagerAdapter);

		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between pages, select the
				// corresponding tab.
				getActionBar().setSelectedNavigationItem(position);
			}
		});
		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

		};

		// Add 3 tabs, specifying the tab's text and TabListener

		actionBar.addTab(actionBar.newTab().setText("Newest")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Search")
				.setTabListener(tabListener));
		pager.setCurrentItem(0);

	}
}
