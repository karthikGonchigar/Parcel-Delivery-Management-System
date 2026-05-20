package login.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import login.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

}
