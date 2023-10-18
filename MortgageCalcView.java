package lab4;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The view component of the Mortgage Calculator application.
 */
public class MortgageCalcView extends JFrame {
    private JTextField interestRateTF;
    private JTextField principalTF;
    private JTextField numPaymentsTF;
    private JComboBox<Integer> compoundFreqCB;
    private JButton calcButton;
    private JTextArea resultsTA;
    private JComboBox<String> paymentFreqCB;


    /**
     * Constructs a new MortgageCalcView object.
     * Sets the title of the window, the close operation, and the layout of the components.
     * Initializes the components, adds them to the layout, and sets the location of the window.
     */
    public MortgageCalcView() {
        setTitle("Mortgage Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        initComponents();
        addComponentsToLayout();

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes all the components for the view.
     */
    private void initComponents() {
        principalTF = new JTextField(15);
        interestRateTF = new JTextField(15);
        numPaymentsTF = new JTextField(15);

        paymentFreqCB = new JComboBox<>(new String[]{"Monthly", "Bi-Weekly", "Weekly"});
        compoundFreqCB = new JComboBox<>(new Integer[]{1, 2, 4, 12, 365});

        calcButton = new JButton("Calculate");

        resultsTA = new JTextArea(15, 40);
        resultsTA.setEditable(false);
    }

    /**
     * Adds all the components to the layout.
     */
    private void addComponentsToLayout() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        add(new JLabel("Principal:"), constraints);
        constraints.gridx++;
        add(principalTF, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Interest Rate (%):"), constraints);
        constraints.gridx++;
        add(interestRateTF, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Number of Payments:"), constraints);
        constraints.gridx++;
        add(numPaymentsTF, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Payment Frequency:"), constraints);
        constraints.gridx++;
        add(paymentFreqCB, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(new JLabel("Compounding Frequency:"), constraints);
        constraints.gridx++;
        add(compoundFreqCB, constraints);

        constraints.gridx = 1;
        constraints.gridy++;
        add(calcButton, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 2;
        add(new JScrollPane(resultsTA), constraints);
    }

    /**
     * Returns the principal entered by the user.
     *
     * @return The principal entered by the user.
     */
    public double getPrincipal() {
        return Double.parseDouble(principalTF.getText());
    }

    /**
     * Returns the annual interest rate entered by the user.
     *
     * @return The annual interest rate entered by the user.
     */
    public double getInterestRate() {
        return Double.parseDouble(interestRateTF.getText()) / 100;
    }

    public int getNumPayments() {
        return Integer.parseInt(numPaymentsTF.getText());
    }

    /**
     * Returns the payment frequency selected by the user.
     *
     * @return The payment frequency selected by the user.
     */
    public int getPaymentFreq() {
        String selectedItem = (String) paymentFreqCB.getSelectedItem();
        if (selectedItem == null) {
            return 12;
        }

        return switch (selectedItem) {
            case "Bi-Weekly" -> 26;
            case "Weekly" -> 52;
            default -> 12;
        };
    }

    /**
     * Returns the compounding frequency selected by the user.
     *
     * @return The compounding frequency selected by the user.
     */
    public int getCompoundFreq() {
        Integer selectedItem = (Integer) compoundFreqCB.getSelectedItem();
        if (selectedItem == null) {
            return 2;
        }
        return selectedItem;
    }

    /**
     * Adds an ActionListener to the calculate button.
     *
     * @param listener The ActionListener to add.
     */
    public void addCalcButtonListener(ActionListener listener) {
        calcButton.addActionListener(listener);
    }

    /**
     * Displays the results in the text area.
     *
     * @param results The results to display.
     */
    public void displayResults(String results) {
        resultsTA.setText(results);
    }
}