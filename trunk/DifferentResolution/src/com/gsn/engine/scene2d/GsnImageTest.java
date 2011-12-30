package com.gsn.engine.scene2d;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GsnImageTest extends Image {
	SpriteBatch myBatch;
	Camera oldCam;
	Vector3 vector = new Vector3();
	public GsnImageTest (TextureRegion texture, Camera oldCam, SpriteBatch batch) {
		super(new TextureRegion(texture));
		this.myBatch = batch;		
		this.oldCam = oldCam;
	}	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.end();
		myBatch.begin();
		super.draw(myBatch, parentAlpha);
		myBatch.end();
		batch.begin();
	}
		
	@Override
	public void touchUp(float x, float y, int pointer) {
		// TODO Auto-generated method stub	
		vector.set(x, y, 0);
		oldCam.unproject(vector);
		super.touchUp(vector.x, vector.y, pointer);
	}
}
