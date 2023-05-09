package perhaps.progressions.client.gui.scroll_wheels;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import perhaps.progressions.MythicProgressions;

import java.util.List;

public class WheelSelectionScreen extends Screen {
    private int centerX;
    private int centerY;
    private int wheelRadius;
    private double angleStep;
    private ScrollWheel currentScrollWheel;
    private int lastHoveredOptionIndex = -1;
    private List<WheelOption> options;


    public static final ResourceLocation PERKS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks.png");
    public static final ResourceLocation ABILITIES_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/abilities.png");
    public static final ResourceLocation SKILLS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills.png");
    public static final ResourceLocation STATS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/stats.png");
    public static final ResourceLocation SETTINGS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/settings.png");
    public static final SoundEvent HOVER_SOUND = new SoundEvent(new ResourceLocation(MythicProgressions.MOD_ID, "hover_sound"));
    public static final SoundEvent CLICK_SOUND = new SoundEvent(new ResourceLocation(MythicProgressions.MOD_ID, "click_sound"));

    public WheelSelectionScreen(ScrollWheel rootScrollWheel) {
        super(new TextComponent("Wheel Selection"));
        this.currentScrollWheel = rootScrollWheel;
        wheelRadius = 90;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void recalculateAngleStep() {
        angleStep = 360.0 / currentScrollWheel.getOptions().size();
    }

    @Override
    protected void init() {
        super.init();
        centerX = width / 2;
        centerY = height / 2;
        angleStep = 360.0 / currentScrollWheel.getOptions().size();

        if (currentScrollWheel.getParent() != null) {
            currentScrollWheel.getOptions().removeIf(option -> option.title.equals("Go Back"));
            currentScrollWheel.addOption(new WheelOption(SETTINGS_ICON, "Go Back", "Go back to previous wheel", () -> {
                currentScrollWheel = currentScrollWheel.getParent();
                init();
            }));
        }

        recalculateAngleStep();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int selectedIndex = getHoveredOptionIndex(mouseX, mouseY);

        // Draw the darker grey background circle using drawCircle
        for (int i = 0; i < currentScrollWheel.getOptions().size(); i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 18, wheelRadius - 18, 0x444444, -1, i);
        }

        for (int i = 0; i < currentScrollWheel.getOptions().size(); i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 15, wheelRadius - 15, 0x88888888, selectedIndex, i);
        }

        for (int i = 0; i < currentScrollWheel.getOptions().size(); i++) {
            drawOption(poseStack, currentScrollWheel.getOptions().get(i), i, i == selectedIndex);
        }

        if (selectedIndex >= 0) {
            WheelOption selectedOption = currentScrollWheel.getOptions().get(selectedIndex);
            drawScaledText(poseStack, selectedOption.title, centerX, centerY - 10, 0xFFFFFF, 1.15F, true);
            drawScaledText(poseStack, selectedOption.description, centerX, centerY + 5, 0xFFFFFF, 0.95F, true);
        }

        if (selectedIndex >= 0 && selectedIndex != lastHoveredOptionIndex) {
            playHoverSound();
        }

        lastHoveredOptionIndex = selectedIndex;

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawOption(PoseStack poseStack, WheelOption option, int index, boolean isSelected) {
        double angle = angleStep * index - 90;
        int x = centerX + (int) (Math.cos(Math.toRadians(angle)) * wheelRadius);
        int y = centerY + (int) (Math.sin(Math.toRadians(angle)) * wheelRadius);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, option.icon);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(poseStack, x - 8, y - 8, 0, 0, 16, 16, 16, 16);
    }

    private int getHoveredOptionIndex(int mouseX, int mouseY) {
        int selectedIndex = -1;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < currentScrollWheel.getOptions().size(); i++) {
            double angle = angleStep * i - 90;
            int x = centerX + (int) (Math.cos(Math.toRadians(angle)) * wheelRadius);
            int y = centerY + (int) (Math.sin(Math.toRadians(angle)) * wheelRadius);
            double distanceFromCenter = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));

            if (distanceFromCenter <= (wheelRadius - 15) && distanceFromCenter < minDistance) {
                minDistance = distanceFromCenter;
                selectedIndex = i;
            }
        }

        return selectedIndex;
    }

    private void playHoverSound() {
        // Play a sound here using Minecraft's SoundEngine
        // For example, to play the "UI.button.hover" sound:
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(HOVER_SOUND, 1.5F));
    }

    private void playClickSound() {
        // Play a sound here using Minecraft's SoundEngine
        // For example, to play the "UI.button.click" sound:
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(HOVER_SOUND, 1.0F));
    }

    private void drawScaledText(PoseStack poseStack, String text, int x, int y, int color, float scale, boolean center) {
        int length = font.width(text);
        if (center) {
            x -= (int) (length * scale / 2);
        }

        poseStack.pushPose();
        poseStack.scale(scale, scale, 1.0F);
        font.draw(poseStack, text, x / scale, y / scale, color);
        poseStack.popPose();
    }

    private void drawCircle(PoseStack poseStack, int centerX, int centerY, int outerRadius, int innerRadius, int color, int selectedIndex, int index) {
        float angle = (float) (angleStep * index) - 90;
        float startAngle = angle - (float) (angleStep / 2);
        float endAngle = angle + (float) (angleStep / 2);

        if (selectedIndex == index) {
            color = 0xFFFFFF;
            startAngle -= 1; // How much it goes along the circle
            endAngle += 1;

            innerRadius += 2; // How much it goes inwards
            outerRadius += 3; // How much it goes outwards
        }

        drawArc(poseStack, centerX, centerY, outerRadius, innerRadius, startAngle, endAngle, color, selectedIndex == index);
    }

    private void drawArc(PoseStack poseStack, int centerX, int centerY, int outerRadius, int innerRadius, float startAngle, float endAngle, int color, boolean isSelected) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        Matrix4f matrix = poseStack.last().pose();

        float startRadians = (float) Math.toRadians(startAngle);
        float endRadians = (float) Math.toRadians(endAngle);
        float step = (endRadians - startRadians) / 64;

        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        for (float angle = startRadians; angle <= endRadians + step; angle += step) {
            if (color != 0x444444) {
                if (angle < startRadians + Math.toRadians(1) || angle > endRadians - Math.toRadians(1)) {
                    continue;
                }
            }

            float xOuter = centerX + (float) (Math.cos(angle) * outerRadius);
            float yOuter = centerY + (float) (Math.sin(angle) * outerRadius);
            float xInner = centerX + (float) (Math.cos(angle) * innerRadius);
            float yInner = centerY + (float) (Math.sin(angle) * innerRadius);

            bufferBuilder.vertex(matrix, xOuter, yOuter, 0).color(red, green, blue, isSelected ? 1.0F : 0.7F).endVertex();
            bufferBuilder.vertex(matrix, xInner, yInner, 0).color(red, green, blue, isSelected ? 1.0F : 0.7F).endVertex();
        }

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();

        bufferBuilder.end();
        BufferUploader.end(bufferBuilder);

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int hoveredOptionIndex = getHoveredOptionIndex((int) mouseX, (int) mouseY);
        if (button == 0 && hoveredOptionIndex != -1) {  // 0 is the left mouse button
            WheelOption hoveredOption = currentScrollWheel.getOptions().get(hoveredOptionIndex);
            hoveredOption.action.run();  // Run the action associated with the hovered option
            playClickSound();

            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}