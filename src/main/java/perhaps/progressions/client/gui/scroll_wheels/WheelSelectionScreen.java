package perhaps.progressions.client.gui.scroll_wheels;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import perhaps.progressions.MythicProgressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WheelSelectionScreen extends Screen {
    private int centerX;
    private int centerY;
    private int wheelRadius;
    private double angleStep;
    private ScrollWheel currentScrollWheel;
    private int lastHoveredOptionIndex = -1;
    private List<WheelOption> options;

    private int currentOptionIndex;
    private int tickCounter;

    boolean isReverseOrder = false;
    private int unloadOptionIndex = -1;

    public static int darkOutline = Integer.parseInt("333332", 16);
    public static int lightOutline = Integer.parseInt("FFFFFF", 16);

    public static int darkBase = Integer.parseInt("1B1A1D", 16);
    public static int lightBase = Integer.parseInt("A2A2A2", 16);

    public static int outlineColor = darkOutline;
    public static int baseColor = darkBase;
    public static boolean darkMode = true;

    public static final ResourceLocation PERKS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks/perks.png");
    public static final ResourceLocation ABILITIES_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/abilities/abilities.png");
    public static final ResourceLocation SKILLS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills/skills.png");
    public static final ResourceLocation STATS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/stats.png");
    public static final ResourceLocation SETTINGS_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/settings/settings.png");
    public static final ResourceLocation MODE_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/settings/mode.png");
    public static final ResourceLocation BACK_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/back.png");

    public static final ResourceLocation REROLL_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/prestige.png");
    public static final ResourceLocation UPGRADE_ICON = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/upgrade.png");

    public static final ResourceLocation PERK_SACRIFICE = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks/perk_sacrifice.png");
    public static final ResourceLocation SKILL_PRESTIGE = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks/perk_sacrifice.png");

    public static final ResourceLocation PERK_UPGRADE = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks/perk_upgrade.png");
    public static final ResourceLocation SKILL_UPGRADE = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills/skill_upgrade.png");
    public static final ResourceLocation ABILITY_UPGRADE = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/abilities/ability_upgrade.png");

    public static final ResourceLocation PERK_INFORMATION = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/perks/perk_information.png");
    public static final ResourceLocation SKILL_INFORMATION = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/skills/skill_information.png");
    public static final ResourceLocation ABILITY_INFORMATION = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/abilities/ability_information.png");
    public static final ResourceLocation MAGIC_INFORMATION = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/magic_information.png");
    public static final ResourceLocation GENERAL_INFORMATION = new ResourceLocation(MythicProgressions.MOD_ID + ":textures/gui/icons/general_information.png");
    public static final SoundEvent HOVER_SOUND = new SoundEvent(new ResourceLocation(MythicProgressions.MOD_ID, "hover_sound"));
    public static final SoundEvent LEVEL_UP_SOUND = new SoundEvent(new ResourceLocation(MythicProgressions.MOD_ID, "level_up_sound"));
    public static final SoundEvent ERROR_SOUND = new SoundEvent(new ResourceLocation(MythicProgressions.MOD_ID, "error_sound"));

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

    private static List<WheelOption> addElement(List<WheelOption> arr, WheelOption element, int position) {
        List<WheelOption> result = new ArrayList<>(arr.size() + 1);
        result.addAll(arr.subList(0, position));
        result.add(element);
        result.addAll(arr.subList(position, arr.size()));
        return result;
    }

    @Override
    protected void init() {
        //super.init();
        centerX = width / 2;
        centerY = height / 2;
        angleStep = 360.0 / currentScrollWheel.getOptions().size();

        // TODO: Automatically seperate wheel into pages once exceeding 10 options
        if (currentScrollWheel.getParent() != null) {
            currentScrollWheel.getOptions().removeIf(option -> option.title.equals("Go Back"));

            angleStep = 360.0 / (currentScrollWheel.getOptions().size() + 1);

            // Calculate the desired angle for the "Go Back" option
            double desiredAngleBack = 270; // Angle pointing towards the left side
            double desiredAngleForward = 90; // Angle pointing towards the right side

            // Calculate the index of the option closest to the desired angle
            int optionCount = currentScrollWheel.getOptions().size();
            if (currentScrollWheel.getOptions().size() >= 10) {
                angleStep = 360.0 / (currentScrollWheel.getOptions().size() + 2);
                currentScrollWheel.getOptions().removeIf(option -> option.title.equals("Go Forward"));

                List<WheelOption> currentPage = new ArrayList<>();
                List<WheelOption> nextPageOptions = new ArrayList<>();

                for (int i = 0; i < currentScrollWheel.getOptions().size(); i++) {
                    if (i > 10) {
                        nextPageOptions.add(currentScrollWheel.getOptions().get(i));
                    } else {
                        currentPage.add(currentScrollWheel.getOptions().get(i));
                    }
                }

                currentScrollWheel.setOptions(currentPage);

                int closestOptionIndexForward = (int) Math.round(desiredAngleForward / angleStep);
                closestOptionIndexForward = Math.max(0, Math.min(optionCount, closestOptionIndexForward));

                WheelOption goForward = new WheelOption(BACK_ICON, "Go Forward", "Go forward to next wheel", () -> {
                    ScrollWheel nextWheel = new ScrollWheel(currentScrollWheel);
                    for (WheelOption option : nextPageOptions) {
                        nextWheel.addOption(option);
                        currentScrollWheel.addOption(option);
                    }

                    currentScrollWheel = nextWheel;
                    init();
                });

                goForward.addRotation(180);

                currentScrollWheel.setOptions(addElement(currentScrollWheel.getOptions(), goForward, closestOptionIndexForward));

                optionCount = currentScrollWheel.getOptions().size();
            }

            int closestOptionIndex = (int) Math.round(desiredAngleBack / angleStep);

            // Ensure the closest option index is within the valid range
            closestOptionIndex = Math.max(0, Math.min(optionCount, closestOptionIndex));

            WheelOption goBack = new WheelOption(BACK_ICON, "Go Back", "Go back to previous wheel", () -> {
                currentScrollWheel = currentScrollWheel.getParent();
                init();
            });

            currentScrollWheel.setOptions(addElement(currentScrollWheel.getOptions(), goBack, closestOptionIndex));
        }

        this.currentOptionIndex = 0;
        this.tickCounter = 0;
        this.isReverseOrder = false;
        this.unloadOptionIndex = -1;

        recalculateAngleStep();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        List<WheelOption> wheelOptions = this.currentScrollWheel.getOptions();
        int selectedIndex = getHoveredOptionIndex(mouseX, mouseY);
        int totalSize = wheelOptions.size() - 1;

        // Draw the darker grey background circle using drawCircle
        for (int i = 0; i <= totalSize; i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 17, wheelRadius - 17, outlineColor, -1, i);
        }

        for (int i = 0; i <= totalSize; i++) {
            drawCircle(poseStack, centerX, centerY, wheelRadius + 15, wheelRadius - 15, baseColor, selectedIndex, i);
            drawOption(poseStack, wheelOptions.get(i), i, i == selectedIndex);
        }

        if (selectedIndex >= 0) {
            WheelOption selectedOption = wheelOptions.get(selectedIndex);

            int moveHeight = drawScaledText(poseStack, selectedOption.description, centerX, centerY + 5, 0xFFFFFF, 0.95F, true);
            drawScaledText(poseStack, selectedOption.title, centerX, centerY - moveHeight, 0xFFFFFF, 1.15F, true);


            if(selectedOption.onHover != null) {
                selectedOption.onHover.run();
            }
        }

        if (selectedIndex >= 0 && selectedIndex != lastHoveredOptionIndex) {
            playHoverSound();
        }

        lastHoveredOptionIndex = selectedIndex;

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawOption(PoseStack poseStack, WheelOption option, int index, boolean isSelected) {
        double angle = angleStep * index - 90;
        int adjustedRadius = isSelected ? wheelRadius + 3 : wheelRadius;
        int x = centerX + (int) (Math.cos(Math.toRadians(angle)) * adjustedRadius);
        int y = centerY + (int) (Math.sin(Math.toRadians(angle)) * adjustedRadius);
        int iconSize = isSelected ? 22 : 20;  // Increase icon size when selected

        Float rotation = option.rotation;
        if (rotation == null) {
            rotation = 0.0f;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, option.icon);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.pushPose();  // Save the current transformation matrix
        poseStack.translate(x, y, 0);  // Translate to the icon position
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(rotation));  // Apply rotation
        poseStack.translate(-(float) iconSize / 2, -(float) iconSize / 2, 0);  // Translate back to the icon's origin
        blit(poseStack, 0, 0, 0, 0, iconSize, iconSize, iconSize, iconSize);
        poseStack.popPose();  // Restore the previous transformation matrix
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
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(HOVER_SOUND, 1.5F));
    }

    private void playClickSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(HOVER_SOUND, 1.0F));
    }

    private int drawScaledText(PoseStack poseStack, String text, int x, int y, int color, float scale, boolean center) {
        List<String> lines = wordWrap(text, 130);  // Adjust this value to suit your needs

        // Calculate the total height of the text
        int totalHeight = lines.size() * 10;  // 10 is the height of a line in pixels

        // Calculate the move-up height
        int moveUpHeight = Math.min(totalHeight / 2, 70);

        // Move everything up by the move-up height
        y -= moveUpHeight;

        for (String line : lines) {
            int length = font.width(line);

            int currentX = x;
            int currentY = y;
            if (center) {
                currentX -= (int) (length * scale / 2);
                currentY -= (int) (10 * scale / 2);
            }

            poseStack.pushPose();
            poseStack.scale(scale, scale, 1.0F);
            font.draw(poseStack, line, currentX / scale, currentY / scale, color);
            poseStack.popPose();

            y += 10;  // Adjust this value to modify the space between  lines
        }

        return moveUpHeight;
    }

    private List<String> wordWrap(String text, int maxLineWidth) {
        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("\n");  // Split the text into paragraphs at each newline character

        for (String paragraph : paragraphs) {
            if (font.width(paragraph) < maxLineWidth) {
                lines.add(paragraph);
            } else {
                String[] words = paragraph.split(" ");
                StringBuilder currentLine = new StringBuilder(words[0]);
                for (int i = 1; i < words.length; i++) {
                    if (font.width(currentLine + " " + words[i]) >= maxLineWidth) {
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder(words[i]);
                    } else {
                        currentLine.append(" ").append(words[i]);
                    }
                }
                lines.add(currentLine.toString());
            }
        }

        return lines;
    }


    private void drawCircle(PoseStack poseStack, int centerX, int centerY, int outerRadius, int innerRadius, int color, int selectedIndex, int index) {
        float angle = (float) (angleStep * index) - 90;
        float startAngle = angle - (float) (angleStep / 2);
        float endAngle = angle + (float) (angleStep / 2);

        if (selectedIndex == index) {
            startAngle -= 1; // How much it goes along the circle
            endAngle += 1;

            innerRadius += 3; // How much it goes inwards
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
            if (color != outlineColor) {
                if (angle < startRadians + Math.toRadians(1) || angle > endRadians - Math.toRadians(1)) {
                    continue;
                }
            }

            float xOuter = centerX + (float) (Math.cos(angle) * outerRadius);
            float yOuter = centerY + (float) (Math.sin(angle) * outerRadius);
            float xInner = centerX + (float) (Math.cos(angle) * innerRadius);
            float yInner = centerY + (float) (Math.sin(angle) * innerRadius);

            bufferBuilder.vertex(matrix, xOuter, yOuter, 0).color(red, green, blue, 0.8F).endVertex();
            bufferBuilder.vertex(matrix, xInner, yInner, 0).color(red, green, blue, 0.8F).endVertex();
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
            if (hoveredOption == null) return super.mouseClicked(mouseX, mouseY, button);

            hoveredOption.action.run();  // Run the action associated with the hovered option
            if (hoveredOption.soundEffect != null) {
                if (hoveredOption.conditionalCallback.get()) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(hoveredOption.soundEffect, 1.0F));
                } else if(hoveredOption.playErrorSound) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ERROR_SOUND, 1.0F));
                } else playClickSound();
            } else {
                playClickSound();
            }

            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}