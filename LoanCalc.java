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

    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
        iterationCounter = 0;
        double payment = loan / n; // Start with an initial guess (no interest)
        double increment = 0.01;  // Increment for brute force search
    
        while (true) {
            double balance = endBalance(loan, rate, n, payment);
            iterationCounter++;
    
            // Check if the balance is within epsilon range (solution found)
            if (Math.abs(balance) <= epsilon) {
                break;
            }
    
            // Increment payment for the next iteration
            payment += increment;
    
            // Safety check to prevent infinite loop if solution is missed
            if (payment > loan) {
                throw new RuntimeException("Brute force solver failed to converge");
            }
        }
    
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