package com.gsn.caro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gsn.caro.asset.ImageAsset;


public class GlobalStage extends Stage {
	public String tag = GlobalStage.class.getSimpleName();
	Vector2 point = new Vector2();		
	ImageAsset asset;
	Image avatar;	
	
	SpriteBatch myBatch = new SpriteBatch();
	
	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	
	Sprite pieceO;
	
	public GlobalStage(float width, float height, boolean stretch) {		
		super(width, height, stretch);
		Gdx.app.log(tag, "kt " + width + " " + height + " ");
		Gdx.app.log(tag, "center " + centerX + " " + centerY + " ");
		
		asset = ImageAsset.getInstance();
		avatar = new Image(asset.avatar);
		avatar.x = -100;
		avatar.y = -100;
		avatar.setClickListener(new ClickListener() {			
			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.app.log(tag, "click GLOBAL!!!");
			}
		});
		
		addActor(new Image(asset.background));
		
		addActor(avatar);		
		camera.translate(-100, -100, 0);
		
		pieceO = new Sprite(asset.pieceO);
	}		
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub		
		super.draw();				
		myBatch.begin();
		pieceO.setPosition(100, 100);
		pieceO.draw(myBatch);
		myBatch.end();
	}
	
	Vector2 vector = new Vector2();
	Camera newCam = new OrthographicCamera();
	Camera tmp;
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub		
		y = Gdx.graphics.getHeight() - y;
		Rectangle r = pieceO.getBoundingRectangle();
//		Gdx.app.log(tag, " rec   : " + r);
//		Gdx.app.log(tag, " click : " + x + " " + y);
//		Gdx.app.log(tag, " ------------ ");
		if (pointInRectangle(r, x, y)){
			Gdx.app.log(tag, "click LOCAL!!!");			
			return true;
		} 		
				
		
		y = Gdx.graphics.getHeight() - y;
		return super.touchUp(x, y, pointer, button);
	}
}
