import java.util.ArrayList;
import java.util.Scanner;

class User {
    String name;
    String voterId;
    String password;
    boolean hasVotedMLA;
    boolean hasVotedMP;

    public User(String name, String voterId, String password) {
        this.name = name;
        this.voterId = voterId;
        this.password = password;
        this.hasVotedMLA = false;
        this.hasVotedMP = false;
    }
}

class Candidate {
    String name;
    int encryptedVotes;

    public Candidate(String name) {
        this.name = name;
        this.encryptedVotes = 0;
    }

    public void addVote(int encryptedVote) {
        this.encryptedVotes ^= encryptedVote; // Encrypting votes using XOR
    }

    public int getVotes() {
        return this.encryptedVotes;
    }
}

class Admin {
    String username;
    String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class ElectronicVotingSystem {
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Candidate> mlaCandidates = new ArrayList<>();
    static ArrayList<Candidate> mpCandidates = new ArrayList<>();
    static Admin admin = new Admin("admin", "admin123");

    public static void main(String[] args) {
        initializeCandidates();  // Add MLA and MP candidates for Andhra Pradesh
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register\n2. Login\n3. Admin Login\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    loginAdmin(scanner);
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    public static void registerUser(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Voter ID: ");
        String voterId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        users.add(new User(name, voterId, password));
        System.out.println("Registration successful!");
    }

    public static void loginUser(Scanner scanner) {
        System.out.print("Enter Voter ID: ");
        String voterId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User currentUser = null;
        for (User user : users) {
            if (user.voterId.equals(voterId) && user.password.equals(password)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            System.out.println("Invalid credentials, please try again.");
            return;
        }

        if (currentUser.hasVotedMLA && currentUser.hasVotedMP) {
            System.out.println("You have already voted in both MLA and MP elections. You cannot vote again.");
            return;
        }

        if (!currentUser.hasVotedMLA) {
            voteMLA(scanner, currentUser);
        }

        if (!currentUser.hasVotedMP) {
            voteMP(scanner, currentUser);
        }
    }

    public static void voteMLA(Scanner scanner, User currentUser) {
        if (mlaCandidates.isEmpty()) {
            System.out.println("No MLA candidates available to vote for. Please try later.");
            return;
        }

        System.out.println("MLA Candidates:");
        for (int i = 0; i < mlaCandidates.size(); i++) {
            System.out.println((i + 1) + ". " + mlaCandidates.get(i).name);
        }

        System.out.print("Enter the number of the MLA candidate you want to vote for: ");
        int candidateIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (candidateIndex < 0 || candidateIndex >= mlaCandidates.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Candidate chosenCandidate = mlaCandidates.get(candidateIndex);
        int encryptedVote = (int) (Math.random() * 1000000); // Random number as encrypted vote
        chosenCandidate.addVote(encryptedVote);

        currentUser.hasVotedMLA = true;
        System.out.println("Your MLA vote has been cast successfully!");
    }

    public static void voteMP(Scanner scanner, User currentUser) {
        if (mpCandidates.isEmpty()) {
            System.out.println("No MP candidates available to vote for. Please try later.");
            return;
        }

        System.out.println("MP Candidates:");
        for (int i = 0; i < mpCandidates.size(); i++) {
            System.out.println((i + 1) + ". " + mpCandidates.get(i).name);
        }

        System.out.print("Enter the number of the MP candidate you want to vote for: ");
        int candidateIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (candidateIndex < 0 || candidateIndex >= mpCandidates.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Candidate chosenCandidate = mpCandidates.get(candidateIndex);
        int encryptedVote = (int) (Math.random() * 1000000); // Random number as encrypted vote
        chosenCandidate.addVote(encryptedVote);

        currentUser.hasVotedMP = true;
        System.out.println("Your MP vote has been cast successfully!");
    }

    public static void loginAdmin(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (admin.username.equals(username) && admin.password.equals(password)) {
            adminMenu(scanner);
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    public static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. View Results\n2. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewResults();
                    break;
                case 2:
                    return;
            }
        }
    }

    public static void initializeCandidates() {
        // Adding MLA candidates for Andhra Pradesh
        mlaCandidates.add(new Candidate("N. Chandrababu Naidu"));
        mlaCandidates.add(new Candidate("Y.S. Jagan Mohan Reddy"));
        mlaCandidates.add(new Candidate("Pawan Kalyan"));
        mlaCandidates.add(new Candidate("Nara Lokesh"));

        // Adding MP candidates for Andhra Pradesh
        mpCandidates.add(new Candidate("V. Vijayasai Reddy"));
        mpCandidates.add(new Candidate("K. Rammohan Naidu"));
        mpCandidates.add(new Candidate("N. Siva Prasad"));
        mpCandidates.add(new Candidate("Y.S. Avinash Reddy"));
    }

    public static void viewResults() {
        if (mlaCandidates.isEmpty() && mpCandidates.isEmpty()) {
            System.out.println("No candidates available.");
            return;
        }

        System.out.println("Voting Results:");

        System.out.println("MLA Candidates:");
        for (Candidate candidate : mlaCandidates) {
            System.out.println(candidate.name + ": " + candidate.getVotes() + " votes");
        }

        System.out.println("MP Candidates:");
        for (Candidate candidate : mpCandidates) {
            System.out.println(candidate.name + ": " + candidate.getVotes() + " votes");
        }
    }
}
