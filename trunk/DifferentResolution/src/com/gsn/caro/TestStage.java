package com.gsn.caro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gsn.caro.asset.ImageAsset;

public class TestStage extends Stage {
	Vector2 point = new Vector2();
	public String tag = TestStage.class.getSimpleName();
	public TestStage(float width, float height, boolean stretch) {
		super(width, height, stretch);
		// TODO Auto-generated constructor stub
		Image win = new Image(ImageAsset.getInstance().win);
		Image background = new Image(ImageAsset.getInstance().background);
		addActor(background);
		addActor(win);
		//win.action(Forever.$(Sequence.$(ScaleTo.$(1.1f, 1.1f, 0.3f), ScaleTo.$(1f, 1f, 0.3f))));		
	}	
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		toStageCoordinates(x, y, point);
		Gdx.app.log(tag, "touch Down : " + x + " " + y);
		Gdx.app.log(tag, "convert : " + point.x + " " + point.y);
		return true;		
	}
}
