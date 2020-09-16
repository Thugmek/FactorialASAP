import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Výpočet faktoriálu je založen na předpokladu, že násobení celých čísel je operace komutativní (a*b=b*a)
 * a asociativní ((a*b)*c=a*(b*c)). Násobení lze podle těchto principů možno rozdělit do více vláken, které spolu
 * pronásobí každé část intervalu <1;n>. Poté se výsledky se spolu vynásobí výsledky každého z vláken, což bude
 * matematicky ekvivalntní, jako by se postupně vynásobili vśechny prvky intervalu <1;n>.
 *
 * Další z optimalizací staví na předpokladu, že n! = (n-1)!*n. Pokud tedy známe libovolný faktoriá m!, kde m<n, můžeme
 * i při výpočtu n! uśetřit značnou část počítání.
 *
 * Faktoriály jsou již od 20! tak velká čísla, že datový typ long kapacitně neostačuje a double by způsoboval ztrátu
 * přesnosti. Je proto nutné využít datový typ BigInteger. Tento datový typ je omezen pouze velikostí operační paměti.
 * Nevýhodou je, že s tak velkým objemem dat nedokáže ALU přímo pracovat. Výpočet se tak stáva softwarovou záležitostí
 * a je výpočetně poměrně náročný.
 *
 * Návrh na optimalizaci: Pokud bychom každému vlkáknu předali je tolik ćísel, aby se výsledek vešel do datového typu
 * long, výpočet by nebyl bottleneckován rychlostí operační paměti. Bohužel si nejsem jist, že jsem toto se svými 2mi
 * semestry matematiky schopný vypočítat.
 *
 * */

public class Factorial {
    private int threads;
    private Map<Integer, BigInteger> results = new TreeMap<>();

    public Factorial(int threads){
        this.threads = threads;
        results.put(1,new BigInteger("1")); //1! = 1. Toto se hodí, ušetří to pár if statementů.
    }

    public BigInteger calculate(int a){
        ArrayList<ThreadMult> threadList = new ArrayList<>();   //Seznam všech vláken výpočtu

        //Nalezení největšího výsledku, který je ale nižší než a. Není vždy třeba počítat od začátku.
        int maxSmaller = 0;
        for (int i:results.keySet()) {
            if(i <= a && i> maxSmaller){
                if(a == i){
                    return results.get(a); //pokud byl už výsledek nalezen, hned ho vrátím.
                }
                maxSmaller = i;
            }
        }

        double interval = (double)(a-maxSmaller)/threads; //velikost intervalu pro každé vlákno

        for(int i =0;i< threads;i++){
            int from = (int)(maxSmaller + i*interval + 1);
            int to = (int)(maxSmaller + (i+1)*interval + 1);
            if(i == threads-1)  //Vzhledem k k faktu, že počet čísel k pronásobení nemusí být dělitelný počtem vláken
                to = a+1;       //je poslednímu vláknu předán interval zkorigovaný touto podmínkou
            ThreadMult t = new ThreadMult(from,to);
            t.start();
            threadList.add(t);
        }


        BigInteger result = results.get(maxSmaller);
        //vyčkání na všechna vlákna a přinásobení jejich výsledku.
        for(ThreadMult t:threadList){
            try {
                t.join();
                result = result.multiply(t.getResult());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        results.put(a,result);  //uložení mezi výsledky pro pozdější použití.
        return result;
    }


}
