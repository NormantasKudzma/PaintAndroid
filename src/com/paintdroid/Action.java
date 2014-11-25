package com.paintdroid;

// x1 y1 x2 y2 - nesilaikai susitarimo - nukaposiu pirstus
public enum Action {
	point {
		int x, y;
		void set(int ...args){
			x = args[0];
			y = args[1];
		}
		public String toString(){
			return formatString(x, y);
		}
	},
	line {
		int x1, y1, x2, y2;
		void set(int ...args){
			x1 = args[0];
			y1 = args[1];
			x2 = args[2];
			y2 = args[3];
		}
		public String toString(){
			return formatString(x1, y1, x2, y2);
		}
	};
	
	abstract void set(int ...args);
	public abstract String toString();
	static private String formatString(int ...args){
		String str = "";
		for (int i : args){
			str += i + " ";
		}
		return str;
	}
}
