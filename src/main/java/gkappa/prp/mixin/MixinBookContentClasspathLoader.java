package gkappa.prp.mixin;

import com.google.gson.JsonElement;
import gkappa.prp.PRP;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookContentClasspathLoader;
import vazkii.patchouli.client.book.BookContentLoader;
import vazkii.patchouli.common.book.Book;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.UncheckedIOException;


@Mixin(BookContentClasspathLoader.class)
public class MixinBookContentClasspathLoader {
	@Inject(at = @At("HEAD"), method = "loadJson", cancellable = true, remap = false)
	private void loadJson(Book book, ResourceLocation resloc, @Nullable ResourceLocation fallback, CallbackInfoReturnable<JsonElement> callback) {
		PRP.LOGGER.debug("Loading {}", resloc);
		ResourceManager manager = Minecraft.getInstance().getResourceManager();
		try {
			if (manager.hasResource(resloc)) {
				callback.setReturnValue(BookContentLoader.streamToJson(manager.getResource(resloc).getInputStream()));
			} else if (fallback != null && manager.hasResource(fallback)) {
				callback.setReturnValue(BookContentLoader.streamToJson(manager.getResource(fallback).getInputStream()));
			}
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
