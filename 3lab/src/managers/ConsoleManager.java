package managers;

import java.util.Scanner;

public class ConsoleManager {

    private final Scanner scanner = new Scanner(System.in);
    private UserRequestManager requestManager;

    public void start() {
        String datafilePath = readDatafilePath();
        requestManager = new UserRequestManager(datafilePath, scanner);
        int number = readActionNumber();
        parseActionNumber(number);
    }

    private String readDatafilePath() {
        System.out.println("Prašome įvesti kelią iki ir pavadinimą failo, kuriame yra (arba bus) vartotojų duomenis: ");
        return scanner.nextLine();
    }

    private int readActionNumber() {
        String message = """
         Prašome įvesti atitinkamą skaičių:
         1 - peržiūrėti esamus vartotojus.
         2 - peržiūrėti vartotoją pagal jo ID.
         3 - įvesti naują vartotoją.
         4 - pakeisti vartotojo duomenis, pagal jo ID.
         5 - ištrinti vartotoją, pagal jo ID.
         """;
        System.out.println(message);
        String input;
        int number;
        while(true) {
            input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                if(number < 1 || number > 5) {
                    printIfInputIsInvalid(message);
                }
                else break;
            } catch(NumberFormatException e) {
                printIfInputIsInvalid(message);
            }
        }
        return number;
    }

    private void parseActionNumber(int number) {
        switch (number) {
            case 1 -> requestManager.printUsers();
            case 2 -> requestManager.printUser();
            case 3 -> requestManager.addUser();
            case 4 -> requestManager.updateUser();
            case 5 -> requestManager.deleteUser();
            default -> {
                System.err.println("Kodo vykdymo logika, įvedus šį skaičių, nėra aprašyta!");
                System.exit(0);
            }
        }
    }

    private void printIfInputIsInvalid(String instructionMessage) {
        System.out.println("Netinkama įvestis.");
        System.out.println(instructionMessage);
    }

}
