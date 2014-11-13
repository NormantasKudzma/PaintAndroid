package com.paintdroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
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
		((RelativeLayout)findViewById(R.id.draw_layout)).setOnTouchListener(new View.OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent e) {
				int index = e.getActionIndex();
				int pid = e.getPointerId(index);
			    int action = e.getActionMasked();
			    Point p = new Point();
			    p.x = (int)e.getX(pid);
	        	p.y = (int)e.getY(pid);
			    if (pid < e.getPointerCount()){
				    switch (action){
			        	case MotionEvent.ACTION_POINTER_DOWN: {
			        		break;
			        	}          
			        	case MotionEvent.ACTION_DOWN: { 	        		
				            Action.point.set(p.x, p.y);
				            client.performAction(Action.point);
				            break;
				        }  
				        case MotionEvent.ACTION_MOVE: {
				            Action.line.set(p.x, p.y, oldPoint.x, oldPoint.y);
				            client.performAction(Action.line);
				            break;
				        }
				        case MotionEvent.ACTION_UP: {
				        	break;
				        }
				    }		   
			    }	
			    return true;
			}
		});
		
//		((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
}
