package graphics;

import java.util.*;
import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Parser {
	//really not a fan of throwing this in main
	
	private Scanner sc;
	private Matrix matrix;
	private EdgeMatrix eMatrix; 
	private static Map<String, Consumer<String>> map = new HashMap<>();

    public static void init(){
        map.put("line", Commands::line);
        map.put("scale", Commands::scale);
        map.put("translate", Commands::translate);
        map.put("rotate", Commands::rotate);
        map.put("apply", Commands::apply);
        map.put("display", Commands::display);
        map.put("save", Commands::save);
        map.put("ident", Commands::ident);
        map.put("move", Commands::translate); 
		map.put("bezier", Commands::bezier);
		map.put("circle", Commands::circle);
		map.put("hermite", Commands::hermite);
		map.put("drawClr", Commands::drawClr);
		map.put("justSave", Commands::justSave);
		map.put("clrEdges", Commands::clrEdges);
		map.put("Hbezier", Commands::Hbezier);
		map.put("fillCircle", Commands::fillCircle);
		map.put("justDisplay", Commands::justDisplay);
		map.put("Hline", Commands::Hline);
		map.put("Vline", Commands::Vline);
		map.put("clear", Commands::clear);
		map.put("box", Commands::box);
		map.put("sphere", Commands::sphere);
		map.put("torus", Commands::torus);
        map.put("customMatrix", Commands::customMatrix);
        map.put("proj2D", Commands::proj2D);
	}


	public Parser(Scanner sc, EdgeMatrix edges, Matrix transform){
		this.sc = sc; this.eMatrix = edges; this.matrix = transform;


	}

    public void run(){
        Consumer<String> cmd = null;
        while(sc.hasNextLine()){
            String line = sc.nextLine().strip();
            System.out.println(line);
            if(line.isEmpty() || line.charAt(0) == '#') continue;
            if(map.containsKey(line)){
                if(cmd != null){
                    //0 args command was last line
                    //System.out.println("No arg cmd, it is before the call to " + line);
                    cmd.accept(" ");
                }
                cmd = map.get(line);
            }else {
                if(line.equals("quit")){
                    return;
                }else{
                    assert cmd != null;
					//System.out.println(cmd + " " + line);
                    cmd.accept(line);
                    cmd = null;
                }
            }
        }
    }





}
