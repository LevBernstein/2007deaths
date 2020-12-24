/**
 * 
 */
package edu.nyu.cs.lkb335;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

/**
 * This programs parses a CSV and allows the user to sort it by certain categories. 
 * Specifically, it makes uses of NYC Open Data
 * and, even more specifically, "New York City Leading Causes of Death," located here 
 * (https://data.cityofnewyork.us/Health/New-York-City-Leading-Causes-of-Death/jb7j-dtam/data).
 * I have massaged the data extensively, cutting out all data except from the year 2007. 
 * In addition, I have removed empty entries to make the total data more meaningful.
 * The columns I am using are causes of death, sex, ethnicity, and number of deaths.
 * 
 * @author Lev Bernstein (lkb335)
 * @version 3.4
 */
public class Homework4 {
	
	/**
	 * Main method. Loads in the csv, manipulates it, makes it into four arrays, combines them into one two-dimensional array with four columns, explains what the user should type, etc. Does everything.
	 * @param args
	 */
	public static void main(String[] args) {
		String search_term = "";
		
		/*String[] fields = splitter();
		System.out.println(fields.length);
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i]);
		}
		*/
		
		//Using the same method I utilized in Homework 3, first I convert the CSV into a single array separated by line.
		String fileName= "edu\\nyu\\cs\\lkb335\\death.csv";
        File sourceFile= new File(fileName);
        String sourceFileString = "";
        try {
        	sourceFileString = new String (Files.readAllBytes(Paths.get(sourceFile.getAbsolutePath())));
        	//I convert sourceFileString to lowercase here to make it easy to manipulate the data.
        	sourceFileString = sourceFileString.toLowerCase();
        	}
        	catch (IOException exception) {
        	    //I originally also printed out the exact I/O error via the exception variable, but decided that users wouldn't get much out of it, and neither would I.
        	    System.out.println("Error! Invalid file! Please specify a valid filepath and file.");
        	    System.out.println("The path you specified was " + sourceFile.getAbsolutePath());
        	    System.exit(0);
        	}
        //I separate by line by splitting on \n.
        String[] splitCSV = sourceFileString.toLowerCase().split("\n", 0);
    	
        /*
        //For debugging:
    	for ( int i = 0; i < splitCSV.length; i++ ) {
    	    System.out.println(i + ": " + splitCSV[i]);
    	}
    	*/
    	
    	//I make arraylists here because I don't know exactly what length the csv will be; this gets around hardcoding in a length.
    	ArrayList<String> ethnicitiesAL = new ArrayList<String>();
    	ArrayList<String> causesAL = new ArrayList<String>();
    	ArrayList<String> sexesAL = new ArrayList<String>();
    	ArrayList<String> deathsAL = new ArrayList<String>();
    	
    	//Next, I add to each arrayList the entry that pertains to them from each row of the csv.
    	for (int i = 0; i < splitCSV.length; i++ ) {
    		String[] tempList = splitCSV[i].split(",");
    		causesAL.add(tempList[0]);
    		sexesAL.add(tempList[1]);
    		ethnicitiesAL.add(tempList[2]);
    		deathsAL.add(tempList[3]);
    	}
    	
    	//then I convert all the arraylists into arrays, so that I can have a true multidimensional array later.
    	String[] ethnicities = new String[ethnicitiesAL.size()];
    	ethnicities = ethnicitiesAL.toArray(ethnicities);
    	String[] causes = new String[causesAL.size()];
    	causes = causesAL.toArray(causes);
    	String[] sexes = new String[sexesAL.size()];
    	sexes = sexesAL.toArray(sexes);
    	String[] deaths = new String[deathsAL.size()];
    	deaths = deathsAL.toArray(deaths);
    	
    	/*
    	//For debugging:
    	for ( int i = 0; i < causes.length; i++ ) {
    	    System.out.println(i + ": " + causes[i]);
    	}
    	for ( int i = 0; i < sexes.length; i++ ) {
    	    System.out.println(i + ": " + sexes[i]);
    	}
    	for ( int i = 0; i < ethnicities.length; i++ ) {
    	    System.out.println(i + ": " + ethnicities[i]);
    	}
    	for ( int i = 0; i < deaths.length; i++ ) {
    	    System.out.println(i + ": " + deaths[i]);
    	}
    	*/
    	
    	//Now, time to make the two-dimensional array. I use causes.length for the length of the array because, presumably, all the columns are the same length, and, even if they're not, cause of death is sure to exist in every row. It's kind of the whole point of the dataset.
    	//It's size [4] in the first dimension because there are four columns.
    	String[][] totalArray = new String[4][causes.length];
    	for (int i = 0; i < causes.length; i++ ) {
    		totalArray[0][i] = causes[i];
    		totalArray[1][i] = sexes[i];
    		totalArray[2][i] = ethnicities[i];
    		totalArray[3][i] = deaths[i];
    		//System.out.println("Row " + i + " " + totalArray[0][i] + " " + totalArray[1][i] + " " + totalArray[2][i] + " " + totalArray[3][i]);
    	}
    	
    	//Time for all the front-end. I explain where I got the data and how the user should use the program.
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Leading Causes of Death for New York City in the year 2007.");
		System.out.println("Cheery, I know. This app mines data from https://data.cityofnewyork.us/Health/New-York-City-Leading-Causes-of-Death/jb7j-dtam/data");
		System.out.println("");
		System.out.println("It will present the cause of death, the ethnicity, and the number of deaths for those two factors.");
		System.out.println("");
		System.out.println("First, would you like to view female or male deaths? Please type in 'female' or 'male.' Sadly, the data works in a gender binary; therefore, so must we.");
		String sex = input.nextLine().toLowerCase();
		//System.out.println(sex);
		//System.out.println(sex.equals("female"));
		//Here I make sure the user is inputting a valid response.
		while (!"female".equals(sex) && !"male".equals(sex)) {
			System.out.println("Sorry, invalid response! Please type in 'female' or 'male'.");
			sex = input.nextLine();
		}
		
		System.out.println("");
		System.out.println("Excellent. Now, would you like to search by ethnicity, cause of death, or just display all results?");
		System.out.println("If you choose the first, the program will print out the number of deaths by cause for that ethnicity.");
		System.out.println("If you choose the second, the program will print out the number of deaths for that cause by ethnicity.");
		System.out.println("If you choose the third, the program will print out all deaths for that sex.");
		System.out.println("Please type in 'ethnicity', 'cause', or 'none'.");
		String search_type = input.nextLine().toLowerCase();
		//Here I make sure the user is inputting a valid response.
		while (!"ethnicity".equals(search_type) && !"cause".equals(search_type) && !"none".equals(search_type)) {
			System.out.println("Sorry, invalid response! Please type in 'ethnicity', 'cause', or 'none'.");
			search_type = input.nextLine();
		}
		
		System.out.println("");
		if ("ethnicity".equals(search_type)) {
			System.out.println("Your options for ethnicity are Hispanic, Asian and Pacific Islander, White, Black, Other, and Unknown.");
			System.out.println("Please type in the full name of any of those ethnicities to see their deaths by cause of death.");
			search_term = input.nextLine().toLowerCase();
			//I find that I commonly type in Asian instead of the lengthy Asian and Pacific Islander, which would normally yeild nothing; this fixes that issue.
			if (search_term.equals("asian")) {
				search_term = "asian and pacific islander";
			}
		}
		if ("cause".equals(search_type)) {
			System.out.println("Your options for cause of death are: Heart Disease, Cancer, HIV, Influenza and Pneumonia, Substance Use Disorders, Diabetes, Accidents Except Drug Poisoning, Cerebrovascular Disease");
			System.out.println("...Assault, Chronic Liver Disease and Cirrhosis, Intentional Self-Harm, Hypertension, Chronic Lower Respiratory Diseases, Septicemia, Nephritis, Alzheimer's, and All Other Causes.");
			System.out.println("Please type in the full name of any of those causes of death to see their deaths by ethnicity.");
			search_term = input.nextLine().toLowerCase();
		}
		
		//numDisplayed tracks the number of lines that has been printed; I am required to cap it at 10. Because most of the ethnicities have 11 entries, I have decided to cap it at 6, so that going to the next page doesn't just display one entry.
		int numDisplayed = 0;
		//beenDisplayed tracks if the user has had to press enter. I use it to check if anything has been displayed at all.
		boolean beenDisplayed = false;
		//this makes it so that the "press enter to see the remaining results" dialog will display again after the first press if more results need to be displayed.
		String resetter = "";
		
		for (int i = 0; i < causes.length; i++) {
			//I check to make sure that the data displayed matches the sex the user chose to search for by checking if totalArray[1][i] matches what the user typed.
			if (totalArray[1][i].equals(sex)) {
				if (search_type.equals("ethnicity")) {
					if (totalArray[2][i].equals(search_term)) {
						numDisplayed += 1;
						beenDisplayed = true;
						if (numDisplayed < 7) {
							//Here's the printf formatting. It justifies left and stretches everything to the point where the second block will start in the same place every time. I got the numbers by determining the maximum possible length for each of those blocks.
							System.out.printf("%-55s", "Row " + i + ": deaths by " + totalArray[0][i] + ",");
							System.out.printf("%-37s", totalArray[1][i] + ", " + totalArray[2][i] + ": ");
							System.out.printf("%-4s %n", totalArray[3][i]);
						}
						//After 6 lines have been displayed, the program asks if the user wants to view more. 
						else {
							System.out.println("Press enter to see the remaining results. Type anything and press enter to exit.");
							resetter = input.nextLine();
							if (resetter.equals("")) {
								System.out.printf("%-55s", "Row " + i + ": deaths by " + totalArray[0][i] + ",");
								System.out.printf("%-37s", totalArray[1][i] + ", " + totalArray[2][i] + ": ");
								System.out.printf("%-4s %n", totalArray[3][i]);
							}
							else {
								//If all the results have been displayed, exit the program.
								System.exit(0);
							}
						}
					}
				}
				else if (search_type.equals("cause")) {
					//This code is the same as the above bit, but with totalArray[2][i] swapped out for totallArray[0][i].
					if (totalArray[0][i].equals(search_term)) {
						numDisplayed += 1;
						beenDisplayed = true;
						if (numDisplayed < 7) {
							System.out.printf("%-55s", "Row " + i + ": deaths by " + totalArray[0][i] + ",");
							System.out.printf("%-37s", totalArray[1][i] + ", " + totalArray[2][i] + ": ");
							System.out.printf("%-4s %n", totalArray[3][i]);
						}
						else {
							System.out.println("Press enter to see the remaining results. Type anything and press enter to exit.");
							resetter = input.nextLine();
							if (resetter.equals("")) {
								numDisplayed = 0;
								System.out.printf("%-55s", "Row " + i + ": deaths by " + totalArray[0][i] + ",");
								System.out.printf("%-37s", totalArray[1][i] + ", " + totalArray[2][i] + ": ");
								System.out.printf("%-4s %n", totalArray[3][i]);
							}
							else {
								System.exit(0);
							}
						}
					}
				}
				else if (search_type.equals("none")) {
					//This code is the same as the other two, but without the testing for a specific column.
					numDisplayed += 1;
					beenDisplayed = true;
					if (numDisplayed < 7) {
						System.out.printf("%-55s", "Row " + i + ": deaths by " + totalArray[0][i] + ",");
						System.out.printf("%-37s", totalArray[1][i] + ", " + totalArray[2][i] + ": ");
						System.out.printf("%-4s %n", totalArray[3][i]);
					}
					else {
						System.out.println("Press enter to see the remaining results. Type anything and press enter to exit.");
						resetter = input.nextLine();
						if (resetter.equals("")) {
							numDisplayed = 0;
							System.out.printf("%-55s", "Row " + i + ": deaths by " + totalArray[0][i] + ",");
							System.out.printf("%-37s", totalArray[1][i] + ", " + totalArray[2][i] + ": ");
							System.out.printf("%-4s %n", totalArray[3][i]);
						}
						else {
							
							System.exit(0);
						}
					}
				}
			}
			
		}
		//If nothing has been displayed:
		if (beenDisplayed == false) {
			System.out.println("Oops, something went wrong! Most likely, you typed in a " + search_type + "that doesn't exist in the data. Please check your spelling.");
			System.out.println("It is also possible that you chose a cause of death that does not have any entries for that sex; for instance, assault and female.");
			System.out.println("Please try running the program again.");
		}
		
		
		
		//Here I close the scanner just so eclipse will shut up about the resource leak that comes if you don't close a scanner. I could just suppress the warning, but it couldn't hurt to close a leak.
		input.close();

	}

}
