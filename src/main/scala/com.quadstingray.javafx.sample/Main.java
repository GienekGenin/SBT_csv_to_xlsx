package com.quadstingray.javafx.sample;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Set file path: ");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        File selectedFile = new File(path);
        CSVParser csvParser = new CSVParser();
        csvParser.parseCSV(selectedFile);
        XLSXCreator.newWorkSheet(selectedFile, csvParser.vendorKeys, csvParser.hmap);
        System.exit(0);
    }
}

