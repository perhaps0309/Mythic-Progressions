package perhaps.progressions.perks;

import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.perksScrollWheel;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.PERKS_ICON;

public class Perks {
    public void initializePerks() {
        perks.forEach((perkName, perkObject) -> {
            String displayName = (String) perkObject.get("displayName");
            String description = (String) perkObject.get("description");
            String saveName = (String) perkObject.get("saveName");
            Runnable callback = (Runnable) perkObject.get("callback");

            addPerk(displayName, description, saveName, callback);
        });
    }

    private final Map<String, Map<String, Object>> perks = Map.ofEntries(
            Map.entry("miners_instinct", Map.of(
                    "displayName", "Miner's Instinct",
                    "description", "Increases mining speed and ore drop rate.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("warriors_might", Map.of(
                    "displayName", "Warrior's Might",
                    "description", "Increases melee damage and damage resistance.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("archers_precision", Map.of(
                    "displayName", "Archer's Precision",
                    "description", "Increases bow/crossbow damage and arrow velocity.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("farmers_bounty", Map.of(
                    "displayName", "Farmer's Bounty",
                    "description", "Increases crop yield and growth speed.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("alchemists_wisdom", Map.of(
                    "displayName", "Alchemist's Wisdom",
                    "description", "Increases potion potency and duration.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("wizards_sorcery", Map.of(
                    "displayName", "Wizard's Sorcery",
                    "description", "to-do",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            ))
    );

    public static void addPerk(String displayName, String description, String saveName, Runnable callback) {
        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, displayName, description, callback));
    }
}
