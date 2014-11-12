package com.paintdroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class CommClient extends Thread{
	Socket socket;
	PrintWriter writer;
	BufferedReader reader;
	
	boolean done = false;
	boolean send = false;
	Action action;
	
	public CommClient(Socket s){
		try {
			socket = s;
			writer = new PrintWriter(s.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch (Exception e){
			Log.e("CommClient", "Socket error: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try {
			while (!done){
				if (send && checkConnection()){
					writer.println(action);
					writer.flush();
					// wait for response after flush
					// image = reader.read().convert to bufferedimage, pass to design
					send = false;
					Log.d("CommClient", "Sent action : " + action);
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
