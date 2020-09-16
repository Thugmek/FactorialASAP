import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        /**
         * Zde lze nastavit počet výpočetních vláken. Mám 8 jader procerou, proto 8. Zkoušel jsem i 4, 16 a 32, ale 8
         * je na mém hardwaru výrazně nejrychlejší volba. Procesro je vytěžován na 25%, bottleneck jerychlost operační
         * paměťi :/
         */
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
            System.out.println("Výsledek uložen do output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
