import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JLabel operationLabel;
    private double firstNumber = 0;
    private String operation = "";
    private boolean isNewNumber = true;
    
    public calculator() {
        // Frame setup
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(45, 45, 45));
        
        // Display panel
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(new Color(45, 45, 45));
        
        operationLabel = new JLabel("0");
        operationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        operationLabel.setForeground(new Color(150, 150, 150));
        operationLabel.setHorizontalAlignment(JLabel.RIGHT);
        operationLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setText("0");
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        displayPanel.add(operationLabel, BorderLayout.NORTH);
        displayPanel.add(display, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(45, 45, 45));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Button labels
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "←", "%", "√"
        };
        
        // Create buttons
        for (String btnLabel : buttons) {
            JButton btn = createButton(btnLabel);
            buttonPanel.add(btn);
        }
        
        // Add components to main panel
        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JButton createButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setFocusPainted(false);
        btn.setBackground(getButtonColor(label));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.addActionListener(this);
        return btn;
    }
    
    private Color getButtonColor(String label) {
        if (label.equals("C") || label.equals("←")) {
            return new Color(200, 50, 50); // Red for clear
        } else if (label.matches("[+\\-*/=√%]")) {
            return new Color(255, 140, 0); // Orange for operations
        } else {
            return new Color(80, 80, 80); // Gray for numbers
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = ((JButton) e.getSource()).getText();
        String currentDisplay = display.getText();
        
        if (command.equals("C")) {
            display.setText("0");
            operationLabel.setText("0");
            firstNumber = 0;
            operation = "";
            isNewNumber = true;
        } else if (command.equals("←")) {
            if (currentDisplay.length() > 1) {
                display.setText(currentDisplay.substring(0, currentDisplay.length() - 1));
            } else {
                display.setText("0");
            }
            isNewNumber = false;
        } else if (command.matches("[+\\-*/%]")) {
            firstNumber = Double.parseDouble(currentDisplay);
            operation = command;
            operationLabel.setText(firstNumber + " " + operation);
            isNewNumber = true;
        } else if (command.equals("=")) {
            performCalculation();
            isNewNumber = true;
        } else if (command.equals("√")) {
            double num = Double.parseDouble(currentDisplay);
            display.setText(String.valueOf(Math.sqrt(num)));
            isNewNumber = true;
        } else if (command.equals(".")) {
            if (!currentDisplay.contains(".")) {
                display.setText(currentDisplay + ".");
                isNewNumber = false;
            }
        } else {
            if (isNewNumber && !command.equals("0")) {
                display.setText(command);
                isNewNumber = false;
            } else if (!isNewNumber) {
                display.setText(currentDisplay + command);
            } else if (command.equals("0") && !currentDisplay.equals("0")) {
                display.setText(currentDisplay + command);
            }
        }
    }
    
    private void performCalculation() {
        double secondNumber = Double.parseDouble(display.getText());
        double result = 0;
        boolean validOperation = true;
        
        switch (operation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    display.setText("Error: Div by 0");
                    validOperation = false;
                }
                break;
            case "%":
                result = firstNumber % secondNumber;
                break;
            default:
                validOperation = false;
        }
        
        if (validOperation) {
            display.setText(String.valueOf(result));
            operationLabel.setText(String.valueOf(result));
        }
        operation = "";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calculator());
    }
}
