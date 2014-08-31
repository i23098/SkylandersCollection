package collections.skylanders.installer;

public enum Element {
    AIR("Air"), EARTH("Earth"), FIRE("Fire"), LIFE("Life"), MAGIC("Magic"),
    TECH("Tech"), UNDEAD("Undead"), WATER("Water");
    
    String str;
    
    Element(String str) {
        this.str = str;
    }
    
    @Override
    public String toString() {
        return str;
    }
}
