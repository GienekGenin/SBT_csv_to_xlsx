package com.quadstingray.javafx.sample;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class MainController {

    static void launch() {
        System.out.println("Set file path: ");
        Scanner scan = new Scanner(System.in);
        String path = scan.nextLine();
        File selectedFile = new File(path);
        CSVParser csvParser = new CSVParser();
// D:\Bitstream\TestFilesBom\CHIRON_nowy_modul - Copy.bom
        // D:\Bitstream\TestFilesBom\GFOX_nowy_modul - Copy.bom
        HashMap<String, ProductModel> allProducts;
        HashSet<String> vendorKeys;
        csvParser.parseCSV(selectedFile);
        allProducts = csvParser.allProducts;
        vendorKeys = csvParser.vendorKeys;
        for(String key : vendorKeys){
            ProductModel product = allProducts.get(key);
            product.goldpins = product.count / product.ulamek;
            allProducts.put(key, product);
        }
        XLSXCreator.fullWorkSheet(selectedFile, csvParser.vendorKeys, csvParser.allProducts);
        XLSXCreator.thtWorkSheet(selectedFile, csvParser.vendorKeys, csvParser.allProducts);
        XLSXCreator.smdWorkSheet(selectedFile, csvParser.vendorKeys, csvParser.allProducts);
    }
}
