package com.quadstingray.javafx.sample;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

class MainController {

    static void launch(String path) {
        File selectedFile = new File(path);
        CSVParser csvParser = new CSVParser();
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
