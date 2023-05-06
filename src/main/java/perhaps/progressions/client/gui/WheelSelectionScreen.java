package perhaps.progressions.client.gui;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.TextComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import perhaps.progressions.MythicProgressions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WheelSelectionScreen extends Screen {
    private int centerX;
    private int centerY;
    private int wheelRadius;
    private double angleStep;

    private static class WheelOption {
        public final ResourceLocation icon;
        public final String title;
        public final String description;
        public final Runnable action;

        public WheelOption(ResourceLocation icon, String title, String description, Runnable action) {
            this.icon = icon;
            this.title = title;
            this.description = description;
            this.action = action;
        }
    }

    private List<WheelOption> options;

    private static final ResourceLocation PERKS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks.jpg");
    private static final ResourceLocation ABILITIES_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/abilities.png");
    private static final ResourceLocation SKILLS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills.png");
    private static final ResourceLocation STATS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/stats.png");

    public WheelSelectionScreen() {
        super(new TextComponent("Wheel Selection"));
        options = new ArrayList<>();
        options.add(new WheelOption(PERKS_ICON, "Perks", "Select a perk to unlock", () -> {
            // Add code to execute when the Perks option is selected
        }));

        options.add(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage", () -> {
            // Add code to execute when the Abilities option is selected
        }));

        options.add(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills", () -> {
            // Add code to execute when the Skills option is selected
        }));

        options.add(new WheelOption(STATS_ICON, "Stats", "View your stats", () -> {
            // Add code to execute when the Stats option is selected
        }));

        wheelRadius = 90;
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        centerX = width / 2;
        centerY = height / 2;
        angleStep = 360.0 / options.size();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int selectedIndex = getHoveredOptionIndex(mouseX, mouseY);

        // Draw the darker grey background circle using drawCircle
        for (int i = 0; i < options.size(); i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 18, wheelRadius - 18, 0x444444, -1, i);
        }


        for (int i = 0; i < options.size(); i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 15, wheelRadius - 15, 0x88888888, selectedIndex, i);
        }

        for (int i = 0; i < options.size(); i++) {
            drawOption(poseStack, options.get(i), i, i == selectedIndex);
        }

        if (selectedIndex >= 0) {
            WheelOption selectedOption = options.get(selectedIndex);
            drawScaledText(poseStack, selectedOption.title, centerX, centerY - 10, 0xFFFFFF, 1.15F, true);
            drawScaledText(poseStack, selectedOption.description, centerX, centerY + 5, 0xFFFFFF, 0.95F, true);
        }

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawOption(PoseStack poseStack, WheelOption option, int index, boolean isSelected) {
        double angle = angleStep * index;
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

        for (int i = 0; i < options.size(); i++) {
            double angle = angleStep * i;
            int x = centerX + (int) (Math.cos(Math.toRadians(angle)) * wheelRadius);
            int y = centerY + (int) (Math.sin(Math.toRadians(angle)) * wheelRadius);
            double distance = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));

            if (distance <= 20 && distance < minDistance) {
                minDistance = distance;
                selectedIndex = i;
            }
        }

        return selectedIndex;
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
        float angle = (float) (angleStep * index);
        float startAngle = angle - (float) (angleStep / 2);
        float endAngle = angle + (float) (angleStep / 2);

        if (selectedIndex == index) {
            color = 0xFFFFFF;
            startAngle -= 1; // How much it goes along the circle
            endAngle += 1;

            innerRadius += 2; // How much it goes inwards
            outerRadius += 2; // How much it goes outwards
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

            bufferBuilder.vertex(matrix, xOuter, yOuter, 0).color(red, green, blue, isSelected ? 1.0F : 0.5F).endVertex();
            bufferBuilder.vertex(matrix, xInner, yInner, 0).color(red, green, blue, isSelected ? 1.0F : 0.5F).endVertex();
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
}