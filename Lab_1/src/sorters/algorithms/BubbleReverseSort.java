package sorters.algorithms;

public class BubbleReverseSort extends BubbleSort {
    public BubbleReverseSort(int[] array){
        super(array);
        setName("Bubble Reverse Sort");
    }

    @Override
    public void sort() {
        for (int i = 0; i < len ; i++) {
            for (int j = len - 2; j > i - 1; j--) {
                if (array[j + 1] < array[j]) {
                    //swap elements
                    swap(j, j + 1);
                }

            }
        }
    }
}
