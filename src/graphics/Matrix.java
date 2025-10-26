/*==========================================
  A matrix will be an arraylist of size 4 double arrays.
  Each array  will represent an [x, y, z, 1] point.
  For multiplication purposes, consider the rows like so:
  x0  x1      xn
  y0  y1      yn
  z0  z1  ... zn
  1  1        1
  ==========================================*/
import java.util.*;

public class Matrix {

	public static int POINT_SIZE = 4;
	public static int TRANSLATE = 0;
	public static int SCALE = 1;
	public static int ROTATE = 2;

	protected ArrayList<double []>m;

	Matrix() {
		m = new ArrayList<double []>();
	}//constructor


	/*========= Transform constructors =================
	  These two constructors will create the appropriate
	  transformation matrix based on the transformation
	  type constants defined above.
	  You may leave theses alone and focus on the various
	  "make" methods below.
	  ================================================*/
	Matrix(int transformType, double x, double y, double z) {
		ident();
		if (transformType == TRANSLATE)
			makeTranslate(x, y, z);
		else if (transformType == SCALE)
			makeScale(x, y, z);
	}
	Matrix(int transformType, double theta, char axis) {
		ident();
		//System.out.println(transformType + " " + theta + " " + axis);

		if (transformType == ROTATE) {
			if (axis == 'x')
				makeRotX(theta);
			else if (axis == 'y')
				makeRotY(theta);
			else if (axis == 'z')
				makeRotZ(theta);
		}
	}

	/*======== void makeTranslate() ==========
Inputs:  double x, double y, double z
Returns:
Modifies the calling object to become
a translation matrix using x, y, and z as the
translation offsets.
====================*/
	private void makeTranslate(double x, double y, double z){
		this.m.get(3)[0] = x; this.m.get(3)[1] = y; this.m.get(3)[2] = z;
	}//makeTranslate

	/*======== void makeScale() ==========
Inputs:  double x, double y, double z
Returns:
Modifies the calling object to become
a scale matrix using x, y, and z as the
scale factors.
====================*/
	private void makeScale(double x, double y, double z) {
		this.m.get(0)[0] = x; this.m.get(1)[1] = y; this.m.get(2)[2] = z;
	}//makeScale
	//

	/*======== void  makeRotX() ==========
Inputs:  double theta
Returns:
Modifies the calling object to become
a rotation matrix using theta as the angle
and x as the axis of rotation.
====================*/
	private static final double rad = 180 / Math.PI;
	private void makeRotX(double theta) {
		this.m.get(1)[1] = Math.cos(theta / rad);
		this.m.get(1)[2] = Math.sin(theta / rad);
		this.m.get(2)[1] = -1 * Math.sin(theta / rad);
		this.m.get(2)[2] = Math.cos(theta / rad);
	}//makeRotX

	/*======== void  makeRotY() ==========
Inputs:  double theta
Returns:
Modifies the calling object to become
a rotation matrix using theta as the angle
and y as the axis of rotation.
====================*/
	private void makeRotY(double theta) {
		this.m.get(0)[0] = Math.cos(theta / rad);
		this.m.get(2)[0] = Math.sin(theta / rad);
		this.m.get(0)[2] = -1 * Math.sin(theta / rad);
		this.m.get(2)[2] = Math.cos(theta / rad);
	}//makeRotY

	/*======== void  makeRotZ() ==========
Inputs:  double theta
Returns:
Modifies the calling object to become
a rotation matrix using theta as the angle
and z as the axis of rotation.
====================*/
	private void makeRotZ(double theta) {
		this.m.get(0)[0] = Math.cos(theta / rad);
		this.m.get(0)[1] = Math.sin(theta / rad);
		this.m.get(1)[0] = -1 * Math.sin(theta / rad);
		this.m.get(1)[1] = Math.cos(theta / rad);
	}//makeRotZ



	public void addColumn(double x, double y, double z) {
		double[] col = {x, y, z, 1};
		m.add(col);
	}//addColumn

	public void addColumn(double x, double y, double z, double w){
		double[] col = {x, y, z, w};
		m.add(col);
	}
	public double[] getCol(int p){
		return m.get(p);
	}

	public void ident() {
		m = new ArrayList<double []>();
		for (int i=0; i<POINT_SIZE; i++) {
			double[] point = new double[POINT_SIZE];
			point[i] = 1;
			m.add(point);
		}
	}//ident



	public void mult(Matrix a) {
		double[] tmp = new double[POINT_SIZE];
		for (int c=0; c<m.size(); c++) {
			double[] point = m.get(c);
			//copy values from m over
			for (int r=0; r < point.length; r++)
				tmp[r] = point[r];

			for (int r=0; r < point.length; r++) {
				m.get(c)[r] = a.m.get(0)[r] * tmp[0] +
					a.m.get(1)[r] * tmp[1] +
					a.m.get(2)[r] * tmp[2] +
					a.m.get(3)[r] * tmp[3];
			}
		}
	}//mult
	public void reverseMult(Matrix a){
		a.mult(this);
	}

	public void clear() {
		m = new ArrayList<double[] >();
	}//clear

	public String toString() {

		String s = "";
		if (m.size() == 0) {
			return s;
		}

		for (int i=0; i<POINT_SIZE; i++) {
			for (double[] p : m) {
				s+= p[i] + " ";
			}
			s+= "\n";
		}
		return s;
	}
}//Matrix
