package collections.skylanders.installer.reader.xml;

import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import collections.skylanders.Game;
import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.GameEntry;

public class GameXmlReader implements Reader<GameEntry> {
    private final NodeList gamesNodeList;
    int curGame;
    
    public GameXmlReader(Document xmlDocument) {
        this(xmlDocument.getElementsByTagName("game"));
    }
    private GameXmlReader(NodeList gamesNodeList) {
        this.gamesNodeList = gamesNodeList;
        curGame = 0;
    }
    
    @Override
    public Iterator<GameEntry> iterator() {
        return new GameXmlReader(gamesNodeList);
    }
    
    public boolean hasNext() {
        return curGame < gamesNodeList.getLength();
    }
    
    public GameEntry next() {
        Element gameNodeElement = (Element) gamesNodeList.item(curGame);
        curGame++;
        
        String title = gameNodeElement.getAttribute("title");
        String name = gameNodeElement.getAttribute("name");
        Game game = collections.skylanders.Game.valueOf(name);
        
        ObjectXmlReader objectXmlReader = new ObjectXmlReader(gameNodeElement);
        
        return new GameEntry(game, title, objectXmlReader);
    }
}
