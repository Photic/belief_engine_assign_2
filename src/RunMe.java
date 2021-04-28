import java.util.Scanner;

public class RunMe {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        String line = "";

        while (!line.equals("q")) {
            line = input(scan);

        }

        System.out.println("Agent have Exited");
    }

    public static String input(Scanner scan) {
        System.out.print("Give input to agent: ");
        return scan.nextLine();
    }
}

