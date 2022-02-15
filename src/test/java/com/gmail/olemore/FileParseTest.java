package com.gmail.olemore;

import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    private ClassLoader cl = FileParseTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        ZipFile zip = new ZipFile("files/res.zip");

        // pdf
        ZipEntry zipEntryPDF = zip.getEntry("PDF.pdf");
        try(InputStream isPDF = zip.getInputStream(zipEntryPDF)) {
            PDF parsed = new PDF(isPDF);
            assertThat(parsed.text).contains("I am PDF file!");
        }
    }
}