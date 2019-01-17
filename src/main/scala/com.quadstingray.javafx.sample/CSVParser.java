package com.quadstingray.javafx.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (!vendo.equals("") && !vendo.equals(" ")) {
                    int count = 0;
                    float ulamek = 0;
                    if (Functional.isNumber(line.get(0))) {
                        count = Integer.parseInt(line.get(0));
                    }
                    String strUlamek = line.get(7).replace(",", ".");
                    if (Functional.isNumber(strUlamek)) {
                        ulamek = Float.parseFloat(strUlamek);
                    }
                    String vendoKey = vendo + token + ulamek;

                    String refDes = line.get(1), value = line.get(2), patternName = line.get(3);
                    String producent = line.get(4), uwagi = line.get(5);

                    ArrayList<String> splitedVendo = new ArrayList<>();
                    if (vendo.contains("&")) {
                        String[] splitVendo = vendo.split("&");
                        splitedVendo.add(splitVendo[0]);
                        splitedVendo.add(splitVendo[1]);
                    }
                    if (!(splitedVendo.isEmpty())) {
                        for(String key : splitedVendo){
                            vendorKeys.add(key + token + ulamek);
                            if (allProducts.get(key + token + ulamek) == null) {
                                ProductModel product = new ProductModel(uwagi, value, producent, patternName, key, ulamek);
                                product.setCount(product.getCount() + count);
                                product.refDes.add(refDes);
                                allProducts.put(key + token + ulamek, product);
                            } else {
                                ProductModel product = allProducts.get(key + token + ulamek);
                                product.setCount(product.getCount() + count);
                                if (product.refDes.size() % 8 == 0) {
                                    product.refDes.add("\n" + refDes);
                                } else product.refDes.add(refDes);
                                allProducts.put(key + token + ulamek, product);
                            }
                        }

                    } else {
                        vendorKeys.add(vendoKey);
                        if (allProducts.get(vendoKey) == null) {
                            ProductModel product = new ProductModel(uwagi, value, producent, patternName, vendo, ulamek);
                            product.setCount(product.getCount() + count);
                            product.refDes.add(refDes);
                            allProducts.put(vendoKey, product);
                        } else {
                            ProductModel product = allProducts.get(vendoKey);
                            product.setCount(product.getCount() + count);
                            if (product.refDes.size() % 8 == 0) {
                                product.refDes.add("\n" + refDes);
                            } else product.refDes.add(refDes);
                            allProducts.put(vendoKey, product);
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
