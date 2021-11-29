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
        if (!store.containsKey(fromId)) {
            throw new IllegalArgumentException("Payer dont exist");
        }
        if (!store.containsKey(toId)) {
            throw new IllegalArgumentException("Payment reciever dont exist");
        }
        var fromUser = store.get(fromId);
        if (fromUser.getAmount() <= amount) {
            throw new IllegalArgumentException("Payer's account got not enough money");
        }
        var toUser = store.get(toId);
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
        return update(fromUser) && update(toUser);
    }

    /**
     * @return возвращает копию всех user в store
     */
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
