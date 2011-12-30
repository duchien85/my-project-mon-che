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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class ImageAsset {
	final String tag = ImageAsset.class.getSimpleName(); 
	public AtlasRegion win;
	public AtlasRegion background;
	public AtlasRegion pieceO;
	public AtlasRegion pieceX;
	public AtlasRegion avatar;
	public List winAni;
	AssetManager manager;
	BitmapFont font;
	public TextureRegion bet;


	private void assignContent() {
		TextureAtlas atlas = manager.get("gdx/pack", TextureAtlas.class);
		win = atlas.findRegion("win");
		background = atlas.findRegion("background");		
		winAni = atlas.findRegions("Thang");
		pieceO = atlas.findRegion("dauO");
		pieceX = atlas.findRegion("dauX1");
		avatar = atlas.findRegion("avatarIngame");
		bet = atlas.findRegion("bet5001");
	}

	private static ImageAsset _instance;

	public static ImageAsset getInstance() {
		if (_instance == null)
			_instance = new ImageAsset();
		return _instance;
	}

	private ImageAsset() {
	}
	
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
//===================================================================
//	Anh khóc, vì giờ đây anh đã mất em rồi
//	Anh khóc, vì giờ đây em đã xa thật rồi.
//	Anh nhớ, lời hẹn ước ta không xa rời.
//	Mà giờ đây, sao chỉ anh lẻ loi
//	Anh cứ, cố gắng tại sao vẫn cứ xa vời
//	Anh và em, dường như ta đã hết yêu nhau rồi.
//	Em hãy nói cho anh nghe đi hỡi người yêu ơi.
//	Tại sao giờ chúng ta lìa đôi.
//	===================================================================
//	Tại anh đã vô tâm hay tại anh không quan tâm em mỗi ngày.
//	Để giờ đây khi lời anh nói, em không tin anh nữa vậy.
//	Trong tình yêu, đôi lúc ta hay giận hờn anh biết.
//	Nhưng anh thấy,giờ em không còn yêu anh.
//	Vậy thôi anh cho em đi về nơi em chưa bắt đầu
//	Nơi mà em khi chưa quen anh, anh thấy em vui hơn nhiều.
//	Anh xin lỗi, vì đã cướp mất khoảng trời của em, nhưng có người sẽ cho em lại một bầu trời. 
