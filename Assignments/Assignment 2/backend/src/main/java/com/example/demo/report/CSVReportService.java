package com.example.demo.report;

import com.example.demo.book.BookService;
import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.example.demo.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {
    private final BookService bookService;
    private final String columns = "Id,Title,Author,Price\n";

    @Override
    public String export() {

        List<BookDTO> booksOutOfStock = bookService.booksOutOfStock();

        File csv = new File("OutOfStockBooks_Report.csv");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(columns);

        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File("OutOfStockBooks_Report.csv"), false));

            for(BookDTO bookDTO : booksOutOfStock)
            {
                stringBuilder.append(bookDTO.getId());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getTitle());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getAuthor());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getGenre());
                stringBuilder.append(",");
                stringBuilder.append(bookDTO.getPrice());
                stringBuilder.append("\n");
            }

            writer.write(stringBuilder.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed CSV Report";
        }

        return "OutOfStockBooks_Report.csv";
    }


    @Override
    public ReportType getType() {
        return CSV;
    }
}