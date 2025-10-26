.PHONY: main clean compile myimage

main: Main.class
	@java Main script
myimage: Main.class
	@java Main myimage
compile: Main.class

Main.class: Main.java Matrix.class Screen.class Parser.class EdgeMatrix.class Commands.class
	@javac Main.java
Matrix.class: Matrix.java
	@javac Matrix.java
Screen.class: Screen.java
	@javac Screen.java
Parser.class: Parser.java
	@javac Parser.java
EdgeMatrix.class: EdgeMatrix.java
	@javac EdgeMatrix.java
Commands.class: Commands.java
	@javac Commands.java
ScriptMaker.class: ScriptMaker.java
	@javac ScriptMaker.java
script: ScriptMaker.class
	@java ScriptMaker model.obj >>out.txt
rot: RotMaker.class
	@java RotMaker >>out.txt
RotMaker.class: RotMaker.java
	@javac RotMaker.java
clean: 
	@rm *.class
