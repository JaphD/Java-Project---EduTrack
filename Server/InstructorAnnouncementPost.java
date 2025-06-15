package Net1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.LocalTime.now;

public class InstructorAnnouncementPost {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/edutruck";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Chernet5527&$";

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        try (ServerSocket serverSocket = new ServerSocket(500)) {
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
                 
                    boolean isValid = saveToDatabase(data);
                    if (isValid) {
                        out.write("posted".getBytes());
                    }else{
                        out.write("error\n".getBytes());

                    }

                out.flush();
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }


        }
    }


    private static boolean saveToDatabase(String post) throws SQLException {
        System.out.println(1+post);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                String updateVerificationStatusQuery = "insert into announcementPost(content,postTime) values(?,NOW())";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateVerificationStatusQuery)) {
                    preparedStatement.setString(1, post);
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