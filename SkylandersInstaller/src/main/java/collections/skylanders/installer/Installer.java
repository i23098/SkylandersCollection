package collections.skylanders.installer;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
    public void install(GameReader gameXmlReader) throws IOException {
    	while (gameXmlReader.hasNext()) {
    		Game game = gameXmlReader.next();
    		
            System.out.println(game.title);
            
            String imgDir = "/images/" + game.name + "/";
            
            collections.serverapi.Category gameCategory;
            try (InputStream imgStream = Main.class.getResourceAsStream(imgDir + "game.png")) {
                gameCategory = categoryService.addTopLevel(game.title, imgStream);
            }
            
            while (game.categoryReader.hasNext()) {
            	Category category = game.categoryReader.next();
            	
            	ItemCategory itemCategory = ItemCategory.valueOf(category.name);
            	
                System.out.println(itemCategory);
                
                collections.serverapi.Category elementSubCategory = categoryService.add(itemCategory.toString(), gameCategory.getId());
                
                while (category.itemReader.hasNext()) {
                	String character = category.itemReader.next();
                	
                    System.out.println(character);
                    
                    String imgFileName;
                    if (itemCategory == ItemCategory.MAGIC_ITEMS || itemCategory == ItemCategory.ADVENTURE_PACKS) {
                    	imgFileName = imgDir + getCamelCase(itemCategory.toString()) + "/" + getCamelCase(character) + ".png";
                    } else {
                    	imgFileName = imgDir + getCamelCase(character) + ".png";
                    }
                    
                    try (InputStream imgStream = Main.class.getResourceAsStream(imgFileName)) {
                        itemService.add(character, imgStream, elementSubCategory.getId());
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
