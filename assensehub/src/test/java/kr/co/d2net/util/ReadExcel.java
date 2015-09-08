package kr.co.d2net.util;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.junit.Test;

public class ReadExcel {
	
	@Test
	public void testExcelRead(){
		Workbook w;
		try {
			File inputWorkbook = new File("E:\\tmp\\nick.xls");
			
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines

			for (int j = 0; j < sheet.getColumns(); j++) {
				for (int i = 0; i < sheet.getRows(); i++) {
					Cell cell = sheet.getCell(j, i);
					if(i==3)
					System.out.print(cell.getContents()+",");
					/*
					CellType type = cell.getType();
					
		x			if (type == CellType.LABEL) {
						System.out.println("I got a label "+ cell.getContents());
					}
					if (type == CellType.NUMBER) {
						System.out.println("I got a number "+ cell.getContents());
					}
					*/
					if(i==3) continue;
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
