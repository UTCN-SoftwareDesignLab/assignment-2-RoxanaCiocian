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
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD_ITALIC, 11);
            contentStream.setLeading(15f);
            contentStream.newLineAtOffset(45,700);
            contentStream.showText("Books out of stock: ");
            contentStream.newLine();
            contentStream.newLine();

            for(BookDTO book: books){
                contentStream.showText("Id: " + book.getId());
                contentStream.newLine();
                contentStream.showText("Title: " + book.getTitle());
                contentStream.newLine();
                contentStream.showText("Author: "+ book.getAuthor());
                contentStream.newLine();
                contentStream.showText("Genre: "+ book.getGenre());
                contentStream.newLine();
                contentStream.showText("Price: "+book.getPrice());
                contentStream.newLine();
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
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
