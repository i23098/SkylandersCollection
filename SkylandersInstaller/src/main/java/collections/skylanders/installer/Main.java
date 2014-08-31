package collections.skylanders.installer;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import collections.serverapi.Category;
import collections.serverapi.service.CategoryService;
import collections.serverapi.service.ItemService;
import collections.serverapi.service.UserService;

@Component
public class Main {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    ItemService itemService;

    void execute() throws Exception {
        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
        Document xmlDocument = xmlBuilder.parse(Main.class.getClassLoader().getResourceAsStream("skylanders.xml"));
        
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        xmlDocument.getDocumentElement().normalize();
        
        NodeList gamesNodeList = xmlDocument.getElementsByTagName("game");
        for (int i = 0; i < gamesNodeList.getLength(); i++) {
            org.w3c.dom.Element gameNodeElement = (org.w3c.dom.Element) gamesNodeList.item(i);
            String gameTitle = gameNodeElement.getAttribute("title");
            String gameName = gameNodeElement.getAttribute("name");
            System.out.println(gameTitle);
            
            String imgDir = "/images/" + gameName + "/";
            
            Category gameCategory;
            try (InputStream imgStream = Main.class.getResourceAsStream(imgDir + "game.png")) {
                gameCategory = categoryService.addTopLevel(gameTitle, imgStream);
            }
            
            NodeList categoryNodeList = gameNodeElement.getElementsByTagName("category");
            for (int j = 0; j < categoryNodeList.getLength(); j++) {
                org.w3c.dom.Element categoryNodeElement = (org.w3c.dom.Element) categoryNodeList.item(j);
                Element element = Element.valueOf(categoryNodeElement.getAttribute("name"));
                
                System.out.println(element);
                Category elementSubCategory = categoryService.add(element.toString(), gameCategory.getId());

                NodeList itemNodeList = categoryNodeElement.getElementsByTagName("item");
                for (int k = 0; k < itemNodeList.getLength(); k++) {
                    org.w3c.dom.Element itemNodeElement = (org.w3c.dom.Element) itemNodeList.item(k);
                    String character = itemNodeElement.getTextContent();
                    
                    System.out.println(character);
                    try (InputStream imgStream = Main.class.getResourceAsStream(imgDir + getCamelCase(character) + ".png")) {
                        itemService.add(character, imgStream, elementSubCategory.getId());
                    }
                }
            }
        }
        
        /*
        for (GameInfo gameInfo : GAMES) {
            addGame(gameInfo);
        }*/
    }

    String getCamelCase(String str) {
        StringBuffer sb = new StringBuffer();
        boolean lastSpace = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ' || c == '-') {
                lastSpace = true;
            } else {
                c = (lastSpace ? Character.toUpperCase(c) : Character.toLowerCase(c));
                sb.append(c);
                lastSpace = false;
            }
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context;
        try (InputStream is = Main.class.getResourceAsStream("/spring.xml")) {
            // setValidating
            context = new GenericXmlApplicationContext();
            context.setValidating(false);
            context.load(new InputStreamResource(is));
            context.refresh();
        }

        Main main = context.getBean(Main.class);
        main.execute();

        context.stop();
    }
}