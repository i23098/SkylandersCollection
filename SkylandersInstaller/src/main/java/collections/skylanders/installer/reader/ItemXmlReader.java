package collections.skylanders.installer.reader;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ItemXmlReader implements ItemReader {
	NodeList itemNodeList;
	int curItem;
	
	public ItemXmlReader(Element categoryNodeElement) {
		itemNodeList = categoryNodeElement.getElementsByTagName("item");
		curItem = 0;
	}
	
	public boolean hasNext() {
		return curItem < itemNodeList.getLength();
	}
	
	public String next() {
	    Element itemNodeElement = (Element) itemNodeList.item(curItem);
	    curItem++;
	    
        String item = itemNodeElement.getTextContent();

	    return item;
	}
}
