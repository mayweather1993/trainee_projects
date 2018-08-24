package dao;

import configs.DB;
import entity.Author;
import entity.Book;
import utils.Books;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public interface BookDAO {
    Connection connect(DB db);

    Connection getConnection();

    boolean save(Object object);

    List<Book> searchBookByAuthor(String name, String surname);

    boolean deleteAuthorWithHisBooksById(Long authorId);

    boolean updateBookNameByBookId(String newName, Long bookId);

    boolean addAuthorWithAllHisBooks(Set<Book> books);

    Set<Author> findAllAuthors();

    List<Book> findAllBooks();

    int getTransactionType();

    void setTransactionType(int transactionType);

    Set<Book> checkBooks(Books books);

    boolean writeXmlFile(String xml);

    boolean closeConnection();

    String moneyCourse(String path);


}
