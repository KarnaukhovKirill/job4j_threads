package ru.job4j.store;

import java.util.Objects;

/**
 * Модель данных
 * @author Karnaukhov Kirill
 * @version beta
 */
public class User {
    /**
     * Уникальный номер
     */
    private final int id;
    /**
     * Кол-во денежных средств
     */
    private int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && amount == user.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
