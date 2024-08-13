package main.java.utils;

public class Log {
    public static boolean DEBUG = false;
    public static boolean INFO = false;

    public static void d(String msg) {
        if (DEBUG) {
            System.out.println("DEBUG: " + msg);
        }
    }

    public static void e(String msg) {
        System.err.println("ERROR: " + msg);
    }

    public static void i(String msg) {
        if (INFO) {
            System.out.println("INFO: " + msg);
        }
    }

    public static void w(String msg) {
        System.out.println("WARNING: " + msg);
    }
}