package dao;

import configs.ConnectionPool;
import configs.DB;
import entity.Author;
import entity.Book;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.Books;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

public class BookDAOImpl implements BookDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    final static Logger rootLogger = LogManager.getLogger(BookDAOImpl.class);


    @Override
    public Connection connect(DB db) {
        try {
            connectionPool = new ConnectionPool(db, 5);
            connection = connectionPool.retrieve();
            connection.setAutoCommit(false);
            rootLogger.info("db connect");
        } catch (SQLException e) {
            rootLogger.error("Incorrect URL");
        }
        return connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean save(Object object) {
        if (object instanceof Author) {
            Author author = (Author) object;
            PreparedStatement preparedStatement = null;
            int response = 0;
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO Author(Name , Surname , Birthday) values (?,?,?)");
                preparedStatement.setString(1, author.getName());
                preparedStatement.setString(2, author.getSurname());
                preparedStatement.setDate(3, author.getBirthday());
                response = preparedStatement.executeUpdate();
                rootLogger.info("Author inserted into db");
                connection.commit();
            } catch (SQLException e) {
                rootLogger.error("Illegal author arguments");
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                System.out.println(response);
            }
        } else if (object instanceof Book) {
            Book book = (Book) object;
            System.out.println(book);
            PreparedStatement preparedStatement = null;
            int response = 0;
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO Book(Name , NumberOfPages , AuthorId) values (?,?,?)");
                preparedStatement.setString(1, book.getName());
                preparedStatement.setInt(2, book.getNumberOfPages());
                preparedStatement.setLong(3, book.getAuthorId());
                response = preparedStatement.executeUpdate();
                rootLogger.info("Book inserted into db");
                connection.commit();
            } catch (SQLException e) {
                rootLogger.error("Illegal book arguments");
            }
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                System.out.println(response);
            }
        } else {
            rootLogger.error("Unknown object");
            return false;
        }
        return true;
    }

    @Override
    public List<Book> searchBookByAuthor(String name, String surname) {
        List<Book> books = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement pr = connection.prepareStatement("Select b.ID,b.title,b.NumberOfPages,b.AuthorID From Book b, Author a WHERE b.AuthorID=a.ID and a.Surname=? AND a.name like ?");
            pr.setString(1, surname);
            pr.setString(2, "%" + name + "%");
            rs = pr.executeQuery();
            connection.commit();
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getLong(1));
                b.setName(rs.getString(2));
                b.setNumberOfPages(rs.getInt(3));
                b.setAuthorId(rs.getLong(4));
                books.add(b);
            }
        } catch (SQLException e) {
            rootLogger.error("Incorrect arguments");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return books;
    }

    @Override
    public boolean deleteAuthorWithHisBooksById(Long authorId) {
        PreparedStatement preparedStatement;
        int response = 0;
        try {
            preparedStatement = connection.prepareStatement("DELETE * FROM Author where id=?");
            preparedStatement.setLong(1, authorId);
            response = preparedStatement.executeUpdate();
            connection.commit();
            rootLogger.info("Author with id " + authorId + " was deleted");
        } catch (SQLException e) {
            rootLogger.error("Incorrect author id");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;
        } finally {
            System.out.println(response);
        }
        return true;
    }

    @Override
    public boolean updateBookNameByBookId(String newName, Long bookId) {
        return false;
    }

    @Override
    public boolean addAuthorWithAllHisBooks(Set<Book> books) {
        return false;
    }

    @Override
    public Set<Author> findAllAuthors() {
        Set<Author> authors = new HashSet<>();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * From Author";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Author a = new Author();
                a.setId(rs.getLong(1));
                a.setName(rs.getString(2));
                a.setSurname(rs.getString(3));
                a.setBirthday(rs.getDate(4));
                authors.add(a);
            }
            rootLogger.info(authors.size() + " authors were found");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            rootLogger.error("Incorrect arguments");
            return null;
        }
        return authors;
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        ResultSet rs = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * From Book";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong(1));
                book.setName(rs.getString(2));
                book.setNumberOfPages(rs.getInt(3));
                book.setAuthorId(rs.getLong(4));
                books.add(book);
            }
            rootLogger.info(books.size() + " books were found");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            rootLogger.error("Incorrect arguments");
            return null;
        }
        return books;
    }

    @Override
    public int getTransactionType() {
        try {
            return connection.getTransactionIsolation();
        } catch (SQLException e) {
            e.printStackTrace();
            return -10;
        }
    }

    /*
     * TRANSACTION_NONE- not supported in MySQL
     * TRANSACTION_READ_UNCOMMITTED =1
     * TRANSACTION_READ_COMMITTED=2
     * TRANSACTION_REPEATABLE_READ=4
     * TRANSACTION_SERIALIZABLE=8
     * */
    @Override
    public void setTransactionType(int transactionType) {
        try {
            connection.setTransactionIsolation(transactionType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Thats fine bro's")
    @Override
    public Set<Book> checkBooks(Books books) {
        Set<Book> repeatedBooks = new HashSet<>();
        List<Book> xmlBooks = new ArrayList(books.getBooks());
        System.out.println(xmlBooks.toString());
        List<Book> booksList = new ArrayList(findAllBooks());
        Set<Book> bookSet = new HashSet<>();
        for (int i = 0; i < xmlBooks.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < booksList.size(); j++) {
                if ((xmlBooks.get(i).getName().equals(booksList.get(j).getName())) && i != j) {
                    flag = true;
                }
            }
            if (!flag) {
                System.out.println(xmlBooks.get(i));
                bookSet.add(xmlBooks.get(i));
            } else {
                repeatedBooks.add(xmlBooks.get(i));
            }
        }
        if (bookSet.size() > 0) {
            addAuthorWithAllHisBooks(bookSet);
        }
        return repeatedBooks;
    }

    @Override
    public boolean writeXmlFile(String xml) {
        String path = "main\\resources\\";
        String name = "output.xml";
        File file = new File(path, name);
        FileWriter fw = null;
        try {
            fw = new FileWriter(path + name);
            fw.write(xml);
            fw.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean closeConnection() {
        try {
            connectionPool.putback(connection);
            rootLogger.info("Connection closed");
            return true;
        } catch (Exception e) {
            rootLogger.error("Connection wasnt closed");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return false;

        }
    }

    @Override
    public String moneyCourse(String path) {
        try (Scanner scanner = new Scanner(new URL(path).openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
