package com.github.Jaecuber.shootSwingKill.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerTool {

    public static void main(String[] args) throws Exception{
        String inputDir = "assets_raw/objects";
        String outputDir = "assets/graphics";
        String packFileName = "objects";

        TexturePacker.process(inputDir, outputDir, packFileName);
    }
}
