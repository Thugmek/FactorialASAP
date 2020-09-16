import java.math.BigInteger;

/**
 * Vlákno násobící část intervalu <1;n>. Kód je snad sebepopisující.
 * Hodnota "from" se do výpočtu započítává, hodnota "to" nikoliv
 */
public class ThreadMult extends Thread{
    private int from;
    private int to;
    private BigInteger result = new BigInteger("1");

    public ThreadMult(int from, int to){
        this.from = from;
        this.to = to;
    }
    public void run() {
        for(int i = from;i < to;i++){
            result = result.multiply(BigInteger.valueOf(i));
        }
    }
    public BigInteger getResult(){
        return result;
    }
}
