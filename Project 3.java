import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Expense {
    private String category;
    private String expense;
    private String date;

    public Expense(String category, String expense, String date) {
        this.category = category;
        this.expense = expense;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getExpense() {
        return expense;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Category: " + category + ", Expense: " + expense + ", Date: " + date;
    }
}

class AccountManager extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;
    private JButton loginButton;
    private Map<String, JTextField> categoryFields;
    private Map<String, JTextField> totalExpenseFields; // Store total expense fields for each category
    private JButton addExpenseButton;
    private JButton showExpensesButton;
    private Map<String, UserAccount> userAccounts;
    private UserAccount activeUser;

    public AccountManager() {
        setTitle("EXPENSE TRACKER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userAccounts = new HashMap<>();
        activeUser = null;

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        createAccountButton = new JButton("Create Account");
        loginButton = new JButton("Login");
        addExpenseButton = new JButton("Add Expense");
        showExpensesButton = new JButton("Show Expenses");
        categoryFields = new HashMap<>();
        totalExpenseFields = new HashMap<>(); // Initialize total expense fields map

        addCategoryField("Food");
        addCategoryField("Travel");
        addCategoryField("Utilities");

        usernameField.setBounds(120, 30, 150, 25);
        passwordField.setBounds(120, 60, 150, 25);
        createAccountButton.setBounds(80, 100, 120, 30);
        loginButton.setBounds(220, 100, 120, 30);

        int yPos = 140;
        for (JTextField field : categoryFields.values()) {
            field.setBounds(50, yPos, 100, 25);
            yPos += 40;
        }

        addExpenseButton.setBounds(280, 140, 120, 25);
        showExpensesButton.setBounds(280, 180, 120, 25);

        setLayout(null);
        add(new JLabel("Username:")).setBounds(40, 30, 80, 25);
        add(usernameField);
        add(new JLabel("Password:")).setBounds(40, 60, 80, 25);
        add(passwordField);
        add(createAccountButton);
        add(loginButton);
        add(new JLabel("Food:")).setBounds(10, 140, 40, 25);
        add(new JLabel("Travel:")).setBounds(10, 180, 60, 25);
        add(new JLabel("Utilities:")).setBounds(10, 220, 60, 25);
        for (JTextField field : categoryFields.values()) {
            add(field);
        }
        add(addExpenseButton);
        add(showExpensesButton);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        addExpenseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        showExpensesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showExpenses();
            }
        });

        loadUserAccounts();

        setSize(450, 300);
        setVisible(true);
    }

    private void addCategoryField(String category) {
        JTextField field = new JTextField();
        categoryFields.put(category, field);
        
        // Create and add total expense field for this category
        JTextField totalField = new JTextField("0");
        totalField.setEditable(false);
        totalExpenseFields.put(category, totalField);
        add(totalField);
    }

    private void createAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userAccounts.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose another one.");
            return;
        }

        UserAccount newUser = new UserAccount(username, password);
        userAccounts.put(username, newUser);
        saveUserAccounts();

        JOptionPane.showMessageDialog(this, "Account created successfully.");
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (!userAccounts.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
            return;
        }

        UserAccount user = userAccounts.get(username);
        if (!user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful.");

        activeUser = user;
    }

    private void addExpense() {
        if (activeUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.");
            return;
        }

        StringBuilder expenseDetails = new StringBuilder();
        for (String category : categoryFields.keySet()) {
            JTextField expenseField = categoryFields.get(category);

            String expenseAmount = expenseField.getText();
            double newExpense = Double.parseDouble(expenseAmount.isEmpty() ? "0" : expenseAmount);
            
            double totalExpense = activeUser.getTotalExpenseForCategory(category) + newExpense;

            // Update the total expense field with new total amount
            JTextField totalField = totalExpenseFields.get(category);
            totalField.setText(String.valueOf(totalExpense));

            LocalDate currentDate = LocalDate.now();
            String date = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Get the current date

            Expense newExpenseEntry = new Expense(category, String.valueOf(newExpense), date); // Create new expense entry
            activeUser.addExpense(newExpenseEntry);
            expenseDetails.append(newExpenseEntry).append("\n");
        }
        JOptionPane.showMessageDialog(this, "Expense added:\n" + expenseDetails.toString());
        saveUserAccounts(); // Save user accounts after updating expenses
    }

    private void showExpenses() {
        if (activeUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.");
            return;
        }
    
        List<Expense> userExpenses = activeUser.getExpenses();
    
        if (userExpenses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No expenses found.");
            return;
        }
    
        StringBuilder expensesText = new StringBuilder("Expenses:\n");
        for (Expense expense : userExpenses) {
            expensesText.append(expense.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, expensesText.toString());
    }

    private void loadUserAccounts() {
        // Load user accounts from file (if any)
        try (BufferedReader reader = new BufferedReader(new FileReader("user_credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String username = parts[0];
                    String password = parts[1];
                    UserAccount user = new UserAccount(username, password);
                    for (int i = 2; i < parts.length; i += 3) {
                        String category = parts[i];
                        String expense = parts[i + 1];
                        String date = parts[i + 2];
                        user.addExpense(new Expense(category, expense, date));
                    }
                    userAccounts.put(username, user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUserAccounts() {
        // Save user accounts to file
        try (PrintWriter writer = new PrintWriter(new FileWriter("user_credentials.txt"))) {
            for (UserAccount user : userAccounts.values()) {
                writer.print(user.getUsername() + "," + user.getPassword());
                for (Expense expense : user.getExpenses()) {
                    writer.print("," + expense.getCategory() + "," + expense.getExpense() + "," + expense.getDate());
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AccountManager();
            }
        });
    }
}

class UserAccount {
    private String username;
    private String password;
    private List<Expense> expenses;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public double getTotalExpenseForCategory(String category) {
        double totalExpense = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                totalExpense += Double.parseDouble(expense.getExpense());
            }
        }
        return totalExpense;
    }
}
