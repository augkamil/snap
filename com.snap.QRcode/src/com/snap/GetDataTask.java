package com.snap;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.google.android.maps.GeoPoint;


public class GetDataTask extends AsyncTask<String, Integer, ArrayList<PartnerPoint>> {
	
	private Context context = null;

	GetDataTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected ArrayList<PartnerPoint> doInBackground(String... params) {
		
		// pobieramy dane z API, korzystając z Apache HTTP Client
		HttpClient httpclient = new DefaultHttpClient();
		// ułatwiamy sobie życie
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		String response = null;
		
		try {
			response = httpclient.execute(new HttpGet(params[0]), responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<PartnerPoint> result = null;
		
		if (response != null) {
	
			JSONObject json = null;
			
			try {
				json = new JSONObject(response);
				
				if (json != null) {
					result = new ArrayList<PartnerPoint>();
					JSONArray partners = json.getJSONArray("partners");
					
					for (int i = 0; i < partners.length(); i++) {

						// konstruujemy obiekt pozycji
						GeoPoint point = new GeoPoint(	(int) (partners.getJSONObject(i).getInt("latitude")), 
														(int) (partners.getJSONObject(i).getInt("longitude")));

						Drawable marker1 = ((Map) context).getResources().getDrawable(R.drawable.restaurant);
						
						// dodajemy obiekt miejsca do listy
						result.add(new PartnerPoint(point, marker1, partners.getJSONObject(i).getInt("id"), 
																	partners.getJSONObject(i).getString("name"), 
																	partners.getJSONObject(i).getString("description")));


					}			
				}	
				
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		
		return result;
	}

	@Override
	protected void onPostExecute(ArrayList<PartnerPoint> result) {
		((Map) context).makeOverlay(result);
	}

}
