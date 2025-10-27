package game;
import graphics.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FrameGen{

    public static Matrix projection;
    public static final double farPlane = 1;
    public static final double closePlane = 0.1;
    public static final double left = -1;
    public static final double right = 1;
    public static final double top = 1;
    public static final double bottom = -1;

    public static void init(){
        projection = new Matrix().inline_ident();

        double fov = 1 / Math.tan(Math.toRadians(45));
        projection.m.get(0)[0] = fov * 1;
        projection.m.get(1)[1] = fov;
        projection.m.get(2)[2] = ((farPlane+closePlane) / (farPlane-closePlane));
        projection.m.get(3)[2] = 1;
        projection.m.get(2)[3] = -((2 * farPlane * closePlane) / (farPlane-closePlane));
        projection.m.get(3)[3] = 0;
    }

    public static void main(String[] args){
        init();
        System.out.println(projection.toString());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("../script"));
            //int x = 0; int z = 20;
            for(int x = -1200; x<1200; x+=50) {
                for (int z = 0; z < 2400; z+=50) {
                    writer.write("box\n" + x + " -200 " + z + " 50 50 50\n");
                }
            }
            writer.write("customMatrix\n");
            for(int i=0; i<16; i++){
                writer.write(projection.m.get(i/4)[i%4] + " ");
            }
            //writer.write("\nrotate\n y 20\nrotate\nx 20");

            writer.write("\napply\nproj2D\ndisplay\nsave\nimg.png");
            writer.close();
            Main.main(new String[]{"../script"});
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    
/*projection.m.get(0)[0] = 1/Math.tan(Math.toRadians(60));
        projection.m.get(1)[1] = 1/Math.tan(Math.toRadians(60));
        projection.m.get(2)[2] = -(farPlane / (farPlane-closePlane));
        projection.m.get(3)[2] = -1;
        projection.m.get(2)[3] = -((farPlane * closePlane) / (farPlane-closePlane));
        projection.m.get(3)[3] = 0;*/

        /*projection.m.get(0)[0] = 2 * closePlane / (right - left);
        projection.m.get(1)[1] = 2 * closePlane / (top - bottom);
        projection.m.get(2)[0] = (right + left) / (right - left);
        projection.m.get(2)[1] = (top + bottom) / (top - bottom);
        projection.m.get(2)[2] = - (farPlane + closePlane) / (farPlane - closePlane);
        projection.m.get(2)[3] = -1;
        projection.m.get(3)[2] = (-2 * farPlane * closePlane) / (farPlane - closePlane);*/

        /*projection.m.get(0)[0] = (2 * closePlane) / (right - left);
        projection.m.get(1)[1] = (2 * closePlane) / (top - bottom);
        projection.m.get(2)[0] = (right + left) / (right - left);
        projection.m.get(2)[1] = (top + bottom) / (top - bottom);
        projection.m.get(2)[2] = -1;
        projection.m.get(2)[3] = -1;
        projection.m.get(3)[2] = -2 * closePlane;*/

    /*projection.m.get(0)[0] = (2 * closePlane) / (right - left);
        projection.m.get(1)[1] = (2 * closePlane) / (top - bottom);
        projection.m.get(2)[0] = (right + left) / (right - left);
        projection.m.get(2)[1] = (top + bottom) / (top - bottom);
        projection.m.get(2)[2] = -1;
        projection.m.get(2)[3] = -1;
        projection.m.get(3)[2] = -2 * closePlane;
        projection.m.get(3)[3] = 0;*/
    /*projection.m.get(0)[0] = (2 * closePlane) / (right - left);
        projection.m.get(1)[1] = (2 * closePlane) / (top - bottom);
        projection.m.get(2)[0] = (right + left) / (right - left);
        projection.m.get(2)[1] = (top + bottom) / (top - bottom);
        projection.m.get(2)[2] = -1;
        projection.m.get(2)[3] = -1;
        projection.m.get(3)[2] = -2 * closePlane;
        projection.m.get(3)[3] = 0;*/
}