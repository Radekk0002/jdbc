import org.junit.jupiter.api.Assertions;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DataSourceTest {
  private static DataSource dataSource;
  private static Book[] books;

  @org.junit.jupiter.api.BeforeAll
  static void setUp() {
    //deleting previous db
    File file = new File("./db");
    if(file.exists()){
      String[]entries = file.list();
      for(String s: entries){
        File currentFile = new File(file.getPath(),s);
        currentFile.delete();
      }
      file.delete();
    }
    books = new Book[] {new Book(java.util.UUID.randomUUID().toString(), "test", "test"),
        new Book(java.util.UUID.randomUUID().toString(), "test2", "test2"),
        new Book(java.util.UUID.randomUUID().toString(), "test3", "test3"),
        new Book(java.util.UUID.randomUUID().toString(), "test4", "test4"),
        new Book(java.util.UUID.randomUUID().toString(), "test5", "test5")};
    dataSource = new DataSource("jdbc:h2:./db/booktest.db", "root", "");
  }

  @org.junit.jupiter.api.Test
  @org.junit.jupiter.api.BeforeEach
  void openConnection(){
    dataSource.open();
    assertNotEquals(null,dataSource.select("1=1"));
  }

  @org.junit.jupiter.api.AfterEach
  void closeConnection(){
    dataSource.close();
  }

  @org.junit.jupiter.api.Test
  void insertBook(){
    Book book = new Book(java.util.UUID.randomUUID().toString(), "test", "test");
    dataSource.insert(book);
    assertEquals(1, dataSource.select("guid='" + book.getGuid() + "'").size());
  }

  @org.junit.jupiter.api.Test
  void insertBooks(){
    int count = dataSource.select("1=1").size();
    dataSource.insertMany(books);
    assertEquals(count + books.length, dataSource.select("1=1").size());
  }

  @org.junit.jupiter.api.Test
  void delete(){
    Book book = new Book(java.util.UUID.randomUUID().toString(), "test7", "test7");
    dataSource.insert(book);
    dataSource.delete(book.getGuid());
    assertEquals(0, dataSource.select("guid='"+book.getGuid() + "'").size());
  }

  @org.junit.jupiter.api.Test
  void deleteNonExisting(){
    Book book = new Book(java.util.UUID.randomUUID().toString(), "test7", "test7");
    dataSource.delete(book.getGuid());
    assertEquals(0, dataSource.select("guid='"+book.getGuid() + "'").size());
  }

  @org.junit.jupiter.api.Test
  void update(){
    Book book = new Book(java.util.UUID.randomUUID().toString(), "test8", "test8");
    Book updatedBook = new Book(java.util.UUID.randomUUID().toString(), "testupt8", "testupt8");
    dataSource.insert(book);
    dataSource.update(book.getGuid(), updatedBook);

    assertEquals(updatedBook.getTitle(), dataSource.select("guid='"+book.getGuid() + "'").get(0).getTitle());
  }

  @org.junit.jupiter.api.Test
  void updateNonExisting(){
    Book book = new Book(java.util.UUID.randomUUID().toString(), "test8", "test8");

    dataSource.update(book.getGuid(), book);

    assertThrows(IndexOutOfBoundsException.class, ()->dataSource.select("guid='"+book.getGuid() + "'").get(0));
  }

}
