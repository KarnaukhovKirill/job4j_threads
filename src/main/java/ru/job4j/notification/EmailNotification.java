package ru.job4j.notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Сервис для рассылки почти
 */
public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Метод должен отправлять почту через ExecutorService
     * @param user пользователь с email
     */
    public void emailTo(User user) {
        var email = user.getEmail();
        var username = user.getUsername();
        var subject = String.format("Notification %s to email %s", username, email);
        var body = String.format("Add a new event to %s", email);
        pool.submit(() -> send(subject, body, email));
    }

    /**
     * Метод закрывает пул и ждёт пока завершаться все задачи
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод отправляет email
     * @param subject заголовок email
     * @param body текст email
     * @param email email конкретного {@link User}
     */
    public void send(String subject, String body, String email) {
    }
}
