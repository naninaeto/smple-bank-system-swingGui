import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainUI {
    private JFrame frame;
    private JTextField accField, nameField, amountField;
    private JTextArea output;

    public MainUI() {
        frame = new JFrame("Bank System");
        frame.setSize(500, 400);
        frame.setLayout(null);

        JLabel accLabel = new JLabel("Account No:");
        accLabel.setBounds(20, 20, 100, 25);
        frame.add(accLabel);

        accField = new JTextField();
        accField.setBounds(130, 20, 150, 25);
        frame.add(accField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 50, 100, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 50, 150, 25);
        frame.add(nameField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(20, 80, 100, 25);
        frame.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(130, 80, 150, 25);
        frame.add(amountField);

        JButton createBtn = new JButton("Create Account");
        createBtn.setBounds(20, 120, 150, 25);
        frame.add(createBtn);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(180, 120, 100, 25);
        frame.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(290, 120, 100, 25);
        frame.add(withdrawBtn);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(400, 120, 80, 25);
        frame.add(searchBtn);

        output = new JTextArea();
        output.setBounds(20, 160, 460, 180);
        frame.add(output);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createBtn.addActionListener(e -> {
            try {
                BankOperations.createAccount(accField.getText(), nameField.getText());
                output.setText("Account created successfully.");
                accField.setText("");
                nameField.setText("");
                amountField.setText("");
            } catch (SQLException ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });

        depositBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                BankOperations.deposit(accField.getText(), amt);
                output.setText("Amount deposited.");
                amountField.setText("");
            } catch (Exception ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });

        withdrawBtn.addActionListener(e -> {
            try {
                double amt = Double.parseDouble(amountField.getText());
                BankOperations.withdraw(accField.getText(), amt);
                output.setText("Amount withdrawn.");
                amountField.setText("");
            } catch (Exception ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });

        searchBtn.addActionListener(e -> {
            try {
                Account acc = BankOperations.getAccount(accField.getText());
                if (acc != null) {
                    output.setText("Account: " + acc.getAccountNo() + "\nHolder: " + acc.getHolderName() + "\nBalance: " + acc.getBalance());
                } else {
                    output.setText("Account not found.");
                }
            } catch (SQLException ex) {
                output.setText("Error: " + ex.getMessage());
            }
        });
    }
}