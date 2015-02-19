package collections.skylanders;

import static collections.skylanders.Element.*;

import java.util.Collection;
import java.util.LinkedList;

public enum Figure {
    BASH(EARTH),
    BOUNCER(TECH, Category.GIANT),
    BOOMER(TECH),
    CAMO(EARTH),
    CHILL(WATER),
    CHOP_CHOP(UNDEAD),
    CRUSHER(EARTH, Category.GIANT),
    CYNDER(UNDEAD),
    DINO_RANG(EARTH),
    DOUBLE_TROUBLE(MAGIC),
    DRILL_SERGEANT(TECH),
    DROBOT(TECH),
    ERUPTOR(FIRE),
    EYE_BRAWL(UNDEAD, Category.GIANT),
    FLAMESLINGER(FIRE),
    FLASHWING(EARTH),
    FRIGHT_RIDER(UNDEAD),
    GHOST_ROASTER(UNDEAD),
    GILL_GRUNT(WATER),
    HEX(UNDEAD),
    HOT_DOG(FIRE),
    HOT_HEAD(FIRE, Category.GIANT),
    IGNITOR(FIRE),
    JET_VAC(AIR),
    LIGHTNING_ROD(AIR),
    NINJINI(MAGIC, Category.GIANT),
    POP_FIZZ(TECH),
    PRISM_BREAK(EARTH),
    SHROOMBOOM(EARTH),
    SLAM_BAM(WATER),
    SONIC_BOOM(AIR),
    SPROCKET(TECH),
    SPYRO(TECH),
    STEALTH_ELF(LIFE),
    STUMP_SMASH(LIFE),
    SUNBURN(FIRE),
    SWARM(AIR, Category.GIANT),
    TERRAFIN(EARTH),
    THUMPBACK(WATER, Category.GIANT),
    TREE_REX(LIFE, Category.GIANT),
    TRIGGER_HAPPY(TECH),
    VOODOOD(TECH),
    WARNADO(AIR),
    WHAM_SHELL(WATER),
    WHIRLWIND(AIR),
    WRECKING_BALL(TECH),
    ZAP(WATER),
    ZOOK(LIFE);
    
    public enum Variant {
        DARK, LEGENDARY, SIDEKICK, COLOR;
    }
    
    public enum Category {
        REGULAR, GIANT, LIGHTCORE;
    }
    
    private final Element element;
    private final Category type;
    
    Figure(Element element) {
        this(element, Category.REGULAR);
    }
    
    Figure(Element element, Category type) {
        this.element = element;
        this.type = type;
    }
    
    public Element getElement() {
        return element;
    }
    
    public Category getType() {
        return type;
    }
    
    Collection<Figure> getFigures(Element element) {
        Collection<Figure> figures = new LinkedList<>();
        
        for (Figure figure : Figure.values()) {
           if (figure.getElement() == element) {
               figures.add(figure);
           }
        }
        
        return figures;
    }
}
