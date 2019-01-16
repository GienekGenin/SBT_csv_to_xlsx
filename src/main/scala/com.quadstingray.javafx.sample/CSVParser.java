package com.quadstingray.javafx.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

class CSVParser {
    private static final String token = "|key|";
    HashMap<String, ProductModel> allProducts = new HashMap<>();
    HashSet<String> vendorKeys = new HashSet<>();

    void parseCSV(File selectedFile) {

        try {
            Scanner scanner = new Scanner(new File(selectedFile.getAbsolutePath()));

            while (scanner.hasNext()) {
                List<String> line = CSVLineParser.parseLine(scanner.nextLine());
                String vendo = "";
                try {
                    vendo = line.get(6);
                } catch (Exception e){
                    System.out.println(e);
                }
                if (!vendo.equals("") && !vendo.equals(" ")) {
                    int count = 0;
                    float ulamek = 0;
                    if (Functional.isNumber(line.get(0))) {
                        count = Integer.parseInt(line.get(0));
                    }
                    String strUlamek = line.get(7).replace(",",".");
                    if (Functional.isNumber(strUlamek)) {
                        ulamek = Float.parseFloat(strUlamek);
                    }
                    String vendorKey = vendo +token+ ulamek;
                    vendorKeys.add(vendorKey);
                    String refDes = line.get(1), value = line.get(2), patternName = line.get(3);
                    String producent = line.get(4), uwagi = line.get(5);
                    if (allProducts.get(vendorKey) == null) {
                        ProductModel product = new ProductModel(uwagi, value, producent, patternName, vendo, ulamek);
                        product.setCount(product.getCount() + count);
                        product.refDes.add(refDes);
                        allProducts.put(vendorKey, product);
                    } else {
                        ProductModel product = allProducts.get(vendorKey);
                        product.setCount(product.getCount() + count);
                        if(product.refDes.size() % 8 == 0){
                            product.refDes.add("\n"+refDes);
                        } else product.refDes.add(refDes);
                        allProducts.put(vendorKey, product);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
