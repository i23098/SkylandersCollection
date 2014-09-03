package collections.skylanders.installer.reader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GameXmlReader implements GameReader {
	NodeList gamesNodeList;
	int curGame;
	
	public GameXmlReader(Document xmlDocument) {
		gamesNodeList = xmlDocument.getElementsByTagName("game");
		curGame = 0;
	}
	
	public boolean hasNext() {
		return curGame < gamesNodeList.getLength();
	}
	
	public Game next() {
		Element gameNodeElement = (Element) gamesNodeList.item(curGame);
		curGame++;
		
        String title = gameNodeElement.getAttribute("title");
        String name = gameNodeElement.getAttribute("name");
        CategoryXmlReader categoryXmlReader = new CategoryXmlReader(gameNodeElement);
        
        return new Game(name, title, categoryXmlReader);
	}
}
