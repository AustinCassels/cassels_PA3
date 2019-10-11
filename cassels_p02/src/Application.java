public class Application {
    public static void main(String[] args) {
        //declare variables
        SavingsAccount saver1;
        SavingsAccount saver2;

        //initialize variables
        saver1 = new SavingsAccount(2000);
        saver2 = new SavingsAccount(3000);

        //set savings accounts' interest rate
        SavingsAccount.modifyInterestRate(4);

        //calculate 12 months of interest
        for(int i = 0; i < 12; i++) {
            saver1.calculateMonthlyInterest();
            saver2.calculateMonthlyInterest();
        }

        //print balances
        saver1.printBalance();
        saver2.printBalance();

        //change interest rate
        SavingsAccount.modifyInterestRate(5);

        //calculate 1 month of interest
        saver1.calculateMonthlyInterest();
        saver2.calculateMonthlyInterest();

        //print balances
        saver1.printBalance();
        saver2.printBalance();
    }
}
