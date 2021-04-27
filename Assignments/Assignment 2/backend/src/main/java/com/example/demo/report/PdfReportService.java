package com.example.demo.report;


import com.example.demo.book.BookService;
import com.example.demo.book.model.dto.BookDTO;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.demo.report.ReportType.PDF;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {
    private BookService bookService;

    @Override
    public String export() {
        List<BookDTO> books = bookService.booksOutOfStock();

        PDDocument pdfDocument = new PDDocument();
        PDPage page = new PDPage();
        pdfDocument.addPage(page);

        try{
            PDPageContentStream cs = new PDPageContentStream(pdfDocument, page);
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_BOLD_ITALIC, 11);
            cs.setLeading(15f);
            cs.newLineAtOffset(45,700);
            cs.showText("Books out of stock: ");
            cs.newLine();
            cs.newLine();

            for(BookDTO book: books){
                cs.showText("Id: " + book.getId());
                cs.newLine();
                cs.showText("Title: " + book.getTitle());
                cs.newLine();
                cs.showText("Author: " + book.getAuthor());
                cs.newLine();
                cs.showText("Genre: " + book.getGenre());
                cs.newLine();
                cs.showText("Price: " + book.getPrice());
                cs.newLine();
                cs.newLine();
            }

            cs.endText();
            cs.close();
            pdfDocument.save("OutOfStockBooks_Report.pdf");

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed PDF Report";
        }

        return "OutOfStockBooks_Report.pdf";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
