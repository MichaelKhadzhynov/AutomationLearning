package utils;

import org.testng.annotations.DataProvider;

public class ExcelDataProviders {

    @DataProvider
    public Object[][] usersData() {
        String path = "src/test/resources/xlsx/tddExample.xlsx";
        ExcelReader reader = new ExcelReader(path, "List1");
        return reader.getSheetDataForTDD();
    }
}
