// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

    static double epsilon = 0.001;  // Approximation accuracy
    static int iterationCounter;    // Number of iterations 

    // Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).  
    public static void main(String[] args) {
        // Gets the loan data
        double loan = Double.parseDouble(args[0]);
        double rate = Double.parseDouble(args[1]) / 100.0; // Fix: Convert percentage to decimal
        int n = Integer.parseInt(args[2]);
        System.out.println("Loan = " + loan + ", interest rate = " + (rate * 100) + "%, periods = " + n);

        // Computes the ending balance of the loan, given a periodical payment
        double payment = 10000;
        double endBalance = endBalance(loan, rate, n, payment);
        System.out.println("If your periodical payment is " + payment + ", your ending balance is: " + (int) endBalance);

        // Computes the periodical payment using brute force search
        System.out.print("\nPeriodical payment, using brute force: ");
        double bruteForcePayment = bruteForceSolver(loan, rate, n, epsilon);
        System.out.println((int) bruteForcePayment);
        System.out.println("Number of iterations: " + iterationCounter);

        // Computes the periodical payment using bisection search
        System.out.print("\nPeriodical payment, using bisection search: ");
        double bisectionPayment = bisectionSolver(loan, rate, n, epsilon);
        System.out.println((int) bisectionPayment);
        System.out.println("Number of iterations: " + iterationCounter);
    }

    // Computes the ending balance of a loan, given the loan amount, the periodical
    // interest rate (as a percentage), the number of periods (n), and the periodical payment.
    private static double endBalance(double loan, double rate, int n, double payment) {
        double balance = loan;
        for (int i = 0; i < n; i++) {
            balance = (balance - payment) * (1 + rate);
        }
        return balance;
    }

    // Uses sequential search to compute an approximation of the periodical payment
    // that will bring the ending balance of a loan close to 0.
    // Given: the sum of the loan, the periodical interest rate (as a percentage),
    // the number of periods (n), and epsilon, the approximation's accuracy
    // Side effect: modifies the class variable iterationCounter.
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
    // Given: the sum of the loan, the periodical interest rate (as a percentage),
    // the number of periods (n), and epsilon, the approximation's accuracy
    // Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double lo = loan / n; // Lower bound
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