package collections.skylanders.installer.reader;

public class Game {
    public final String name;
    public final String title;
    public final CategoryReader categoryReader;
    
    public Game(String name, String title, CategoryReader categoryReader) {
        this.name = name;
        this.title = title;
        this.categoryReader = categoryReader;
    }
}
