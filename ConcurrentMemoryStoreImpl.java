import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMemoryStoreImpl implements ConcurrentMemoryStore {
    private ConcurrentHashMap<String, Item> itemsStorage;

    public ConcurrentMemoryStoreImpl() {
        this.itemsStorage = new ConcurrentHashMap<>();
    }

    @Override
    public void store(String key, Item item) throws IllegalArgumentException {
        this.itemsStorage.compute(key, (k,v) -> {
            if (v != null || item == null || key == null) {
                throw new IllegalArgumentException();
            }
            return item;
        });
    }

    @Override
    public void update(String key, int value1, int value2) {
        this.itemsStorage.compute(key, (k, i) -> {
            Item item = i != null ? i : new Item();
            item.setValue1(value1);
            item.setValue2(value2);
            return item;
        });
    }

    @Override
    public Iterator<Item> valueIterator() {
        return this.itemsStorage.values().iterator();
    }

    @Override
    public void remove(String key) {
        this.itemsStorage.compute(key, (k,v) -> null);
    }
}
