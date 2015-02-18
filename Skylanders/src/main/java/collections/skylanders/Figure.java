package collections.skylanders;

import static collections.skylanders.Element.*;

import java.util.Collection;
import java.util.LinkedList;

public enum Figure {
    BASH(EARTH),
    BOUNCER(TECH),
    BOOMER(TECH),
    CAMO(EARTH),
    CHILL(WATER),
    CHOP_CHOP(UNDEAD),
    CRUSHER(EARTH),
    CYNDER(UNDEAD),
    DINO_RANG(EARTH),
    DOUBLE_TROUBLE(MAGIC),
    DRILL_SERGEANT(TECH),
    DROBOT(TECH),
    ERUPTOR(FIRE),
    EYE_BRAWL(UNDEAD),
    FLAMESLINGER(FIRE),
    FLASHWING(EARTH),
    FRIGHT_RIDER(UNDEAD),
    GHOST_ROASTER(UNDEAD),
    GILL_GRUNT(WATER),
    HEX(UNDEAD),
    HOT_DOG(FIRE),
    HOT_HEAD(FIRE),
    IGNITOR(FIRE),
    JET_VAC(AIR),
    LIGHTNING_ROD(AIR),
    NINJINI(MAGIC),
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
    SWARM(AIR),
    TERRAFIN(EARTH),
    THUMPBACK(WATER),
    TREE_REX(LIFE),
    TRIGGER_HAPPY(TECH),
    VOODOOD(TECH),
    WARNADO(AIR),
    WHAM_SHELL(WATER),
    WHIRLWIND(AIR),
    WRECKING_BALL(TECH),
    ZAP(WATER),
    ZOOK(LIFE);
    
    public enum Variant {
        DARK, LEGENDARY, SIDEKICK, LIGHTCORE, COLOR;
    }
    
    private final Element element;
    
    Figure(Element element) {
        this.element = element;
    }
    
    public Element getElement() {
        return element;
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
