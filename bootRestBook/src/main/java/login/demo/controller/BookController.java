package login.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import login.demo.entity.Book;
import login.demo.service.BookService;

@RestController
public class BookController {
	
	@Autowired
	private BookService bookService;
	

	// ***************     getting all the books     ******************************
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
	    List<Book> list = bookService.getAllBooks();
	    if (list.size() <= 0) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	    return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	// *****************************************************************************
	
	
	// ***************     getting the book by id     ******************************
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") int id){
		Book book = bookService.getBookById(id);
		if(book == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(book));
	}
	// *****************************************************************************

	
	
	// ***************    Adding the book    ******************************
	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book b = null;
		try {
			b = bookService.addBook(book);
			System.out.println(book);
			return ResponseEntity.of(Optional.of(b));
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	// *****************************************************************************


	
	// ***************    Delete the book by id     ******************************
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void>deleteBook(@PathVariable("id") int id) {
		try {
			bookService.deleteBook(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	// *****************************************************************************

	
	
	// ***************     Updating the book by id     ******************************
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") int id){
		try {
			bookService.updateBook(book,  id);
			return ResponseEntity.ok().body(book);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	// *****************************************************************************


}
