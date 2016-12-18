package de.alphahelix.hivetokens;

/**
 * Created by AlphaHelixDev.
 */
public enum EquipSlot {

    HELMET(1),
    CHESTPLATE(2),
    LEGGINGS(3),
    BOOTS(4);

    private int nmsSlot;

    EquipSlot(int nmsSlot) {
        this.nmsSlot = nmsSlot;
    }

    public int getNmsSlot() {
        return nmsSlot;
    }
}
