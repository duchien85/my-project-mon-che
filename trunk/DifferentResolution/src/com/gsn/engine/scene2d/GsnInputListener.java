package com.gsn.engine.scene2d;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GsnInputListener implements InputProcessor {
	public List<Stage> stageList = new ArrayList<Stage>();
	
	public GsnInputListener(Stage... stages){
		for (int i = 0; i < stages.length; i++)
			stageList.add(stages[i]);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		for (Stage stage : stageList)
			stage.touchDown(x, y, pointer, button);		
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
