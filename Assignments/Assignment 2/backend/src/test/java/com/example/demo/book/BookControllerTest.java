package com.example.demo.book;

import com.example.demo.BaseControllerTest;
import com.example.demo.book.model.Book;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.report.CSVReportService;
import com.example.demo.report.PdfReportService;
import com.example.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static com.example.demo.report.ReportType.CSV;
import static com.example.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    void findAll() throws Exception {
        List<BookDTO> books = listOf(Book.class);

        when(bookService.findAll()).thenReturn(books);
        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }



    @Test
    void createBook() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title("Title1")
                .author("Author1")
                .genre("Genre1")
                .quantity(12)
                .price(1.5F)
                .build();
        when(bookService.create(bookDTO)).thenReturn(bookDTO);
        ResultActions result = performPostWithRequestBody(BOOKS,bookDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    void updateBook() throws Exception {
        long id = 1L;
        BookDTO reqBook =  BookDTO.builder()
                .id(id)
                .title("Title1")
                .author("Author1")
                .genre("Genre1")
                .quantity(12)
                .price(1.5F)
                .build();

        when(bookService.update(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariable (BOOKS + ENTITY, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }



    @Test
    void deleteBook() throws Exception {
        long id = 1L;

        doNothing().when(bookService).deleteById(id);

        ResultActions result = performDeleteWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk());
    }

    @Test
    void deleteAll() throws Exception {

        doNothing().when(bookService).deleteAll();

        ResultActions result = performDeleteAll(BOOKS);

        result.andExpect(status().isOk());
        verify(bookService, times(1)).deleteAll();

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);

        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));

        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }


}