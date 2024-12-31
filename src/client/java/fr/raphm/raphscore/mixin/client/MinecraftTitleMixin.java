package fr.raphm.raphscore.mixin.client;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
 * Mixin to override
 */
@Mixin(MinecraftClient.class)
public class MinecraftTitleMixin {
	@Inject(at = @At("HEAD"), method = "getWindowTitle()Ljava/lang/String;", cancellable = true)
	private void getWindowTitleMixin(CallbackInfoReturnable<String> info) {
		MinecraftClient mc = MinecraftClient.getInstance();

		StringBuilder stringBuilder = new StringBuilder("Minecraft - ");
		stringBuilder.append(mc.getGameProfile().getName());

		info.setReturnValue(stringBuilder.toString());
	}
}