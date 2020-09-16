import java.math.BigInteger;
/**
 * Test výpočtu faktoriálu. Test je postaven na předpokladu, že n!/(n-1)! = n.
 * Testovací rutina postupně prochází různá n a pokud pro některé n nebude platit výše uvedená rovnice, přeruší test
 * a oznámí chybu. Test pokaždé vytváří novou instanci tŕídy Factorial, aby smazal dříve vapočítané hodnoty. Jednak se
 * ukázalo, že 16GB ram neni dost pro uložení všech faktorialů od 0 do 100000 (dle zadání si program vśechny výsledky
 * ukládá do mapy), jednak by prakticky nedocházelo k vícevláknovému výpočtu, jelikož by se poze přinásobovala jedna
 * hodnota. Test je časově velice náročný, jelikož i výpočet samotný je pro velká n relativně náročný. Domnívám se, že
 * je to způsobeno pomalým pŕístupem do paměti RAM. Čísla jsou tak velká, že se nevejdou ani do cashe procesoru.
 * Nápad na výrazné zlepšení využití procesoru je popsán ve třídě Factorial. Dal jsem tomu ten čas a nechal jsem běžet
 * test do 100000. Chyba nenastala ani jednou :)
 * */
public class Test {
    public static void main(String[] args) {
        for(int i = 1;i<10000;i++){
            Factorial f = new Factorial(8);
            Factorial f1 = new Factorial(8);
            BigInteger n = f.calculate(i);
            BigInteger n1 = f1.calculate(i+1);
            BigInteger res = n1.divide(n);
            System.out.println(res.intValue());
            if(res.intValue() != i+1){
                System.out.println(String.format("Failed at %d",i));
                return;
            }
        }
        System.out.println("Success");
    }
}
