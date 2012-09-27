package com.snap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class ListMyCards extends Activity{
	
	ImageButton mapBtn;
	ImageButton listBtn;
	ImageButton cardBtn;
	
	Context context;
	Intent i = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_my_cards);
        
        context = getApplicationContext();
        
        mapBtn = (ImageButton)findViewById(R.id.map_btn);
        listBtn = (ImageButton)findViewById(R.id.list_btn);
        cardBtn = (ImageButton)findViewById(R.id.card_btn);
        
        mapBtn.setImageResource(R.drawable.map_btn_lq);
        listBtn.setImageResource(R.drawable.list_btn_pressed_lq);
        cardBtn.setImageResource(R.drawable.card_btn_lq);
        
        cardBtn.setOnClickListener(lCard);
        mapBtn.setOnClickListener(lMap);
    }

    private View.OnClickListener lCard = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			i = new Intent(context, CardFirstPage.class);
			startActivity(i);
		}
	};
	
	private View.OnClickListener lMap = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			i = new Intent(context, Map.class);
			startActivity(i);
		}
	};
}
