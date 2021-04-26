package com.example.demo;

import com.example.demo.book.BookRepository;
import com.example.demo.book.BookService;
import com.example.demo.book.model.dto.BookDTO;
import com.example.demo.report.ReportService;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    private final BookService bookService;

    private final ReportServiceFactory reportServiceFactory;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("alex@email.com")
                    .username("alex")
                    .password("WooHoo1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("alex1@email.com")
                    .username("alex2")
                    .password("WooHoo1!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());

            BookDTO book = BookDTO.builder()
                    .title("Philosopher's Stone")
                    .author("J.K. Rowling")
                    .genre("Fantasy")
                    .price(25.9f)
                    .quantity(100)
                    .build();
            BookDTO book1 = BookDTO.builder()
                    .title("The Silent Patient")
                    .author("Alex Michaelides")
                    .genre("Thriller")
                    .price(35.8f)
                    .quantity(27)
                    .build();
            BookDTO book2 = BookDTO.builder()
                    .title("Shining")
                    .author("Stephen King")
                    .genre("SF")
                    .price(40.1f)
                    .quantity(50)
                    .build();

            bookService.create(book);
            bookService.create(book1);
            bookService.create(book2);
            List<BookDTO> searched =  bookService.search("Shining");
            System.out.println(searched.toString());
           // BookDTO b = bookService.sell(39L, 100);
           // System.out.println(b.toString());

            //bookService.update(11l, book1);
            //reportServiceFactory.getReportService(ReportType.CSV).export();
            //reportServiceFactory.getReportService(ReportType.PDF).export();
        }
    }
}
