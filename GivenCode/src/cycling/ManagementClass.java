package cycling;

import java.util.Arrays;

public class ManagementClass<T> {
    private T[] array;

    public ManagementClass(T[] array) {
        this.array = array;
    }

    public int countItems() {
        return array.length;
    }

    public void printArray() {
        System.out.println(Arrays.toString(array));
    }

    public void addElement(T element) {
        T[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = element;
        array = newArray;
    }

    public void deleteElement(int index) {
        if (index >= 0 && index < array.length) {
            T[] newArray = Arrays.copyOf(array, array.length - 1);
            System.arraycopy(array, 0, newArray, 0, index);
            System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
            array = newArray;
        } else {
            System.out.println("Index out of bounds.");
        }
    }

    public static void main(String[] args) {
        String[] intArray = {"5","hdf","hdcv"};
        ManagementClass<String> genericIntArray = new ManagementClass<>(intArray);

        System.out.println("Number of items: " + genericIntArray.countItems());
        genericIntArray.printArray();
        genericIntArray.addElement("6");
        System.out.println("After adding an element:");
        genericIntArray.printArray();
        genericIntArray.deleteElement(2);
        System.out.println("After deleting an element:");
        genericIntArray.printArray();
    }
}
