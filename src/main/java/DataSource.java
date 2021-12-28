import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.h2.jdbcx.JdbcDataSource;

public class DataSource {
//  public static final String URL = "jdbc:h2:˜/book.db";
//  private final static String DBUSER = "root";
//  private final static String DBPASS = "";
  private final SQLBookParser bookParser;
  private Statement statement;
  JdbcDataSource ds;

  public DataSource(String url, String user, String pass){
    bookParser = new SQLBookParser();
    ds = new JdbcDataSource();
    ds.setURL(url);
    ds.setUser(user);
    ds.setPassword(pass);

    query = "CREATE TABLE books (id IDENTITY, guid VARCHAR(100), title VARCHAR(100), author VARCHAR(50));";
    try {
      connection = ds.getConnection();
      statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
      connection.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  private Connection connection;
  private String query;

  public boolean open() {
    try {
      connection = ds.getConnection();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean close() {
    try {
      connection.close();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void insert(Book book) {
    query = bookParser.createInsertQuery(book);
    try {
      statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void insertMany(Book[] books) {
    query = bookParser.createInsertManyQuery(books);
    try {
      statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
  public void delete(String guid) {
    query = bookParser.createDeleteQuery(guid);
    try {
      statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void update(String guid, Book book) {
    query = bookParser.createUpdateQuery(guid, book);
    try {
      statement = connection.createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public List<Book> select(String where) {
    query = bookParser.createSelectQuery(where);
    List<Book> books = new ArrayList<>();
    try {
      statement = connection.createStatement();
      ResultSet result = statement.executeQuery(query);
      while(result.next()) {
        String guid = result.getString("guid");
        String title = result.getString("title");
        String author = result.getString("author");
        books.add(new Book(guid, title, author));
      }
      statement.close();
      return books;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }
}
