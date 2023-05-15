package perhaps.progressions.skills;

import net.minecraft.resources.ResourceLocation;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.client.gui.WheelSelectionManager;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;
public class Skills {
    public void initializeSkills() {
        skills.forEach((skillName, skillObject) -> {
           String displayName = (String) skillObject.get("displayName");
           String description = (String) skillObject.get("description");
           String saveName = (String) skillObject.get("saveName");
           Runnable callback = (Runnable) skillObject.get("callback");

           addSkill(displayName, description, saveName, callback);
        });
    }

    public final Map<String, Map<String, Object>> skills = Map.ofEntries(
            Map.entry("arcane_mastery", Map.of(
                    "displayName", "Arcane Mastery",
                    "description", "Improves your proficiency with magic, increasing the damage, range, and accuracy of your spells.",
                    "saveName", "arcane_mastery",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("elemental_control", Map.of(
                    "displayName", "Elemental Control",
                    "description", "Increases your ability to harness the raw power of the elements. ",
                    "saveName", "elemental_control",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("alchemy", Map.of(
                    "displayName", "Alchemy",
                    "description", "Allows you to brew more powerful potions.",
                    "saveName", "alchemy",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("enchanting", Map.of(
                    "displayName", "Enchanting",
                    "description", "Increases your ability to enchant items with more powerful enchantments.",
                    "saveName", "enchanting",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("conjuration", Map.of(
                    "displayName", "Conjuration",
                    "description", "Allows you to summon and control creatures from other planes of existence.",
                    "saveName", "conjuration",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("meditation", Map.of(
                    "displayName", "Meditation",
                    "description", "Improves your mana regeneration rate.",
                    "saveName", "meditation",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("herbalism", Map.of(
                    "displayName", "Herbalism",
                    "description", "Improves the effectiveness of herbs when used in crafting or alchemy.",
                    "saveName", "herbalism",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("mining", Map.of(
                    "displayName", "Mining",
                    "description", "Enhances your ability to extract resources from the world.",
                    "saveName", "mining",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("smithing", Map.of(
                    "displayName", "Smithing",
                    "description", "Allows you to craft more advanced weapons and armor, increasing their durability and toughness.",
                    "saveName", "smithing",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("archery", Map.of(
                    "displayName", "Archery",
                    "description", "Improves your proficiency with bows, increases the damage, range, and accuracy of your arrows.",
                    "saveName", "archery",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("swordsmanship", Map.of(
                    "displayName", "Swordsmanship",
                    "description", "Increases your proficiency with swords, dealing more damage from farther.",
                    "saveName", "swordsmanship",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("athletics", Map.of(
                    "displayName", "Athletics",
                    "description", "Determines your physical prowess, affecting running speed, jump height, and swimming speed.",
                    "saveName", "athletics",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("excavation", Map.of(
                    "displayName", "Excavation",
                    "description", "Enhances your ability to dig and extract resources from the soil and rocks.",
                    "saveName", "excavation",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("lumberjack", Map.of(
                    "displayName", "Lumberjack",
                    "description", "Improves your efficiency and speed when chopping down trees.",
                    "saveName", "lumberjack",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("auto_compression", Map.of(
                    "displayName", "Auto Compression",
                    "description", "Automatically compresses blocks/items.",
                    "saveName", "auto_compression",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            ))
    );

    public static void addSkill(String displayName, String description, String saveName, Runnable callback) {
        ResourceLocation icon = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills/" + saveName + ".png");
        skillsScrollWheel.addOption(new WheelOption(icon, displayName, description, () -> {
            callback.run();
            ScrollWheel skill1Wheel = new ScrollWheel(skillsScrollWheel);
            skill1Wheel.addOption(new WheelOption(SKILL_PRESTIGE, "Prestige", "Prestige your skill", () -> {

            }));

            WheelOption levelUpOption = new WheelOption(SKILL_UPGRADE, "Level Up", "Level up your skill", () -> {

            });

            levelUpOption.addSound(1.0f, LEVEL_UP_SOUND, true, () -> {
                return false; // No error here
            });

            WheelOption skill1Option = new WheelOption(SKILL_INFORMATION, displayName, "Prestige: 0\nLevel: 1/10\nXP: 57/100", () -> {

            });

            skill1Wheel.addOption(skill1Option);
            skill1Wheel.addOption(levelUpOption);
            WheelSelectionManager.openWheelSelectionScreen(globalPlayer, skill1Wheel);
        }));
    }
}
