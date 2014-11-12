package collections.skylanders.installer.reader.entry;

import collections.skylanders.Game;
import collections.skylanders.installer.reader.Reader;

public class GameEntry {
    public final Game game;
    public final String title;
    public final Reader<ObjectEntry> objectReader;
    
    public GameEntry(Game game, String title, Reader<ObjectEntry> objectReader) {
        this.game = game;
        this.title = title;
        this.objectReader = objectReader;
    }
}
