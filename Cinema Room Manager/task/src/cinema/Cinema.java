package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows, seats;
        do {
            System.out.print("Enter the number of rows: ");
            rows = scanner.nextInt();
            System.out.print("Enter the number of seats in each row: ");
            seats = scanner.nextInt();
        } while (rows <= 0 || seats <= 0);

        char[][] seating = createSeatingArray(rows, seats);

        int choice;
        int totalIncome = calculateTotalIncome(rows, seats);
        int currentIncome = 0;
        int purchasedTickets = 0;
        do {
            displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showSeats(seating);
                    break;
                case 2:
                    currentIncome += buyTicket(seating, scanner);
                    purchasedTickets++;
                    break;
                case 3:
                    displayStatistics(rows, seats, purchasedTickets, currentIncome, totalIncome);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        System.out.print("> ");
    }

    public static char[][] createSeatingArray(int rows, int seats) {
        char[][] seating = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                seating[i][j] = 'S'; // 'S' for empty seat
            }
        }
        return seating;
    }

    public static void showSeats(char[][] seating) {
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 1; i <= seating[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < seating.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < seating[i].length; j++) {
                System.out.print(seating[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int buyTicket(char[][] seating, Scanner scanner) {
        int row, seat;
        boolean isValidInput;
        do {
            System.out.print("Enter a row number: ");
            row = scanner.nextInt();
            System.out.print("Enter a seat number in that row: ");
            seat = scanner.nextInt();
            isValidInput = row >= 1 && row <= seating.length && seat >= 1 && seat <= seating[0].length;
            if (!isValidInput) {
                System.out.println("Wrong input! Please try again.");
            } else if (seating[row - 1][seat - 1] == 'B') {
                System.out.println("That ticket has already been purchased! Please select another seat.");
                isValidInput = false;
            }
        } while (!isValidInput);

        seating[row - 1][seat - 1] = 'B'; // 'B' for booked seat

        int totalSeats = seating.length * seating[0].length;
        final int PRICE_STANDARD = 10;
        final int PRICE_PREMIUM = 8;
        int frontHalfRows = seating.length / 2;
        int ticketPrice = (totalSeats <= 60 || row <= frontHalfRows) ? PRICE_STANDARD : PRICE_PREMIUM;

        System.out.println("Ticket price: $" + ticketPrice);
        return ticketPrice;
    }

    public static void displayStatistics(int rows, int seats, int purchasedTickets, int currentIncome, int totalIncome) {
        double occupancy = ((double) purchasedTickets / (rows * seats)) * 100;
        System.out.println("\nNumber of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", occupancy);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static int calculateTotalIncome(int rows, int seats) {
        final int PRICE_STANDARD = 10;
        final int PRICE_PREMIUM = 8;
        int frontHalfRows = rows / 2;
        return (frontHalfRows * seats * PRICE_STANDARD) + ((rows - frontHalfRows) * seats * PRICE_PREMIUM);
    }
}