import java.io.*;
import java.util.*;

public class StockTradingSimulator {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Stock> marketStocks =
            new ArrayList<>();

    static User user =
            new User("Anushka", 100000);

    public static void main(String[] args) {

        marketStocks.add(new Stock("TCS", 3800));
        marketStocks.add(new Stock("INFY", 1600));
        marketStocks.add(new Stock("RELIANCE", 2900));
        marketStocks.add(new Stock("HDFC", 1700));

        int choice;

        do {

            System.out.println("\n===== STOCK TRADING SIMULATOR =====");
            System.out.println("1. Display Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    savePortfolio();
                    break;

                case 6:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 6);
    }

    static void displayMarket() {

        System.out.println("\nMarket Data");

        for (Stock stock : marketStocks) {
            System.out.println(stock);
        }
    }

    static void buyStock() {

        System.out.print("Enter Stock Symbol: ");
        String symbol = sc.next();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        for (Stock stock : marketStocks) {

            if (stock.getSymbol()
                    .equalsIgnoreCase(symbol)) {

                double cost =
                        stock.getPrice() * qty;

                if (user.deductBalance(cost)) {

                    user.getPortfolio()
                            .buyStock(symbol, qty);

                    System.out.println(
                            "Purchased Successfully!");

                } else {
                    System.out.println(
                            "Insufficient Balance");
                }

                return;
            }
        }

        System.out.println("Stock Not Found");
    }

    static void sellStock() {

        System.out.print("Enter Stock Symbol: ");
        String symbol = sc.next();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        for (Stock stock : marketStocks) {

            if (stock.getSymbol()
                    .equalsIgnoreCase(symbol)) {

                if (user.getPortfolio()
                        .sellStock(symbol, qty)) {

                    double amount =
                            stock.getPrice() * qty;

                    user.addBalance(amount);

                    System.out.println(
                            "Sold Successfully");

                } else {

                    System.out.println(
                            "Not Enough Shares");
                }

                return;
            }
        }

        System.out.println("Stock Not Found");
    }

    static void viewPortfolio() {

        System.out.println(
                "\nCurrent Balance : ₹"
                        + user.getBalance());

        user.getPortfolio()
                .displayPortfolio();
    }

    static void savePortfolio() {

        try {

            FileWriter writer =
                    new FileWriter("portfolio.txt");

            writer.write(
                    "Balance: ₹"
                            + user.getBalance()
                            + "\n");

            for (Map.Entry<String, Integer> entry :
                    user.getPortfolio()
                            .getHoldings()
                            .entrySet()) {

                writer.write(
                        entry.getKey()
                                + " "
                                + entry.getValue()
                                + "\n");
            }

            writer.close();

            System.out.println(
                    "Portfolio Saved Successfully");

        } catch (IOException e) {

            System.out.println(
                    "Error Saving File");
        }
    }
}