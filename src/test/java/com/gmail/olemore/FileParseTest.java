package com.gmail.olemore;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    @Test
    void zipTest() throws Exception {
        ZipFile zip = new ZipFile("src/test/resources/files/files.zip");

        // pdf
        ZipEntry zipEntryPDF = zip.getEntry("sql.pdf");
        try (InputStream isPDF = zip.getInputStream(zipEntryPDF)) {
            PDF parsed = new PDF(isPDF);
            assertThat(parsed.text).contains("sql");
        }

        // xlsx
        ZipEntry zipEntryXLSX = zip.getEntry("sample-xlsx-file.xlsx");
        try (InputStream isXLSX = zip.getInputStream(zipEntryXLSX)) {
            XLS parsed = new XLS(isXLSX);
            assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(4).getStringCellValue()).isEqualTo("United States");
        }

        //csv
        ZipEntry zipEntryCSV = zip.getEntry("example.csv");
        try (InputStream isCSV = zip.getInputStream(zipEntryCSV)) {
            CSVReader reader = new CSVReader(new InputStreamReader(isCSV));
            List<String[]> list = reader.readAll();
            assertThat(list)
                    .contains(
                            new String[]{"Blok", "Apteka"},
                            new String[]{"Esenin", "Cherniy Chelovek"}
                    );
        }
    }
}