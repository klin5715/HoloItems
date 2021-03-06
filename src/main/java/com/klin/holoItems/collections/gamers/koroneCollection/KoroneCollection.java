package com.klin.holoItems.collections.gamers.koroneCollection;

import com.klin.holoItems.Collection;
import com.klin.holoItems.collections.gamers.koroneCollection.items.Radar;
import com.klin.holoItems.collections.gen4.watameCollection.items.UberSheepPackage;
import com.klin.holoItems.collections.gen4.watameCollection.items.LaunchPad;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class KoroneCollection extends Collection {
    public static final String name = "Korone";
    public static final String desc = "";
    public static final String theme = "Pastry baked";
//    public static final String ign = "inugamikorone";
//    public static final String uuid = "10752967-5daf-47a2-be31-01f70f0b3294";
    public static final String base64 = "ewogICJ0aW1lc3RhbXAiIDogMTYyMDE4NDI0MDUyNCwKICAicHJvZmlsZUlkIiA6ICIxMDc1Mjk2NzVkYWY0N2EyYmUzMTAxZjcwZjBiMzI5NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJpbnVnYW1pa29yb25lIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2UyZTdjZGQ5MGMxMmRhZDlhZmMwMGI5M2E2ZWIwOGZjYWU5MzZjNGQ5ZjkxNWU3NDBkNDVkNzMwMjI5MjU3MzQiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ=";

    public KoroneCollection(){
        super(name, desc, theme, base64);
        collection.add(new Radar());
    }

    public Map<String, Integer> getStat(Player player){
        Map<String, Integer> stat = new LinkedHashMap<>();
        return stat;
    }
}
