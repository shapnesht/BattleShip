package battleship;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    static class Inti {
        public int a;
        Inti(int a) {
            this.a = a;
        }
        int getA() {
            return a;
        }
        void setA(int a) {
            this.a = a;
        }
    }
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[][] fieldForPlayer1 = new String[11][11];
        String[][] fieldForPlayer2 = new String[11][11];
        generateNewField(fieldForPlayer1);
        generateNewField(fieldForPlayer2);

        addPlanes(fieldForPlayer1, 1);
        addPlanes(fieldForPlayer2, 2);
        startGame(fieldForPlayer1, fieldForPlayer2);
    }

    private static void addPlanes(String[][] field, int player) {
        System.out.printf("Player %d, place your ships to the game field\n", player);
        System.out.println();
        printField(field);
        placePlane(field, "Aircraft Carrier", 5);
        placePlane(field, "Battleship", 4);
        placePlane(field, "Submarine", 3);
        placePlane(field, "Cruiser", 3);
        placePlane(field, "Destroyer", 2);

        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        scanner.nextLine();
    }

    private static void startGame(String[][] fieldForPlayer1, String[][] fieldForPlayer2) {
        System.out.println("The game starts!\n");
        String[][] gameField1 = new String[11][11];
        String[][] gameField2 = new String[11][11];
        generateNewField(gameField1);
        generateNewField(gameField2);
        printFieldBoth(gameField2, fieldForPlayer1);
        int count1 = 0;
        int count2 = 0;
        Inti currentPlayer = new Inti(1);
        System.out.println("Player 1, it's your turn:\n");
//        scanner.nextLine();
        String coordinate = scanner.nextLine().trim();
        System.out.println();
        while (true) {
            while (!checkIndividualCoordinates(coordinate)) {
                coordinate = scanner.nextLine().trim();
            }
            if (checkIfHit(fieldForPlayer1, fieldForPlayer2, gameField1, gameField2, coordinate, currentPlayer) == 1) {
                if (currentPlayer.getA() == 1) {
                    count1 += 1;
                } else {
                    count2 += 1;
                }
                if (count1 == 5 || count2 == 5) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                } else {
                    System.out.println("You sank a ship!\nPress Enter and pass the move to another player\n");
                    scanner.nextLine();
                }
            }
            if (currentPlayer.getA() == 1) {
                printFieldBoth(gameField2, fieldForPlayer1);
                System.out.println("Player 1, it's your turn:\n");
            } else {
                printFieldBoth(gameField1, fieldForPlayer2);
                System.out.println("Player 2, it's your turn:\n");
            }
            coordinate = scanner.nextLine().trim();
            System.out.println();
        }
    }

    private static void printFieldBoth(String[][] gameField1, String[][] field2) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(gameField1[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------");
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(field2[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int checkIfHit(String[][] fieldForPlayer1, String[][] fieldForPlayer2, String[][] gameField1, String[][] gameField2, String coordinate, Inti currentPlayer) {
        int row = coordinate.charAt(0) - 64;
        int col = Integer.parseInt(coordinate.substring(1));
        if (currentPlayer.getA() == 1) {
            if (Objects.equals(fieldForPlayer2[row][col], "O") && !Objects.equals(gameField2[row][col], "X")) {
                gameField2[row][col] = "X";
                fieldForPlayer2[row][col] = "X";
                printFieldBoth(gameField2, fieldForPlayer1);
                if (checkIfShipIsSunk(fieldForPlayer2, row, col, -1, -1)) {
                    if (currentPlayer.getA() == 1) {
                        currentPlayer.setA(2);
                    } else {
                        currentPlayer.setA(1);
                    }
                    return 1;
                } else {
                    System.out.println("You hit a ship!\nPress Enter and pass the move to another player");
                    scanner.nextLine();
                    if (currentPlayer.getA() == 1) {
                        currentPlayer.setA(2);
                    } else {
                        currentPlayer.setA(1);
                    }
                    return 0;
                }
            } else if (Objects.equals(fieldForPlayer2[row][col], "~") && !Objects.equals(gameField2[row][col], "X")) {
                gameField2[row][col] = "M";
                fieldForPlayer2[row][col] = "M";
                printFieldBoth(gameField2, fieldForPlayer1);
                System.out.println("You missed!\nPress Enter and pass the move to another player\n");
                scanner.nextLine();
            } else {
                gameField2[row][col] = "X";
                fieldForPlayer2[row][col] = "X";
                printFieldBoth(gameField2, fieldForPlayer1);
                System.out.println("Error! You already hit that ship previously!\nPress Enter and pass the move to another player");
                scanner.nextLine();
            }
        } else {
            if (Objects.equals(fieldForPlayer1[row][col], "O") && !Objects.equals(gameField1[row][col], "X")) {
                gameField1[row][col] = "X";
                fieldForPlayer1[row][col] = "X";
                printFieldBoth(gameField1, fieldForPlayer2);
                if (checkIfShipIsSunk(fieldForPlayer1, row, col, -1, -1)) {
                    if (currentPlayer.getA() == 1) {
                        currentPlayer.setA(2);
                    } else {
                        currentPlayer.setA(1);
                    }
                    return 1;
                } else {
                    System.out.println("You hit a ship!\nPress Enter and pass the move to another player");
                    scanner.nextLine();
                    if (currentPlayer.getA() == 1) {
                        currentPlayer.setA(2);
                    } else {
                        currentPlayer.setA(1);
                    }
                    return 0;
                }
            } else if (Objects.equals(fieldForPlayer1[row][col], "~") && !Objects.equals(gameField1[row][col], "X")) {
                gameField1[row][col] = "M";
                fieldForPlayer1[row][col] = "M";
                printFieldBoth(gameField1, fieldForPlayer2);
                System.out.println("You missed!\nPress Enter and pass the move to another player");
                scanner.nextLine();
            } else {
                gameField1[row][col] = "X";
                fieldForPlayer1[row][col] = "X";
                printFieldBoth(gameField1, fieldForPlayer2);
                System.out.println("Error! You already hit that ship previously!\nPress Enter and pass the move to another player");
                scanner.nextLine();
            }
        }
        if (currentPlayer.getA() == 1) {
            currentPlayer.setA(2);
        } else {
            currentPlayer.setA(1);
        }
        return 0;
    }

    private static boolean checkIfShipIsSunk(String[][] field, int row, int col, int prevRow, int prevCol) {
        if (row == 10 && col == 10) {
            if (Objects.equals(field[row][col - 1], "X") && col - 1 != prevCol) {
                if (!checkIfShipIsSunk(field, row, col-1, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row - 1][col], "X") && row - 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row - 1, col, row, col)){
                    return false;
                }
            }
        }
        else if (row == 10) {
            if (Objects.equals(field[row][col - 1], "X") && col - 1 != prevCol) {
                if (!checkIfShipIsSunk(field, row, col-1, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row - 1][col], "X") && row - 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row - 1, col, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row][col + 1], "X") && col + 1 != prevCol) {
                if (!checkIfShipIsSunk(field, row, col+1, row, col)) {
                    return false;
                }
            }
        } else if (col == 10) {
            if (Objects.equals(field[row][col - 1], "X") && col - 1 != prevCol) {
                if (!checkIfShipIsSunk(field, row, col-1, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row - 1][col], "X") && row - 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row - 1, col, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row + 1][col], "X") && row + 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row + 1, col, row, col)) {
                    return false;
                }
            }
        } else {
            if (Objects.equals(field[row][col - 1], "X") && col - 1 != prevCol) {
                if (!checkIfShipIsSunk(field, row, col-1, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row - 1][col], "X") && row - 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row - 1, col, row, col)){
                    return false;
                }
            }
            if (Objects.equals(field[row + 1][col], "X") && row + 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row + 1, col, row, col)) {
                    return false;
                }
            }
            if (Objects.equals(field[row + 1][col + 1], "X") && row + 1 != prevRow) {
                if (!checkIfShipIsSunk(field, row + 1, col + 1, row, col)) {
                    return false;
                }
            }
        }
        if (row == 10 && col == 10)
            return  !(Objects.equals(field[row][col - 1], "O") || Objects.equals(field[row - 1][col], "O"));
        if (row == 10)
            return !(Objects.equals(field[row][col + 1], "O") || Objects.equals(field[row][col - 1], "O") || Objects.equals(field[row - 1][col], "O"));
        if (col == 10)
            return !(Objects.equals(field[row + 1][col], "O") || Objects.equals(field[row][col - 1], "O") || Objects.equals(field[row - 1][col], "O"));

        return ((!Objects.equals(field[row][col + 1], "O") || col + 1 == prevCol) && (!Objects.equals(field[row][col - 1], "O") || col - 1 == prevCol) && (!Objects.equals(field[row + 1][col], "O") || row + 1 == prevRow) && (!Objects.equals(field[row - 1][col], "O") || row - 1 == prevRow));
    }

    private static boolean checkIndividualCoordinates(String coordinate) {
        if (Integer.parseInt(coordinate.substring(1)) < 1 || Integer.parseInt(coordinate.substring(1)) > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }

        if (coordinate.charAt(0) < 'A' || coordinate.charAt(0) > 'J') {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }
        return true;
    }

    private static void printField(String[][] field) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void generateNewField(String[][] field) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0 && j == 0) {
                    field[i][j] = " ";
                } else if (i == 0) {
                    field[0][j] = Integer.toString(j);
                } else if (j == 0) {
                    field[i][0] = String.valueOf((char) (i + 64));
                } else {
                    field[i][j] = "~";
                }
            }
        }
    }

    private static void placePlane(String[][] field, String type, int length) {
        while (true) {
            System.out.println("Enter the coordinates of the " + type + " (" + length + " cells):\n");
            String from = scanner.next();
            String to = scanner.next();
            System.out.println();
            if (checkCoordinatesForPlacingShips(from, to, type, length) && checkForOtherShips(field, from, to)) {
                markCoordinates(field, from, to);
                printField(field);
                break;
            }
        }
    }

    private static void markCoordinates(String[][] field, String from, String to) {
        if (from.charAt(0) == to.charAt(0)) {
            int row = from.charAt(0) - 64;
            if (Integer.parseInt(from.substring(1)) > Integer.parseInt(to.substring(1))) {
                for (int i = Integer.parseInt(to.substring(1)); i <= Integer.parseInt(from.substring(1)); i++) {
                    field[row][i] = "O";
                }
            } else {
                for (int i = Integer.parseInt(from.substring(1)); i <= Integer.parseInt(to.substring(1)); i++) {
                    field[row][i] = "O";
                }
            }
        } else {
            int row = Integer.parseInt(from.substring(1));
            if (from.charAt(0) > to.charAt(0)) {
                for (int i = to.charAt(0) - 64; i <= from.charAt(0) - 64; i++) {
                    field[i][row] = "O";
                }
            } else {
                for (int i = from.charAt(0) - 64; i <= to.charAt(0) - 64; i++) {
                    field[i][row] = "O";
                }
            }
        }
    }

    private static boolean checkForOtherShips(String[][] field, String from, String to) {
        if (from.charAt(0) == to.charAt(0)) {
            int row = from.charAt(0) - 64;
            if (Integer.parseInt(from.substring(1)) > Integer.parseInt(to.substring(1))) {
                for (int i = Integer.parseInt(to.substring(1)); i <= Integer.parseInt(from.substring(1)); i++) {
                    if (row != 10 && i != 10) {
                        if (Objects.equals(field[row - 1][i], "O") || Objects.equals(field[row][i - 1], "O") || Objects.equals(field[row][i + 1], "O") || Objects.equals(field[row + 1][i], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (i == 10 && row == 10) {
                        if (field[row - 1][i].equals("O") || field[row][i - 1].equals("O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (row == 10) {
                        if (field[row - 1][i].equals("O") || field[row][i - 1].equals("O") || field[row][i + 1].equals("O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else {
                        if (field[row - 1][i].equals("O") || Objects.equals(field[row][i - 1], "O") || field[row + 1][i].equals("O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    }
                }
            } else {
                for (int i = Integer.parseInt(from.substring(1)); i <= Integer.parseInt(to.substring(1)); i++) {
                    if (row != 10 && i != 10) {
                        if (Objects.equals(field[row - 1][i], "O") || Objects.equals(field[row][i - 1], "O") || Objects.equals(field[row][i + 1], "O") || Objects.equals(field[row + 1][i], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (i == 10 && row == 10) {
                        if (Objects.equals(field[row - 1][i], "O") || Objects.equals(field[row][i - 1], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (row == 10) {
                        if (Objects.equals(field[row - 1][i], "O") || Objects.equals(field[row][i - 1], "O") || Objects.equals(field[row][i + 1], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else {
                        if (Objects.equals(field[row - 1][i], "O") || Objects.equals(field[row][i - 1], "O") || Objects.equals(field[row + 1][i], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    }
                }
            }
        } else {
            int row = Integer.parseInt(from.substring(1));
            if (from.charAt(0) > to.charAt(0)) {
                for (int i = to.charAt(0) - 64; i <= from.charAt(0) - 64; i++) {
                    if (row != 10 && i != 10) {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O") || Objects.equals(field[i + 1][row], "O") || Objects.equals(field[i][row + 1], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (i == 10 && row == 10) {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (i == 10) {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O") || Objects.equals(field[i + 1][row], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O") || Objects.equals(field[i][row + 1], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    }
                }
            } else {
                for (int i = from.charAt(0) - 64; i <= to.charAt(0) - 64; i++) {
                    if (row != 10 && i != 10) {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O") || Objects.equals(field[i + 1][row], "O") || Objects.equals(field[i][row + 1], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (i == 10 && row == 10) {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else if (i == 10) {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O") || Objects.equals(field[i][row + 1], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    } else {
                        if (Objects.equals(field[i][row - 1], "O") || Objects.equals(field[i - 1][row], "O") || Objects.equals(field[i + 1][row], "O")) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }



    private static boolean checkCoordinatesForPlacingShips(String from, String to, String type, int length) {
        if (Math.abs(from.charAt(0) - to.charAt(0)) + 1 != length && Math.abs(Integer.parseInt(from.substring(1)) - Integer.parseInt(to.substring(1))) + 1 != length) {
            System.out.println("Error! Wrong length of the " + type + "! Try again:\n");
            return false;
        }

        if (Integer.parseInt(from.substring(1)) < 1 || Integer.parseInt(from.substring(1)) > 10 || Integer.parseInt(to.substring(1)) > 10 || Integer.parseInt(to.substring(1)) < 1) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }

        if (from.charAt(0) != to.charAt(0) && Integer.parseInt(from.substring(1)) != Integer.parseInt(to.substring(1)) || from.charAt(0) < 'A' || from.charAt(0) > 'J' || to.charAt(0) < 'A' && to.charAt(0) > 'J') {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }
        return true;
    }
}
