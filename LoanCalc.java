public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations 

    // Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
    public static void main(String[] args) {
        // Gets the loan data
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1]) / 100.0 / 12; // Convert annual rate to monthly decimal
        int n = Integer.parseInt(args[2]);
        System.out.println(String.format("Loan = %.2f, interest rate = %.1f%%, periods = %d", loan, rate * 12 * 100, n));

        // Computes the periodical payment using brute force search
        System.out.print("\nPeriodical payment, using brute force: ");
        double bruteForcePayment = bruteForceSolver(loan, rate, n, epsilon);
        System.out.println(String.format("%.0f", bruteForcePayment));
        System.out.println("Number of iterations: " + iterationCounter);

        // Computes the periodical payment using bisection search
        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, rate, n, epsilon);
        System.out.println(String.format("%.0f", bisectionPayment));
        System.out.println("Number of iterations: " + iterationCounter);
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a decimal), the number of periods (n), and the periodical payment.
    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = balance * (1 + rate) - payment;
        }
        return balance;
    }

    // Uses sequential search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double payment = loan / n; // Initial guess
        while (endBalance(loan, rate, n, payment) > 0) {
            payment += epsilon;
            iterationCounter++;
        }
        return payment;
    }

    // Uses bisection search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double lo = 0;          // Lower bound
        double hi = loan;       // Upper bound
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