package perhaps.progressions.perks;

import net.minecraft.resources.ResourceLocation;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.client.gui.WheelSelectionManager;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;

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
                    "saveName", "miners_instinct",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("warriors_might", Map.of(
                    "displayName", "Warrior's Might",
                    "description", "Increases melee damage and damage resistance.",
                    "saveName", "warriors_might",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("archers_precision", Map.of(
                    "displayName", "Archer's Precision",
                    "description", "Increases bow/crossbow damage and arrow velocity.",
                    "saveName", "archers_precision",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("farmers_bounty", Map.of(
                    "displayName", "Farmer's Bounty",
                    "description", "Increases crop yield and growth speed.",
                    "saveName", "farmers_bounty",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("alchemists_wisdom", Map.of(
                    "displayName", "Alchemist's Wisdom",
                    "description", "Increases potion potency and duration.",
                    "saveName", "alchemists_wisdom",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("wizards_sorcery", Map.of(
                    "displayName", "Wizard's Sorcery",
                    "description", "Increases maximum mana, regeneration speed, and efficiency.",
                    "saveName", "wizards_sorcery",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            ))
    );

    public static void addPerk(String displayName, String description, String saveName, Runnable callback) {
        ResourceLocation icon = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks/" + saveName + ".png");
        perksScrollWheel.addOption(new WheelOption(icon, displayName, description, () -> {
            callback.run();

            ScrollWheel perkWheel = new ScrollWheel(perksScrollWheel);
            perkWheel.addOption(new WheelOption(PERK_SACRIFICE, "Sacrifice", "Sacrifice your perk with 3 netherrite blocks.", () -> {

            }));

            perkWheel.addOption(new WheelOption(PERK_UPGRADE, "Level Up", "Level up your perk", () -> {

            }));

            WheelOption perkInfoOption = new WheelOption(PERK_INFORMATION, displayName, "Base Level: 1/3\nPerk Level: 1/10\nXP: 78/100", () -> {});

            perkInfoOption.addHover(() -> {
                perkInfoOption.description = "Base Level: 3/3\nPerk Level: 1/10\nXP: 78/100";
            });

            perkWheel.addOption(perkInfoOption);
            WheelSelectionManager.openWheelSelectionScreen(globalPlayer, perkWheel);
        }));
    }
}
