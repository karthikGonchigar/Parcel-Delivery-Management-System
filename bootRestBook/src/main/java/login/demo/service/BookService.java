package login.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import login.demo.entity.Book;
import login.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    
    // Get All Books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    
    // Get Book By Id
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    
    // Add Book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    
    // Delete Book
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    
    // Update Book
    public Book updateBook(Book book, int id) {

        Book oldBook = bookRepository.findById(id).orElse(null);

        if (oldBook != null) {
            oldBook.setTitle(book.getTitle());
            oldBook.setAuthor(book.getAuthor());

            return bookRepository.save(oldBook);
        }

        return null;
    }
}
