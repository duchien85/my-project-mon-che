package com.gsn.caro;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gsn.caro.asset.ImageAsset;

public class CaroGame extends Game {
	ImageAsset asset;

	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void create() {
		asset = ImageAsset.getInstance();
		asset.create();
		Stage tmp = new TestStage(320, 240, false);
		setStage(tmp);		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
		asset.finishLoading();
	}
}
