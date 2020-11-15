package com.ptaushanov;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Swapper {
    // ArrayList storing all lines of text from text file
    private ArrayList<String> textContent;
    private final Scanner conReader;
    // Path to the text file where swapping is done.
    private String path;

    public Swapper() {
        // When a new Swapper is instantiated, a "Console Reader" is created
        this.conReader = new Scanner(System.in);
    }

    // Sets a valid path to a file and reads all lines
    // storing them to the ArrayList textContent of the Swapper instance.
    public void importFileData() {
        boolean isValidPath = false;
        do {
            // Asking the user for a file path and reading it
            System.out.print("Path to file (.txt): ");
            path = this.conReader.nextLine();

            // Checking if it is a txt file
            if (!path.matches(".*\\.txt")) {
                System.out.println("Not a (.txt) file! Please enter path for a (.txt) file.");
                continue;
            }

            try {
                // Trying to create a BufferReader
                BufferedReader textReader = new BufferedReader(new FileReader(path));
                // Instantiating a new ArrayList meanwhile removing old
                // data from textContent for code reuse
                this.textContent = new ArrayList<String>();
                isValidPath = true;

                String line;
                // Read all lines from the file till the end of the file
                while ((line = textReader.readLine()) != null) {
                    this.textContent.add(line);
                }

                textReader.close();
            } catch (FileNotFoundException e) {
                // In case the path passed to the FileReader is invalid
                System.out.println("Invalid path! Please enter correct file path.");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } while (!isValidPath);
    }

    // Displays the options menu
    public void displayMenu() {
        System.out.println("(1) Swap one line with another");
        System.out.println("(2) Swap one word from a line with a word from another line");
        System.out.print("Choose option 1 or 2 to proceed: ");
    }

    // Gets a valid choice (1 or 2) from the user
    private int getUserChoice() {
        while (true) {
            if (this.conReader.hasNextInt()) {
                int choice = this.conReader.nextInt();
                // Forcing the user to choose a correct choice
                if (choice == 1 || choice == 2) return choice;
                else {
                    // If the user's input is wrong then make them choose again
                    System.out.println("Wrong choice! Please try again!");
                    this.conReader.nextLine();
                }
            } else {
                // If the user's input was not an int
                System.out.println("Your choice must be a number! (1 or 2)");
                this.conReader.nextLine();
            }
        }
    }

    // Gets the 2 line indexes, finds the correct lines and swaps them
    private void swapLines() {
        int line1Idx, line2Idx;

        // Getting the values for the 2 line indexes
        while (true) {
            System.out.print("Line indexes <first_line_index> <second_line_index>: ");
            try {
                line1Idx = this.conReader.nextInt();
                line2Idx = this.conReader.nextInt();
                break;
            } catch (InputMismatchException e) {
                // Make the user choose again if the indexes are not int values
                System.out.println("Line indexes must be whole numbers!");
                this.conReader.nextLine();
            }
        }

        // Don't swap anything if the lines are the same.
        if (line1Idx == line2Idx) return;

        try {
            // Trying to swap the lines
            String temp = this.textContent.get(line1Idx);
            this.textContent.set(line1Idx, this.textContent.get(line2Idx));
            this.textContent.set(line2Idx, temp);
        } catch (IndexOutOfBoundsException e) {
            // When indexes are not in the range just start over and ask
            // the user for line indexes again
            System.out.println("Swap failed! One or more indexes are not valid!");
            System.out.println("Please try again!");
            this.swapLines();
        }
    }

    // Gets the 2 line and 2 word indexes, finds the correct words and swaps them
    private void swapWords() {

        int line1Idx, line2Idx, word1Idx, word2Idx;

        // Getting the values for the word and line indexes
        while (true) {
            System.out.print("Line and word indexes <first_line_index> <first_line_word_index> <second_line_index> <second_line_word_index>: ");
            try {
                line1Idx = this.conReader.nextInt();
                word1Idx = this.conReader.nextInt();
                line2Idx = this.conReader.nextInt();
                word2Idx = this.conReader.nextInt();
                break;
            } catch (InputMismatchException e) {
                // Make the user choose again if the indexes are not int values
                System.out.println("Line and word indexes must be whole numbers!");
                this.conReader.nextLine();
            }
        }

        // Don't swap anything if the line and word indexes are the same
        if (line1Idx == line2Idx && word1Idx == word2Idx) return;

        try {
            // Trying to get the lines  to be prepared for the swap
            String line1 = this.textContent.get(line1Idx);
            String line2 = this.textContent.get(line2Idx);

            // Splitting the lines into individual words
            // The split is done by a delimiter matching all whitespaces
            String[] wordsFLine = line1.split("\\s+");
            String[] wordsSLine = line2.split("\\s+");

            // Saving the first line word to a temporary variable
            String temp = wordsFLine[word1Idx];
            // Swapping the words
            wordsFLine[word1Idx] = wordsSLine[word2Idx];
            wordsSLine[word2Idx] = temp;

            // Joining back the words into strings and updating the textContent's lines
            this.textContent.set(line1Idx, String.join(" ", wordsFLine));
            this.textContent.set(line2Idx, String.join(" ", wordsSLine));
        } catch (IndexOutOfBoundsException e) {
            // When indexes are not in the range just start over and ask
            // the user for line and word indexes again
            System.out.println("Swap failed! One or more indexes are not valid!");
            System.out.println("Please try again!");
            swapWords();
        }
    }

    // Gets a choice from the user on what to swap and does the swap
    public void startSwapping() {
        // Checks if the textContent is empty, because the importFileData method was not called
        if (this.textContent == null) {
            System.out.println("Can't swap! Import file data first!");
            return;
        }

        int choice = this.getUserChoice();
        switch (choice) {
            case 1 -> this.swapLines();
            case 2 -> this.swapWords();
        }
    }

    // Displays the textContent
    public void displayData() {
        // Checks if the textContent is empty, because the importFileData method was not called
        if (this.textContent == null) {
            System.out.println("No data to display! Import file data first!");
            return;
        }

        // Printing text content
        for (String line : this.textContent) {
            System.out.println(line);
        }
    }

    // Created a BufferWriter that save to the same file the content form textContent
    public void exportFileData() {
        try {
            BufferedWriter textWriter = new BufferedWriter(new FileWriter(path));
            // Writing all lines from memory to the file
            for (String line : this.textContent) {
                textWriter.write(line);
                textWriter.newLine();
            }
            textWriter.close();
        } catch (NullPointerException e) { // Thrown by FileWriter
            // In case the importFileData method was not called
            System.out.println("No data to export! Import data from file first!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
