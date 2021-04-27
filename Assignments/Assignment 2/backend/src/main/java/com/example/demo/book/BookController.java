package com.example.demo.book;

import com.example.demo.book.model.dto.BookDTO;

import com.example.demo.report.ReportServiceFactory;

import com.example.demo.report.ReportType;
import com.example.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;
    //private final ReviewService reviewService;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @PutMapping(ENTITY)
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookService.update(id, bookDTO);
    }

    @DeleteMapping
    public void deleteAll() {
        bookService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
    }


    @GetMapping(EXPORT_REPORT)
    public String export(@PathVariable ReportType type){
        return reportServiceFactory.getReportService(type).export();
    }
}
