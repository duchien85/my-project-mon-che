package com.gsn.caro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gsn.caro.asset.ImageAsset;
import com.gsn.engine.GsnAnimation;

public class TestStage extends Stage {
	public String tag = TestStage.class.getSimpleName();

	Vector2 point = new Vector2();
	Image win;
	GsnAnimation winAni;

	public TestStage(float width, float height, boolean stretch) {
		super(width, height, stretch);
		// TODO Auto-generated constructor stub
		win = new Image(ImageAsset.getInstance().win);
		Image background = new Image(ImageAsset.getInstance().background);
		addActor(background);
//		addActor(win);
//		win.x = 100;
//		win.y = 100;
//		Gdx.app.log(tag, " win : " + win.x + " " + win.y);
		winAni = new GsnAnimation(0.3f, ImageAsset.getInstance().winAni);
		addActor(winAni);
		winAni.x = 200;
		winAni.y = 100;
		
//		winAni.width = 50;
//		winAni.height = 200;				

		// addActor(ImageAsset.getInstance().loseAni);
		// win.action(Forever.$(Sequence.$(ScaleTo.$(1.1f, 1.1f, 0.3f),
		// ScaleTo.$(1f, 1f, 0.3f))));
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		toStageCoordinates(x, y, point);
		Actor actor = hit(point.x, point.y);
		if (actor == null)
			Gdx.app.log(tag, "click nothing!!!");
		else if (actor == winAni)
			Gdx.app.log(tag, "click WIN ANIMATION!!!");
		return true;
	}
}
