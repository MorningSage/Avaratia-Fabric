package avaritia.api.registration;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * Implemented on an item for model registration, completely arbitrary.
 */
public interface IModelRegister {

    /**
     * Called when it is time to initialize models in preInit.
     */
    @Environment(EnvType.CLIENT)
    void registerModels();
}
