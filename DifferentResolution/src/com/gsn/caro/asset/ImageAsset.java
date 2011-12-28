package com.gsn.caro.asset;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class ImageAsset {
	final String tag = ImageAsset.class.getSimpleName(); 
	public AtlasRegion win;
	public AtlasRegion background;
	public List winAni;

	private void assignContent() {
		TextureAtlas atlas = manager.get("gdx/pack", TextureAtlas.class);
		win = atlas.findRegion("win");
		background = atlas.findRegion("background");		
		winAni = atlas.findRegions("Thang");
	}

	private static ImageAsset _instance;

	public static ImageAsset getInstance() {
		if (_instance == null)
			_instance = new ImageAsset();
		return _instance;
	}

	private ImageAsset() {
	}

	AssetManager manager;
	BitmapFont font;

	public void create() {
		Resolution[] resolutions = { new Resolution(320, 480, "320480"), new Resolution(480, 800, "480800"), new Resolution(480, 856, "480854") };
		ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);
		manager = new AssetManager();
		manager.setLoader(Texture.class, new TextureLoader(resolver));
		manager.setErrorListener(new AssetErrorListener() {

			@Override
			public void error(String fileName, Class type, Throwable throwable) {
				// TODO Auto-generated method stub
				Gdx.app.error("AssetManagerTest", "couldn't load asset '" + fileName + "'", (Exception) throwable);
			}
		});
		loadAll();
		Texture.setAssetManager(manager);
		// font = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		font = new BitmapFont();
	}

	private void loadAll() {
		manager.load("gdx/pack", TextureAtlas.class);
		finishLoading();
		assignContent();
	}

	public void finishLoading() {
		manager.finishLoading();
	}
}
