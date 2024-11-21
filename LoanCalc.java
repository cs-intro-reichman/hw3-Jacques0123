public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations 

    public static void main(String[] args) {
        // Gets the loan data
        double loan = Double.parseDouble(args[0]);
        double annualRate = Double.parseDouble(args[1]); // Annual interest rate as a percentage
        int n = Integer.parseInt(args[2]); // Total number of payments
        double monthlyRate = annualRate / 100.0 / 12; // Convert annual rate to monthly rate

        System.out.println(String.format("Loan = %.2f, interest rate = %.1f%%, periods = %d", loan, annualRate, n));

        // Calculate the periodical payment using the formula
        double exactPayment = calculatePayment(loan, monthlyRate, n);
        System.out.println(String.format("\nExact periodical payment: %.2f", exactPayment));

        // Compute the payment using brute force search
        System.out.print("\nPeriodical payment, using brute force: ");
        double bruteForcePayment = bruteForceSolver(loan, monthlyRate, n, epsilon);
        System.out.println(String.format("%.0f", bruteForcePayment));
        System.out.println("Number of iterations: " + iterationCounter);

        // Compute the payment using bisection search
        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, monthlyRate, n, epsilon);
        System.out.println(String.format("%.0f", bisectionPayment));
        System.out.println("Number of iterations: " + iterationCounter);
    }

    // Calculate the payment using the standard loan payment formula
    public static double calculatePayment(double loan, double rate, int n) {
        if (rate == 0) {
            return loan / n; // Edge case: No interest
        }
        return (loan * rate) / (1 - Math.pow(1 + rate, -n));
    }

    // Compute the ending balance of a loan, given the loan amount, periodical interest rate, and payments
    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = balance * (1 + rate) - payment;
        }
        return balance;
    }

    // Uses sequential search to approximate the payment
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double payment = loan / n; // Initial guess
        while (endBalance(loan, rate, n, payment) > 0) {
            payment += epsilon;
            iterationCounter++;
        }
        return payment;
    }

    // Uses bisection search to approximate the payment
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double lo = 0;       // Lower bound
        double hi = loan;    // Upper bound
        double mid;

        while (hi - lo > epsilon) {
            mid = (lo + hi) / 2;
            if (endBalance(loan, rate, n, mid) > 0) {
                lo = mid; // Solution lies in [mid, hi]
            } else {
                hi = mid; // Solution lies in [lo, mid]
            }
            iterationCounter++;
        }
        return (lo + hi) / 2;
    }
}