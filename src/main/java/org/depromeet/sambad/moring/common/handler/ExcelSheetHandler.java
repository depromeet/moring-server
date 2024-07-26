package org.depromeet.sambad.moring.common.handler;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ExcelSheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

	private final List<List<String>> rows = new ArrayList<List<String>>();

	public static ExcelSheetHandler readExcel(File file) throws Exception {

		ExcelSheetHandler sheetHandler = new ExcelSheetHandler();
		try {

			OPCPackage opc = OPCPackage.open(file);
			XSSFReader xssfReader = new XSSFReader(opc);
			StylesTable styles = xssfReader.getStylesTable();
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

			InputStream inputStream = xssfReader.getSheetsData().next();
			InputSource inputSource = new InputSource(inputStream);
			ContentHandler handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, false);

			XMLReader xmlReader = SAXHelper.newXMLReader();
			xmlReader.setContentHandler(handle);

			xmlReader.parse(inputSource);
			inputStream.close();
			opc.close();
		} catch (Exception e) {
			throw new Exception("Failed to read excel file");
		}
		return sheetHandler;
	}

	@Override
	public void startRow(int rowNum) {
	}

	@Override
	public void endRow(int rowNum) {
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
	}
}

