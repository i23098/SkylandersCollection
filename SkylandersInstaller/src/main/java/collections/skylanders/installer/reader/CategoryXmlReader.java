package collections.skylanders.installer.reader;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CategoryXmlReader implements CategoryReader {
	NodeList categoryNodeList;
	int curCategory;
	
	public CategoryXmlReader(Element gameNodeElement) {
		categoryNodeList = gameNodeElement.getElementsByTagName("category");
		curCategory = 0;
	}
	
	public boolean hasNext() {
		return curCategory < categoryNodeList.getLength();
	}
	
	public Category next() {
	    Element categoryNodeElement = (Element) categoryNodeList.item(curCategory);
	    curCategory++;
	    
	    String name = categoryNodeElement.getAttribute("name");
	    ItemXmlReader itemXmlReader = new ItemXmlReader(categoryNodeElement);
	    
	    return new Category(name, itemXmlReader);
	}
}
