package com.example.takemethere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.example.takemethere.LocationService;
import com.example.takemethere.LocationService.LocalBinder;

import android.R.string;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.Settings;


public class MainActivity extends ActionBarActivity  {
	TextToSpeech ttobj;
	Button cleartext;
	Button btGo;
	AutoCompleteTextView AutoComp;
	//TextView dellink;
	public Object temp ;
	String[] address=null;
	
	LocationService mService;
	boolean mBound ;
	TextView myLocationText;
	 Intent intent;
//	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//	boolean syncConnPref = sharedPref.getBoolean("prefDistUnit", true);
	
	@Override
	protected void onResume(){
		
		
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		super.onResume();
		
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		super.onStart();

		temp = this.getApplicationContext();
		
		 ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			   @Override
			   public void onInit(int status) {
			   }
			}
			);
		
	//	dellink = (TextView)findViewById(R.id.tvDelLink);
		 
         intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        
        PopulateAddress();
 //  btGo=(Button)findViewById(R.id.btGo);
 //  btGo.setOnClickListener(new onClicListner({});
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		
		if (!enabled) {
		  Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent1);
		  Toast.makeText(getApplicationContext(), "Please enable location service", Toast.LENGTH_SHORT).show();
		  myLocationText.setText("Turn on Location services!");//return "Turn on Location services";
		} 
        
   
        
      	  
        
        
      /*  Uri allAddresses = Uri.parse("content://com.example.takemethere.Provider/cte");

                Cursor c = getContentResolver().query(allAddresses, null, null, null, null);
               // Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(), c.getColumnCount(), Toast.LENGTH_SHORT).show();
                EditText address = (EditText)findViewById(R.id.etAddress);
            	address.setText(c.getColumnCount());
          */  	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(MainActivity.this, com.example.takemethere.UserSettings.class); 
			startActivityForResult(intent, 1);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	//Begin Add guru
	public void sendSMS(View v){
		
		
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		
		if (!enabled) {
		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent);
		  Toast.makeText(getApplicationContext(), "Please enable location service", Toast.LENGTH_SHORT).show();
		} 
		Criteria criteria =new Criteria();
		
		String provider = service.getBestProvider(criteria,false);
		String lat;
		String lon;
		String MapURL;
		String NavURL;
	//	LocationListener mlocListener = null ;
		// service.requestLocationUpdates( provider, 0, 0, mlocListener);
		
		service.getLastKnownLocation(provider);
		Location location = service.getLastKnownLocation(provider);
		lat=Double.toString(location.getLatitude());
		lon=Double.toString(location.getLongitude());
		MapURL= "http://maps.google.com/maps?&q=loc:"+lat +"+" + lon;
		NavURL="http://maps.google.com/maps?saddr=my+location&daddr=" +lat +"+" + lon;
		
		double lat1,lat2,lng1,lng2;
		lat1=37.361637;
		lng1=-122.025495;
		//lat2=location.getLatitude();
		//lng2=location.getLongitude();
		
		
	
		
		lat2=37.353193;
		lng2=-122.014423;
		
		double earthRadius = 6371; //kilometers
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) ((earthRadius * c)/1.60934);
	    
	    Toast.makeText(getApplicationContext(), Float.toString(dist), Toast.LENGTH_SHORT).show();
		
		
		
		
		//Toast.makeText(getApplicationContext(), MapURL, Toast.LENGTH_SHORT).show();
		
		//Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			//    Uri.parse(MapURL));
			//startActivity(intent);
			
			//    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
			//shttp://maps.google.com/maps?saddr=my+location&daddr=37.383301,-121.971881
			
			
	    
	    
	    
		String phoneNumber = "4692688887";
	    String message = "Hello World!";

	    SmsManager smsManager = SmsManager.getDefault();
	 //  smsManager.sendTextMessage(phoneNumber, null, MapURL, null, null);
	
	}
	


	public void Fact(View v){
		
	    
		MainActivity my = new MainActivity();

		    if (mBound || true) {
		        // Call a method from the LocalService.
		        // However, if this call were something that might hang, then this request should
		        // occur in a separate thread to avoid slowing down the activity performance.
		        int num = mService.getRandomNumber();
		        Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
		    }
		    
		}

		@Override
		protected void onStart() {
		    super.onStart();
		    // Bind to LocalService
		   // Intent intent = new Intent(this, BindService.class);
		    //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		    
		    
		    
		}

		@Override
		protected void onStop() {
		    super.onStop();
		    // Unbind from the service
		    if (mBound) {
		        unbindService(mConnection);
		        mBound = false;
		    }
		}
		
		
		

		/** Called when a button is clicked (the button in the layout file attaches to
		  * this method with the android:onClick attribute) 
		 * @throws IOException */
		@SuppressWarnings("null")
		public void onButtonClick(View v) throws IOException {
			 bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			 
		//	Toast.makeText(getBaseContext(), "Goooo!", Toast.LENGTH_SHORT)
		 //   .show();
			
			EditText address1 = (EditText)findViewById(R.id.etAddress);
		   if (mBound) {
	
		    	
		    	InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		    	imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		    	
		    	
		       
		    	//Toast.makeText(getBaseContext(), "Service called!", Toast.LENGTH_SHORT)
			   //a .show();
		        
		    	 myLocationText = (TextView)findViewById(R.id.tvDistance);
		    mService.GetDistance(myLocationText,address1);
		  
		    }
		    
			
			//Content provider code
			//beg guru start
			
		  
		  if(! Arrays.asList(address).contains(address1.getText().toString() )){
			  
		
			 ContentValues values = new ContentValues();
			  values.put(Provider.name, ((EditText) findViewById(R.id.etAddress))
			    .getText().toString());
			  Uri uri = getContentResolver().insert(Provider.CONTENT_URI, values);
			  Toast.makeText(getBaseContext(), "New location/address saved", Toast.LENGTH_SHORT)
			    .show();
			//beg guru end
		
		  }
			
		        
		        
			  PopulateAddress();
              
			  
			  
			
		}

		/** Defines callbacks for service binding, passed to bindService() */
		private ServiceConnection mConnection = new ServiceConnection() {

		    @Override
		    public void onServiceConnected(ComponentName className,
		            IBinder service) {	
		        // We've bound to LocalService, cast the IBinder and get LocalService instance
		    	LocalBinder binder = (LocalBinder) service;
		        mService = binder.getService();
		        mBound = true;
		      //  Toast.makeText(getApplicationContext(), "Service connected", Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onServiceDisconnected(ComponentName arg0) {
		        mBound = false;
		      //  Toast.makeText(getApplicationContext(), "Service disconnected", Toast.LENGTH_SHORT).show();
		    }


			
		};



