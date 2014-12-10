package com.paintdroid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Environment;
import android.util.Log;

public class CommClient implements Runnable{
	protected enum SetVar {
		VARIP {
			void set(String s){
				CommClient.IP = s;
			}
			String get(){
				return CommClient.IP;
			}
		},
		VARPORT {
			void set(String s){
				CommClient.PORT = Integer.parseInt(s);
			}
			Integer get(){
				return CommClient.PORT;
			}
		};
		
		abstract void set(String s);
		abstract Object get();
	}
	
	public final static String DEF_PATH = Environment.getExternalStorageDirectory().toString();
	public final static String FILE_NAME = "CommClientSettings.dat";
	
	static int PORT = 60210;
	static String IP = "192.168.1.74";
	
	Socket socket;
	PrintWriter writer;
	BufferedReader reader;
	Design design;
	
	boolean done = false;
	boolean send = false;
	Action action;
	
	public CommClient(Design d){
		design = d;
		loadSettings(DEF_PATH + "/" + FILE_NAME);	
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket();
			Log.d("CommClient", "Created new socket");
			socket.setReuseAddress(true);
			socket.bind(null);
			
			socket.connect(new InetSocketAddress(IP, 60210));
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		//	Action.point.set(10, 10);
		//	performAction(Action.point);
			
			while (!done){
				if (send && checkConnection()){
					writer.println(action.toString());
					Log.w("CommClient", "Sent some stuff :" + action);
				//	writer.flush();
				//	Thread.sleep(50);
				//	String line = reader.readLine();
				//	Log.w("CommClient", "Got answer " + line.getBytes());
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

	private void initSettings(String args){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(design.getAssets().open(FILE_NAME)));
			loadSettings(br);
			saveSettings(args);
			br.close();
		}
		catch (Exception e){
			Log.e("CommClient", "Error initializing settings " + e.getMessage());
		}
	}
	
	private void loadSettings(BufferedReader br){
		try {
			String line;
			while ((line = br.readLine()) != null){
				try {
					String st = line.substring(0, line.indexOf('='));
					String end = line.substring(line.indexOf('=') + 1);
					Log.d("CommClient", "Read parameter : " + st + " ; value : " + end);
					SetVar.valueOf(st).set(end);
				}
				catch (Exception e){
					e.printStackTrace();
				}			
			}
			br.close();
		}
		catch (Exception e){
			Log.e("CommClient", "Error loading settings " + e.getMessage());
			initSettings(DEF_PATH + "/" + FILE_NAME);
		}
	}
	
	private void loadSettings(String args){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(args)));
			loadSettings(br);
			br.close();
		}
		catch (Exception e){
			Log.e("CommClient", "Error loading settings " + e.getMessage());
			initSettings(DEF_PATH + "/" + FILE_NAME);
		}
	}
	
	private void saveSettings(String args){
		try {
			Log.d("CommClient", "Writing to settings file..");
			BufferedWriter br = new BufferedWriter(new FileWriter(new File(args)));
			br.write(SetVar.VARIP.name() + "=" + IP + "\n");
			br.write(SetVar.VARPORT.name() + "=" + PORT);
			br.flush();
			br.close();
			Log.d("CommClient", "Writing - success.");
		}
		catch (Exception e){
			Log.e("CommClient", "Error saving settings " + e.getMessage());
		}
	}
}
