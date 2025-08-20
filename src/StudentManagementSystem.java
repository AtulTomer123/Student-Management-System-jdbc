import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    static final String url = "jdbc:mysql://localhost:3306/studentdb";
    static final String dbname = "root";
    static final String pass = "Atult@123";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===========Student management system============");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Deletion Student");
            System.out.println("5. Search Student by Name");
            System.out.println("6. Exit");

            System.out.println("enter your choice");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addstudents(sc);
                case 2 -> viewstudents();
                case 3 -> updatestudents(sc);
                case 4 -> deletionstudent(sc);
                case 5 -> searchStudentById(sc);
                case 6 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                }
                default -> System.out.println("invalid choice");

            }

        }

    }

    private static void addstudents(Scanner sc) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, dbname, pass);

            String name, email, course;
            int age;

            while (true) {

                System.out.print("enter name : ");
                name = sc.nextLine().trim();

                if (!name.isEmpty())
                    break;
                System.out.println("name cann't be empty");
            }

            while (true) {
                System.out.print("enter email : ");

                email = sc.nextLine().trim();
                if (email.contains("@") && email.contains("."))
                    break;
                System.out.println("invalid emial format");
            }

            while (true) {
                System.out.print("enter course : ");
                course = sc.nextLine().trim();
                if (!course.isEmpty())
                    break;
                System.out.println("plz enter coourse");
            }

            while (true) {

                System.out.print("enter age : ");
                age = sc.nextInt();
                sc.nextLine();
                if (age >= 18)
                    break;
                System.out.println("you are below age");
            }

            String sql = "insert into students(name,email,course,age) values(?,?,?,?)";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, name);
            psmt.setString(2, email);
            psmt.setString(3, course);
            psmt.setInt(4, age);
            int row = psmt.executeUpdate();

            if (row > 0) {
                System.out.println("student record successfully");
            } else {
                System.out.println("inavlid details fill again");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void viewstudents() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbname, pass);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from students");

            while (rs.next()) {
                System.out.println(rs.getString("id") + " | " + rs.getString("name") + " | " + rs.getString("email")
                        + " | " + rs.getString("course") + " | " + rs.getInt("age"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updatestudents(Scanner sc) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, dbname, pass);

            System.out.println("enter your id for update");
            int id = sc.nextInt();
            sc.nextLine();
            String name, email, course;
            int age;
            while (true) {

                System.out.println("enter your new name");
                name = sc.nextLine().trim();
                if (!name.isEmpty())
                    break;
                System.out.println("this field is not empty");
            }

            while (true) {

                System.out.println("enter your new email");
                email = sc.nextLine().trim();
                if (email.contains("@") && email.contains("."))
                    break;
                System.out.println("please enter a valid email");
            }

            while (true) {

                System.out.println("enter your new course");
                course = sc.nextLine().trim();
                if (!course.isEmpty())
                    break;
                System.out.println("this field is not empty");
            }

            while (true) {

                System.out.println("enter your new age");
                age = sc.nextInt();
                sc.nextLine();
                if (age >= 18)
                    break;
                System.out.println("you are below age");
            }

            String sql = "update students set name=?,email=?,course=?,age=? where id=? ";
            PreparedStatement psmt = con.prepareStatement(sql);

            psmt.setString(1, name);
            psmt.setString(2, email);
            psmt.setString(3, course);
            psmt.setInt(4, age);
            psmt.setInt(5, id);

            int row = psmt.executeUpdate();

            if (row > 0) {
                System.out.println("students updated successfully");
            } else {
                System.out.println("Student not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deletionstudent(Scanner sc) {

        try {
            Connection con = DriverManager.getConnection(url, dbname, pass);

            System.out.println("enter student id to delete data ");

            int id = sc.nextInt();
            sc.nextLine();

            String sql = "delete from students where id=?";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setInt(1, id);

            int row = psmt.executeUpdate();
            if (row > 0) {
                System.out.println("data deleted successfully");
            } else {
                System.out.println("wrong input or invalid student id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private static void searchStudentById(Scanner sc) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, dbname, pass);

        System.out.print("Enter Student ID to Search: ");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "SELECT * FROM students WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("\n--- Student Found ---");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Course: " + rs.getString("course"));
            System.out.println("Age: " + rs.getInt("age"));
        } else {
            System.out.println("⚠️ No student found with ID: " + id);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}



}
