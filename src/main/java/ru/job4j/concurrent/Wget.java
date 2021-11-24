package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        new Thread(new Load()).start();
    }


    private static class Load implements Runnable {
        @Override
        public void run() {
            try {
                for (int index = 0; index <= 100; index++) {
                    System.out.print("\rLoading : " + index + "%");
                    Thread.sleep(1000);
            }
                System.out.println(System.lineSeparator() + "Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
