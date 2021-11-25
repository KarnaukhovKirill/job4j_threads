package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String out;

    public Wget(String url, int speed, String out) {
        this.url = url;
        this.speed = speed;
        this.out = out;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(out)) {
            byte[] dataBuffer = new byte[speed];
            int byteRead;
            long bytesWrited = 0;
            long deltaTime;
            var startTime = System.currentTimeMillis();
            while ((byteRead = in.read(dataBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dataBuffer, 0, byteRead);
                bytesWrited += byteRead;
                if (bytesWrited >= speed) {
                    deltaTime = System.currentTimeMillis() - startTime;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                    bytesWrited = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]) * 1024 * 1024;
        String out = args[2];
        Thread wget = new Thread(new Wget(url, speed, out));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Напишите аргументы в формате: url speed target,"
                    + System.lineSeparator()
                    + "где url - целевая страница для парсинга" + System.lineSeparator()
                    + "speed - ограничение скорости скачивания, Мбайт/с" + System.lineSeparator()
                    + "target - целевой файл для сохранения");
        }
        if (args[1].length() > 100) {
            throw new IllegalArgumentException("Единицы измерения второго параметра speed - МБайт/с");
        }
    }
}
