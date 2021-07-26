package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {
	
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;	
	FileInputStream fileInputStream;
	FileOutputStream fileOutputStream;

		 static String path="demodata.xlsx";
	

	// Identify TestCases column by scanning the entire 1st row
	// Ones column is identified then scan entire testcase column to identify
	// purchase testcase row
	// after you grab purchase testcase row == pull all data of that data of that
	// row and feed into test

	public ArrayList<String> getData(String testcaseName, String sheetName) {

		ArrayList<String> strList = new ArrayList<>();
		
		try {
			// FileInputStream argument
			
	
			fileInputStream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			int sheets = workbook.getNumberOfSheets();
			for (int i = 0; i < sheets; i++) {

				if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
					XSSFSheet sheet = workbook.getSheetAt(i);
					// Identify TestCases column by scanning the entire 1st row

					Iterator<Row> rows = sheet.iterator(); // sheet is collection of rows
					Row firstrow = rows.next();
					Iterator<Cell> ce = firstrow.cellIterator(); // row is collection of cells
					int k = 0;
					int column = 0;
					while (ce.hasNext()) {
						Cell value = ce.next();
						if (value.getStringCellValue().equalsIgnoreCase("Testcases")) {
							column = k;
						}
						k++;
					}

					// Ones column is identified then scan entire testcase column to identify
					// purchase testcase row

					while (rows.hasNext()) {
						Row r = rows.next();
						if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) {

							// after you grab purchase testcase row == pull all data of that data of that
							// row and feed into test

							Iterator<Cell> cv = r.cellIterator();
							while (cv.hasNext()) {
								Cell c = cv.next();
								if (c.getCellType() == CellType.STRING)	strList.add(c.getStringCellValue());

								else strList.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
					}

				}

			}
			
			    workbook.close();
		        fileInputStream.close();
	
		} catch (IOException e) {
			System.out.println(e);
		}
		return strList;

	}
	
	public void setData(String testCaseName, String sheetName, String id) {
		
		try {
			
			fileInputStream=new FileInputStream(path);		
			workbook=new XSSFWorkbook(fileInputStream);
			sheet=workbook.getSheet(sheetName);		
		
			for (int i=0; i<=sheet.getLastRowNum(); i++) {
				
				XSSFRow currentRow =sheet.getRow(i);
				if(currentRow.getCell(0).toString().equalsIgnoreCase(testCaseName)) {
					if(currentRow.getCell(5)==null) {
					currentRow.createCell(5);
					}if(currentRow.getCell(6)==null) {	currentRow.createCell(6);	
					}
					currentRow.getCell(5).setCellValue(id);
					currentRow.getCell(6).setCellValue("PASS!!!");
				}				
		}
			 fileOutputStream = new FileOutputStream(path);
		        workbook.write(fileOutputStream);

		        workbook.close();
		        fileInputStream.close();
		        fileOutputStream.close();
		
		} catch (IOException e) {
			System.out.println(e);
		}		
	}
}
