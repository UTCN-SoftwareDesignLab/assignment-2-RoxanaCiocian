package com.example.demo.report;

import com.example.demo.book.BookMapper;
import com.example.demo.book.BookService;
import com.example.demo.book.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.report.ReportType.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReportServiceFactoryTest {
    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void getReportService() {
        bookService.create(bookMapper.toDto(Book.builder()
                .id(1L)
                .title("Title1")
                .author("Author1")
                .genre("Genre1")
                .quantity(0)
                .price(1.5F)
                .build()));

        bookService.create(bookMapper.toDto(Book.builder()
                .id(2L)
                .title("Title2")
                .author("Author2")
                .genre("Genre2")
                .quantity(0)
                .price(1.5F)
                .build()));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("OutOfStockBooks_Report.pdf", pdfReportService.export());

        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("OutOfStockBooks_Report.csv", csvReportService.export());

    }
}