public void ExitApp(View v) 
{
	
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
					mService.stopSelf();
					//mService.de
					MainActivity.this.finish();
					android.os.Process.killProcess(android.os.Process.myPid());
				
					MainActivity.super.onDestroy();
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
			
		}


public void testPref(View v){
   SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
 String syncConnPref = sharedPref.getString("prefDisNot", "");
 Toast.makeText(getApplicationContext(), String.valueOf(sharedPref.getBoolean("prefDistUnit", true)), Toast.LENGTH_SHORT).show();

 }
	
	


public void ClearAddress(View v)
{
	EditText address = (EditText)findViewById(R.id.etAddress);
	address.setText(null);
	cleartext.setVisibility(View.INVISIBLE);
	//dellink.setVisibility(1);
	
}

public void SpeakUp(View v){

	
	
	InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
	imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	
	
	 Button b = (Button) v.findViewById(R.id.bSpeak);
	 
	//Button b = new Button(this);
	if ( b.getTextColors().equals(Color.GREEN))
	{
		b.setTextColor(Color.GREEN);
	
	}
	else
	{
	if (!b.getTextColors().equals(Color.WHITE))
	{
	b.setTextColor(Color.WHITE);
	}
	}
	EditText address = (EditText)findViewById(R.id.etAddress);
	//ttobj.speak(address.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
	
	
	//Beg add guru
	
	//EditText ad = (EditText)findViewById(R.id.etAddress);
	
	
	//end add guru
}
public void OpenMap(View v){
	//EditText address = (EditText)findViewById(R.id.etAddress);
    
	Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(mService.NavURL));
	startActivity(intent);
	
	//  Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345");
    //mService.OpenMapApp();
}

