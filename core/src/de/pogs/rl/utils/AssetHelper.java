package de.pogs.rl.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import org.w3c.dom.Text;

public class AssetHelper {

    public final AssetManager assetManager;

    private HashMap<String, AssetDescriptor> images = new HashMap<String, AssetDescriptor>();
    private HashMap<String, AssetDescriptor> sounds = new HashMap<String, AssetDescriptor>();
    private HashMap<String, AssetDescriptor> fonts = new HashMap<String, AssetDescriptor>();
    private HashMap<String, AssetDescriptor> fontImages = new HashMap<String, AssetDescriptor>();
    // private String[] dirs = { "images", "sounds", "fonts", "particles" };

    private FileHandle baseDir;

    public AssetHelper() {
        assetManager = new AssetManager();
        // Gdx.files.internal("dd/");
        baseDir = Gdx.files.getFileHandle("", FileType.Local);
        System.out.println(baseDir.file().getAbsolutePath());
    }

    public void loadAll() {
        for (FileHandle entry : baseDir.list()) {
            System.out.println(entry.name());
            if (entry.isDirectory()) {
                switch (entry.name()) {
                    case "images":
                        loadImages(entry.list());
                        break;
                    case "sounds":
                        loadSounds(entry.list());
                        break;
                    case "fonts":
                        // loadFonts(entry.list());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void loadFonts(FileHandle[] list) {
        for (FileHandle file : list) {
            if (file.extension() == "png") {
                System.out.println(file.path());
                AssetDescriptor asset = new AssetDescriptor<>(file.path(), Texture.class);
                fontImages.put(file.nameWithoutExtension(), asset);
                assetManager.load(asset);
            } else {
                System.out.println(file.path());
                AssetDescriptor asset = new AssetDescriptor<>(file.path(), BitmapFont.class);
                fonts.put(file.nameWithoutExtension(), asset);
                assetManager.load(asset);
            }
        }
    }

    public BitmapFont getFont(String name) {
        return (BitmapFont) this.assetManager.get(fonts.get(name));
    }

    public Texture getFontImage(String name) {
        return (Texture) this.assetManager.get(fontImages.get(name));
    }

    private void loadSounds(FileHandle[] list) {
        for (FileHandle file : list) {
            System.out.println(file.path());
            AssetDescriptor asset = new AssetDescriptor<>(file.path(), Sound.class);
            sounds.put(file.nameWithoutExtension(), asset);
            assetManager.load(asset);
        }
    }

    public Sound getSound(String name) {
        return (Sound) this.assetManager.get(sounds.get(name));
    }

    private void loadImages(FileHandle[] list) {
        for (FileHandle file : list) {
            System.out.println(file.path());
            AssetDescriptor asset = new AssetDescriptor(file.path(), Texture.class);
            images.put(file.nameWithoutExtension(), asset);
            assetManager.load(asset);
        }
    }

    public Texture getImage(String name) {
        return (Texture) this.assetManager.get(images.get(name));
    }
}
