package collections.skylanders.installer.reader.xml;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import collections.skylanders.Figure;
import collections.skylanders.Figure.Variant;
import collections.skylanders.Item;
import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.FigureEntry;
import collections.skylanders.installer.reader.entry.ItemEntry;
import collections.skylanders.installer.reader.entry.ObjectEntry;

public class ObjectXmlReader implements Reader<ObjectEntry> {
    private final NodeList figuresNodeList;
    private final NodeList itemsNodeList;
    int curItem;
    
    public ObjectXmlReader(Element gameNodeElement) {
        this(gameNodeElement.getElementsByTagName("figure"), gameNodeElement.getElementsByTagName("item"));
    }
    
    private ObjectXmlReader(NodeList figuresNodeList, NodeList itemsNodeList) {
        this.figuresNodeList = figuresNodeList;
        this.itemsNodeList = itemsNodeList;
        
        curItem = 0;
    }
    
    @Override
    public Iterator<ObjectEntry> iterator() {
        return new ObjectXmlReader(figuresNodeList, itemsNodeList);
    }
    
    public boolean hasNext() {
        return curItem < figuresNodeList.getLength() + itemsNodeList.getLength();
    }
    
    public ObjectEntry next() {
        ObjectEntry objectEntry;
        if (curItem < figuresNodeList.getLength()) {
            objectEntry = getFigureEntry(curItem);
        } else {
            objectEntry = getItemEntry(curItem - figuresNodeList.getLength());
        }
        
        curItem++;
        
        return objectEntry;
    }
    
    FigureEntry getFigureEntry(int i) {
        Element nodeElement = (Element) figuresNodeList.item(i);
        
        String type = nodeElement.getAttribute("type");
        Figure figure = Figure.valueOf(type);
        String name = nodeElement.getAttribute("name");
        Variant variant = null;
        if (nodeElement.hasAttribute("variant")) {
            variant = Variant.valueOf(nodeElement.getAttribute("variant"));
        }
        int series = 1;
        if (nodeElement.hasAttribute("series")) {
            series = Integer.parseInt(nodeElement.getAttribute("series"));
        }
        
        return new FigureEntry(figure, name, variant, series);
    }
    
    ItemEntry getItemEntry(int i) {
        Element nodeElement = (Element) itemsNodeList.item(i);
        
        String type = nodeElement.getAttribute("type");
        Item itemType = Item.valueOf(type);
        String name = nodeElement.getAttribute("name");
        
        return new ItemEntry(itemType, name);
    }
}
