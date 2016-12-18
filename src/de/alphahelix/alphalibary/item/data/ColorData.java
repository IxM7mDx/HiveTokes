package de.alphahelix.alphalibary.item.data;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ColorData extends ItemData {

    private int red = 0;
    private int blue = 0;
    private int green = 0;
    private DyeColor dyeColor = DyeColor.WHITE;

    public ColorData(int red, int blue, int green) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public ColorData(DyeColor color) {
        this.dyeColor = color;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void applyOn(ItemStack applyOn) throws WrongDataException {

        ItemMeta meta = applyOn.getItemMeta();

        if (meta instanceof LeatherArmorMeta) {
            try {
                LeatherArmorMeta armor = (LeatherArmorMeta) meta;

                armor.setColor(Color.fromRGB(red, green, blue));
            } catch (IllegalArgumentException e) {
                throw new WrongDataException(this);
            }

        } else if (applyOn.getType() == Material.STAINED_CLAY
                || applyOn.getType() == Material.STAINED_GLASS
                || applyOn.getType() == Material.STAINED_GLASS_PANE
                || applyOn.getType() == Material.WOOL
                || applyOn.getType() == Material.CARPET
                || applyOn.getType() == Material.INK_SACK
                || applyOn.getType() == Material.BANNER) {

            applyOn.setDurability((applyOn.getType() == Material.INK_SACK || applyOn.getType() == Material.BANNER) ? dyeColor.getDyeData() : dyeColor.getData());
        } else {
            throw new WrongDataException(this);
        }
    }

}