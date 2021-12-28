public class SQLBookParser {
  public String createInsertQuery(Book book) {
    return "INSERT INTO books(guid,title,author) VALUES ('" + book.getGuid() + "', '" + book.getTitle() + "', '" + book.getAuthor() +"');";
  }

  public String createInsertManyQuery(Book[] books) {
    String query = "INSERT INTO books(guid,title,author) VALUES ";
    for (int i = 0; i < books.length; i++) {
      query += "('" + books[i].getGuid() + "', '" + books[i].getTitle() + "', '" + books[i].getAuthor() +"')";
      if(i != books.length-1) query += ",";
      else query += ";";
    }
    return query;
  }

  public String createDeleteQuery(String guid) {
    return "DELETE FROM books WHERE guid='" + guid +"';";
  }

  public String createUpdateQuery(String guid, Book book) {
    return "UPDATE books SET title='" + book.getTitle() + "', author='" + book.getAuthor() + "' WHERE guid='" + guid +"';";
  }

  public String createSelectQuery(String where){
    return "SELECT * FROM books WHERE " + where + ";";
  }
}
