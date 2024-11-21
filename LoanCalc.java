public class LoanCalc {

    static double epsilon = 0.001;
    static int iterationCounter;

    public static void main(String[] args) {
        double loan = Double.parseDouble(args[0]);
        double annualRate = Double.parseDouble(args[1]);
        int n = Integer.parseInt(args[2]);
        double monthlyRate = annualRate / 100.0 / 12;

        System.out.println(String.format("Loan = %.2f, interest rate = %.1f%%, periods = %d", loan, annualRate, n));

        System.out.print("\nPeriodical payment, using brute force: ");
        double bruteForcePayment = bruteForceSolver(loan, monthlyRate, n, epsilon);
        System.out.println(String.format("%.0f", bruteForcePayment));
        System.out.println("Number of iterations: " + iterationCounter);

        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, monthlyRate, n, epsilon);
        System.out.println(String.format("%.0f", bisectionPayment));
        System.out.println("Number of iterations: " + iterationCounter);
    }

    public static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = balance * (1 + rate) - payment;
        }
        return balance;
    }

    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double payment = loan / n;
        while (Math.abs(endBalance(loan, rate, n, payment)) > epsilon) {
            payment += epsilon;
            iterationCounter++;
        }
        return payment;
    }

    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double lo = 0;
        double hi = loan;
        double mid;

        while (hi - lo > epsilon) {
            mid = (lo + hi) / 2;
            double balance = endBalance(loan, rate, n, mid);
            if (Math.abs(balance) < epsilon) {
                return mid;
            }
            if (balance > 0) {
                lo = mid;
            } else {
                hi = mid;
            }
            iterationCounter++;
        }
        return (lo + hi) / 2;
    }
}