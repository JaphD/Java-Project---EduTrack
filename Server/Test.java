package Net1;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

public class Test{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/edutruck";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Chernet5527&$";
    private static final String EMAIL_USERNAME = "verify.edutrack@gmail.com";
    private static final String EMAIL_PASSWORD = "hzjm kidc ftwd pwpo";

    public static void main(String[] args) {
        // Start the server to listen for incoming connections
        startServer();
    }

    public static void startServer() {
        ExecutorService executor = null;
        try (ServerSocket serverSocket = new ServerSocket(350)) {
            out.println("Server is listening on port 357...");
            executor = Executors.newFixedThreadPool(20);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                out.println("New connection established: " + clientSocket.getInetAddress());

                executor.execute(() -> {
                    try {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (executor != null) {
                executor.shutdown(); // Shut down the thread pool when the server is no longer accepting connections
            }
        }
    }

    public static void handleClient(Socket clientSocket) throws IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {
            String database=in.readLine();
            String data = in.readLine();
            if (data != null) {
                String[] fields = data.split(",");
                if (fields.length == 1) {
                    String email = fields[0];
                    System.out.println("Your request is processing");
                    sendVerificationCode(email,database);
                    out.write("VerificationCodeSent".getBytes());
                } else if (fields.length == 2) {
                    String email = fields[0];
                    String enteredCode = fields[1];
                    boolean verificationResult=verifyVerificationCode(email, enteredCode,database);
                    if (verificationResult) {
                        out.write("SignUpVerified".getBytes());
                    } else {
                        out.write("IncorrectVR\n".getBytes());
                    }
                    out.flush();
                }


            } else {
                System.out.println("ERROR");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendVerificationCode(String email,String database) {
        // Generate verification code
        String verificationCode = generateVerificationCode();
        insertVerificationCodeIntoDatabase(email, verificationCode,database);
        // Send email with verification code
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("EduTrack");
            message.setText("Your verification code is: " + verificationCode);
            Transport.send(message);
            out.println("Verification code sent to " + email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertVerificationCodeIntoDatabase(String email, String verificationCode,String database) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                String insertQuery = "UPDATE "+database+" SET verification_code = ?, expiration_time = NOW() + INTERVAL 1 MINUTE WHERE email = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, verificationCode);
                    preparedStatement.setString(2, email);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateVerificationStatusInDatabase(String email, boolean verificationSuccess,String database) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                String securityKey=generateVerificationCode();
                String updateVerificationStatusQuery = "UPDATE "+database+" SET securityKey=?, is_verified = ? WHERE email = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateVerificationStatusQuery)) {
                    preparedStatement.setString(1, securityKey);
                    preparedStatement.setBoolean(2, verificationSuccess);
                    preparedStatement.setString(3, email);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static String generateVerificationCode() {
        // Generate a random 6-digit verification code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
    public static boolean verifyVerificationCode(String email, String enteredCode,String database) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                // Query the database to get the actual verification code
                String actualCode = getActualVerificationCodeFromDatabase(email,database);


                // Compare the entered code with the actual code
                if (enteredCode.equals(actualCode)) {
                    // Update the database to mark the instructor as verified
                    updateVerificationStatusInDatabase(email, true,database);
                    out.println("Verification successful. Student account is now verified.");
                    return true;
                } else {
                    // Update the database to mark the instructor as not verified
                    updateVerificationStatusInDatabase(email, false,database);
                    out.println("Verification failed. Entered code does not match.");
                    out.println(actualCode);
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false in case of exceptions or other failures
    }

    public static String getActualVerificationCodeFromDatabase(String email,String database) {
        String verificationCode = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT verification_code FROM "+database+" WHERE email = ?")) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the verification code from the correct column
                    verificationCode = resultSet.getString("verification_code");  // **Fix: Specify column name**
                } else {
                    // Handle no code found scenario (optional)
                    System.out.println("No verification code found for the given email.");
                }
            }
        } catch (SQLException e) {
            // Handle database errors (optional)
            System.out.println("Error retrieving verification code from database: " + e.getMessage());
        }

        return verificationCode;
    }


}