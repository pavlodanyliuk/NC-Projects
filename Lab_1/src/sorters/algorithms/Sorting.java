package sorters.algorithms;

public abstract class Sorting {
    protected int[] array;
    protected int len;
    protected static String name;

    public Sorting (int[] array){
        len = array.length;
        this.array = new int[len];
        for(int i = 0; i < array.length; i++){
            this.array[i] = array[i];
        }
        name = "";

    }
    protected void swap(int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        str.append(name + ":" +'\n');
        str.append("[ ");
        for(int i : array){
            str.append(i + " ");
        }
        str.append("]");
        return str.toString();
    }

    public int getLen() {
        return len;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
        len = array.length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void sort();

}
