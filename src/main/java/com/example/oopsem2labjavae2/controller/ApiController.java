package com.example.oopsem2labjavae2.controller;

import com.example.oopsem2labjavae2.Core.Models.*;
import com.example.oopsem2labjavae2.repositories.BookBorrowersRepository;
import com.example.oopsem2labjavae2.repositories.BookInstanceRepository;
import com.example.oopsem2labjavae2.repositories.BookRepository;
import com.example.oopsem2labjavae2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@ControllerAdvice
public class ApiController {
    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookBorrowersRepository borrowersRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/findBookInstancesByPrompt")
    @ResponseBody
    public List<BookInstance> findBookInstancesByPrompt(Model model, @RequestParam String prompt) {
        List<Book> books = bookRepository.findAllByPrompt(prompt);

        List<Integer> ids = books.stream().map(x -> x.getId()).toList();
        List<BookInstance> instances = bookInstanceRepository.findAllByIds(ids);

        List<Long> isbns = instances.stream().map(x -> x.getIsbn()).toList();
        List<BookBorrower> borrowers = borrowersRepository.findAllById(isbns);

        List<String> usernames = borrowers.stream().map(x -> x.getUsername()).toList();
        List<User> users = userRepository.findAllById(usernames);

        for (Book book : books) {
            for (BookInstance instance : instances) {
                if (book.getId() == instance.getId()) {
                    instance.setBook(book);
                }
            }
        }

        for (BookBorrower borrower : borrowers) {
            User user = null;

            for (User current : users) {
                if (Objects.equals(current.getUsername(), borrower.getUsername())) {
                    user = current;
                }
            }

            if (user == null) {
                continue;
            }

            BookInstance book = null;
            for (BookInstance current : instances) {
                if (current.getIsbn() == borrower.getIsbn()) {
                    book = current;
                }
            }

            book.setBorrower(user);
        }

        return instances;
    }

    @GetMapping("/api/findBookInstanceByISBN")
    @ResponseBody
    public BookInstance findBookInstancesByIsbn(Model model, @RequestParam long isbn) {
        Optional<BookInstance> optionalBookInstance = bookInstanceRepository.findById(isbn);

        if (optionalBookInstance.isEmpty()) {
            return  null;
        }

        BookInstance instance = optionalBookInstance.get();

        Optional<Book> book = bookRepository.findById(instance.getId());
        instance.setBook(book.get());

        if (instance.getStatus() != BookInstanceStatus.Available) {
            BookBorrower borrower = borrowersRepository.findById(isbn).get();
            User user = userRepository.findById(borrower.getUsername()).get();
            instance.setBorrower(user);
        }

        return instance;
    }

    @GetMapping("/api/findBookByPrompt")
    @ResponseBody
    public List<Book> findBooksByPrompt(Model model, @RequestParam String prompt) {
        return bookRepository.findAllByPrompt(prompt);
    }

    @PostMapping("/api/addBookInstance")
    @ResponseBody
    public void addBookInstance(Model model, @RequestParam long isbn, @RequestParam int id) {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setIsbn(isbn);
        bookInstance.setId(id);
        bookInstance.setStatus(BookInstanceStatus.Available);

        bookInstanceRepository.save(bookInstance);
    }

    @PostMapping("/api/addBook")
    @ResponseBody
    public void addBook(Model model, @RequestParam String title, @RequestParam String description) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);

        bookRepository.save(book);
    }

    @PostMapping("/api/borrowBook")
    @ResponseBody
    public void borrowBook(Model model, @RequestParam long isbn, @RequestParam String username, @RequestParam int type) {
        Optional<BookInstance> instanceOptional = bookInstanceRepository.findById(isbn);

        if (instanceOptional.isPresent()) {
            BookInstance instance = instanceOptional.get();

            if (instance.getStatus() == BookInstanceStatus.Available) {
                instance.setStatus(BookInstanceStatus.values()[type]);

                bookInstanceRepository.delete(instance);
                bookInstanceRepository.save(instance);

                BookBorrower borrower = new BookBorrower();
                borrower.setIsbn(isbn);
                borrower.setUsername(username);

                borrowersRepository.save(borrower);
            }
        }
    }

    @PostMapping("/api/returnBook")
    @ResponseBody
    public void returnBook(Model model, @RequestParam long isbn) {
        Optional<BookInstance> instanceOptional = bookInstanceRepository.findById(isbn);

        if (instanceOptional.isPresent()) {
            BookInstance instance = instanceOptional.get();

            instance.setStatus(BookInstanceStatus.Available);

            bookInstanceRepository.delete(instance);
            bookInstanceRepository.save(instance);

            borrowersRepository.deleteById(isbn);
        }
    }
}
