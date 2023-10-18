package lab4;

import java.util.Collections;

public class MortgageCalcModel {
    private double interestRate;
    private double principal;
    private int compoundFreq;
    private int numPayments;
    private int paymentFreq;


    public MortgageCalcModel(double principal, double interestRate, int numPayments, int paymentFreq, int compoundFreq) {
        this.principal = principal;
        this.interestRate = interestRate;
        this.numPayments = numPayments;
        this.paymentFreq = paymentFreq;
        this.compoundFreq = compoundFreq;
    }

    public double calculatingInterestFactor() {
        return Math.pow((interestRate / compoundFreq) + 1, compoundFreq / (double) paymentFreq) - 1;
    }

    public double calculatingBlendedPayment() {
        double interestFactor = calculatingInterestFactor();
        return (principal * interestFactor) / (1 - Math.pow(1 + interestFactor, -numPayments));
    }

    public double getTheTotalPaidInterest() {
        return (calculatingBlendedPayment() * numPayments) - principal;
    }

    public double getTotalPrincipalAndInterest() {
        return principal + getTheTotalPaidInterest();
    }

    public double getInterestPrincipalRatio() {
        return getTheTotalPaidInterest() / principal;
    }

    public double getAverageInterestPerYear() {
        return getTheTotalPaidInterest() / (numPayments / (double) paymentFreq);
    }

    public double getAverageInterestPerMonth() {
        return getAverageInterestPerYear() / 12;
    }

    public double getAmortizationInYears() {
        return numPayments / (double) paymentFreq;
    }


    public String generatePaymentSchedule() {
        double interestFactor = calculatingInterestFactor();
        double blendedPayment = calculatingBlendedPayment();
        double remainingBalance = principal;
        StringBuilder paymentSchedule = new StringBuilder();

        // Format the header row with fixed width columns
        paymentSchedule.append(String.format("%-10s | %-16s | %-16s | %-16s | %-16s%n",
                "Payment #", "Blended Payment", "Interest", "Principal Amount", "Balance"));

        // Add a separator line
        paymentSchedule.append(String.join("", Collections.nCopies(76, "-"))).append("\n");

        for (int paymentNumber = 1; paymentNumber <= numPayments; paymentNumber++) {
            double interestComponent = remainingBalance * interestFactor;
            double principalComponent = blendedPayment - interestComponent;
            remainingBalance -= principalComponent;

            // Set remaining balance to 0 if it's close enough to 0
            if (Math.abs(remainingBalance) < 1e-2) {
                remainingBalance = 0.0;
            }

            // Format each row with fixed width columns
            paymentSchedule.append(String.format("%-16d | %-16f | %-16f | %-16f | %-16f%n",
                    paymentNumber, blendedPayment, interestComponent, principalComponent, remainingBalance));
        }

        // Add the new calculated values after the loop
        paymentSchedule.append("\nFurther Information:\n");
        paymentSchedule.append(String.format("Overall Interest Paid: %.2f%n", getTheTotalPaidInterest()));
        paymentSchedule.append(String.format("Total Principal and Interest: %.2f%n", getTotalPrincipalAndInterest()));
        paymentSchedule.append(String.format("Ratio of Interest to Principal: %.2f%n", getInterestPrincipalRatio()));
        paymentSchedule.append(String.format("Annualized average interest paid: %.2f%n", getAverageInterestPerYear()));
        paymentSchedule.append(String.format("Monthly average interest paid: %.2f%n", getAverageInterestPerMonth()));
        paymentSchedule.append(String.format("Amortization over Time: %.2f%n", getAmortizationInYears()));
        return paymentSchedule.toString();
    }
}
