package com.quadstingray.javafx.sample;

import java.io.*;
import java.util.*;

class CSVParser {
    private static final String token = "|key|";
    HashMap<String, ProductModel> allProducts = new HashMap<>();
    HashSet<String> vendorKeys = new HashSet<>();

    static void initProduct(HashSet<String> vendorKeys, String key, HashMap<String, ProductModel> allProducts,
                            String uwagi, String value, String producent, String patternName, String vendo,
                            float ulamek, int count, String refDes) {
        vendorKeys.add(key);
        if (allProducts.get(key) == null) {
            ProductModel product = new ProductModel(uwagi, value, producent, patternName, vendo, ulamek);
            product.setCount(product.getCount() + count);
            product.refDes.add(refDes);
            allProducts.put(key, product);
        } else {
            ProductModel product = allProducts.get(key);
            product.setCount(product.getCount() + count);
            if (product.refDes.size() % 8 == 0) {
                product.refDes.add("\n" + refDes);
            } else product.refDes.add(refDes);
            allProducts.put(key, product);
        }
    }

    void parseCSV(File selectedFile) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile.getAbsolutePath())));
            String availalbe;
            while ((availalbe = br.readLine()) != null) {
                List<String> line = CSVLineParser.parseLine(availalbe);
                int count = 0;
                float ulamek = 0;
                String refDes = line.get(1);
                String strUlamek = line.get(7).replace(",", "."),
                        value = line.get(2), patternName = line.get(3), producent = line.get(4),
                        uwagi = line.get(5), vendo = line.get(6);
                if (Functional.isNumber(line.get(0))) {
                    count = Integer.parseInt(line.get(0));
                }
                if (Functional.isNumber(strUlamek)) {
                    ulamek = Float.parseFloat(strUlamek);
                }
                if (vendo.equals("IGNORE")) {
                    continue;
                }
                if (vendo.contains("&")) {
                    ArrayList<String> splitedVendo = new ArrayList<>();
                    String[] splitVendo = vendo.split("&");
                    splitedVendo.add(splitVendo[0]);
                    splitedVendo.add(splitVendo[1]);
                    for (String partVendo : splitedVendo) {
                        String key = value + token + patternName + token + partVendo + token + ulamek;
                        initProduct(vendorKeys, key, allProducts, uwagi, value, producent,
                                patternName, partVendo, ulamek, count, refDes);
                    }
                } else {
                    String key = value + token + patternName + token + ulamek;
                    initProduct(vendorKeys, key, allProducts, uwagi, value, producent,
                            patternName, vendo, ulamek, count, refDes);
                }
            }
        } catch (IOException e) {
            System.out.println("This error");
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
