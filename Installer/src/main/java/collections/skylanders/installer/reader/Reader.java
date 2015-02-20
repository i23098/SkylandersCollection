package collections.skylanders.installer.reader;

import java.util.Iterator;

public interface Reader<E extends Object> extends Iterable<E>, Iterator<E> {
    boolean hasNext();
    public E next();
}
