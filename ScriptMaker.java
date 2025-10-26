import java.io.*;
import java.util.*;


public class ScriptMaker {
	public static void main(String[] args){
		try{
			Scanner sc = new Scanner(new File(args[0]));
			ArrayList<Integer> vbo = new ArrayList<>();
			ArrayList<Integer> ebo = new ArrayList<>();
			String strline; String[] line; String[] subline; int[] temparr = new int[3];
			while(sc.hasNextLine()){
				strline = sc.nextLine();
				strline.strip();
				line = strline.split(" "); //probably not very efficient but i don't need efficiency here
				if(line[0].equals("v")){
					//System.out.println(Arrays.toString(line));
					for(int i = 1; i < line.length; i++){
						try{
							vbo.add((int) (Double.parseDouble(line[i])/200));
						}catch(NumberFormatException ignored){
							break;
						}
					}
				}else if(line[0].equals("f")){

					for(int i = 1; i < line.length; i++){
						//System.out.println(Arrays.toString(line));
						subline = line[i].split("/");
						//System.out.println(Arrays.toString(subline));

						temparr[i-1] = Integer.parseInt(subline[0])-1;
						//System.out.println("It did not crash");
					}
					ebo.add(temparr[0]); ebo.add(temparr[1]);
					ebo.add(temparr[1]); ebo.add(temparr[2]);
					ebo.add(temparr[2]); ebo.add(temparr[0]);
				}
			}
			//System.out.println(vbo); System.out.println(ebo);
			for(int i=0; i<ebo.size()-1; i+=2){
				//System.out.println(ebo.get(i) * 3 + " : " + vbo.get(ebo.get(i)*3) + ", " + (vbo.get(ebo.get(i)*3+1)) + ", " + vbo.get(ebo.get(i)*3+2));
				System.out.println("line");
				System.out.print(
						"" + vbo.get((ebo.get(i) * 3)) + " "
								+ vbo.get((ebo.get(i) * 3 + 1)) + " "
								+ vbo.get((ebo.get(i)) * 3 + 2)
				);
				System.out.println(" " + vbo.get((ebo.get(i+1)) * 3) + " "
						+ vbo.get((ebo.get(i+1) * 3 + 1)) + " "
						+ vbo.get((ebo.get(i+1) * 3 + 2))
				);
				//System.out.println("display\nsave\nimages/img_"+i+".png");
			}

		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
