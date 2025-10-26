.PHONY: clean compile 

SGRAPH=src/graphics
BUILD=build

compile: $(BUILD)/Main.class

$(BUILD)/Main.class: $(SGRAPH)/Main.java $(BUILD)/Matrix.class $(BUILD)/Screen.class $(BUILD)/Parser.class $(BUILD)/EdgeMatrix.class $(BUILD)/Commands.class
	@javac -cp $(BUILD)/ $(SGRAPH)/Main.java -d $(BUILD)/
$(BUILD)/Screen.class: $(SGRAPH)/Screen.java
	@javac -cp $(BUILD)/ $(SGRAPH)/Screen.java -d $(BUILD)/ 
$(BUILD)/Parser.class: $(SGRAPH)/Parser.java $(BUILD)/Commands.class
	@javac -cp $(BUILD)/ $(SGRAPH)/Parser.java -d $(BUILD)/
$(BUILD)/Commands.class: $(SGRAPH)/Commands.java $(BUILD)/EdgeMatrix.class
	@javac -cp $(BUILD)/ $(SGRAPH)/Commands.java -d $(BUILD)/
$(BUILD)/EdgeMatrix.class: $(SGRAPH)/EdgeMatrix.java $(BUILD)/Matrix.class
	@javac -cp $(BUILD)/ $(SGRAPH)/EdgeMatrix.java -d $(BUILD)/
$(BUILD)/Matrix.class: $(SGRAPH)/Matrix.java
	@javac -cp $(BUILD)/ $(SGRAPH)/Matrix.java -d $(BUILD)/
clean: 
	@rm $(BUILD)/*
