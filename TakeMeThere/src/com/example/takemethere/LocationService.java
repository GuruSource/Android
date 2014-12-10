package com.example.takemethere;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class LocationService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    private SharedPreferences sharedPref;
    private LocationManager service;
    private String distunit;
    private double distunitMul;
	
    
    TextView myLocationText ;
    EditText address;
    String MapURL;
	String NavURL;
    
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
    	
    	LocationService getService() {
            // Return this instance of LocalService so clients can call public methods
    		
    	
            return LocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
      return mGenerator.nextInt(100);
    }

    public int factorial(Integer n){
    	 int  c, fact = 1;
       
         if ( n > 0 )
         {
            for ( c = 1 ; c <= n ; c++ )
               fact = fact*c;
    
            return fact;
         }
		return 0;
    }
    
    public void OpenMapApp()
    {
    	
    	
    	//Toast.makeText(getApplicationContext(), MapURL, Toast.LENGTH_SHORT).show();
		
    			
    	
    	
    }
    
    public String GetDistance(TextView myLocation, EditText add) throws IOException{
    	
    	myLocationText = myLocation;
    	address=add;
    	
    	


    	 sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		 if (sharedPref.getBoolean("prefDistUnit", true) )
		 {
			 distunit = " miles" ;
			 distunitMul = 1.60934;
		 }
		 else
		 {
			 distunit = " kms" ;
			 distunitMul = 1;
		 }
		 
		 
		 
		 
		 
     service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		
		if (!enabled) {
		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent);
		  Toast.makeText(getApplicationContext(), "Please enable location service", Toast.LENGTH_SHORT).show();
		  return "Turn on Location services";
		} 
		Criteria criteria =new Criteria();
		LocationManager locationManager ;
		   locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		   
		String provider = service.getBestProvider(criteria,false);
		String lat;
		String lon;
		
	//	LocationListener mlocListener = null ;`
		// service.requestLocationUpdates( provider, 0, 0, mlocListener);
		
		service.getLastKnownLocation(provider);
		Location location = service.getLastKnownLocation(provider);
		

	    updateWithNewLocation(location,myLocationText);

	    locationManager.requestLocationUpdates(provider, 2000, 10,
	                                           locationListener);
	    
		lat=Double.toString(location.getLatitude());
		lon=Double.toString(location.getLongitude());
		MapURL= "http://maps.google.com/maps?&q=loc:"+lat +"+" + lon;
		NavURL="http://maps.google.com/maps?saddr=my+location&daddr=" +lat +"+" + lon;
		
		
		
		
		
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
	
    	
		return null;
    	
    
    }

    private void updateWithNewLocation(Location location, TextView myLocationText1) throws IOException {
       // TextView myLocationText;
       //myLocationText = (TextView)findViewById(R.id.tvDistance);
          
        String latLongString = "No location found";
        String addressString = "No address found";
        
        if (location != null) {
        double lat = location.getLatitude();
          double lng = location.getLongitude();
          latLongString = "Lat:" + lat + "\nLong:" + lng;
          
          double latitude = location.getLatitude();
          double longitude = location.getLongitude();
     }
        
        double lat1 = 0,lat2=0,lng1=0,lng2;
		//lat1=37.361637;
		//lng1=-122.025495;
		lat2=location.getLatitude();
		lng2=location.getLongitude();
		
		Geocoder fwdGeocoder = new Geocoder(this, Locale.US);
		
		List<Address> loc = fwdGeocoder.getFromLocationName(address.getText().toString(), 1);
		
		if (loc != null && loc.size() > 0) {  
//37.358436, -122.051259/
			
         lat1 = (loc.get(0).getLatitude() );  

         lng1 = (loc.get(0).getLongitude() );  

         String t;
         
         t= lat1+ " " + lng1;
         
         NavURL="http://maps.google.com/maps?saddr=my+location&daddr=" +lat1 +"+" + lng1;

      // Toast.makeText(getApplicationContext(), "37.358436, -122.051259 " + t , Toast.LENGTH_SHORT).show();
        
     //    Toast.makeText(getApplicationContext(), "Hit!" , Toast.LENGTH_SHORT).show();
         
      //  TextView tvresult =  (TextView)findViewById(R.id.textView1);

//tvresult.setText("Result Display Here");   

			}



		
	
		
		//lat2=37.353193;
		//lng2=-122.014423;
		
		double earthRadius = 6371; //kilometers
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) ((earthRadius * c)/distunitMul );
	    
	    //Toast.makeText(getApplicationContext(), Float.toString(dist), Toast.LENGTH_SHORT).show();
		
	//    myLocationText.setText( Float.toString(dist));

	    myLocationText1.setText(String.format("%.2f", dist) + distunit);
	    

        
          
      
          
       // myLocationText.setText("Your Current Position is:\n" +
       //   latLongString + "\n\n" + addressString);
      }
    
    
    private TextView findViewById(int tvdistance) {
		// TODO Auto-generated method stub
		return null;
	}


	private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
          try {
			updateWithNewLocation(location, myLocationText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
        
    };
}
