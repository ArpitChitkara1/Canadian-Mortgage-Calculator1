package lab4;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 * The controller for the mortgage calculator application.
 */
public class MortgageCalcController {
    private MortgageCalcView view;

    /**
     * Constructs a new MortgageCalcController with the specified view.
     *

     */
    public MortgageCalcController(MortgageCalcView view) {
        this.view = view;
        view.addCalcButtonListener(new CalcButtonListener());
    }

    /**
     * The listener for the calculate button in the view.
     */
    class CalcButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double principal = view.getPrincipal();
                double interestRate = view.getInterestRate();
                int numPayments = view.getNumPayments();
                int paymentFreq = view.getPaymentFreq();
                int compoundFreq = view.getCompoundFreq();

                // Create a new MortgageCalcModel with the user's input
                MortgageCalcModel model = new MortgageCalcModel(principal, interestRate, numPayments,
                        paymentFreq, compoundFreq);

                // Generate a payment schedule based on the user's input and the new model
                String paymentSchedule = model.generatePaymentSchedule();

                // Display the payment schedule in the view
                view.displayResults(paymentSchedule);

            } catch (NumberFormatException ex) {
                // Display an error message if the user's input is invalid
                JOptionPane.showMessageDialog(view, "Enter valid inputs please.", " Input is Invalid", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * The entry point for the mortgage calculator application.

     *
     */
    public static void main(String[] args) {
        // Create a new MortgageCalcView and MortgageCalcController, and make the view visible
        SwingUtilities.invokeLater(() -> {
            MortgageCalcView view = new MortgageCalcView();
            new MortgageCalcController(view);
            view.setVisible(true);
        });
    }
}
