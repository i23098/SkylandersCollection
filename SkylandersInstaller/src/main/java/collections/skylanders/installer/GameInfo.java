package collections.skylanders.installer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameInfo extends TitleResourceName {
    public final Map<Element, List<TitleResourceName>> characters;
    
    GameInfo(String title, String resourceName, Map<Element, List<TitleResourceName>> characters) {
        super(title, resourceName);
        
        this.characters = Collections.unmodifiableMap(characters);
    }
}
