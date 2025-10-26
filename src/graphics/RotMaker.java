public class RotMaker {
    public static String makeStr(int n){
        int len = ("" + n).length();
        StringBuilder str = new StringBuilder();
        str.append("0".repeat(3 - len));
        str.append(n);
        return str.toString();
    }
	public static double xPos = 250;
	public static double yPos = 0;
	public static double Pos = xPos;
	public static double oldPos = xPos; 
	public static double dPos = 0;
    public static void main(String[] args) {
        for(int i=1; i<360; i+=1){
			oldPos = Pos;
			Pos = xPos * Math.cos(Math.toRadians(i)) - yPos * Math.sin(Math.toRadians(i));
			dPos = oldPos - Pos;
			//double d = 0;        
			//for(double dx = 0; dx<1; dx+=0.01){
			//	d += a * Math.sin(Math.toRadians(i)) + b * Math.cos(Math.toRadians(i));
			//}
			//d /= 100;

			System.out.printf("\nident\nrotate\ny 1\napply\nident\nmove\n%f 0 0\napply\nsave\nimages/img_" + makeStr(i)+".png", dPos * 2);
			//a = d * Math.cos(Math.toRadians(i)) - b * Math.sin(Math.toRadians(i)); 

        }
        //for(int i=86; i<96; i++){
        //    System.out.printf("\nident\nrotate\ny 1\napply\nident\nmove\n%d 0 0\napply\nsave\nimages/img_" + makeStr(i)+".png", (int)(Math.sin(Math.toRadians(i)) * 10));
        //}
        //for(int i=101; i<171; i++){
        //    System.out.printf("\nident\nrotate\ny -1\napply\nident\nmove\n%d 0 0\napply\nsave\nimages/img_" + makeStr(i)+".png", (int)(Math.sin(Math.toRadians(i+90)) * 3));
        //}
    }
}
