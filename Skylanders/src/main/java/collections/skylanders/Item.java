package collections.skylanders;

import static collections.skylanders.ItemType.*;

public enum Item {
    ANVIL_RAIN(MAGIC_ITEM),
    DARKLIGHT_CRYPT(ADVENTURE_PACK),
    DRAGONFIRE_CANNON(MAGIC_ITEM),
    DRAGONS_PEAK(ADVENTURE_PACK),
    GHOST_SWORDS(MAGIC_ITEM),
    GOLDEN_DRAGONFIRE_CANNON(MAGIC_ITEM),
    HEALING_ELIXIR(MAGIC_ITEM),
    HIDDEN_TREASURE(MAGIC_ITEM),
    ICE_CAVE(ADVENTURE_PACK),
    PIRATE_SHIP(ADVENTURE_PACK),
    PLATINUM_TREASURE_CHEST(MAGIC_ITEM),
    SCORPION_STRIKER_CATAPULT(MAGIC_ITEM),
    SKY_IRON_SHIELD(MAGIC_ITEM),
    SPARX_DRAGONFLY(MAGIC_ITEM),
    TIME_TWISTER(MAGIC_ITEM),
    VOLCANIC_VAULT(MAGIC_ITEM),
    WINGED_BOOTS(MAGIC_ITEM);
    
    private final ItemType type;
    
    Item(ItemType type) {
        this.type = type;
    }
    
    public ItemType getType() {
        return type;
    }
}
