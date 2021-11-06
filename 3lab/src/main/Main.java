package main;

import managers.ConsoleManager;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        start();
    }

    public void start() {
        ConsoleManager reader = new ConsoleManager();
        reader.start();
    }

}
