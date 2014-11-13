package com.paintdroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.util.Log;

public class CommClient implements Runnable{
	public static final int PORT = 60210;
	public static final String IP = "172.16.143.205";
//	public static final String IP = "192.168.1.74";
	
	Socket socket;
	PrintWriter writer;
	BufferedReader reader;
	Design design;
	
	boolean done = false;
	boolean send = false;
	Action action;
	
	public CommClient(Design d){
		design = d;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket();
			Log.d("CommClient", "Created new socket");
			socket.setReuseAddress(true);
			socket.bind(null);
			
			socket.connect(new InetSocketAddress(IP, 60210));
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			Action.point.set(10, 10);
			performAction(Action.point);
			
			while (!done){
				if (send && checkConnection()){
					writer.println(action.toString());
					Log.w("CommClient", "Sent some stuff :" + action);
					writer.flush();
					Thread.sleep(50);
					String line = reader.readLine();
					Log.w("CommClient", "Got answer " + line.getBytes());
					send = false;
				}
			}
			stopClient();
		}
		catch (Exception e){
			Log.e("CommClient", "Runtime error: " + e.getMessage());
		}

		// stub
		// client logika - siuncia komanda, [apdorojama], laukia atsakymo
		// server logika - laukia komandos, apdoroja, issiuncia		
	}
	
	public void stopClient(){
		try {
			done = true;
			if (writer != null) writer.close();
			if (reader != null) reader.close();
			if (socket != null && !socket.isClosed()) socket.close();
		}
		catch (Exception e){
			Log.e("CommClient", "Error when stopping client: " + e.getMessage());
		}
	}
	
	public void performAction(Action a){
		send = true;
		action = a;
	}
	
	private boolean checkConnection(){
		boolean isConnected = socket.isConnected();
		if (!isConnected){
			stopClient();
		}
		return isConnected;
	}
}
