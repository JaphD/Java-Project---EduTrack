package Net1;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CombinedFileServer {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/edutruck";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Chernet5527&$";
    private static final String INSTRUCTOR_UPLOAD_DIR = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\InstructorAssignmentsUpload\\";
    private static final String STUDENT_UPLOAD_DIR = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\StudentAssignmentsUpload\\";
    private static final String INSTRUCTOR_MATERIAL_DIR = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\InstructorCourseMaterialsUpload\\";
    private static final String Schedule = "C:\\Users\\hp\\OneDrive\\Documents\\Desktop\\Schedule\\";

    private static List<String> getFileNamesFromDatabase(String tableName) {
        List<String> fileNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT FileName FROM " + tableName)) {

            while (resultSet.next()) {
                String fileName = resultSet.getString("FileName");
                fileNames.add(fileName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileNames;
    }


    private static List<String> getAnnouncementFromDatabase() {
        // Replace this with actual code to extract all file names from the database and return them as a list
        List<String> post = new ArrayList<>();
        // Example:
        // Assume you have a database connection and a query to fetch file names
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT content,postTime from announcementPost");

            while (resultSet.next()) {
                String announcement = resultSet.getString("content");
                String postedDate = resultSet.getString("postTime");
                post.add(announcement);
                post.add(postedDate);
                System.out.println(post);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }


    private static List<String> getStudentNamesFromDatabase() {

        List<String> fileNames = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT First_Name,Last_Name, ID_No from studentForm where is_verified=1");

            int rowNum = 1; // Initialize the row number
            while (resultSet.next()) {
                String fileName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String id = resultSet.getString("ID_No");

                fileNames.add(rowNum + "." + "  " + fileName + "  " + lastName + "  " + id);

                rowNum++;
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    private static List<String> getAssessmentNamesFromDatabase() {

        List<String> fileDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT First_Name, Last_Name, ID_No, GradeResult FROM studentForm");

            while (resultSet.next()) {
                String fileName = resultSet.getString("First_Name");
                String lastName = resultSet.getString("Last_Name");
                String id = resultSet.getString("ID_No");
                String gradeResult = resultSet.getString("GradeResult");

                String fileData = fileName + "," + lastName + "," + id + "," + gradeResult;
                fileDataList.add(fileData);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileDataList;

    }


    private static void sendFile(String selectedFile, ObjectOutputStream out, String uploadDir) {
        File fileToSend = new File(uploadDir + selectedFile);

        try {
            if (fileToSend.exists()) {
                byte[] fileData = Files.readAllBytes(fileToSend.toPath());
                out.writeObject(fileData);
                System.out.println("File sent successfully");
            } else {
                out.writeObject(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean updateGradeInDatabase(String id, String newGrade) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE studentForm SET GradeResult = ? WHERE ID_No = ?");
            statement.setString(1, newGrade);
            statement.setString(2, id);

            int rowsAffected = statement.executeUpdate();

            statement.close();
            connection.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getGradeFromDatabase(String studentId) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT GradeResult FROM studentForm WHERE Password = ?");
            statement.setString(1, studentId);


            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String gradeResult = resultSet.getString("GradeResult");
                statement.close();
                connection.close();
                return gradeResult;
            } else {
                statement.close();
                connection.close();
                return "Student not found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error fetching grade";
        }
    }


    private static boolean saveToDatabase(String post) throws SQLException {

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


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(350)) {
            System.out.println("Server started. Waiting for client connection...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                    String request = (String) in.readObject();

                    if ("InstructorRequestFileList".equals(request)) {
                        List<String> fileNames = getFileNamesFromDatabase("InstructorAssignmentsUpload");
                        out.writeObject(fileNames);
                    } else if ("InstructorRequestFile".equals(request)) {
                        String selectedFile = (String) in.readObject();
                        sendFile(selectedFile, out, INSTRUCTOR_UPLOAD_DIR);
                    } else if ("StudentRequestFileList".equals(request)) {
                        List<String> fileNames = getFileNamesFromDatabase("StudentAssignmentsUpload");
                        out.writeObject(fileNames);
                    } else if ("StudentRequestFile".equals(request)) {
                        String selectedFile = (String) in.readObject();
                        sendFile(selectedFile, out, STUDENT_UPLOAD_DIR);
                    } else if ("StudentCourseRequestFileList".equals(request)) {
                        List<String> fileNames = getFileNamesFromDatabase("InstructorCourseMaterialsUpload");
                        out.writeObject(fileNames);
                    } else if ("StudentCourseRequestFile".equals(request)) {
                        String selectedFile = (String) in.readObject();
                        sendFile(selectedFile, out, INSTRUCTOR_MATERIAL_DIR);
                    } else if ("ScheduleRequestFileList".equals(request)) {
                        List<String> fileNames = getFileNamesFromDatabase("schedule");
                        out.writeObject(fileNames);
                    } else if ("ScheduleRequestFile".equals(request)) {
                        String selectedFile = (String) in.readObject();
                        sendFile(selectedFile, out, Schedule);
                    } else if ("DisplayAnnouncement".equals(request)) {
                        List<String> fileNames = getAnnouncementFromDatabase();
                        out.writeObject(fileNames);
                    } else if ("DisplayStudentFileList".equals(request)) {
                        List<String> fileNames = getStudentNamesFromDatabase();
                        out.writeObject(fileNames);
                        System.out.println(fileNames);
                    } else if ("RequestStudentGrade".equals(request)) {
                        String password = (String) in.readObject();

                        String gradeResult = getGradeFromDatabase(password);
                        out.writeObject(gradeResult);
                    } else if ("GradeAssessmentList".equals(request)) {
                        List<String> fileNames = getAssessmentNamesFromDatabase();
                        out.writeObject(fileNames);
                        System.out.println("Student Data Sent to Client.");
                    } else if ("UpdateGrade".equals(request)) {
                        String id = (String) in.readObject();
                        String newGrade = (String) in.readObject();
                        boolean success = updateGradeInDatabase(id, newGrade);
                        out.writeObject(success);
                    } else if ("AnnouncementPost".equals(request)) {
                        String announcement = (String) in.readObject();
                        System.out.println(announcement);
                        boolean isValid = saveToDatabase(announcement);
                        if (isValid) {
                            out.write("posted".getBytes());
                        } else {
                            out.write("error\n".getBytes());
                        }

                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            // Handle the StreamCorruptedException
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
