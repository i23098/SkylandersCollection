package collections.skylanders.installer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameInfo extends TitleResourceName {
    public final Map<ItemCategory, List<TitleResourceName>> characters;
    
    GameInfo(String title, String resourceName, Map<ItemCategory, List<TitleResourceName>> characters) {
        super(title, resourceName);
        
        this.characters = Collections.unmodifiableMap(characters);
    }
}
