package com.example.takemethere;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class UserSettings extends PreferenceActivity implements OnPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {

  super.onCreate(savedInstanceState);

  
 /* @SuppressWarnings("deprecation")
Preference myPref = (Preference) findPreference("prefSearSettclea");
  myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
               public boolean onPreferenceClick(Preference preference) {
				return false;
                 /* 
            	   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserSettings.this);

           		// set title
           	//	alertDialogBuilder.setTitle("Your Title");

           		// set dialog message
           		alertDialogBuilder
           			.setMessage("Do you want to exit the app?")
           			.setCancelable(false)
           			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
           				public void onClick(DialogInterface dialog,int id) {
           					// if this button is clicked, close
           					// current activity
           					Toast.makeText(getApplicationContext(), "All addresses deleted", Toast.LENGTH_SHORT).show();
           				}
           			  })
           			.setNegativeButton("No",new DialogInterface.OnClickListener() {
           				public void onClick(DialogInterface dialog,int id) {
           					// if this button is clicked, just close
           					// the dialog box and do nothing
           					dialog.cancel();
           				}
           			});

           			// create alert dialog
           			AlertDialog alertDialog = alertDialogBuilder.create();

           			// show it
           			alertDialog.show();
           			return true; 
               }
           }); */
 
  addPreferencesFromResource(R.xml.settings);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

 // menu.add(Menu.NONE, 0, 0, "Show current settings");

  return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

  switch (item.getItemId()) {

case 0:

    startActivity(new Intent(this, UserSettings.class));

    return true;

  }

  return false;
    }

	@Override
	public boolean onPreferenceChange(Preference arg0, Object arg1) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "All addresses deleted", Toast.LENGTH_SHORT).show();
		return false;
	}
    
  

}