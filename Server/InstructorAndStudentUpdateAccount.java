package Net1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.LocalTime.now;

public class InstructorAndStudentUpdateAccount {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/edutruck";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Chernet5527&$";

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        try (ServerSocket serverSocket = new ServerSocket(250)) {
            System.out.println("Server started");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected");
                    executor.execute(new ClientHandler(socket));

                    Thread thread = new Thread(new ClientHandler(socket));
                    thread.start();
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating server socket: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private record ClientHandler(Socket socket) implements Runnable {

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 OutputStream out = socket.getOutputStream()) {
                String data = in.readLine();
                String[] fields = data.split(",");
                /*if (fields.length == 3) {
                    String username = fields[0];
                    String password = fields[1];
                    String securityKey = fields[2];
                    boolean isValid = validateCredentials(username, password, securityKey);

                    if (isValid) {
                        out.write("Success\n".getBytes());
                    } else {
                        out.write("Failure\n".getBytes());
                    }
                } */if (fields.length == 4) {
                    String oldPassword = fields[0];
                    String newPassword = fields[1];
                    String newSecurityKey = fields[2];
                    boolean isUpdated = updateInstructorAccountInDatabase(oldPassword, newPassword, newSecurityKey);
                    if (isUpdated) {
                        out.write("Updated\n".getBytes());
                    } else {
                        out.write("notUpdated\n".getBytes());
                    }
                } else if (fields.length == 2) {
                    String oldPassword = fields[0];
                    String newPassword = fields[1];

                    boolean isUpdated = updateStudentAccountInDatabase(oldPassword, newPassword);
                    if (isUpdated) {
                        out.write("Updated".getBytes());
                    } else {
                        out.write("notUpdated\n".getBytes());
                    }


                }
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }


        }
    }

    private static boolean validateCredentials(String username, String password, String securityKey) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM instructorform WHERE username = ? AND password = ? AND securityKey=?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, securityKey);// Assuming password is hashed in the database
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }


    private static boolean updateInstructorAccountInDatabase(String oldPassword, String newPassword, String newSecurityKey) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                String updateVerificationStatusQuery = "UPDATE instructorForm SET password= ?, securityKey= ? WHERE password = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(updateVerificationStatusQuery)) {
                    preparedStatement.setString(1, newPassword);
                    preparedStatement.setString(2, newSecurityKey);
                    preparedStatement.setString(3, oldPassword);
                    preparedStatement.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean updateStudentAccountInDatabase(String oldPassword, String newPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                String updateVerificationStatusQuery = "UPDATE studentform SET password=? WHERE password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateVerificationStatusQuery)) {
                    preparedStatement.setString(1, newPassword);
                    preparedStatement.setString(2, oldPassword);
                    preparedStatement.executeUpdate();
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}