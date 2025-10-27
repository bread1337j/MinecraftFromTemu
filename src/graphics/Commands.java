package graphics;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.*;

public class Commands {
	
	private static EdgeMatrix eMatrix;
	private static Matrix matrix;
    private static Screen s;
    private static Color clr;
	
	private static Matrix herm;
	


	public static double precision = 0.0025;
	public static final double TWOPI = Math.PI * 2; 
	public static final double twopiprec = TWOPI * precision / 10; //it's bleak


	public static void init(EdgeMatrix edges, Matrix transform, Screen scr, Color clor){
		eMatrix = edges; matrix = transform; s = scr; clr = clor; herm = new Matrix();
		herm.addColumn(2, -3, 0, 1); 
		herm.addColumn(-2, 3, 0, 0);
		herm.addColumn(1, -2, 1, 0);
		herm.addColumn(1, -1, 0, 0);
	}


	public static void line(String str){
		String[] args = str.split(" ");
		eMatrix.addEdge(
			Double.parseDouble(args[0]),
			Double.parseDouble(args[1]),
			Double.parseDouble(args[2]),
			Double.parseDouble(args[3]),
			Double.parseDouble(args[4]),
			Double.parseDouble(args[5])
		);
	}

	public static void Hline(String str){
		String[] args = str.split(" ");
		double x0 = Double.parseDouble(args[0]);
		double y0 = Double.parseDouble(args[1]);
		double z0 = Double.parseDouble(args[2]);
		double x1 = Double.parseDouble(args[3]);
		double y1 = Double.parseDouble(args[4]);
		double z1 = Double.parseDouble(args[5]);
		double h = Double.parseDouble(args[6]);
		for(; h>0; h-=1){
			eMatrix.addEdge(
					x0 + h, 
					y0, 
					z0,
					x1 + h,
					y1,
					z1
					);
		}
	}
	public static void Vline(String str){
		String[] args = str.split(" ");
		double x0 = Double.parseDouble(args[0]);
		double y0 = Double.parseDouble(args[1]);
		double z0 = Double.parseDouble(args[2]);
		double x1 = Double.parseDouble(args[3]);
		double y1 = Double.parseDouble(args[4]);
		double z1 = Double.parseDouble(args[5]);
		double h = Double.parseDouble(args[6]);
		for(; h>0; h-=1){
			eMatrix.addEdge(
					x0, 
					y0 + h, 
					z0,
					x1,
					y1 + h,
					z1
					);
		}
	}


	public static void box(String str){
		String[] args = str.split(" ");
		double px = Double.parseDouble(args[0]);
		double py = Double.parseDouble(args[1]);
		double pz = Double.parseDouble(args[2]);
		double sx = Double.parseDouble(args[3]);
		double sy = Double.parseDouble(args[4]);
		double sz = Double.parseDouble(args[5]);
		eMatrix.addEdge(px, py, pz, px+sx, py, pz);
		eMatrix.addEdge(px, py, pz, px, py-sy, pz);
		eMatrix.addEdge(px, py, pz, px, py, pz+sz);
		eMatrix.addEdge(px+sx, py, pz, px+sx, py-sy, pz);
		eMatrix.addEdge(px+sx, py, pz, px+sx, py, pz+sz);
		eMatrix.addEdge(px+sx, py, pz+sz, px+sx, py-sy, pz+sz);
		eMatrix.addEdge(px+sx, py-sy, pz, px+sx, py-sy, pz+sz);
		eMatrix.addEdge(px, py-sy, pz, px, py-sy, pz+sz);
		eMatrix.addEdge(px, py-sy, pz, px+sx, py-sy, pz);
		eMatrix.addEdge(px, py, pz+sz, px, py-sy, pz+sz);
		eMatrix.addEdge(px, py, pz+sz, px+sx, py, pz+sz);
		eMatrix.addEdge(px, py-sy, pz+sz, px+sx, py-sy, pz+sz);
	}


	public static void sphere(String str){
		String args[] = str.split(" ");
		double x = Double.parseDouble(args[0]); double y = Double.parseDouble(args[1]); double z = Double.parseDouble(args[2]);
		double r = Double.parseDouble(args[3]); 
		java.util.List<double[]> points = new LinkedList<>(); //trust the process
		for(double t = 0; t < TWOPI; t += twopiprec * 100){
			for(double p = 0; p < Math.PI; p += twopiprec * 100){
				points.add(new double[]{
					r*Math.cos(t) + x,
					r*Math.sin(t)*Math.cos(p) + y,
					r*Math.sin(t)*Math.sin(p) + z,
				});
			}
		}

		for(double[] arr : points){
			eMatrix.addEdge(arr[0], arr[1], arr[2], arr[0]+1, arr[1]+1, arr[2]+1);
		}
	}

