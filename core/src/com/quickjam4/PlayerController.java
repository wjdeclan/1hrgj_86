package com.quickjam4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by libri on 12/3/16.
 */
public class PlayerController extends VectorizedSprite {

	private float gravityAccel = 0;
	private float friction = 100;
	
	TextureRegion pTD, pTU, pTR;

	public PlayerController(TextureRegion playerTextureDown, TextureRegion playerTextureUp,
			TextureRegion playerTextureRight) {
		super(playerTextureDown);
		pTD = playerTextureDown;
		pTU = playerTextureUp;
		pTR = playerTextureRight;
	}

	public void update(float dt) {
		if (!(getY() < 0 || getY() > (568 - getHeight()))) {
			movement.add(0, gravityAccel * dt);
		} else {
			if (getY() < 0) {
				setPosition(getX(), 0);
				movement.y = 0;
			} else {
				setPosition(getX(), 568 - getHeight());
				movement.y = 0;
			}
		}
		
		if (Math.abs(movement.x) > Math.abs(movement.y)) {
			if (movement.x > 0) {
				this.setRegion(pTR);
				this.setSize(pTR.getRegionWidth(), pTR.getRegionHeight());
			} else {
				this.setRegion(pTR);
				this.flip(true, false);
				this.setSize(pTR.getRegionWidth(), pTR.getRegionHeight());
			}
		} else {
			if (movement.y > 0) {
				this.setRegion(pTU);
				this.setSize(pTU.getRegionWidth(), pTU.getRegionHeight());
			} else {
				this.setRegion(pTD);
				this.setSize(pTD.getRegionWidth(), pTD.getRegionHeight());
			}
		}

		if (movement.x > 0 && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			movement.x -= friction * dt;
			if (movement.x < 0) {
				movement.x = 0;
			}
		} else if (movement.x < 0 && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			movement.x += friction * dt;
			if (movement.x > 0) {
				movement.x = 0;
			}
		}

		if(movement.y > 0 && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			movement.y -= friction * dt;
			if (movement.y < 0) {
				movement.y = 0;
			}
		} else if (movement.y < 0 && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
			movement.y += friction * dt;
			if (movement.y > 0) {
				movement.y = 0;
			}
		}

		if (getX() < 0) {
			movement.x = 0;
			setX(0);
		} else if (getX() > 768 - getWidth()) {
			movement.x = 0;
			setX(768 - getWidth());
		}

		super.update(dt);
	}

	public void flipGravity() {
		gravityAccel *= -1;
	}
	
	public boolean gravityDown() {
		return gravityAccel < 0;
	}

	public Vector2 getMovement() {
		return movement;
	}

	public void jump() {
		movement = new Vector2(super.movement.x, -500 * (Math.abs(gravityAccel) / gravityAccel));
	}

}