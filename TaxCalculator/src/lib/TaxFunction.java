package lib;

public class TaxFunction {

    private static final int BASIC_NONTAXABLE_INCOME = 54000000;
    private static final int MARRIAGE_DEDUCTION = 4500000;
    private static final int CHILD_DEDUCTION = 1500000;
    private static final double TAX_RATE = 0.05;

    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {

        if (numberOfMonthWorking > 12) {
            System.err.println("More than 12 month working per year");
        }

        numberOfChildren = Math.min(numberOfChildren, 3);

        int annualIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;

        int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);

        int taxableIncome = annualIncome - deductible - nonTaxableIncome;

        int tax = (int) Math.round(TAX_RATE * taxableIncome);

        return Math.max(tax, 0); 
    }

    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int result = BASIC_NONTAXABLE_INCOME;
        if (isMarried) {
            result += MARRIAGE_DEDUCTION + (CHILD_DEDUCTION * numberOfChildren);
        }
        return result;
    }
}
