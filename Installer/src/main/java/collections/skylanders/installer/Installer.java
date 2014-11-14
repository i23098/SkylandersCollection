package collections.skylanders.installer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import collections.serverapi.Item;
import collections.serverapi.service.CategoryService;
import collections.serverapi.service.ItemService;
import collections.serverapi.service.UserService;
import collections.skylanders.ItemType;
import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.FigureEntry;
import collections.skylanders.installer.reader.entry.GameEntry;
import collections.skylanders.installer.reader.entry.ItemEntry;
import collections.skylanders.installer.reader.entry.ObjectEntry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Component
public class Installer {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ItemService<JsonElement> itemService;
    
    private collections.serverapi.Category getCategory(List<collections.serverapi.Category> categoryList, String gameTitle) {
        for (collections.serverapi.Category category : categoryList) {
            if (category.getTitle().equals(gameTitle)) {
                return category;
            }
        }
        
        return null;
    }
    
    private Item<JsonElement> getItem(List<Item<JsonElement>> itemList, String title) {
        for (Item<JsonElement> item : itemList) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        
        return null;
    }
    
    public void install(Reader<GameEntry> gameXmlReader) throws IOException {
        List<collections.serverapi.Category> gameCategoryList = categoryService.getTopLevelCategories();
        
        while (gameXmlReader.hasNext()) {
            GameEntry gameEntry = gameXmlReader.next();
            
            String imgDir = "/images/" + getCamelCase(gameEntry.game.toString()) + "/";
            
            collections.serverapi.Category gameCategory = getCategory(gameCategoryList, gameEntry.title);
            if (gameCategory == null) {
                System.out.println(gameEntry.title);
                
                try (InputStream imgStream = Main.class.getResourceAsStream(imgDir + "game.png")) {
                    gameCategory = categoryService.addTopLevel(gameEntry.title, imgStream);
                }
            }
            
            List<Item<JsonElement>> objectList = itemService.getItems(gameCategory.getId());
            while (gameEntry.objectReader.hasNext()) {
                ObjectEntry objectEntry = gameEntry.objectReader.next();
                
                Item<JsonElement> objectItem = getItem(objectList, objectEntry.getName());
                if (objectItem == null) {
                    System.out.println(objectEntry.getName());
                    
                    JsonObject extra = new JsonObject();
                    String imgFileName = imgDir;
                    if (objectEntry instanceof FigureEntry) {
                    	FigureEntry figureEntry = (FigureEntry) objectEntry;
                    	
                    	extra.addProperty("figure", figureEntry.getFigure().toString());
                    	extra.addProperty("series", figureEntry.getSeries());
                    	if (figureEntry.getVariant() != null) {
                            imgFileName += "inGameVariants/";
                    		extra.addProperty("variant", figureEntry.getVariant().toString());
                    	}
                    } else { // ItemEntry
                    	ItemEntry itemEntry = (ItemEntry) objectEntry;
                    	
                    	extra.addProperty("item", itemEntry.getItem().toString());
                    	
                        imgFileName += (itemEntry.getItem().getType() == ItemType.ADVENTURE_PACK ? "adventurePacks/" : "magicItems/");
                    }
            		imgFileName += getCamelCase(objectEntry.getName()) + ".png";
                    
                    try (InputStream imgStream = Main.class.getResourceAsStream(imgFileName)) {
                    	Item<JsonElement> item = itemService.add(objectEntry.getName(), extra, imgStream, gameCategory.getId());
                    }
                }
            }
        }
    }
    
    String getCamelCase(String str) {
        StringBuffer sb = new StringBuffer();
        boolean lastSpace = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\'') {
                // ignore char
            } else if (c == ' ' || c == '-' || c == '_') {
                lastSpace = true;
            } else {
                c = (lastSpace ? Character.toUpperCase(c) : Character.toLowerCase(c));
                sb.append(c);
                lastSpace = false;
            }
        }
        
        return sb.toString();
    }
}