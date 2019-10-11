public class SavingsAccount {
    private static double annualInterestRate;

    private double savingsBalance;

    public SavingsAccount(double balance) {
        savingsBalance = balance;
    }

    public void calculateMonthlyInterest() {
        savingsBalance += savingsBalance * (annualInterestRate / 100) / 12;
    }

    public static void modifyInterestRate(double rate) {
        annualInterestRate = rate;
    }

    public void printBalance() {
        System.out.printf("Account balance: $%.2f\n", savingsBalance);
    }
}
