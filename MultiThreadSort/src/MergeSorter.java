import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class MergeSorter implements Sorter{

    private long sortTime = 0;
    private List<MergeSortWorker> workers = new ArrayList<>();

    public MergeSorter() {
        this.needPrintArrays = false;
    }

    public MergeSorter(boolean needPrintArrays) {
        this.needPrintArrays = needPrintArrays;
    }

    private boolean needPrintArrays;

    @Override
    public void sort(int[] array) {
        sort(array, 1);
    }

    @Override
    public void sort(int[] array, int numOfThreads) {
        if (needPrintArrays)
            System.out.println("Source: " + Arrays.toString(array));
        System.out.println("Sorting start...");
        System.out.println("Num of threads: " + numOfThreads);

        createWorkers(Arrays.copyOf(array, array.length), numOfThreads);

        long startTime = System.currentTimeMillis();
        runAllWorkers();

        try {
            joinAllWorkers();
            finalMerge(array);

        } catch (InterruptedException e) {
            System.err.println("Sort interrupted.");
            return;
        } catch (WorkersNotFoundException e) {
            e.printStackTrace();
            return;
        }

        long finishTime = System.currentTimeMillis();
        sortTime = finishTime - startTime;

        printResult(array);
    }

    private void printResult(int[] array) {
        System.out.println("Sorting time: " + sortTime + " milliseconds");
        if (needPrintArrays)
            System.out.println("Result: " + Arrays.toString(array));
        System.out.println();
    }

    private void createWorkers(int[] array, int n) {
        workers.clear();

        if (array.length < n)
            n = array.length;

        int subArrayLength = array.length / n;
        int beginIndex = 0;
        int endIndex = Math.min(beginIndex + subArrayLength, array.length);

        while (endIndex <= array.length){
            workers.add(new MergeSortWorker(array, beginIndex, endIndex - 1));
            beginIndex = endIndex;
            endIndex = beginIndex + subArrayLength;

            if (Math.abs(array.length - endIndex) < subArrayLength) {
                endIndex = array.length;
            }
        }
    }

    private void runAllWorkers() {
        for (MergeSortWorker worker : workers) {
            worker.start();
        }
    }

    private void joinAllWorkers() throws InterruptedException {
        for (MergeSortWorker worker : workers) {
            worker.join();
        }
    }

    private void finalMerge(int[] result) throws WorkersNotFoundException {
        for (int i = 0; i < result.length; i++) {
            result[i] = mergeMin();
        }
    }

    private int mergeMin() throws WorkersNotFoundException {
        if (workers.size() < 1)
            throw new WorkersNotFoundException("Sort worker not found.");

        int min = 0;
        MergeSortWorker selectedWorker = null;
        for (MergeSortWorker worker : workers) {
            if (worker.getSize() > 0)
                if (selectedWorker != null) {
                    if (min > worker.getFirst()){
                        min = worker.getFirst();
                        selectedWorker = worker;
                    }
                } else {
                    min = worker.getFirst();
                    selectedWorker = worker;
                }
        }

        if (selectedWorker != null) {
            return selectedWorker.extractFirst();
        } else
            throw new WorkersNotFoundException("Sort worker not found.");
    }

    public long getSortTime() {
        return sortTime;
    }

    public double getSortTimeSeconds() {
        return ((double) sortTime) / 1000;
    }

    private class WorkersNotFoundException extends Exception {
        public WorkersNotFoundException(String message) {
            super(message);
        }
    }
}
