package org.depromeet.sambad.moring.common.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import lombok.Getter;

public class ExcelSheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
	private int currentCol = -1;

	@Getter
	private List<List<String>> rows = new ArrayList<List<String>>();
	private final List<String> row = new ArrayList<String>();
	private List<String> header = new ArrayList<String>();

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
		} catch (SAXException | OpenXML4JException | ParserConfigurationException | IOException e) {
			throw new Exception();
		}

		return sheetHandler;

	}

	@Override
	public void startRow(int arg0) {
		this.currentCol = -1;
	}

	@Override
	public void cell(String columnName, String value, XSSFComment var3) {
		int iCol = (new CellReference(columnName)).getCol();
		int emptyCol = iCol - currentCol - 1;

		for (int i = 0; i < emptyCol; i++) {
			row.add("");
		}
		currentCol = iCol;
		row.add(value);
	}

	@Override
	public void endRow(int rowNum) {
		if (rowNum == 0) {
			header = new ArrayList(row);
		} else {
			if (row.size() < header.size()) {
				for (int i = row.size(); i < header.size(); i++) {
					row.add("");
				}
			}
			rows.add(new ArrayList(row));
		}
		row.clear();
	}

}

