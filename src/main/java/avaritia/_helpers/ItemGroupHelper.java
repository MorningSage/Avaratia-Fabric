package avaritia._helpers;

import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.ItemGroup;

public abstract class ItemGroupHelper extends ItemGroup {
    public final String modID;

    private static int addGroupReturnIndex() {
        // Add another item to the list of groups and return the new length
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }

    public ItemGroupHelper(String modID) {
        super(addGroupReturnIndex(), modID);

        this.modID = modID;
    }

    public boolean getIsSearchable() {
        return false;
    }

    public boolean showModTooltip() {
        return false;
    }

    public int getSearchWidth() {
        return 80;
    }

    @Override
    public ItemGroup setTexture(String texture) {
        return super.setTexture(texture);
    }
}

