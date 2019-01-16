package com.quadstingray.javafx.sample;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.ColorStyle;
import org.apache.poi.ss.usermodel.*;

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
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    private static Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb) {
        Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();

        HSSFCellStyle regularCellStyle;
        HSSFFont regularFont = wb.createFont();
        regularFont.setBold(false);
        regularFont.setFontHeightInPoints((short) 11);
        regularFont.setFontName("Calibri");
        regularCellStyle = createBorderedStyle(wb);

        regularCellStyle.setAlignment(HorizontalAlignment.LEFT);
        regularCellStyle.setFont(regularFont);
        regularCellStyle.setWrapText(true);
        regularCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put("regularCell", regularCellStyle);

        HSSFCellStyle headerCellStyle;
        HSSFFont headerFont = wb.createFont();
        headerFont.setBold(false);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setFontName("Arial");
        headerCellStyle = createBorderedStyle(wb);

        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFPalette palette = wb.getCustomPalette();
        HSSFColor headerBackgroundColor = palette.findSimilarColor(188, 228, 229);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFillForegroundColor(headerBackgroundColor.getIndex());
        styles.put("header", headerCellStyle);

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

            rowhead.createCell(0).setCellValue("Ilosc");
            rowhead.createCell(1).setCellValue("RefDes");
            rowhead.createCell(2).setCellValue("Wartosc");
            rowhead.createCell(3).setCellValue("PatternName");
            rowhead.createCell(4).setCellValue("Symbol");
            rowhead.createCell(5).setCellValue("Producent");
            rowhead.createCell(6).setCellValue("Uwagi");
            rowhead.createCell(7).setCellValue("Vendo");
            rowhead.createCell(8).setCellValue("Czesc \nulamkowa\n(goldpiny)");
            for (int i = 0; i <= 8; i++) {
                rowhead.getCell(i).setCellStyle(styles.get("header"));
            }

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
                if(towarInfo.ulamek != 0){
                    row.createCell(8).setCellValue(String.format("%.5f",towarInfo.ulamek));
                } else row.createCell(8).setCellValue(towarInfo.ulamek);
                for (int i = 0; i <= 8; i++) {
                    row.getCell(i).setCellStyle(styles.get("regularCell"));
                }
            }
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
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
//                String refDes = String.join(",", towarInfo.refDes);

                HSSFRow row = sheet.createRow((short) cnt);

                row.createCell(0).setCellValue(towarInfo.vendo);
                row.createCell(1).setCellValue("");
                row.createCell(2).setCellValue("");
                row.createCell(3).setCellValue("");
                if (towarInfo.ulamek != 0) {
                    row.createCell(4).setCellValue(Float.parseFloat(String.format("%.5f",towarInfo.count * towarInfo.ulamek)));
                } else row.createCell(4).setCellValue(towarInfo.count);
                row.createCell(5).setCellValue(1);
                row.createCell(6).setCellValue("Z brakami");
                row.createCell(7).setCellValue("");
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
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
