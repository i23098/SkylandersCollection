package collections.skylanders.installer.reader;

public interface Reader<E extends Object> {
    boolean hasNext();
    public E next();
}
