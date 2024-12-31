package fr.raphm.raphscore.mixin.client;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// Sets the title screen to Raph's Core's one.
@Mixin(MinecraftClient.class)
public class TitleScreenMixin {
    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    private void setScreenMixin(Screen screen, CallbackInfo info)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (screen.getClass() == TitleScreen.class)
        {
            mc.currentScreen = new fr.raphm.raphscore.gui.screen.TitleScreen();
            info.cancel();
        }
    }
}
