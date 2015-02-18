package collections.skylanders.installer.reader.entry;

import collections.skylanders.Item;

public class ItemEntry extends ObjectEntry {
    protected final Item item;
    
    public ItemEntry(Item item, String name) {
        super(name);
        
        this.item = item;
    }
    
    public Item getItem() {
        return item;
    }
}
