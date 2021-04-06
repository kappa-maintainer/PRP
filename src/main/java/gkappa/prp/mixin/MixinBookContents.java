package gkappa.prp.mixin;

import java.io.IOException;
import java.io.InputStream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gkappa.prp.PRP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.client.book.BookContents;



@Mixin(BookContents.class)
public class MixinBookContents {
	@Inject(at = @At("HEAD"), method = "loadJson", cancellable = true, remap = false)
	private void loadJson(ResourceLocation resloc, ResourceLocation fallback,  CallbackInfoReturnable<InputStream> callback) {
		PRP.LOGGER.debug("loading json from {}.",resloc);
		try {
			callback.setReturnValue(Minecraft.getInstance().getResourceManager().getResource(resloc).getInputStream());
		} catch (IOException e) {
			//no-op
		}
	}
}
