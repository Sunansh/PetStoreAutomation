package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {
	
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell column;
	public CellStyle style;
	String path;
	
	public XLUtility(String path) {
		this.path = path;
	}
	
	public int getRowCount(String sheetName) throws IOException {
		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		
		workbook.close();
		fi.close();
		return rowcount;
	}
	
	public int getColumnCount(String sheetName, int rownum) throws IOException {
		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int columncount = row.getLastCellNum();
		
		workbook.close();
		fi.close();
		return columncount;	
	}
	
	public String getCellData(String sheetName, int rownum, int columnnum) throws IOException {
		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		column = row.getCell(columnnum);
		
		DataFormatter df = new DataFormatter();
		String data;
		
		try {
			data = df.formatCellValue(column);
		}
		
		catch(Exception e) {
			data = "";
		}
		
		workbook.close();
		fi.close();
		
		return data;
	}
	
	public void setCellData(String sheetName, int rownum, int columnnum, String data) throws IOException {
		
		File xlfile = new File(path);
		if(!xlfile.exists()) {
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
		}
		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook();
		
		if(workbook.getSheetIndex(sheetName)==-1)
			workbook.createSheet(sheetName);
		sheet = workbook.getSheet(sheetName);
		
		if(sheet.getRow(rownum)==null)
			sheet.createRow(rownum);
		row =  sheet.getRow(rownum);
		
		column = row.createCell(columnnum);
		column.setCellValue(data);
		
		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
		
	}
	

}
