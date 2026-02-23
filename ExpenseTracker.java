package ExpenseTracker;

import java.io.*;
import java.util.*;

public class ExpenseTracker {

    static final String FILE_NAME = "expenses.txt";
    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== Personal Expense Tracker ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Search by Category");
            System.out.println("4. Total Expense");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addExpense(sc);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    searchByCategory(sc);
                    break;
                case 4:
                    calculateTotal();
                    break;
                case 5:
                    saveExpenses();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }

    static void addExpense(Scanner sc) {
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        expenses.add(new Expense(category, amount, date));
        System.out.println("Expense Added Successfully!");
    }

    static void viewExpenses() {
        for (Expense e : expenses) {
            System.out.println("Category: " + e.category +
                    ", Amount: " + e.amount +
                    ", Date: " + e.date);
        }
    }

    static void searchByCategory(Scanner sc) {
        System.out.print("Enter Category to Search: ");
        String search = sc.nextLine();

        for (Expense e : expenses) {
            if (e.category.equalsIgnoreCase(search)) {
                System.out.println("Category: " + e.category +
                        ", Amount: " + e.amount +
                        ", Date: " + e.date);
            }
        }
    }

    static void calculateTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.println("Total Expense: " + total);
    }

    static void saveExpenses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                pw.println(e);
            }
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    static void loadExpenses() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                expenses.add(new Expense(
                        parts[0],
                        Double.parseDouble(parts[1]),
                        parts[2]
                ));
            }
        } catch (Exception e) {
            // File may not exist first time
        }
    }
}
