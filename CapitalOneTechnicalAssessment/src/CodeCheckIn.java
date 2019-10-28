import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CodeCheckIn {
	
	public static void getInfo(String filename) throws FileNotFoundException {
		
		// Here I am only reading files that have an extension and does not start with a "."
		if (filename.contains(".") && !filename.startsWith(".")) {
			
			// Initialize all counters that will be adjusted throughout the program
			int numLines = 0;
			int numComs = 0;
			int singleComs = 0;
			int blocksLines = 0;
			int blocks = 0;
			int toDo = 0;
			
			// Scan the file and organize it into a HashMap to be able to be iterated over for simple indexing
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			String input;
			HashMap<Integer, String> lines = new HashMap<Integer, String>();
			
			while (scanner.hasNextLine()) {
				input = scanner.nextLine();
				numLines += 1;
				lines.put(numLines, input);
			}
			
			// Loop over the HashMap containing the lines to adjust the counters due to the conditions 
			for (int s = 1; s < lines.size() + 1; s++) {
						
				// FOR CODE USING JAVA COMMENT STRUCTURE
				// Case 1: Total number of comments and single comments
				if (lines.get(s).contains("//")) {
					numComs += 1;
					singleComs += 1;
				}
				
				// Case 2: Total number of comment lines within block comments
				if (lines.get(s).contains("/*") || lines.get(s).contains("*") || lines.get(s).contains("*/")) {
					blocksLines += 1;
					numComs += 1;
				}
				
				// Case 3: Total Number of Block Line Comments
				if (lines.get(s).contains("/*")) {
					blocks += 1;
				}
			
				
				// Case 4: Total Number of TODOs
				if (lines.get(s).contains("TODO")) {
					toDo += 1;
				}
				
				
				// FOR CODE USING THE PYTHON COMMENT STRUCTURE
				
				// Case 1: Incrementing the number of comments
				if (lines.get(s).contains("#")) {
					numComs += 1;
				}
				
				
				if (s==1) {
					if (lines.get(s).contains("#")) {
						// Case 1: if the first line has a comment and the second line does not have a comment then increment single comment
						if (!lines.get(s+1).contains("#")) {
							singleComs += 1;
						}
						else {
							// Case 2: if the first line has a comment and the second line has a comment then increment comments within blocks
							blocksLines += 1;
							
						}
					}
				}
				else if (s == lines.size()) {
					if (lines.get(s).contains("#") && lines.get(s-1).contains("#")) {
						// Case 2 & Case 3: If the last line in the file is a comment and the second last line also contains a comment then increment the number of blocks and comments within blocks
						blocks += 1;
						blocksLines += 1;
					}
					if (lines.get(s).contains("#") && !lines.get(s-1).contains("#")) {
						// Case 1: If the last line contains a comment but the second last line does not contain a comment then there is an increment in the number of single comments
						singleComs += 1;
					}
					
				}
					
				else {
					if (lines.get(s).contains("#")) {
						// Case 1: If the line is between the first and last of the file, contains a comment, but the previous and next DO NOT contain a comment then there is an increment in single comments
						if (!lines.get(s-1).contains("#") && !lines.get(s+1).contains(("#"))) {
							singleComs += 1;	
						}
						// Case 2: If the line contains a comment and the previous or the next line contains a comment this increments the number of comments within blocks
						if (lines.get(s-1).contains("#") || lines.get(s+1).contains(("#"))) {
							blocksLines += 1;
						}
						// Case 3: If the line contains a comment and the previous contains a comment but the next does not contain a comment then there is an increment in the number of blocks
						if (lines.get(s-1).contains("#") && !lines.get(s+1).contains("#")) {
							blocks += 1;
						}
					}
				}
			}
			
			// Print out the final sums for each counter
			System.out.printf("Total # of lines: " + numLines + "\n" + "Total # of comment lines: " +
					numComs + "\n" + "Total # of single line comments: " + singleComs + "\n" +
					"Total # of comment lines within block comments: " + blocksLines + "\n" +
					"Total # of block line comments: " + blocks + "\n" + "Total # of TODO's: " +
					toDo + "\n");
		}
	}
		
	// The static method can be used when any filename is inputed into the method which will display the results in the console 
		public static void main(String[] args) throws FileNotFoundException {
			
			 //getInfo("FILE NAME HERE");
		}
}