public void DeleteAdd(View V){
	 Uri allAddresses = Uri.parse("content://com.example.takemethere.Provider/cte/2");
	   // int c = getContentResolver().delete(allAddresses, null, null);
	 String[] a = new String[1];
	 a[0]=AutoComp.getText().toString();
	 int c = getContentResolver().delete(allAddresses, "name=?", a);
	    Toast.makeText(getApplicationContext(),String.valueOf(c) + " address deleted!", Toast.LENGTH_SHORT).show();
	    
	    address =null;
	    PopulateAddress();
}



public void PopulateAddress(){
	
	bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    Uri allAddresses = Uri.parse("content://com.example.takemethere.Provider/cte/2");
    Cursor c = getContentResolver().query(allAddresses, null, null, null, null);

    int i = 0;
     address = new String[c.getCount()] ;

    while(c.moveToNext())
 	  {
 		  //Toast.makeText(getApplicationContext(),c.getString(1), Toast.LENGTH_SHORT).show();
 	  address[i++]=c.getString(1);
 	  
 	 
 	  }
    
 
    myLocationText = (TextView)findViewById(R.id.tvDistance);
cleartext = (Button)findViewById(R.id.bClearAddress);
    
    AutoComp = (AutoCompleteTextView)findViewById(R.id.etAddress);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.dropdown,address);
    AutoComp.setThreshold(0);
   AutoComp.addTextChangedListener(new TextWatcher() {

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           if (AutoComp.getText().length()==0)  
        	   cleartext.setVisibility(View.INVISIBLE);
           else
        	   cleartext.setVisibility(View.VISIBLE);
        		   
       }

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}});
    

    AutoComp.setOnItemClickListener(new OnItemClickListener() {


		

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), arg0.set, Toast.LENGTH_SHORT).show();
			//dellink.setVisibility(0);
			
			
		}
    });
    
    AutoComp.setAdapter(adapter);	
}


public void startNotification(View V) {
    
/*	String i = null;
	if (syncConnPref) 
i="Yes";
	else
		i="No";
	
	
	Toast.makeText(getApplicationContext(),i , Toast.LENGTH_SHORT).show();
	*/
	/*
	// TODO Auto-generated method stub
    //Creating Notification Builder
      NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this);
       //Title for Notification
       notification.setContentTitle("Destination Reached!");
       //Message in the Notification
       notification.setContentText("Click to close.");
       //Alert shown when Notification is received
       notification.setTicker("You have reached your destination. This is a test status message !");
       //Icon to be set on Notification
       notification.setSmallIcon(R.drawable.ic_launcher);
       //Creating new Stack Builder
       TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
       stackBuilder.addParentStack(MainActivity.class);
       //Intent which is opened when notification is clicked
      Intent resultIntent = new Intent(this, MainActivity.class);
      resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      
    stackBuilder.addNextIntent(resultIntent);   
     PendingIntent pIntent =  stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    // PendingIntent pIntent = new PendingIntent();
        //  notification.setContentIntent(pIntent);
      notification.setContentIntent(pIntent);
           
       
       Uri ringURI = 
    	        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

       notification.setSound(ringURI);
       long[] vibrate = {100,500,100,500};
       notification.setVibrate(vibrate);
       
       
       NotificationManager  manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           manager.notify(0, notification.build());
           
           
           */
  }

}




