package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Teacher on 10.05.2016.
 */
public class DetailActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment()).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //String IntentMessage = findViewById(R.id.detail_text_view).toString();
        //mShareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
        //mShareIntent.putExtra(Intent.EXTRA_TEXT,IntentMessage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity( new Intent (DetailActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class DetailFragment extends Fragment {
        private String extrasStr;
        private ShareActionProvider mShareActionProvider;
        private final static String LOG_TAG = DetailFragment.class.getSimpleName();

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.detail_fragment,
                    container, false);
            Intent intent = getActivity().getIntent();
            if(intent!=null && intent.getExtras()!=null){
                extrasStr = intent.getStringExtra(intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.detail_text_view)).setText(extrasStr);
            }
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.share_menu, menu);
            MenuItem item = menu.findItem(R.id.menu_item_share);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
            if(mShareActionProvider!=null){
                mShareActionProvider.setShareIntent(getDefaultIntent());}
            else{
                Log.w(LOG_TAG, "Share action provider is null?");
            }
            super.onCreateOptionsMenu(menu, inflater);
        }

        private Intent getDefaultIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
            shareIntent.putExtra(Intent.EXTRA_TEXT,extrasStr);
            return shareIntent;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int newItem = item.getItemId();
            if(newItem == R.id.menu_item_share){
                startActivity(getDefaultIntent());
            }
            return super.onOptionsItemSelected(item);
        }
    }
}