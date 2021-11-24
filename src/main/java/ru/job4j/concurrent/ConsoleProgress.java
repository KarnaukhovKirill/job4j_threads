package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[] {'-', '\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char c : process) {
                    System.out.print("\rload : " + c);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
        System.out.println(System.lineSeparator() + "Done");
    }
}
