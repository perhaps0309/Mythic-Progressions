package perhaps.progressions.enchantments;

public enum EnchantmentRarity {
    COMMON(1),
    UNCOMMON(1.5),
    RARE(2),
    EPIC(3),
    LEGENDARY(5),
    GODLY(10);

    private final double scalingFactor;

    EnchantmentRarity(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public int getMinCost(int level) {
        return (int) (level * 10 * scalingFactor);
    }

    public int getMaxCost(int level) {
        return getMinCost(level) + 50;
    }
}