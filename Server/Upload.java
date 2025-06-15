package Net1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Upload{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/edutruck";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Chernet5527&$";

    // ...
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(300);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while (true) {
            Socket clientSocket = serverSocket.accept();

            // Create a new thread to handle the client connection
            executor.execute(() -> {
                try (InputStream inputStream = clientSocket.getInputStream();
                     DataInputStream dataInputStream = new DataInputStream(inputStream)) {

                    String fileName = dataInputStream.readUTF();// Read the file name from the input stream
                    String fileType = dataInputStream.readLine();
                    System.out.println(fileType);
                    // Choose the appropriate file path based on the file type
                    String filePath;
                    if ("InstructorAssignmentsUpload".equals(fileType)) {
                        filePath = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\InstructorAssignmentsUpload" + File.separator + fileName;
                    } else if ("InstructorCourseMaterialsUpload".equals(fileType)) {
                        filePath = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\InstructorCourseMaterialsUpload" + File.separator + fileName;
                    } else if ("StudentAssignmentsUpload".equals(fileType)) {
                        filePath = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\StudentAssignmentsUpload" + File.separator + fileName;

                    } else if ("Schedule".equals(fileType)) {
                        filePath = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\Schedule" + File.separator + fileName;

                    } else {
                        throw new IllegalArgumentException("Invalid file type: " + fileType);
                    }

                    System.out.println(fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    // Read the file data from the input stream and write it to a file
                    while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    // Save the absolute file path to the database
                    saveToDatabase(fileType, filePath, fileName);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });

        }
    }
    private static void saveToDatabase(String database,String filePath, String fileName) throws SQLException {
        // Add code to save the absolute file path to the database
        System.out.println("Absolute file path saved to database: " + filePath);
        //Example: You can use JDBC to save the file path to a database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO "+database+"(FilePath, FileName) VALUES (?, ?)");
            statement.setString(1, filePath);
            statement.setString(2, fileName);
            statement.executeUpdate();
        }
    }
}
