package sorters.algorithms;

public class QuickSort extends Sorting{

    public QuickSort(int[] array){
        super(array);
        setName("Quick Sort");
    }

    /* This function takes last element as pivot,
     places the pivot element at its correct
     position in sorted array, and places all
     smaller (smaller than pivot) to left of
     pivot and all greater elements to right
     of pivot */
    private int partition(int low, int high)
    {
        int pivot = array[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (array[j] <= pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                swap(i, j);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        swap(i+1, high);

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    private void reverseSort(int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(low, high);

            // Recursively sort elements before
            // partition and after partition
            reverseSort(low, pi-1);
            reverseSort(pi+1, high);
        }
    }

    @Override
    public void sort() {
        reverseSort(0, len-1);
    }
}
