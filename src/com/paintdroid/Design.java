package com.paintdroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Design extends Activity {
	CommClient client;
	
	Point oldPoint = new Point();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_droid_design);
		setListeners();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		client = new CommClient(this);
		(new Thread(client)).start();
	}
	
	private void setListeners(){
		 findViewById(R.id.draw_layout).setOnTouchListener(new View.OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent e) {
//				int index = e.getActionIndex();
//				int pid = e.getPointerId(index);
//			    int action = e.getActionMasked();
//			    Point p = new Point();
//			    p.x = (int)e.getX(pid);
//	        	p.y = (int)e.getY(pid);
//			    if (pid < e.getPointerCount()){
//				    switch (action){
//			        	case MotionEvent.ACTION_POINTER_DOWN: {
//			        		break;
//			        	}          
//			        	case MotionEvent.ACTION_DOWN: { 	        		
//				            Action.point.set(p.x, p.y);
//				            client.performAction(Action.point);
//				            break;
//				        }  
//				        case MotionEvent.ACTION_MOVE: {
//				            Action.line.set(p.x, p.y, oldPoint.x, oldPoint.y);
//				            client.performAction(Action.line);
//				            break;
//				        }
//				        case MotionEvent.ACTION_UP: {
//				        	break;
//				        }
//				    }		   
//			    }	
			    return true;
			}
		});
		final RelativeLayout rl = (RelativeLayout)findViewById(R.id.menu_layout);
		
		final Animation zoom_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.zoom_in);
        final Animation zoom_out = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.zoom_out);
        
        final Animation move_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.move_up);
        final Animation move_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.move_down);
        
        final ImageButton menuButton = (ImageButton)findViewById(R.id.menubutton);
        
		menuButton.setOnClickListener(new View.OnClickListener() {
           boolean isOpen = false;
			
           @Override
			public void onClick(View v) {
        	   if(!isOpen){
        		   rl.setLayoutParams(new LinearLayout.LayoutParams(
                           LayoutParams.MATCH_PARENT,
                           LayoutParams.MATCH_PARENT));
        		   menuButton.startAnimation(move_down);
        		   rl.startAnimation(zoom_in);
        		   
        		   isOpen = !isOpen;
        	   }
        	   else {
        		   menuButton.startAnimation(move_up);
        		   rl.startAnimation(zoom_out);
        		  
        		   
        		   final Handler zoomHandler = new Handler(){
        			   @Override
        			   public void handleMessage(Message msg) {
        				   rl.setLayoutParams(new LinearLayout.LayoutParams(
        						   LayoutParams.MATCH_PARENT,
    	                           0));
        			   }
        		   	};
        		   
        		   	new Thread(new Runnable(){
        		   		@Override
        		   		public void run() {
        		   			try{
        		   				Thread.sleep(800);
        		   				zoomHandler.sendEmptyMessage(0);
        		   			}
        		   			catch(Exception e){
        		   				e.printStackTrace();
        		   			}
        		   		}
        		   	}).start();
        		   isOpen = !isOpen;
        	   }
        		   
            }
        });
	}
}
