package sorters;

public class BubbleSort extends Sorting {
    public BubbleSort(int[] array){
        super(array);
        setName("Bubble Sort");
    }
    @Override
    public void sort() {
        for (int i = 0; i < len; i++) {
            for (int j = 1; j < (len - i); j++) {
                if (array[j - 1] > array[j]) {
                    //swap elements
                    swap(j, j - 1);
                }

            }
        }
    }
}