import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class PasswordGeneratorUI extends JFrame {
    // Create UI components
    private JTextField lengthField;
    private JCheckBox uppercaseCheckBox, lowercaseCheckBox, numbersCheckBox, specialCharsCheckBox;
    private JButton generateButton;
    private JTextArea passwordArea;

    // Characters for password generation
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+<>?";

    public PasswordGeneratorUI() {
        // Set up the UI frame
        setTitle("Random Password Generator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel for inputs
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Length input
        JLabel lengthLabel = new JLabel("Password Length:");
        lengthField = new JTextField();
        panel.add(lengthLabel);
        panel.add(lengthField);

        // Checkboxes for password options
        uppercaseCheckBox = new JCheckBox("Include Uppercase Letters");
        lowercaseCheckBox = new JCheckBox("Include Lowercase Letters");
        numbersCheckBox = new JCheckBox("Include Numbers");
        specialCharsCheckBox = new JCheckBox("Include Special Characters");
        panel.add(uppercaseCheckBox);
        panel.add(lowercaseCheckBox);
        panel.add(numbersCheckBox);
        panel.add(specialCharsCheckBox);

        // Generate button
        generateButton = new JButton("Generate Password");
        panel.add(generateButton);

        // Area to display the generated password
        passwordArea = new JTextArea(3, 20);
        passwordArea.setEditable(false);
        panel.add(new JScrollPane(passwordArea));

        // Add panel to the frame
        add(panel);

        // Add action listener for the generate button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePassword();
            }
        });
    }

    // Method to generate the password based on user's criteria
    private void generatePassword() {
        int length;
        try {
            length = Integer.parseInt(lengthField.getText());
            if (length <= 0) {
                JOptionPane.showMessageDialog(this, "Password length must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid length. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build character set based on user selections
        StringBuilder charSet = new StringBuilder();
        if (uppercaseCheckBox.isSelected()) {
            charSet.append(UPPERCASE);
        }
        if (lowercaseCheckBox.isSelected()) {
            charSet.append(LOWERCASE);
        }
        if (numbersCheckBox.isSelected()) {
            charSet.append(NUMBERS);
        }
        if (specialCharsCheckBox.isSelected()) {
            charSet.append(SPECIAL_CHARS);
        }

        // Ensure that at least one option is selected
        if (charSet.length() == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one character type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate the password using a secure random generator
        String password = generateRandomPassword(length, charSet.toString());
        passwordArea.setText(password);
    }

    // Method to generate a random password from the character set
    private String generateRandomPassword(int length, String charSet) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charSet.length());
            password.append(charSet.charAt(randomIndex));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        // Run the UI
        SwingUtilities.invokeLater(() -> {
            PasswordGeneratorUI generator = new PasswordGeneratorUI();
            generator.setVisible(true);
        });
    }
}
