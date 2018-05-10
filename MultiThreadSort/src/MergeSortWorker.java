public class MergeSortWorker extends Thread {
    private int[] numbers;
    private int[] helper;

    private int number;
    private int indexBegin;
    private int indexEnd;

    public MergeSortWorker(int[] numbers, int indexBegin, int indexEnd) {
        this.numbers = numbers;
        this.indexBegin = indexBegin;
        this.indexEnd = indexEnd;
    }

    public void sort() {
        number = numbers.length;
        this.helper = new int[number];
        mergesort(indexBegin, indexEnd);
    }

    private void mergesort(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergesort(low, middle);
            mergesort(middle + 1, high);
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {

        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
    }

    public int[] getInternal() {
        return numbers;
    }

    public int getSize() {
        return indexEnd + 1 - indexBegin;
    }

    public int getFirst() {
        return numbers[indexBegin];
    }

    public int extractFirst() {
        indexBegin++;
        return numbers[indexBegin - 1];
    }

    @Override
    public void run() {
        sort();
    }
}