package collections.skylanders.installer.reader.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import collections.skylanders.Game;
import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.GameEntry;

public class GameXmlReader implements Reader<GameEntry> {
    NodeList gamesNodeList;
    int curGame;
    
    public GameXmlReader(Document xmlDocument) {
        gamesNodeList = xmlDocument.getElementsByTagName("game");
        curGame = 0;
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
