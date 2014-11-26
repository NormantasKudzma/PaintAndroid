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
			return formatString(1, x, y);
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
			return formatString(2, x1, y1, x2, y2);
		}
	},
	color {
		int cRGB;
		void set(int... args) {
			cRGB = args[0];
		}
		
		public String toString() {
			return formatString(3, cRGB);
		}
	},
	brushSize {
		int bSize;
		void set(int... args) {
			bSize = args[0];
		}

		public String toString() {
			return formatString(4, bSize);
		}
			
	},
	shape {
		String shapeName;
		void set(int... args) {
			switch(args[0]) {
			case 1:
				shapeName = "rect";
				break;
			case 2: shapeName = "cicle"; break;
			case 3: shapeName = "triangle"; break;
			case 4: shapeName = "star"; break;
			}
		}

		public String toString() {
			return "5 " + shapeName;
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
