package ch.loewe.normal_use_client.fabricclient.zoom;

import ch.loewe.normal_use_client.fabricclient.modmenu.Config;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.client.util.InputUtil;

public class Zoom {
    private static boolean currentlyZoomed;
    private static KeyBinding keyBinding;
    private static boolean originalSmoothCameraEnabled;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static double zoomLevel = 0.33D;
    public static double zoom_X = 1;

    public Zoom() {
    }

    public static void onInitializeClient() {
        keyBinding = new KeyBinding("loewe.key.zoom", Type.KEYSYM, InputUtil.GLFW_KEY_C, "loewe.category");
        currentlyZoomed = false;
        originalSmoothCameraEnabled = false;
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    public static boolean isZooming() {
        return keyBinding.isPressed();
    }

    public static void manageSmoothCamera() {
        if (zoomStarting()) {
            zoomStarted();
            enableSmoothCamera();
            zoomLevel = getStandardZoom();
        }

        if (zoomStopping()) {
            zoomStopped();
            resetSmoothCamera();
            zoomLevel = getStandardZoom();
        }

    }

    private static boolean isSmoothCamera() {
        return mc.options.smoothCameraEnabled;
    }

    private static void enableSmoothCamera() {
        mc.options.smoothCameraEnabled = true;
    }

    private static void disableSmoothCamera() {
        mc.options.smoothCameraEnabled = false;
    }

    private static boolean zoomStarting() {
        return isZooming() && !currentlyZoomed;
    }

    private static boolean zoomStopping() {
        return !isZooming() && currentlyZoomed;
    }

    private static void zoomStarted() {
        originalSmoothCameraEnabled = isSmoothCamera();
        currentlyZoomed = true;
    }

    private static void zoomStopped() {
        currentlyZoomed = false;
    }

    private static void resetSmoothCamera() {
        if (originalSmoothCameraEnabled) {
            enableSmoothCamera();
        } else {
            disableSmoothCamera();
        }

    }


    public static Double getStandardZoom() {
        switch (Config.getStandardZoom()) {
            case 1 -> {
                return 1D;
            }
            case 2 -> {
                return 0.5D;
            }
            case 3 -> {
                return 0.33D;
            }
            case 4 -> {
                return 0.2D;
            }
            case 5 -> {
                return 0.1D;
            }
            default -> {
                return 0.05D;
            }
        }
    }
}
