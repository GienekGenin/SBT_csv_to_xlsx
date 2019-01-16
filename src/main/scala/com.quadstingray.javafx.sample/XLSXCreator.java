package com.quadstingray.javafx.sample;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

class XLSXCreator {

    private static File getOutputFile(File selectedFile, String ending){
        return new File(selectedFile.getParent(),
                FilenameUtils.removeExtension(selectedFile.getName()) + ending + ".xls");
    }

    private static void createXlsxFile(File outputFile, HSSFWorkbook workbook) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFile.getPath());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println(outputFile.getPath() +" file has been generated!");
    }

    private static void initWorkSheet(HSSFSheet sheet){
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("KOD");
        rowhead.createCell(1).setCellValue("KODTOWARU");
        rowhead.createCell(2).setCellValue("NAZWATOWARU");
        rowhead.createCell(3).setCellValue("MAGAZYN");
        rowhead.createCell(4).setCellValue("ILOSC");
        rowhead.createCell(5).setCellValue("NA_IL_OPERACJI");
        rowhead.createCell(6).setCellValue("BRAKI");
        rowhead.createCell(7).setCellValue("REF");
    }

    static void fullWorkSheet(File selectedFile, HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            File outputFile = getOutputFile(selectedFile, "");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Ilosc");
            rowhead.createCell(1).setCellValue("RefDes");
            rowhead.createCell(2).setCellValue("Wartosc");
            rowhead.createCell(3).setCellValue("PatternName");
            rowhead.createCell(4).setCellValue("Symbol");
            rowhead.createCell(5).setCellValue("Producent");
            rowhead.createCell(6).setCellValue("Uwagi");
            rowhead.createCell(7).setCellValue("Vendo");
            rowhead.createCell(7).setCellValue("Czesc ulamkowa(goldpiny)");

            int cnt = 0;
            for (String key : vendorKeys) {
                ProductModel towarInfo = hmap.get(key);
                cnt += 1;
                String refDes = String.join(",", towarInfo.refDes);

                HSSFRow row = sheet.createRow((short) cnt);

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

            XLSXCreator.createXlsxFile(outputFile, workbook);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static void fillWorkSheet(String type, HashSet<String> vendorKeys,
                                      HSSFSheet sheet, HashMap<String, ProductModel> hmap){
        int cnt = 0;
        for (String key : vendorKeys) {
            ProductModel towarInfo = hmap.get(key);
            if (towarInfo.vendo.contains(type)) {
                cnt += 1;
                String refDes = String.join(",", towarInfo.refDes);

                HSSFRow row = sheet.createRow((short) cnt);

                row.createCell(0).setCellValue(towarInfo.vendo);
                row.createCell(1).setCellValue(refDes);
                row.createCell(2).setCellValue(towarInfo.patternName);
                row.createCell(3).setCellValue("");
                if (towarInfo.ulamek != 0) {
                    row.createCell(4).setCellValue(towarInfo.count * towarInfo.ulamek);
                } else row.createCell(4).setCellValue(towarInfo.count);
                row.createCell(5).setCellValue(1);
                row.createCell(6).setCellValue("Z brakami");
                row.createCell(7).setCellValue("");
            }
        }
    }

    static void thtWorkSheet(File selectedFile, HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            File outputFile = getOutputFile(selectedFile, "_THT");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            initWorkSheet(sheet);

            fillWorkSheet("THT", vendorKeys, sheet, hmap);

            XLSXCreator.createXlsxFile(outputFile, workbook);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static void smdWorkSheet(File selectedFile, HashSet<String> vendorKeys, HashMap<String, ProductModel> hmap) {
        try {
            File outputFile = getOutputFile(selectedFile, "_SMD");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Catalog");
            initWorkSheet(sheet);

            fillWorkSheet("SMD", vendorKeys, sheet, hmap);

            XLSXCreator.createXlsxFile(outputFile, workbook);

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

