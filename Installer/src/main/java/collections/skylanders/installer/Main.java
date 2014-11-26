package collections.skylanders.installer;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import collections.skylanders.installer.reader.Reader;
import collections.skylanders.installer.reader.entry.GameEntry;
import collections.skylanders.installer.reader.xml.GameXmlReader;

public class Main {
    public static void main(String[] args) throws Exception {
        try (InputStream xmlInputStream = Main.class.getClassLoader().getResourceAsStream("skylanders.xml")) {
            DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
            Document xmlDocument = xmlBuilder.parse(xmlInputStream);
            
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            xmlDocument.getDocumentElement().normalize();
            
            Reader<GameEntry> gameReader = new GameXmlReader(xmlDocument);
            
            new Installer().install(gameReader);
        }
    }
}