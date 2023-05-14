package perhaps.progressions.capabilities.skills;

import net.minecraft.nbt.CompoundTag;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Skill {
    private final Map<String, Integer> skillData = Stream.of(
            Map.entry("skillLevel", 0),
            Map.entry("skillXP", 0),
            Map.entry("skillPrestige", 0)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public Map<String, Integer> getSkillData() {
        return skillData;
    }

    public void modifySkillData(String skillDataName, int modifiedData) {
        if (skillData.get(skillDataName) == null) return; // No unexpected entries!
        skillData.put(skillDataName, modifiedData);
    }

    public void saveNBTData(CompoundTag nbt) {
        skillData.forEach(nbt::putInt);
    }

    public void loadNBTData(CompoundTag nbt) {
        skillData.forEach((dataName, data) -> {
            skillData.put(dataName, nbt.getInt((dataName)));
        });
    }

    public void copyFrom(Skill skill) {
        skillData.forEach((dataName, data) -> {
            skillData.put(dataName, skill.getSkillData().get(dataName));
        });
    }
}
