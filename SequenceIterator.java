import org.apache.commons.lang3.tuple.Pair;
import java.util.*;

public class SequenceIterator<T extends Comparable<T>> {
    private ArrayList<Iterator<T>> unmergedIterators;
    private PriorityQueue<Pair<Integer, T>> nextValues;

    public SequenceIterator(Collection<Iterator<T>> inputs) {
        this.nextValues = new PriorityQueue<>(Comparator.comparing(Pair::getValue));
        this.unmergedIterators = new ArrayList<>(inputs);

        this.unmergedIterators.forEach(iterator -> {
            if (iterator.hasNext()) {
                nextValues.add(Pair.of(this.unmergedIterators.indexOf(iterator), iterator.next()));
            }
        });
    }

    public boolean hasNext() {
        return nextValues.size() > 0;
    }

    public T next() {
        Pair<Integer, T> pair = nextValues.poll();

        if (pair == null) {
            throw new NoSuchElementException();
        }

        if (this.unmergedIterators.get(pair.getKey()).hasNext()) {
            this.nextValues.add(Pair.of(pair.getKey(), this.unmergedIterators.get(pair.getKey()).next()));
        }

        return pair.getValue();
    }
}
