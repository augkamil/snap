package com.snap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.zxing.client.android.CaptureActivity;

public class ScanQR extends CaptureActivity{
	
	ImageButton collectBtn;
	ImageButton scanBtn;
	ImageButton findNewBtn;
	
	Intent i = null;
	Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr);
        context = getApplicationContext();
        
        collectBtn = (ImageButton)findViewById(R.id.collect_btn); 
        scanBtn = (ImageButton)findViewById(R.id.scan_btn);
        findNewBtn = (ImageButton)findViewById(R.id.find_new_btn);
        
        collectBtn.setOnClickListener(lCollect);
        findNewBtn.setOnClickListener(lFindNew);
        
    }
    
    private View.OnClickListener lCollect = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			i = new Intent(context, CardFirstPage.class);
			startActivity(i);
		}
	};      
	
	
	private View.OnClickListener lFindNew = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			i = new Intent(context, ListCards.class);
			startActivity(i);
		}
	};
	
	

}
