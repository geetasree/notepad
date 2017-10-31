package com.example.welcome.smartnotepad;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.welcome.smartnotepad.DBManager.NotepadManager;
import com.example.welcome.smartnotepad.Fragment.EntryFragment;
import com.example.welcome.smartnotepad.Fragment.ListFragment;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    Fragment currentFragment;
    FragmentManager manager;
    FragmentTransaction transaction;
    NotepadManager objNotepadManager;
    ArrayList<String> objListNoteData1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initialization();
    }

    public void showUpButton() { getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
    public void hideUpButton() { getSupportActionBar().setDisplayHomeAsUpEnabled(false); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               // getSupportFragmentManager().popBackStack();
                currentFragment = new ListFragment();
                manager = getFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.llFragmentHolder, currentFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialization() {
        objNotepadManager=new NotepadManager(this);
        objListNoteData1=new ArrayList<String>();
        currentFragment = new ListFragment();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.llFragmentHolder, currentFragment);
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.llFragmentHolder);
        if (currentFragment != null && currentFragment instanceof ListFragment) {
            this.finish();
        }
        else {
            currentFragment = new ListFragment();
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.llFragmentHolder, currentFragment);
            transaction.addToBackStack(null).commit();
        }
    }

    public void createNote(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("modifyId", -1);
        currentFragment = new EntryFragment();
        currentFragment.setArguments(bundle);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.llFragmentHolder, currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
