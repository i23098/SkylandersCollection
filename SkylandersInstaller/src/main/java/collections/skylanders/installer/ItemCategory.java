package collections.skylanders.installer;

public enum ItemCategory {
    AIR("Air"), EARTH("Earth"), FIRE("Fire"), LIFE("Life"), MAGIC("Magic"),
    TECH("Tech"), UNDEAD("Undead"), WATER("Water"), MAGIC_ITEMS("Magic Items"),
    ADVENTURE_PACKS("Adventure Packs"), INGAME_VARIANTS("In Game Variants");
    
    String str;
    
    ItemCategory(String str) {
        this.str = str;
    }
    
    @Override
    public String toString() {
        return str;
    }
}
