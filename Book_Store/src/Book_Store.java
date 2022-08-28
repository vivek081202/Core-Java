/*
 * Core Java Application for Book Store & Author,s Royality Management.
 * Established RDBMS connection using mysql J/connector with Mysql datbase.
 * Allocated JDBC class drivers for connection
 */

// Importing java utility & sql packages & classes for I/O & DBMS connection respectively.
import java.sql.*;
import java.util.Scanner;

// Base class (book)
class book {
    //Making 'sc' as object of Scanner class to get input from user. 
    Scanner sc = new Scanner(System.in);

    // Class Instances
    public String title, author, publisher;
    public int price, Quantity, TotalP;
    public double authorsRoyalty;

    // Class Methods
    public void InsertBookD() {
        System.out.print("Enter Book Title: ");
        title = sc.nextLine();
        System.out.print("Enter Author Name: ");
        author = sc.nextLine();
        System.out.print("Enter Publisher: ");
        publisher = sc.nextLine();
        System.out.print("Enter Price: ");
        price = sc.nextInt();
    }

    public void getUserD() {
        System.out.print("Enter Title: ");
        title = sc.next();
        System.out.print("Enter Quantity: ");
        Quantity = sc.nextInt();
    }

    public void getPrice() {
        System.out.print("Enter Price (in Rs.): ");
        price = sc.nextInt();
        System.out.print("Enter Quantity: ");
        Quantity = sc.nextInt();
    }

    public int calcPrice() {
        TotalP =  price * Quantity;
        return TotalP;
    }

    public void calcRoyalty() {
        if(Quantity < 500) {
            System.out.println("No Royality to author");
        }else if (Quantity == 500) {
            authorsRoyalty = (TotalP * 10) / 100;
        } else if (Quantity > 500 && Quantity >= 1000) {
            authorsRoyalty = (TotalP * 12.5) / 100;
        } else if (Quantity > 1000) {
            authorsRoyalty = (TotalP * 15) / 100;
        }

        System.out.println("Author Royalty in Rs.: " + authorsRoyalty);
    }
}

// Child class (ebook)
class ebook extends book {
    String EPUB, PDF, MOBI;
}

// Main Executable class (Book_Store)
public class Book_Store 
{
    Scanner sc = new Scanner(System.in);

    /*
    * DBtableP() is a method used to establish a connection between Java application & database.  
    * It use to print all data recorded inside table book.
    */ 
    public static void DBtableP()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books_store", "root", "viv0802");
            // here books_store is database name, root is username and viv0802 is password
            // respectively
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from books");
            while (rs.next())
                System.out.println(
                        rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getInt(4));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /*
    * DBtableP() is a method used to establish a connection between Java application & database.  
    * It is use to insert data inside table book table.
    */
    public static void DBtableI(String Title, String Author, String Publisher, int Price)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books_store", "root", "viv0802");
            // here books_store is database name, root is username and viv0802 is password
            // respectively
            String sql = "Insert into books"+"(Title,Author,Publisher,Price)" + "values(?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, Title);
            stmt.setString(2, Author);
            stmt.setString(3, Publisher);
            stmt.setInt(4, Price);
            stmt.executeUpdate();
           System.out.println("Data Recorded & Saved Successfully");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /*
    * DBtableP() is a method used to establish a connection between Java application & database.  
    * It is use to fetch price of a particular book inside table book table.
    */
    public static void DBfetchPr(String Title) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books_store", "root", "viv0802");
        // here books_store is database name, root is username and viv0802 is password
        // respectively
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select Price from books where Title = '" + Title +"' ");
        while (rs.next())
            System.out.println("Price of "+ Title +" is: "+ + rs.getInt(1));
        con.close();
    } catch (Exception e) {
        System.out.println(e);
    }
}


// Main execulatble statements begin from here.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Created an instance of book class as 'b'
        book b = new book();
        System.out.println("\n<-------------------- Welcome to Vivek's Book Store -------------------->\n");
        System.out.println("0. Exit");
        System.out.println("1. Add Book");
        System.out.println("2. User Purchase Fetch");
        System.out.println("3. Total Amount");
        System.out.println("4. Explore Avaliable books");
        System.out.println("\n");
        System.out.println("Enter from:     | 0 | 1 | 2 | 3 | 4 |");
        int input = sc.nextInt();
            if(input == 1) {
                b.InsertBookD();
                DBtableI(b.title,b.author,b.publisher,b.price);
            }else if(input == 2) {
                b.getUserD();
                DBfetchPr(b.title);
            }else if(input == 3) {
                b.getPrice();
                System.out.println("Total Price of Purchased Books are: " + b.calcPrice());
                b.calcRoyalty();
            }else if(input == 4) {
                System.out.println("|    TITLE   |   AUTHOR  |     PUBLISHER    |   PRICE   |");
                DBtableP();
            }else if(input == 0) {
                System.out.println("Thankyou For Being Here!!! Come Again (:");
            }else if(input != 0 || input != 1 || input != 2 || input != 3 || input != 4) {
                System.out.println("ERR:01 OOPS Some Thing Went Wrong!!! (Wrong Input enter from above.)");
            }
        sc.close();
    }
}

// Design & Developed By-
// Vivek Kumar Singh
// BCA Computer Science