package Net1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpBothAndLogin {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/edutruck";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Chernet5527&$";

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20); // Create a thread pool with 20 threads
        try (ServerSocket serverSocket = new ServerSocket(100)) {
            System.out.println("Server started");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected");

                    Thread thread = new Thread(new ClientHandler(socket));
                    executor.execute(new ClientHandler(socket));
                    thread.start();
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating server socket: " + e.getMessage());
        }finally {
            executor.shutdown(); // Shut down the thread pool when the server is no longer accepting connections
        }
    }
    private record ClientHandler(Socket socket) implements Runnable {

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream out = socket.getOutputStream()) {
                String data = in.readLine();
                String[] fields = data.split(",");
                 if (fields.length == 2) {//studentLogin
                     String username = fields[0];
                     String password = fields[1];

                     boolean isValid = validateStudentCredentials(username, password);
                     System.out.println(6);
                     if (isValid) {
                         out.write("2".getBytes());

                     } else {
                         out.write("3".getBytes());
                     }
                     out.flush();
                 }

                else if (fields.length == 6) {//student SignUp
                    String firstName = fields[0];
                    String lastName = fields[1];
                    String username = fields[2];
                    String idNumber = fields[3];
                    String email = fields[4];
                    String password = fields[5];
                    boolean isInserted = insertStudentData(firstName, lastName, username, idNumber,email, password);
                    if (isInserted) {
                        out.write("Success\n".getBytes());
                    } else {
                        out.write("Failure\n".getBytes());
                    }
                }
                 else if (fields.length == 7) {//instructor sign up
                    String firstName = fields[0];
                    String lastName = fields[1];
                    String username = fields[2];
                    String department = fields[3];
                    String course = fields[4];
                    String email = fields[5];
                    String password = fields[6];
                    boolean isInserted = insertInstructorData(firstName, lastName, username, department, course, email, password);
                    if (isInserted) {
                        out.write("Success\n".getBytes());
                    } else {
                        out.write("Failure\n".getBytes());
                    }
                }else if (fields.length == 3) {//instructor login
                    String username = fields[0];
                    String password = fields[1];
                    String securityKey=fields[2];
                    System.out.println(securityKey);
                    boolean isValid = validateInstructorCredentials(username, password,securityKey);
                    if (isValid) {
                        out.write("Success\n".getBytes());
                    } else {
                        out.write("Failure\n".getBytes()); // Send failure response
                    }// Handle signup requests

                }
                out.flush();
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean insertStudentData(String firstName, String lastName, String username, String idNumber,String email, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO studentform(first_name, last_name, username, ID_No, email, password) VALUES (?, ?, ?, ?, ?,?)")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, idNumber);
            stmt.setString(5, email);
            stmt.setString(6, password); // Assuming password will be hashed before insertion
            int rs = stmt.executeUpdate();
            return rs == 1;

        }
    }

    public static boolean insertInstructorData(String firstName, String lastName, String username, String department, String course, String email, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO instructorform(first_name, last_name, username,Department,course, email,password) VALUES (?, ?, ?, ?, ?,?,?)")) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, username);
            stmt.setString(4, department);
            stmt.setString(5, course);
            stmt.setString(6, email);
            stmt.setString(7, password); // Assuming password will be hashed before insertion
            int me = stmt.executeUpdate();
            return me == 1;
        }
    }


    private static boolean validateStudentCredentials(String username, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM studentform WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Assuming password is hashed in the database
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    private static boolean validateInstructorCredentials(String username, String password,String securityKey) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM instructorform WHERE username = ? AND password = ? AND securityKey=?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, securityKey);// Assuming password is hashed in the database
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

}
