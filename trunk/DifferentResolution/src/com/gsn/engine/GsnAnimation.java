package com.gsn.engine;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GsnAnimation extends Actor {
	final TextureRegion[] keyFrames;
	public float frameDuration;
	private float time = 0;
	private int key = -1;
	public GsnAnimation(float frameDuration, List keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = new TextureRegion[keyFrames.size()];
		for (int i = 0, n = keyFrames.size(); i < n; i++) {
			this.keyFrames[i] = (TextureRegion)keyFrames.get(i);
		}
		if (this.keyFrames.length > 0){
			width = this.keyFrames[0].getRegionWidth();
			height = this.keyFrames[0].getRegionHeight();
		}
	}
	
	public TextureRegion[] getKeyFrames() {
		return keyFrames;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(keyFrames[key], x, y, width, height);	
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		key =  (int)(time / frameDuration);
		if (key >= keyFrames.length)			
			getStage().removeActor(this);		
	}

	@Override
	public Actor hit(float x, float y) {
		Gdx.app.log("Gsn Animation", "click: " + x + " " + y + " ** " + this.x + " " + this.y + " " + width + " " + height);
		if (x >= 0 && x <= width && y >= 0 && y <= height)
			return this;
		else
			return null;
	}
}