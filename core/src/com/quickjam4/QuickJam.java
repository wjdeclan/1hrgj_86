package com.quickjam4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.quickjam4.PlayerController;

public class QuickJam extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	TextureRegion playerTextureUp, playerTextureDown, playerTextureRight;
	
	PlayerController player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture playerTexture = new Texture("player.png");
		playerTextureDown = new TextureRegion(playerTexture, 0, 0, 20, 32);
		playerTextureUp = new TextureRegion(playerTexture, 20, 0, 20, 32);
		playerTextureRight = new TextureRegion(playerTexture, 40, 0, 18, 32);
		
		player = new PlayerController(playerTextureDown, playerTextureUp, playerTextureRight);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		player.update(dt);
		
		batch.begin();
		
		player.draw(batch);
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.LEFT) {
			player.getMovement().set(new Vector2(-100, player.movement.y));
		} else if (keycode == Input.Keys.RIGHT) {
			player.getMovement().set(new Vector2(100, player.movement.y));
		} else if (keycode == Input.Keys.UP) {
			player.getMovement().set(new Vector2(player.movement.x, 100));
		} else if (keycode == Input.Keys.DOWN) {
			player.getMovement().set(new Vector2(player.movement.x, -100));
		}
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
