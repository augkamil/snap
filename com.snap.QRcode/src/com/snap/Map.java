package com.snap;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Map extends MapActivity implements LocationListener {
	
	MapView map = null;
	MyLocationOverlay overlay;
	LocationManager locationManager;
	String bestProvider;
	MapController mapController;
	
	ImageButton mapBtn;
	ImageButton listBtn;
	ImageButton cardBtn;
	
	Context context;
	Intent i = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        context = getApplicationContext();
        
        map = (MapView) findViewById(R.id.map); 
        mapController = map.getController();
        
        mapController.setZoom(16);
        map.setBuiltInZoomControls(true);
        map.setSatellite(false);
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
		bestProvider = locationManager.getBestProvider(criteria, false);
		
		Location location = locationManager.getLastKnownLocation(bestProvider);
		GeoPoint point = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));
		Log.d("GPS", ""+point);   
		
		mapController.animateTo(point);
        
        mapBtn = (ImageButton)findViewById(R.id.map_btn);
        listBtn = (ImageButton)findViewById(R.id.list_btn);
        cardBtn = (ImageButton)findViewById(R.id.card_btn);
        
        mapBtn.setImageResource(R.drawable.map_btn_pressed_lq);
        listBtn.setImageResource(R.drawable.list_btn_lq);
        cardBtn.setImageResource(R.drawable.card_btn_lq);
        
        cardBtn.setOnClickListener(lCard);
        listBtn.setOnClickListener(lList);
        
        GetDataTask getDataTask = new GetDataTask(this);
        getDataTask.execute("http://peaceful-hollows-9449.herokuapp.com/partners.json");
    }
    
    public void makeOverlay(ArrayList<PartnerPoint> partnerPoints) {
    	
		// dodajemy naszą warstwę do mapy
		map.getOverlays().add(new PartnersOverlay(this, partnerPoints));
	}
    
    @Override
	protected void onPause() {
		super.onPause();	
		//overlay.disableMyLocation();
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//overlay.enableMyLocation();
		locationManager.requestLocationUpdates(bestProvider, 20000, 1, this);
	}


    private View.OnClickListener lCard = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			i = new Intent(context, CardFirstPage.class);
			startActivity(i);
		}
	};
	
	private View.OnClickListener lList = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			i = new Intent(context, ListMyCards.class);
			startActivity(i);
		}
	};

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
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

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
