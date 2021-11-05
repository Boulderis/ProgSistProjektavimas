package managers;

import java.util.Scanner;

public class ConsoleManager {

    private Scanner scanner = new Scanner(System.in);
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
        Integer number;
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
        switch(number) {
            case 1:
                requestManager.printUsers();
                break;
            case 2:
                requestManager.printUser();
                break;
            case 3:
                requestManager.addUser();
                break;
            case 4:
                requestManager.updateUser();
                break;
            case 5:
                requestManager.deleteUser();
                break;
            default:
                System.out.println("Kodo vykdymo logika, įvedus šį skaičių, nėra aprašyta!");
                System.exit(0);
                break;
        }
    }

    private void printIfInputIsInvalid(String instructionMessage) {
        System.out.println("Netinkama įvestis.");
        System.out.println(instructionMessage);
    }

}
