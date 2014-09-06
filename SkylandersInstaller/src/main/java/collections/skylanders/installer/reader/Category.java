package collections.skylanders.installer.reader;

public class Category {
    public final String name;
    public final ItemReader itemReader;
    
    public Category(String name, ItemReader itemReader) {
        this.name = name;
        this.itemReader = itemReader;
    }
}
