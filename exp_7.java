// Easy Level: Retrieve data from Employee table
import java.sql.*;

public class EmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection conn = DriverManager.getConnection(url, user, password);
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
            
           
            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID") + ", Name: " + rs.getString("Name") + ", Salary: " + rs.getDouble("Salary"));
            }
            
           
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Medium Level: CRUD Operations on Product table
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static String url = "jdbc:mysql://localhost:3306/your_database";
    static String user = "your_username";
    static String password = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            while (true) {
                System.out.println("1. Create Product  2. Read Products  3. Update Product  4. Delete Product  5. Exit");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        createProduct(conn, sc);
                        break;
                    case 2:
                        readProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, sc);
                        break;
                    case 4:
                        deleteProduct(conn, sc);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Enter ProductID, Name, Price, Quantity:");
        int id = sc.nextInt(); sc.nextLine();
        String name = sc.nextLine();
        double price = sc.nextDouble();
        int quantity = sc.nextInt();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setDouble(3, price);
        ps.setInt(4, quantity);
        ps.executeUpdate();
        System.out.println("Product added successfully!");
    }

    static void readProducts(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
        while (rs.next()) {
            System.out.println(rs.getInt("ProductID") + " " + rs.getString("ProductName") + " " + rs.getDouble("Price") + " " + rs.getInt("Quantity"));
        }
    }

    static void updateProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Enter ProductID to update price:");
        int id = sc.nextInt();
        System.out.println("Enter new price:");
        double price = sc.nextDouble();
        PreparedStatement ps = conn.prepareStatement("UPDATE Product SET Price = ? WHERE ProductID = ?");
        ps.setDouble(1, price);
        ps.setInt(2, id);
        ps.executeUpdate();
        System.out.println("Product updated!");
    }

    static void deleteProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Enter ProductID to delete:");
        int id = sc.nextInt();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Product WHERE ProductID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Product deleted!");
    }
}

// Hard Level: MVC-based Student Management System
class Student {
    int studentID;
    String name, department;
    int marks;
    
    public Student(int studentID, String name, String department, int marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
}

class StudentDAO {
    Connection conn;
    
    public StudentDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
    }
    
    public void addStudent(Student s) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
        ps.setInt(1, s.studentID);
        ps.setString(2, s.name);
        ps.setString(3, s.department);
        ps.setInt(4, s.marks);
        ps.executeUpdate();
    }
    
    public void displayStudents() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
        while (rs.next()) {
            System.out.println(rs.getInt("StudentID") + " " + rs.getString("Name") + " " + rs.getString("Department") + " " + rs.getInt("Marks"));
        }
    }
}

public class StudentManagement {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();
        while (true) {
            System.out.println("1. Add Student  2. Display Students  3. Exit");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.println("Enter ID, Name, Department, Marks:");
                int id = sc.nextInt();
                sc.nextLine();
                String name = sc.nextLine();
                String dept = sc.nextLine();
                int marks = sc.nextInt();
                dao.addStudent(new Student(id, name, dept, marks));
            } else if (choice == 2) {
                dao.displayStudents();
            } else {
                break;
            }
        }
    }
}
