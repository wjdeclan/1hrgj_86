package com.quickjam4;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Obstacle extends VectorizedSprite {
	static TextureRegion pop1, pop2, pop3;
	Texture img;
	
	boolean hitbox = false;
	
	static {
		Texture temp = new Texture("pop.png");
		pop3 = new TextureRegion(temp, 0, 64, 32, 32);
		pop2 = new TextureRegion(temp, 0, 32, 32, 32);
		pop1 = new TextureRegion(temp, 0, 0, 32, 32);
	}
	float time;
	
	public Obstacle(Texture img) {
		super(pop1);
		this.img = img;
		time = 0;
	}
	
	public void update(float dt) {
		super.update(dt);
		time = time + dt;
		if (time > 0.5 && time < 1) {
			this.setRegion(pop2);
		} else if (time > 1 && time < 1.5) {
			this.setRegion(pop3);
		} else if (time > 1.5) {
			this.setRegion(img);
			this.setSize(img.getWidth(), img.getHeight());
			this.setMovementVector(new Vector2(0, 100));
			hitbox = true;
		}
	}

}