	public static void torus(String str){
		String args[] = str.split(" ");
		double x = Double.parseDouble(args[0]); double y = Double.parseDouble(args[1]); double z = Double.parseDouble(args[2]);
		double r = Double.parseDouble(args[3]); double bigR = Double.parseDouble(args[4]);
		java.util.List<double[]> points = new LinkedList<>(); //trust the process
		for(double p = 0; p < TWOPI; p += twopiprec * 100){
			for(double t = 0; t < TWOPI; t += twopiprec * 100){
				points.add(new double[]{
					Math.cos(p)*(r*Math.cos(t)+bigR)+x,
					r*Math.sin(t) + y,
					-1 * Math.sin(p) * (r * Math.cos(t) + bigR) + z,
				});
			}
		}

		for(double[] arr : points){
			eMatrix.addEdge(arr[0], arr[1], arr[2], arr[0]+1, arr[1]+1, arr[2]+1);
		}
	}

	public static void scale(String str){
		String[] args = str.split(" ");
	//	new Matrix(Matrix.SCALE, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]))
	//		.mult(matrix);
        matrix.mult(
                new Matrix(Matrix.SCALE, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]))
        );
    }

    public static void translate(String str){
        String[] args = str.split(" ");
        matrix.mult(new Matrix(Matrix.TRANSLATE, Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
    }

    public static void rotate(String str){
        String[] args = str.split(" ");
        matrix.mult(new Matrix(Matrix.ROTATE, Double.parseDouble(args[1]), args[0].charAt(0)));
    }

	public static void customMatrix(String str){
		//00 01 02 03
		//10 11 12 13 etc
		String[] args = str.split(" ");
		Matrix m = new Matrix();
		m.ident();
		for(int i=0; i<16; i++) {
			m.m.get(i/4)[i%4] = Double.parseDouble(args[i]);
		}
		matrix.mult(m);

	}

    public static void apply(String str){ //they have to take in an argument ok they're all Consumer<String> objects
        //System.out.println(matrix);
        matrix.reverseMult(eMatrix);
        //System.out.println(eMatrix);
        //System.out.println(Arrays.toString(eMatrix.m.get(0)));
    }

    public static void display(String str){
		System.out.println("Display got called");
        s.clearScreen();
        eMatrix.drawEdges(s, clr);
        s.display();
		System.out.println("Display finished its thing");
    }


	public static void drawClr(String str){
		String args[] = str.split(" ");
		Color clr = new Color(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		eMatrix.drawEdges(s, clr);
		System.out.println("Draw clr finished its thing");
	}
	
	public static void justSave(String str){
		s.saveExtension(str);
	}
	public static void justDisplay(String str){
		s.display();
	}

	public static void clrEdges(String str){
		eMatrix = new EdgeMatrix();
	}

	public static void clear(String str){
		s.clearScreen(); 
		eMatrix = new EdgeMatrix();
	}

    public static void save(String str){
        s.clearScreen();
        eMatrix.drawEdges(s, clr);
        s.saveExtension(str);
    }

    public static void ident(String str){
        matrix.ident();
    }

	public static void proj2D(String str){
		double arr[];
        EdgeMatrix nm = new EdgeMatrix();
		for(int i=0; i<eMatrix.m.size(); i++){
			System.out.print(i);
			arr = eMatrix.m.get(i);
			System.out.println(": " + Arrays.toString(arr));

			//arr[0] = ((arr[0]) / arr[3]) + 250;
			//System.out.print("->(" + arr[0] + ", ");
			//arr[1] = ((arr[1]) / arr[3]) + 250;
			//System.out.println(arr[1] + ") ");
            if(arr[3] < 0) {
                if (arr[2] > 0.1 || arr[2] < -0.1) {
                    arr[0] = arr[0] * 250 / arr[2] + 250;
                    arr[1] = arr[1] * 250 / arr[2] + 250;
                }
                nm.addColumn(arr[0], arr[1], arr[2], arr[3]);
            }
		}
		/*System.out.println("Post-projection matrix: ");
		for(int i=0; i<eMatrix.m.size(); i++) {
			System.out.print(i);
			arr = eMatrix.m.get(i);
			System.out.println(": " + Arrays.toString(arr));
		}*/
        eMatrix = nm;
        if(eMatrix.m.size()%2==1){
            eMatrix.m.removeLast();
        }
	}

	public static void bezier(String str){
		String[] args = str.split(" ");
		int x0 = Integer.parseInt(args[0]); int y0 = Integer.parseInt(args[1]);
		int x1 = Integer.parseInt(args[2]); int y1 = Integer.parseInt(args[3]);
		int x2 = Integer.parseInt(args[4]); int y2 = Integer.parseInt(args[5]);
		int x3 = Integer.parseInt(args[6]); int y3 = Integer.parseInt(args[7]);
		
		double ax = -1 * x0 + 3 * x1 - 3 * x2 + x3;
		double ay = -1 * y0 + 3 * y1 - 3 * y2 + y3;

		double bx = 3 * x0 - 6 * x1 + 3 * x2;
		double by = 3 * y0 - 6 * y1 + 3 * y2;

		double cx = -3 * x0 + 3 * x1; 
		double cy = -3 * y0 + 3 * y1;

		double dx = x0;
		double dy = y0;

		for(double t = 0; t <= 1-precision; t += precision){
			eMatrix.addEdge(
				ax*Math.pow(t, 3)+bx*Math.pow(t, 2)+cx*t+dx, ay*Math.pow(t, 3)+by*Math.pow(t, 2)+cy*t+dy, 0,
				ax*Math.pow(t+precision, 3)+bx*Math.pow(t+precision, 2)+cx*t+dx, ay*Math.pow(t+precision, 3)+by*Math.pow(t+precision, 2)+cy*(t+precision)+dy, 0
			);
		}
	}

	public static void Hbezier(String str){
		String[] args = str.split(" ");
		int x0 = Integer.parseInt(args[0]); int y0 = Integer.parseInt(args[1]);
		int x1 = Integer.parseInt(args[2]); int y1 = Integer.parseInt(args[3]);
		int x2 = Integer.parseInt(args[4]); int y2 = Integer.parseInt(args[5]);
		int x3 = Integer.parseInt(args[6]); int y3 = Integer.parseInt(args[7]);
		int xlen = Integer.parseInt(args[8]);

		double ax = -1 * x0 + 3 * x1 - 3 * x2 + x3;
		double ay = -1 * y0 + 3 * y1 - 3 * y2 + y3;

		double bx = 3 * x0 - 6 * x1 + 3 * x2;
		double by = 3 * y0 - 6 * y1 + 3 * y2;

		double cx = -3 * x0 + 3 * x1; 
		double cy = -3 * y0 + 3 * y1;

		double dx = x0;
		double dy = y0;
		for(; dx < x0 + xlen; dx++){
			for(double t = 0; t <= 1-precision; t += precision){
				eMatrix.addEdge(
					ax*Math.pow(t, 3)+bx*Math.pow(t, 2)+cx*t+dx, ay*Math.pow(t, 3)+by*Math.pow(t, 2)+cy*t+dy, 0,
					ax*Math.pow(t+precision, 3)+bx*Math.pow(t+precision, 2)+cx*t+dx, ay*Math.pow(t+precision, 3)+by*Math.pow(t+precision, 2)+cy*(t+precision)+dy, 0
				);
			}
		}
	}

	public static void circle(String str){
		String args[] = str.split(" "); //this must be how the first caveman to discover fire felt
		int x = Integer.parseInt(args[0]); int y = Integer.parseInt(args[1]); int z = Integer.parseInt(args[2]);
		double r = Integer.parseInt(args[3]);

		for(double t = 0; t <= TWOPI-twopiprec; t += twopiprec){
			eMatrix.addEdge(
			x + r * Math.cos(t), y + r * Math.sin(t), 0,
			x + r * Math.cos(t + twopiprec), y + r * Math.sin(t + twopiprec), 0
					);
		}
		

	}

	public static void fillCircle(String str){
		String args[] = str.split(" "); //this must be how the first caveman to discover fire felt
		int x = Integer.parseInt(args[0]); int y = Integer.parseInt(args[1]); int z = Integer.parseInt(args[2]);
		double r = Integer.parseInt(args[3]);
		for(; r > 0; r-=1){ //idk man


			for(double t = 0; t <= TWOPI-twopiprec; t += twopiprec){
				eMatrix.addEdge(
				x + r * Math.cos(t), y + r * Math.sin(t), 0,
				x + r * Math.cos(t + twopiprec), y + r * Math.sin(t + twopiprec), 0
						);
			}
		}
	}

	public static void hermite(String str){
		String args[] = str.split(" ");
		int px0 = Integer.parseInt(args[0]); int py0 = Integer.parseInt(args[1]);
		int px1 = Integer.parseInt(args[2]); int py1 = Integer.parseInt(args[3]);
		int rx0 = Integer.parseInt(args[4]); int ry0 = Integer.parseInt(args[5]);
		int rx1 = Integer.parseInt(args[6]); int ry1 = Integer.parseInt(args[7]);
		
		Matrix mx = new Matrix(); 
		mx.addColumn(px0, px1, rx0, rx1);
		Matrix my = new Matrix();
		my.addColumn(py0, py1, ry0, ry1);

		mx.mult(herm);
		my.mult(herm);
		
		double[] x = mx.getCol(0);
		double[] y = my.getCol(0);

		for(double t = 0; t <= 1-precision; t += precision){
			eMatrix.addEdge(
			x[0]*Math.pow(t, 3)+x[1]*Math.pow(t, 2)+x[2]*t+x[3], y[0]*Math.pow(t, 3)+y[1]*Math.pow(t,2)+y[2]*t+y[3], 0,
			x[0]*Math.pow(t+precision, 3)+x[1]*Math.pow(t+precision, 2)+x[2]*(t+precision)+x[3], y[0]*Math.pow(t+precision, 3)+y[1]*Math.pow(t+precision,2)+y[2]*(t+precision)+y[3], 0
					);
		}

	}

}

