package com.github.Jaecuber.swingShootKill.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class JsonLoader extends AsynchronousAssetLoader<String, JsonLoader.JsonParameter>{
    private String data;

    public JsonLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
        data = file.readString();
    }

    @Override
    public String loadSync(AssetManager manager, String fileName, FileHandle file, JsonParameter parameter) {
        return data;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JsonParameter parameter) {
        return null;
    }

    public static class JsonParameter extends AssetLoaderParameters<String> {}
}
