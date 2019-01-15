package com.quadstingray.javafx.sample;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;

class XLSXCreator {

    static void fullWorkSheet(File selectedFile, HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            File outputFile = new File(selectedFile.getParent(),
                    FilenameUtils.removeExtension(selectedFile.getName()) + ".xls");
            String filename = outputFile.getPath();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Ilość");
            rowhead.createCell(1).setCellValue("RefDes");
            rowhead.createCell(2).setCellValue("Wartość");
            rowhead.createCell(3).setCellValue("PatternName");
            rowhead.createCell(4).setCellValue("Symbol");
            rowhead.createCell(5).setCellValue("Producent");
            rowhead.createCell(6).setCellValue("Uwagi");
            rowhead.createCell(7).setCellValue("Vendo");
            rowhead.createCell(7).setCellValue("Część ułamkowa(goldpiny)");

            int cnt = 0;
            for (String key : vendorKeys) {
                ProductModel towarInfo = hmap.get(key);
                cnt += 1;
                String refDes = "";
                for (String KOD : towarInfo.refDes) {
                    refDes += KOD + ",";
                }

                HSSFRow row = sheet.createRow((short) cnt);

                // TODO: delete sout after tests
//                System.out.println("Vendor key: " + key + ", nazwa: " +
//                        towarInfo.patternName + ", Ilosc: " + towarInfo.count +
//                        ", refDes: " + kody);

                row.createCell(0).setCellValue(towarInfo.count);
                row.createCell(1).setCellValue(refDes);
                row.createCell(2).setCellValue(towarInfo.value);
                row.createCell(3).setCellValue(towarInfo.patternName);
                row.createCell(4).setCellValue("");
                row.createCell(5).setCellValue(towarInfo.producent);
                row.createCell(6).setCellValue(towarInfo.uwagi);
                row.createCell(7).setCellValue(towarInfo.vendo);
                row.createCell(8).setCellValue(towarInfo.ulamek);
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Your excel file has been generated!");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static void thtWorkSheet(File selectedFile, HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            File outputFile = new File(selectedFile.getParent(),
                    FilenameUtils.removeExtension(selectedFile.getName()) + "_THT.xls");
            String filename = outputFile.getPath();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("KOD");
            rowhead.createCell(1).setCellValue("KODTOWARU");
            rowhead.createCell(2).setCellValue("NAZWATOWARU");
            rowhead.createCell(3).setCellValue("MAGAZYN");
            rowhead.createCell(4).setCellValue("ILOSC");
            rowhead.createCell(5).setCellValue("NA_IL_OPERACJI");
            rowhead.createCell(6).setCellValue("BRAKI");
            rowhead.createCell(7).setCellValue("REF");

            int cnt = 0;
            for (String key : vendorKeys) {
                ProductModel towarInfo = hmap.get(key);
                if (towarInfo.vendo.contains("THT")){
                    cnt += 1;
                String refDes = "";
                for (String KOD : towarInfo.refDes) {
                    refDes += KOD + ",";
                }

                HSSFRow row = sheet.createRow((short) cnt);

                // TODO: delete sout after tests
//                System.out.println("Vendor key: " + key + ", nazwa: " +
//                        towarInfo.patternName + ", Ilosc: " + towarInfo.count +
//                        ", refDes: " + kody);

                row.createCell(0).setCellValue(towarInfo.vendo);
                row.createCell(1).setCellValue(refDes);
                row.createCell(2).setCellValue(towarInfo.patternName);
                row.createCell(3).setCellValue("");
                row.createCell(4).setCellValue(towarInfo.count * towarInfo.ulamek);
                row.createCell(5).setCellValue(1);
                row.createCell(6).setCellValue("Z brakami");
                row.createCell(7).setCellValue("");
                }
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Your excel file has been generated!");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static void smdWorkSheet(File selectedFile, HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            File outputFile = new File(selectedFile.getParent(),
                    FilenameUtils.removeExtension(selectedFile.getName()) + "_SMD.xls");
            String filename = outputFile.getPath();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("KOD");
            rowhead.createCell(1).setCellValue("KODTOWARU");
            rowhead.createCell(2).setCellValue("NAZWATOWARU");
            rowhead.createCell(3).setCellValue("MAGAZYN");
            rowhead.createCell(4).setCellValue("ILOSC");
            rowhead.createCell(5).setCellValue("NA_IL_OPERACJI");
            rowhead.createCell(6).setCellValue("BRAKI");
            rowhead.createCell(7).setCellValue("REF");

            int cnt = 0;
            for (String key : vendorKeys) {
                ProductModel towarInfo = hmap.get(key);
                if (towarInfo.vendo.contains("SMD")){
                    cnt += 1;
                    String refDes = "";
                    for (String KOD : towarInfo.refDes) {
                        refDes += KOD + ",";
                    }

                    HSSFRow row = sheet.createRow((short) cnt);

                    // TODO: delete sout after tests
//                System.out.println("Vendor key: " + key + ", nazwa: " +
//                        towarInfo.patternName + ", Ilosc: " + towarInfo.count +
//                        ", refDes: " + kody);

                    row.createCell(0).setCellValue(towarInfo.vendo);
                    row.createCell(1).setCellValue(refDes);
                    row.createCell(2).setCellValue(towarInfo.patternName);
                    row.createCell(3).setCellValue("");
                    if(towarInfo.ulamek != 0){
                        row.createCell(4).setCellValue(towarInfo.count * towarInfo.ulamek);
                    } else row.createCell(4).setCellValue(towarInfo.count);
                    row.createCell(5).setCellValue(1);
                    row.createCell(6).setCellValue("Z brakami");
                    row.createCell(7).setCellValue("");
                }
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Your excel file has been generated!");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

