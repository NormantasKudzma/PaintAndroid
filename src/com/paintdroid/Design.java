package com.paintdroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Design extends Activity {
	CommClient client;
	
	Point p = new Point();
	Point p2 = new Point();
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
			 boolean wasMoved = false;
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				int index = e.getActionIndex();
				int pid = e.getPointerId(index);
			    int action = e.getActionMasked(); 
			    
			    if (pid < e.getPointerCount()){
				    switch (action){
			        	case MotionEvent.ACTION_POINTER_DOWN: {
			        		break;
			        	}          
			        	case MotionEvent.ACTION_DOWN: { 	        		
			        		p.x = (int)e.getX(pid);
				        	p.y = (int)e.getY(pid);
				     //       Log.d("A_DOWN", Integer.toString(p.x) + " " + Integer.toString(p.y));
				            break;
				        }  
			        	case MotionEvent.ACTION_MOVE: {
//			       	    	wasMoved = true;
//			       	    	p2.x = (int)e.getX(pid);
//				         	p2.y = (int)e.getY(pid);
//			        		float dist = (Math.abs(p2.x - p.x) + Math.abs(p2.y - p.y)) / 2f;
//							float treshold = 4 * 0.15f;
//							if (dist > treshold){
//								Action.line.set(p.x, p.y, p2.x, p2.y);
//					        	client.performAction(Action.line);
//							
//							}
//							else {
//								Action.point.set(p2.x, p2.y);
//				        		client.performAction(Action.point);
//							}			        	
//				        	p.x = p2.x;
//				        	p.y = p2.y;
				        	
				            break;
				        }
				        case MotionEvent.ACTION_UP: {
			        		p2.x = (int)e.getX(pid);
				        	p2.y = (int)e.getY(pid);
//				        	if(wasMoved == false) {
//				        		Action.point.set(p.x, p.y);
//				        		client.performAction(Action.point);
//				        		break;
//				        	}
				        	Action.line.set(p.x, p.y, p2.x, p2.y);
				        	client.performAction(Action.line);
				        	Log.d("A_UP", Integer.toString(p.x) + " " + Integer.toString(p.y) + " " + Integer.toString(p2.x) + " " + Integer.toString(p2.y));
				        	wasMoved = false;
				        	 break;
				        }
				    }		   
			    }	
			    return true;
			}
		});
		 
		
		 
		final RelativeLayout rl = (RelativeLayout)findViewById(R.id.menu_layout);
		//final LinearLayout ll = (LinearLayout)findViewById(R.id.brush_layout);
		
		final Animation zoom_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.zoom_in);
        final Animation zoom_out = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.zoom_out);
        
        final Animation move_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.move_up);
        final Animation move_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.drawable.move_down);
        
        final ImageButton menuButton = (ImageButton)findViewById(R.id.menubutton);
        
        final Button colorButton = (Button)findViewById(R.id.color_button);
      //  final Button brushSelectButton = (Button)findViewById(R.id.select_brush);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.brush_size_seek);
        final TextView textView = (TextView) findViewById(R.id.brush_size_view);
        
        rl.setLayoutParams(new LinearLayout.LayoutParams(
				   LayoutParams.MATCH_PARENT,
                0));
        
		menuButton.setOnClickListener(new View.OnClickListener() {
           boolean isOpen = false;
			
           @Override
			public void onClick(View v) {
        	   if(!isOpen){
        		   rl.setLayoutParams(new LinearLayout.LayoutParams(
                           LayoutParams.MATCH_PARENT,
                           LayoutParams.MATCH_PARENT));
        		   //menuButton.startAnimation(move_down);
        		   rl.startAnimation(zoom_in);
        		   
        		   isOpen = !isOpen;
        	   }
        	   else {
        		   //menuButton.startAnimation(move_up);
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
		
	      // Initialize the textview with '0'.
			textView.setText("Brush Size: " + seekBar.getProgress() + "/" + seekBar.getMax());
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			        int progress = 0; 
			          @Override
			        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
			            progress = progresValue;
			        }

			          @Override
			        public void onStopTrackingTouch(SeekBar seekBar) {
			            textView.setText("Brush Size: " + progress + "/" + seekBar.getMax());
			        }

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub	
					}
			       });
		
			
				// colorButton Listener
			colorButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Action.color.set(165102202);
					client.performAction(Action.color);
				}
				
			});
//		brushSelectButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//				ll.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.MATCH_PARENT));
//			}
//		});
	}
}
