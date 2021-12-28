public class Book {
  private String guid;
  private String title;
  private String author;

  public Book(String guid, String title, String author) {
    this.guid = guid;
    this.title = title;
    this.author = author;
  }


  public String getTitle() {
    return title;
  }

  public String getGuid() {
    return guid;
  }

  public String getAuthor() {
    return author;
  }
}
