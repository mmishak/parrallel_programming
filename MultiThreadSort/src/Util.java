import java.util.Random;

final public class Util {
    private Util() {
        throw new AssertionError();
    }

    public static int[] getRandIntArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(1000);
        }
        return arr;
    }
}
