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
import collections.skylanders.installer.reader.Category;
import collections.skylanders.installer.reader.Game;
import collections.skylanders.installer.reader.GameReader;

@Component
public class Installer {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ItemService itemService;
    
    private collections.serverapi.Category getCategory(List<collections.serverapi.Category> categoryList, String gameTitle) {
        for (collections.serverapi.Category category : categoryList) {
            if (category.getTitle().equals(gameTitle)) {
                return category;
            }
        }
        
        return null;
    }
    
    private Item getItem(List<Item> itemList, String title) {
        for (Item item : itemList) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }
        
        return null;
    }
    
    public void install(GameReader gameXmlReader) throws IOException {
        List<collections.serverapi.Category> gameCategoryList = categoryService.getTopLevelCategories();
        
        while (gameXmlReader.hasNext()) {
            Game game = gameXmlReader.next();
            
            String imgDir = "/images/" + game.name + "/";
            
            collections.serverapi.Category gameCategory = getCategory(gameCategoryList, game.title);
            if (gameCategory == null) {
                System.out.println(game.title);
                
                try (InputStream imgStream = Main.class.getResourceAsStream(imgDir + "game.png")) {
                    gameCategory = categoryService.addTopLevel(game.title, imgStream);
                }
            }
            
            List<collections.serverapi.Category> elementSubCategoryList = categoryService.getCategories(gameCategory.getId());
            while (game.categoryReader.hasNext()) {
                Category category = game.categoryReader.next();
                
                ItemCategory itemCategory = ItemCategory.valueOf(category.name);
                
                collections.serverapi.Category elementSubCategory = getCategory(elementSubCategoryList, itemCategory.toString());
                if (elementSubCategory == null) {
                    System.out.println(itemCategory);
                    
                    elementSubCategory = categoryService.add(itemCategory.toString(), gameCategory.getId());
                }
                
                List<Item> characterList = itemService.getItems(elementSubCategory.getId());
                while (category.itemReader.hasNext()) {
                    String character = category.itemReader.next();
                    
                    Item characterItem = getItem(characterList, character);
                    if (characterItem == null) {
                        System.out.println(character);
                        
                        String imgFileName;
                        switch (itemCategory) {
                            case MAGIC_ITEMS:
                            case ADVENTURE_PACKS:
                            case INGAME_VARIANTS:
                                imgFileName = imgDir + getCamelCase(itemCategory.toString()) + "/" + getCamelCase(character) + ".png";
                                break;
                            
                            default:
                                imgFileName = imgDir + getCamelCase(character) + ".png";
                        }
                        
                        try (InputStream imgStream = Main.class.getResourceAsStream(imgFileName)) {
                            characterItem = itemService.add(character, imgStream, elementSubCategory.getId());
                        }
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
            } else if (c == ' ' || c == '-') {
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
