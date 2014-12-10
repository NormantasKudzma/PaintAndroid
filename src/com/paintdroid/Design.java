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
			       	    	p2.x = (int)e.getX(pid);
				         	p2.y = (int)e.getY(pid);
			        		float dist = (Math.abs(p2.x - p.x) + Math.abs(p2.y - p.y)) / 2f;
							float treshold = 4 * 0.15f;
							if (dist > treshold){
								Action.line.set(p.x, p.y, p2.x, p2.y);
					        	client.performAction(Action.line);						
							}
							else {
								Action.point.set(p2.x, p2.y);
				        		client.performAction(Action.point);
							}			        	
				        	p.x = p2.x;
				        	p.y = p2.y;			        	
				            break;
				        }
				        case MotionEvent.ACTION_UP: {
			        		p2.x = (int)e.getX(pid);
				        	p2.y = (int)e.getY(pid);

				        	Action.line.set(p.x, p.y, p2.x, p2.y);
				        	client.performAction(Action.line);
				        	Log.d("A_UP", Integer.toString(p.x) + " " + Integer.toString(p.y) + " " + Integer.toString(p2.x) + " " + Integer.toString(p2.y));

				        	 break;
				        }
				    }		   
			    }	
			    return true;
			}
		});
		 
		
		 
		final RelativeLayout rl = (RelativeLayout)findViewById(R.id.menu_layout);
		
		final RelativeLayout toolsLayout = (RelativeLayout)findViewById(R.id.tools_layout);
		
		final RelativeLayout shapeLayout = (RelativeLayout)findViewById(R.id.shape_layout);
		
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
        
        final Button toolsButton = (Button)findViewById(R.id.tools_button);
        
        final Button shapeButton = (Button)findViewById(R.id.shape_button);
        
        final Button colorButton = (Button)findViewById(R.id.color_button);
        
        final Button saveButton  = (Button)findViewById(R.id.save_image);
        
        final Button loadButton = (Button)findViewById(R.id.load_image);
        
        
        final Button backButton = (Button)findViewById(R.id.back);
        final Button pencilButton = (Button)findViewById(R.id.pencil);
        final Button lineButton   = (Button)findViewById(R.id.straight_line);
        final Button bucketButton = (Button)findViewById(R.id.bucket);
        final Button pickerButton = (Button)findViewById(R.id.picker);
        
        final Button backButton1 = (Button)findViewById(R.id.back1);
        final Button rectangleButton = (Button)findViewById(R.id.rectangle);
        final Button circleButton   = (Button)findViewById(R.id.circle);
        final Button starButton = (Button)findViewById(R.id.star);
        final Button triangleButton = (Button)findViewById(R.id.triange);
        
      //  final Button brushSelectButton = (Button)findViewById(R.id.select_brush);
        
        final SeekBar brushSizeBar = (SeekBar) findViewById(R.id.brush_size_seek);
        
        final TextView brushSizetextView = (TextView) findViewById(R.id.brush_size_view);
        
        final SeekBar angleSizeBar = (SeekBar) findViewById(R.id.angle_seek);
        
        final TextView angleSizetextView = (TextView) findViewById(R.id.angle_view);
        
        
        
        rl.setLayoutParams(new LinearLayout.LayoutParams(
				   	LayoutParams.MATCH_PARENT,
				   	0));
        toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
        			LayoutParams.MATCH_PARENT,
        			0));
        shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
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
			brushSizetextView.setText("Brush Size: " + brushSizeBar.getProgress() + "/" + brushSizeBar.getMax());
			brushSizeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			        int progress = 0; 
			          @Override
			        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
			            progress = progresValue;
			        }

			          @Override
			        public void onStopTrackingTouch(SeekBar seekBar) {
			            brushSizetextView.setText("Brush Size: " + progress + "/" + seekBar.getMax());
			            Action.brushSize.set(progress);
			            client.performAction(Action.brushSize);
			          }

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub	
					//	  Action.brushSize.set(progress);
				    //        client.performAction(Action.brushSize);
					}
			       });
			
			// Initialize the textview with '0'.
			angleSizetextView.setText("Angle Size: " + angleSizeBar.getProgress() + "/" + angleSizeBar.getMax());
			angleSizeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			        int progress = 0; 
			          @Override
			        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
			            progress = progresValue;
			        }

			          @Override
			        public void onStopTrackingTouch(SeekBar seekBar) {
			            angleSizetextView.setText("Angle Size: " + progress + "/" + seekBar.getMax());
			            Action.brushRotation.set(progress);
			            client.performAction(Action.brushRotation);
			          }

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub	
					}
			       });
		
			// toolsButton Listener
						toolsButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								
								
								menuButton.setVisibility(View.GONE);
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			LayoutParams.MATCH_PARENT));
							}	
						});
			
			// shapeButton Listener
						shapeButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.GONE);
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			LayoutParams.MATCH_PARENT));
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
						
			// saveButton Listener
						saveButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								
							}			
			});
			// loadButton Listener
						loadButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								
							}			

			});
						
			// backButton Listener
						backButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								menuButton.setVisibility(View.VISIBLE);
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								
							}
						});
			// pencilButton Listener
						pencilButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.tool.set(1);
								client.performAction(Action.tool);
							}
						});
						
			// straightLineButton Listener
						lineButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.tool.set(2);
								client.performAction(Action.tool);
							}
						});
						
			// bucketButton Listener
						bucketButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.tool.set(3);
								client.performAction(Action.tool);
							}
						});
						
			// pickerButton Listener
						pickerButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.tool.set(4);
								client.performAction(Action.tool);
							}
						});
						
						
			// backButton Listener
						backButton1.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								menuButton.setVisibility(View.VISIBLE);
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								toolsLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								
							}
						});
			// rectangleButton Listener
						rectangleButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.shape.set(1);
								client.performAction(Action.shape);
							}
						});
						
			// circleLineButton Listener
						circleButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.shape.set(2);
								client.performAction(Action.shape);
							}
						});
						
			// starButton Listener
						starButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.shape.set(4);
								client.performAction(Action.shape);
							}
							
						});
						
			// triangleButton Listener
						triangleButton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								menuButton.setVisibility(View.VISIBLE);
								shapeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
					        			LayoutParams.MATCH_PARENT,
					        			0));
								Action.shape.set(3);
								client.performAction(Action.shape);
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
