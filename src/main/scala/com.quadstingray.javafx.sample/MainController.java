package com.quadstingray.javafx.sample;

import java.io.File;
import java.util.Scanner;

class MainController {

    static void launch() {
        System.out.println("Set file path: ");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        File selectedFile = new File(path);
        CSVParser csvParser = new CSVParser();
        csvParser.parseCSV(selectedFile);
        XLSXCreator.newWorkSheet(selectedFile, csvParser.vendorKeys, csvParser.hmap);
    }
}
