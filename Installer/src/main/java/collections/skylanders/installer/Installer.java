package collections.skylanders.installer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import collections.serverapi.Category;
import collections.serverapi.Item;
import collections.serverapi.service.CollectionsTransactionalService;
import collections.serverapi.service.CollectionsTransactionalServiceFactory;
import collections.skylanders.ItemType;
import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.FigureEntry;
import collections.skylanders.installer.reader.entry.GameEntry;
import collections.skylanders.installer.reader.entry.ItemEntry;
import collections.skylanders.installer.reader.entry.ObjectEntry;

import com.google.gson.JsonObject;

public class Installer {
    private Category getCategory(List<Category> categoryList, String gameTitle) {
        return categoryList.stream()
                .filter(c -> c.getTitle().equals(gameTitle))
                .findFirst().orElse(null);
    }
    
    private Item getItem(List<Item> itemList, String title) {
        return itemList.stream()
                .filter(i -> i.getTitle().equals(title))
                .findFirst().orElse(null);
    }
    
    public void install(Reader<GameEntry> gameXmlReader) throws IOException, SQLException {
        try (CollectionsTransactionalService service = CollectionsTransactionalServiceFactory.begin()) {
            List<Category> gameCategoryList = service.category().getTopLevelCategories();
            
            for (GameEntry gameEntry : gameXmlReader) {
                String imgDir = "/images/" + getCamelCase(gameEntry.game.toString()) + "/";
                
                Category gameCategory = getCategory(gameCategoryList, gameEntry.title);
                if (gameCategory == null) {
                    System.out.println(gameEntry.title);
                    
                    try (InputStream imgStream = Main.class.getResourceAsStream(imgDir + "game.png")) {
                        gameCategory = service.category().addTopLevel(gameEntry.title, imgStream);
                    }
                }
                
                List<Item> objectList = service.item().getItems(gameCategory.getId());
                for (ObjectEntry objectEntry : gameEntry.objectReader) {
                    Item objectItem = getItem(objectList, objectEntry.getName());
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
                            Item item = new Item(-1, objectEntry.getName(), gameCategory.getId(), extra);
                            item = service.item().add(item, imgStream);
                        }
                    }
                }
            }
            
            service.commit();
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
