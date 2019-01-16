package com.quadstingray.javafx.sample;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.sl.usermodel.ColorStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class XLSXCreator {

    private static Map<String, HSSFCellStyle> styles;

    private static HSSFCellStyle createBorderedStyle(HSSFWorkbook wb) {
        HSSFCellStyle style = wb.createCellStyle();
        style.setBorderRight(BorderStyle.DASH_DOT);
        style.setRightBorderColor(IndexedColors.RED.getIndex());
        style.setBorderBottom(BorderStyle.DASH_DOT);
        style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderLeft(BorderStyle.DASH_DOT);
        style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        style.setBorderTop(BorderStyle.DASH_DOT);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    private static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb){
        Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();
//        DataFormat df = wb.createDataFormat();

        HSSFCellStyle style;
        HSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(headerFont);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillBackgroundColor((short) 50);
        styles.put("style1", style);

//        style = createBorderedStyle(wb);
//        style.setAlignment(CellStyle.ALIGN_CENTER);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style.setFont(headerFont);
//        style.setDataFormat(df.getFormat("d-mmm"));
//        styles.put("date_style", style);
        return styles;
    }


    private static File getOutputFile(File selectedFile, String ending) {
        return new File(selectedFile.getParent(),
                FilenameUtils.removeExtension(selectedFile.getName()) + ending + ".xls");
    }

    private static void createXlsxFile(File outputFile, HSSFWorkbook workbook) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFile.getPath());
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println(outputFile.getPath() + " file has been generated!");
    }

    private static void initWorkSheet(HSSFSheet sheet) {
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

            styles = createStyles(workbook);
            HSSFCell ff = rowhead.createCell(0);
            ff.setCellStyle(styles.get("style1"));
            ff.setCellValue("Ilosc");
//            rowhead.createCell(0).setCellValue("Ilosc");
            rowhead.createCell(1).setCellValue("RefDes");
            rowhead.createCell(2).setCellValue("Wartosc");
            rowhead.createCell(3).setCellValue("PatternName");
            rowhead.createCell(4).setCellValue("Symbol");
            rowhead.createCell(5).setCellValue("Producent");
            rowhead.createCell(6).setCellValue("Uwagi");
            rowhead.createCell(7).setCellValue("Vendo");
            rowhead.createCell(8).setCellValue("Czesc ulamkowa(goldpiny)");

            int cnt = 0;
            for (String key : vendorKeys) {
                ProductModel towarInfo = hmap.get(key);
                cnt += 1;
                String refDes = String.join(",", towarInfo.refDes);

                HSSFRow row = sheet.createRow((short) cnt);

//                HSSFCell test = row.createCell(0);
//                test.setCellStyle(styles.get("style1"));
//                test.setCellValue(towarInfo.count);
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
                                      HSSFSheet sheet, HashMap<String, ProductModel> hmap) {
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
