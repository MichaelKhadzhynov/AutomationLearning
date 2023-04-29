package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelReader {

    private final String excelFilepath;
    private XSSFSheet sheet;
    private XSSFWorkbook book;

    private String sheetNumber;

    public ExcelReader(String excelFilepath) {
        this.excelFilepath = excelFilepath;
        File file = new File(excelFilepath);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            book = new XSSFWorkbook(fileInputStream);
            sheet = book.getSheet("List1");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExcelReader(String excelFilepath, String sheetNumber) {
        this.excelFilepath = excelFilepath;
        this.sheetNumber = sheetNumber;
        File file = new File(excelFilepath);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            book = new XSSFWorkbook(fileInputStream);
            sheet = book.getSheet(sheetNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String cellToString(XSSFCell cell) {
        Object result = null;
        CellType type = cell.getCellType();

        switch (type) {

            case NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case STRING:
                result = cell.getStringCellValue();
                break;
            case BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            case BLANK:
                result = "";
                break;
            default:
                try {
                    throw new Exception("Reading error");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        return result.toString();
    }

    private int xlsxColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    private int xlsxRawCount() {
        return sheet.getLastRowNum() + 1;
    }

    public String[][] getSheetDataForTDD() {
        File file = new File(excelFilepath);

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            book = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sheet = book.getSheet(sheetNumber);

        int columnsNumber = xlsxColumnCount();
        int rawsNumber = xlsxRawCount();

        String[][] data = new String[rawsNumber - 1][columnsNumber];

        for (int i = 1; i < rawsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                String value = cellToString(cell);

                data[i - 1][j] = value;

                if (value == null) System.out.println("Empty cell");
            }

        }
        return data;
    }

}
