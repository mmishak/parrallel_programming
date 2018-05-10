import java.util.Arrays;

public class Main {
    private static final int NUM_OF_NUMBERS = 19_999_999;
    private static final boolean NEED_PRINT_ARRAYS = false;

    public static void main(String[] args) {
        int[] array = Util.getRandIntArray(NUM_OF_NUMBERS);
        System.out.println("Array size: " + array.length + "\n");

        MergeSorter sorter = new MergeSorter(NEED_PRINT_ARRAYS);

        sorter.sort(Arrays.copyOf(array, array.length), 1);
        sorter.sort(Arrays.copyOf(array, array.length), 2);
        sorter.sort(Arrays.copyOf(array, array.length), 3);
        sorter.sort(Arrays.copyOf(array, array.length), 4);
        sorter.sort(Arrays.copyOf(array, array.length), 5);
        sorter.sort(Arrays.copyOf(array, array.length), 6);
        sorter.sort(Arrays.copyOf(array, array.length), 7);
        sorter.sort(Arrays.copyOf(array, array.length), 8);
    }
}
