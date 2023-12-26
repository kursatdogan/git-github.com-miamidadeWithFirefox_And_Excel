package scott.utils;

public class SingletonInteger {
    private static SingletonInteger instance;
    private int value;

    private SingletonInteger(int initialValue) {
        this.value = initialValue;
    }

    public static SingletonInteger getInstance(int initialValue) {
        if (instance == null) {
            instance = new SingletonInteger(initialValue);
        }
        return instance;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}