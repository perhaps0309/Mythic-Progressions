package perhaps.progressions.capabilities.skills;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Skill {
    public static Map<String, Float> skillData = new HashMap<>();

    static {
        skillData.put("level", 1f);
        skillData.put("xp", 0.0f);
        skillData.put("prestige", 1f);
        skillData.put("neededXp", 100f);
        skillData.put("levelMin", 1f);
        skillData.put("levelMax", 10f);
        skillData.put("xpMin", 0f);
        skillData.put("xpMax", 1000000f);
        skillData.put("prestigeMin", 1f);
        skillData.put("prestigeMax", 25f);
    }

    public Map<String, Float> getSkillData() {
        return skillData;
    }

    public Float getMin(String statName) {
        if (skillData.containsKey(statName + "Min")) {
            return skillData.get(statName + "Min");
        }
        return null;
    }

    public Float getMax(String statName) {
        if (skillData.containsKey(statName + "Max")) {
            return skillData.get(statName + "Max");
        }
        return null;
    }

    public Float calculateMax(String statName, Float value) {
        Float max = getMax(statName);
        Float min = getMin(statName);
        if (max != null && min != null) {
            return Math.max(min, Math.min(max, value));
        }
        return value;
    }

    public Float get(String statName) {
        Float value = skillData.get(statName);
        if (value == null) {
            System.out.println("Failed to get " + statName);
            return null;
        }

        return value;
    }

    public void set(String statName, Float value) {
        if (Objects.equals(statName, "level") || Objects.equals(statName, "prestige")) {
            float currentLevel = get("level");
            float currentPrestige = get("prestige");
            float neededXp = 100f;
            for (int level = 2; level <= currentLevel; level++) {
                double prestigeMultiplier = 1 + (65.0 * currentPrestige / 100.0);
                neededXp *= prestigeMultiplier;
            }

            skillData.put("neededXp", neededXp);
        }

        skillData.put(statName, calculateMax(statName, value));
    }

    public void add(String statName, Float value) {
        set(statName, get(statName) + value);
    }

    public void subtract(String statName, Float value) {
        set(statName, get(statName) - value);
    }

    public void multiply(String statName, Float value) {
        set(statName, get(statName) * value);
    }

    public void divide(String statName, Float value) {
        set(statName, get(statName) / value);
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Map.Entry<String, Float> entry : new HashMap<>(skillData).entrySet()) {
            nbt.putFloat(entry.getKey(), entry.getValue());
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (String key : new HashMap<>(skillData).keySet()) {
            if (nbt.contains(key)) {
                skillData.put(key, nbt.getFloat(key));
            }
        }
    }

    public void copyFrom(Skill skill) {
        skillData.putAll(skill.getSkillData());
    }
}