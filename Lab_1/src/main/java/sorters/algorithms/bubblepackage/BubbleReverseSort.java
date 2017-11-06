package sorters.algorithms.bubblepackage;

public class BubbleReverseSort extends BubbleSort {
    public BubbleReverseSort(int[] array){
        super(array);
        setName("Bubble Reverse Sort");
    }

    @Override
    protected void check(int j) {
        if (array[len - j] < array[len - j - 1]) {
            //swap elements
            swap(len - j, len - j - 1);
        }
    }
}
