package com.example.demo.report;

import com.example.demo.book.BookService;
import com.example.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
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

        StringBuilder sb = new StringBuilder();
        sb.append(columns);

        try (PrintWriter writer = new PrintWriter(new File("OutOfStockBooks_Report.csv"))) {

            for(BookDTO bookDTO : booksOutOfStock)
            {
                sb.append(bookDTO.getId());
                sb.append(",");
                sb.append(bookDTO.getTitle());
                sb.append(",");
                sb.append(bookDTO.getAuthor());
                sb.append(",");
                sb.append(bookDTO.getGenre());
                sb.append(",");
                sb.append(bookDTO.getPrice());
                sb.append("\n");
            }

            writer.write(sb.toString());

            System.out.println("done!");

        } catch ( FileNotFoundException e) {
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