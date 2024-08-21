import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main{
    private static final Map<String, User> userDatabase = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Multi-User Three Level Password System");
        
        while (true) {
            System.out.println("1. Register a new user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter a unique user ID: ");
        String userID = scanner.nextLine();
        if (userDatabase.containsKey(userID)) {
            System.out.println("User ID already exists. Please choose a different ID.");
            return;
        }

        System.out.print("Create your password: ");
        String password = scanner.nextLine();

        System.out.print("Answer the security question: What was your first pet's name? ");
        String securityAnswer = scanner.nextLine();

        // Generate and store an OTP (for simulation purposes, we'll generate and store it immediately)
        String otp = generateOTP();

        // Create a new user and add it to the database
        User newUser = new User(userID, password, securityAnswer, otp);
        userDatabase.put(userID, newUser);

        System.out.println("Registration successful. You can now log in.");

        // Prompt user to log in or exit
        postRegistrationOptions();
    }

    private static void postRegistrationOptions() {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    loginUser();
                    return; // After login attempt, return to main menu
                case 2:
                    System.out.println("Exiting...");
                    return; // Return to main menu or exit
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void loginUser() {
        System.out.print("Enter your user ID: ");
        String userID = scanner.nextLine();
        User user = userDatabase.get(userID);
        
        if (user == null) {
            System.out.println("User ID not found. Please register first.");
            return;
        }

        // Level 1: Password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (!user.validatePassword(password)) {
            System.out.println("Incorrect Password. Access Denied.");
            return;
        }

        // Level 2: OTP
        System.out.println("OTP has been sent to your registered email/phone.");
        System.out.print("Enter the OTP: ");
        String otp = scanner.nextLine();
        if (!user.validateOTP(otp)) {
            System.out.println("Invalid OTP. Access Denied.");
            return;
        }

        // Level 3: Security Question
        System.out.print("Answer the security question: What was your first pet's name? ");
        String securityAnswer = scanner.nextLine();
        if (!user.validateSecurityAnswer(securityAnswer)) {
            System.out.println("Incorrect answer. Access Denied.");
            return;
        }

        // Success
        System.out.println("Access Granted. Welcome, " + userID + "!");
    }

    private static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        System.out.println("Generated OTP : " + otp); // 
        return String.valueOf(otp);
    }

    static class User {
        private final String userID;
        private final String password;
        private final String securityAnswer;
        private final String otp;

        public User(String userID, String password, String securityAnswer, String otp) {
            this.userID = userID;
            this.password = password;
            this.securityAnswer = securityAnswer;
            this.otp = otp;
        }

        public boolean validatePassword(String password) {
            return this.password.equals(password);
        }

        public boolean validateOTP(String otp) {
            return this.otp.equals(otp);
        }

        public boolean validateSecurityAnswer(String answer) {
            return this.securityAnswer.equalsIgnoreCase(answer);
        }
    }
}
