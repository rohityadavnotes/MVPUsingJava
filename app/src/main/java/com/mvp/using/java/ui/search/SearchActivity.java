package com.mvp.using.java.ui.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.mvp.using.java.R;
import com.mvp.using.java.customview.MyBadgeDrawable;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = SearchActivity.class.getSimpleName();

    /*
     ***********************************************************************************************
     ********************* Toolbar & ActionBar & SearchView Related Properties *********************
     ***********************************************************************************************
     */
    private Toolbar toolbar;

    private ActionBar actionBar;

    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private ArrayList<Item> hintArrayList;

    /*
     ***********************************************************************************************
     ***************************************** Menu Related Properties *****************************
     ***********************************************************************************************
     */
    private Menu menu;
    private int badgeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeToolBar();
    }

    protected void initializeToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void doSearch() {
        hintArrayList = new ArrayList<>();
        hintArrayList.add(new Item("one"));
        hintArrayList.add(new Item("two"));
        hintArrayList.add(new Item("three"));
        hintArrayList.add(new Item("four"));
        hintArrayList.add(new Item("five"));
        hintArrayList.add(new Item("six"));
        hintArrayList.add(new Item("seven"));
        hintArrayList.add(new Item("eight"));
        hintArrayList.add(new Item("nine"));
        hintArrayList.add(new Item("ten"));

        SearchAutoCompleteAdapter searchAutoCompleteAdapter = new SearchAutoCompleteAdapter(this, hintArrayList);
        searchAutoComplete.setAdapter(searchAutoCompleteAdapter);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchAutoComplete.setText(hintArrayList.get(position).getString());
            }
        });
    }

    private void configureSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {

            searchView.setIconified(false);
            searchView.setIconifiedByDefault(false);
            searchView.setQueryHint("Search...");

            searchView.setBackgroundResource(R.drawable.searh_view_background);

            LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_edit_frame);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            params.leftMargin = 0;
            params.rightMargin = 0;
            linearLayout.setLayoutParams(params);

            /*Code for changing the search icon */
            ImageView searchIcon = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
            searchIcon.setImageResource(R.drawable.my_search_icon);

            /* Set the font */
            EditText editText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            Typeface myCustomFont = ResourcesCompat.getFont(getApplicationContext(), R.font.bold);
            editText.setTypeface(myCustomFont);

            /*Code for changing the voice search icon */
            ImageView voiceIcon = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_voice_btn);
            voiceIcon.setImageResource(R.drawable.my_voice_search_icon);

            /*Code for changing the close icon */
            ImageView closeIcon = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            closeIcon.setImageResource(R.drawable.ic_close);

            /* Set the submit button icon on the right */
            ImageView imageView = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_go_btn);
            imageView.setImageResource(R.drawable.ic_find_in_page);
            imageView.setVisibility(View.VISIBLE);
            searchView.setSubmitButtonEnabled(false);

            SearchManager searchManager = (SearchManager) getApplicationContext().getSystemService(Context.SEARCH_SERVICE);
            ComponentName componentName = new ComponentName(this, SearchActivity.class);
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);
            searchView.setSearchableInfo(searchableInfo);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    System.out.println("SEARCH SUBMIT: " + query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    doSearch();
                    System.out.println("SEARCH CHANGE: " + newText);
                    return false;
                }
            });

            searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            if (searchAutoComplete != null) {
                searchAutoComplete.setHint("Search...");
                searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.darker_gray));  /* Set the color of the prompt text */
                searchAutoComplete.setTextColor(getResources().getColor(android.R.color.black)); /* Set the color of the input text */
                searchAutoComplete.setTextSize(14);
                searchAutoComplete.setDropDownBackgroundResource(R.color.colorTransparent);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    searchAutoComplete.setTextCursorDrawable(R.color.purple);
                }*/
            }
        }
    }

    private void setBadgeCount(Context context, LayerDrawable icon, int count) {
        MyBadgeDrawable badge;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);

        if (reuse instanceof MyBadgeDrawable) {
            badge = (MyBadgeDrawable) reuse;
        } else {
            badge = new MyBadgeDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    private void updateProductBadge(int count) {
        badgeCount = count;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actions_bar_menu_items, menu);

        configureSearchView(menu);

        /* Add to Cart Count */
        MenuItem menuItem = menu.findItem(R.id.action_add_to_cart_count);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
        setBadgeCount(this, icon, badgeCount);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.action_add_to_cart_count) {
            Toast.makeText(getApplicationContext(), "Show cart screen", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            updateProductBadge(5);
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        /* close search view on back button pressed */
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
