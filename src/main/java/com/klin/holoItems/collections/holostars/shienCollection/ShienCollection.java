package com.klin.holoItems.collections.holostars.shienCollection;

import com.klin.holoItems.Collection;
import org.bukkit.entity.Player;

import java.util.Map;

public class ShienCollection extends Collection {
    public static final String name = "Shien";
    public static final String desc = "";
    public static final String theme = "";
    public static final String ign = "kageyamashien";
    public static final String uuid = "fb729943-3352-4c87-ae87-ba104a174d3d";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYxMTU3OTEzODc0OCwKICAicHJvZmlsZUlkIiA6ICJkODAwZDI4MDlmNTE0ZjkxODk4YTU4MWYzODE0Yzc5OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ0aGVCTFJ4eCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMjVhZGNjNTQzYWRhYjNhZjY5Y2Q5MWM5ZjY1ODdlOWM5NjUwMTM0OWMyY2RiMDEyMjMxMDNjNjY0MDUzZjk3IgogICAgfQogIH0KfQ==";
    public static final char key = '8';

    public ShienCollection(){
        super(name, desc, theme, ign, uuid, key, base64);
    }

    public Map<String, Integer> getStat(Player player){
        return null;
    }
}