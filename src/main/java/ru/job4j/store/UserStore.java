package ru.job4j.store;

import net.jcip.annotations.ThreadSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Karnaukhov Kirill
 * @version beta
 */
@ThreadSafe
public class UserStore implements Store<User> {
    private final ConcurrentHashMap<Integer, User> store = new ConcurrentHashMap<>();

    /**
     * Этот метод сделал не synchronized, так как putIfAbsent атомарная операция, Id у user final.
     * @param user новый юзер
     * @return возвращает true / false в зависимости от того, есть ли user с таким Id
     */
    @Override
    public boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    /**
     * Обновление
     * @param user обновлённый user
     * @return результат удаления старого user на обновлённый
     */
    @Override
    public synchronized boolean update(User user) {
        return store.replace(user.getId(), store.get(user.getId()), user);
    }

    /**
     * Удаление user
     * @param user удаляемый user
     * @return результат удаления. true / false
     */
    @Override
    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    /**
     * Данный метод осуществляет денежный перевод с одного аккаунта на другой по id
     * @param fromId id user откуда списать денежный средства
     * @param toId id user куда нужно перечислить денежные средства
     * @param amount кол-во денежных средств для перевода
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        var fromUser = store.get(fromId);
        var toUser = store.get(toId);
        if (fromUser == null) {
            throw new IllegalArgumentException(String.format("Payer with Id = %s don't exist", fromId));
        }
        if (toUser == null) {
            throw new IllegalArgumentException(String.format("Payment receiver with Id = %s don't exist", toId));
        }
        if (fromUser.getAmount() <= amount) {
            throw new IllegalArgumentException("Payer's account got not enough money");
        }
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
        return true;
    }

    /**
     * @return возвращает копию всех user в store
     */
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
