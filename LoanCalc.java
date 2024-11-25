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
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "% , periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	private static double endBalance(double loan, double rate, int n, double payment) {
		double balance = loan;
		rate = rate / 100;
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
        // Initialize the static iteration counter to 0
        iterationCounter = 0;
    
        // Initial guess for the payment, g = loan / n
        double payment = loan / n;
    
        // Increment step, epsilon
        double increment = epsilon;
    
        // Perform the brute-force search
        while (true) {
            // Compute the ending balance for the current payment guess
            double balance = endBalance(loan, rate, n, payment);
    
            // Check if the ending balance is non-positive (close enough to 0)
            if (balance <= 0) {
                break; // Exit the loop when a solution is found
            }
    
            // Increment the payment guess
            payment += increment;
    
            // Increment the iteration counter
            iterationCounter++;
        }
    
        // Return the approximate solution
        return payment;
    }
    
    

    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double lo = loan / n;
        double hi = loan;
        double mid;

        while (hi - lo > epsilon) {
            mid = (lo + hi) / 2;
            double balance = endBalance(loan, rate, n, mid);
            if (balance > epsilon) {
                lo = mid;
            } else if (balance < -epsilon) {
                hi = mid;
            } else {
                return mid;
            }
            iterationCounter++;
        }
        return (lo + hi) / 2;
    }
}