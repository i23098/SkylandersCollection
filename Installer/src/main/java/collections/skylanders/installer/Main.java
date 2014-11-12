package collections.skylanders.installer;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.GameEntry;
import collections.skylanders.installer.reader.xml.GameXmlReader;

public class Main {
	private final ApplicationContext context;
	
	Main(ApplicationContext context) {
		this.context = context;
	}
	
	void install(String xmlResourceName) throws ParserConfigurationException, SAXException, IOException {
        try (InputStream xmlInputStream = Main.class.getClassLoader().getResourceAsStream(xmlResourceName)) {
	        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
	        Document xmlDocument = xmlBuilder.parse(Main.class.getClassLoader().getResourceAsStream(xmlResourceName));
	        
	        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	        xmlDocument.getDocumentElement().normalize();
	        
	        Reader<GameEntry> gameReader = new GameXmlReader(xmlDocument);
	        
	        Installer installer = context.getBean(Installer.class);
	        installer.install(gameReader);
        }
	}
	
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context = getContext();
        
        new Main(context).install("skylanders.xml");;
        
        context.stop();
    }
    
    private static GenericXmlApplicationContext getContext() throws IOException {
        GenericXmlApplicationContext context;
        try (InputStream is = Main.class.getResourceAsStream("/spring.xml")) {
            // setValidating
            context = new GenericXmlApplicationContext();
            context.setValidating(false);
            context.load(new InputStreamResource(is));
            context.refresh();
        }
        
        return context;
    }
}