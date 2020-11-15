package com.ptaushanov;

public class Main {

    public static void main(String[] args) {
        Swapper swapper = new Swapper();

        // Importing file data into memory
        swapper.importFileData();
        // Displaying options for the user to choose
        swapper.displayMenu();
        // Swapping lines or words based on users choice
        swapper.startSwapping();
        // Displaying final result from swap
        swapper.displayData();
        // Saving result to file
        swapper.exportFileData();

    }
}
