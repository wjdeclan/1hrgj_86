package com.quickjam4;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.quickjam4.VectorizedSprite;

public class QuickJam extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	TextureRegion playerTextureUp, playerTextureDown, playerTextureRight;
	
	Texture[] obstacles = new Texture[7];
	
	ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	
	VectorizedSprite start;
	Texture startTexture;
	
	PlayerController player;
	
	float obstaclesPerSecond = 2;
	float timeToObstacle;
	float currentObstacleTime;
	
	int scene;
	
	boolean sceneSwitchable;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		startTexture = new Texture("start.png");
		start = new VectorizedSprite(startTexture);
		
		obstacles[0] = new Texture("enemy.png");
		obstacles[1] = new Texture("explode.png");
		obstacles[2] = new Texture("explosion.png");
		obstacles[3] = new Texture("obstacle.png");
		obstacles[4] = new Texture("one.png");
		obstacles[5] = new Texture("two.png");
		obstacles[6] = new Texture("three.png");
		
		Texture playerTexture = new Texture("player.png");
		playerTextureDown = new TextureRegion(playerTexture, 0, 0, 20, 32);
		playerTextureUp = new TextureRegion(playerTexture, 20, 0, 20, 32);
		playerTextureRight = new TextureRegion(playerTexture, 40, 0, 18, 32);
		
		player = new PlayerController(playerTextureDown, playerTextureUp, playerTextureRight);
		
		reset();
		
		Gdx.input.setInputProcessor(this);
	}
	
	public void reset() {
		start.setPosition(768/2 - 64, 576/2 - 32);
		start.setMovementVector(new Vector2(0, 0));
		
		player.setPosition(0, 0);
		player.setMovementVector(new Vector2(0, 0));
		
		timeToObstacle = 1 / obstaclesPerSecond;
		currentObstacleTime = 0;
		
		scene = 1;
		sceneSwitchable = true;
		
		obstacleList = new ArrayList<Obstacle>();
	}

	@Override
	public void render () {
		switch (scene) {
		case 1:
		case 2:
			Gdx.gl.glClearColor(0, 0, 0, 1);
			break;
		case 3:
			Gdx.gl.glClearColor(1, 1, 1, 1);
			break;
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		player.update(dt);
		
		switch (scene) {
		case 1:
			break;
		case 2:
			start.setMovementVector(new Vector2(0, start.movement.y + 5));
			start.update(dt);
			if (start.getY() > 1000) {
				scene = 3;
			}
			break;
		case 3:
			currentObstacleTime += dt;
			if (currentObstacleTime > timeToObstacle) {
				Obstacle vs = new Obstacle(obstacles[(int) Math.floor(Math.random()*7)]);
				vs.setSize(24, 24);
				vs.setPosition((float) (Math.random() * 576), (float) (Math.random() * 768));
				obstacleList.add(vs);
				currentObstacleTime -= timeToObstacle;
			}
			for (Obstacle o : obstacleList) {
				o.update(dt);
				if (o.hitbox && Intersector.overlaps(o.getBoundingRectangle(), player.getBoundingRectangle())) {
					reset();
				}
			}
			break;
		}
		
		batch.begin();
		
		player.draw(batch);
		
		switch (scene) {
		case 1:
		case 2:
			start.draw(batch);
			break;
		case 3:
			for (Obstacle o : obstacleList) {
				o.draw(batch);
			}
		}
		
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
		} else if (keycode == Input.Keys.SPACE && sceneSwitchable) {
			scene = scene + 1;
			sceneSwitchable = false;
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
