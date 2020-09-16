import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Factorial f = new Factorial(8);
        Scanner sc = new Scanner(System.in);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", false));
            System.out.println("Zadejte cestu k souboru se vstupními daty: ");
            File inputFile = new File(sc.nextLine());
            Scanner inputReader = new Scanner(inputFile);
            while (inputReader.hasNextLine()) {
                Arrays.stream(inputReader.nextLine().split("[,. ]")).forEach(str ->{
                    try {
                        if(str.length() > 0){
                            writer.append(str + "!=");
                            writer.append(f.calculate(Integer.parseInt(str)).toString());
                            writer.append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            inputReader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
