package perhaps.progressions.skills;

import perhaps.progressions.client.gui.WheelSelectionManager;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import java.util.Map;

import static perhaps.progressions.client.gui.WheelSelectionManager.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;
public class Skills {
    public void initializeSkills() {
        skills.forEach((skillId, skillObject) -> {
           String displayName = (String) skillObject.get("displayName");
           String description = (String) skillObject.get("description");
           String saveName = (String) skillObject.get("saveName");
           Runnable callback = (Runnable) skillObject.get("callback");

           addSkill(displayName, description, saveName, callback);
        });
    }

    private final Map<String, Map<String, Object>> skills = Map.ofEntries(
            Map.entry("arcane_mastery", Map.of(
                    "displayName", "Arcane Mastery",
                    "description", "This skill represents your\n character's knowledge\n of the arcane arts.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        ScrollWheel skill1Wheel = new ScrollWheel(perksScrollWheel);
                        skill1Wheel.addOption(new WheelOption(PRESTIGE_ICON, "Prestige", "Prestige your skill", () -> {

                        }));

                        skill1Wheel.addOption(new WheelOption(UPGRADE_ICON, "Level Up", "Level up your skill", () -> {

                        }));

                        WheelOption skill1Option = new WheelOption(SKILLS_ICON, "Skill 1", "Prestige: 0\nLevel: 1/10\nXP: 57/100", () -> {

                        });

                        skill1Option.addHover(() -> {
                            skill1Option.title = "Skill 2";
                            skill1Option.description = "Prestige: 0\nLevel: 1/10\nXP: 58/100";
                        });

                        skill1Wheel.addOption(skill1Option);

                        WheelSelectionManager.openWheelSelectionScreen(global_player, skill1Wheel);
                    }
            )),
            Map.entry("elemental_control", Map.of(
                    "displayName", "Elemental Control",
                    "description", "A specialization of Arcane Mastery, this skill determines your character's expertise in harnessing the raw power of the elements. ",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("alchemy", Map.of(
                    "displayName", "Alchemy",
                    "description", "This skill allows your character\n to brew powerful potions\n and magical elixirs.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("enchanting", Map.of(
                    "displayName", "Enchanting",
                    "description", "This skill allows your\n character to imbue items with\n magical properties.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("conjuration", Map.of(
                    "displayName", "Conjuration",
                    "description", "This skill allows your character\n to summon and control\n creatures from other planes of existence.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("meditation", Map.of(
                    "displayName", "Meditation",
                    "description", "This skill improves your character's mana regeneration rate.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("herbalism", Map.of(
                    "displayName", "Herbalism",
                    "description", "This skill allows your character to gather and use herbs for various purposes. Higher levels unlock new types of herbs and improve the effectiveness of herbs when used in crafting or alchemy.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("mining", Map.of(
                    "displayName", "Mining",
                    "description", "This skill enhances your character's ability to extract resources from the world. Leveling up this skill improves the chances of finding rare ores.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("smithing", Map.of(
                    "displayName", "Smithing",
                    "description", "This skill allows your character to craft more advanced weapons and armor.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("archery", Map.of(
                    "displayName", "Archery",
                    "description", "This skill improves your character's proficiency with bows. Leveling up this skill improves the damage, range, and accuracy of your arrows.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("swordsmanship", Map.of(
                    "displayName", "Swordsmanship",
                    "description", "This skill increases your character's proficiency with swords.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("athletics", Map.of(
                    "displayName", "Athletics",
                    "description", "This skill determines your character's physical prowess, affecting running speed, jump height, and swimming speed.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("excavation", Map.of(
                    "displayName", "Excavation",
                    "description", "This skill enhances your character's ability to dig and extract resources from the soil and rocks.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            )),
            Map.entry("lumberjack", Map.of(
                    "displayName", "Lumberjack",
                    "description", "This skill improves your character's\n efficiency and speed\n when chopping down trees.",
                    "saveName", "temp",
                    "callback", (Runnable) () -> {
                        System.out.println("Pressed!");
                    }
            ))
    );

    public static void addSkill(String displayName, String description, String saveName, Runnable callback) {
        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, displayName, description, callback));
    }
}
