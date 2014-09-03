package collections.skylanders.installer;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.w3c.dom.Document;

import collections.skylanders.installer.reader.GameReader;
import collections.skylanders.installer.reader.GameXmlReader;

public class Main {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context;
        try (InputStream is = Main.class.getResourceAsStream("/spring.xml")) {
            // setValidating
            context = new GenericXmlApplicationContext();
            context.setValidating(false);
            context.load(new InputStreamResource(is));
            context.refresh();
        }
        
    	try (InputStream xmlInputStream = Main.class.getClassLoader().getResourceAsStream("skylanders.xml")) {
            DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
            Document xmlDocument = xmlBuilder.parse(Main.class.getClassLoader().getResourceAsStream("skylanders.xml"));
            
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            xmlDocument.getDocumentElement().normalize();
            
            GameReader gameReader = new GameXmlReader(xmlDocument);
            
            Installer installer = context.getBean(Installer.class);
            installer.install(gameReader);
    	}
    	
        context.stop();
    }
}