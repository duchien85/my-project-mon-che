package com.gsn.caro;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gsn.caro.asset.ImageAsset;
import com.gsn.engine.scene2d.GsnInputListener;

public class CaroGame extends Game {
	ImageAsset asset;	
	Stage global;	
	
	@Override
	public void create() {
		asset = ImageAsset.getInstance();
		asset.create();
		global = new GlobalStage(240, 320, false);					
		Gdx.input.setInputProcessor(global);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		global.act(Gdx.graphics.getDeltaTime());
		global.draw();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
		asset.finishLoading();
	}
}
