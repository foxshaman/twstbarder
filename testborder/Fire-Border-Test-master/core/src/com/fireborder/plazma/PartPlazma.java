package com.fireborder.plazma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PartPlazma {
	
	private FileHandle part;
	private FileHandle image;
	private float size;
	private ParticleEffect pr;
	private float tempSize;
	private float x;
	private float duration;
	private float durationx;
	private float delta;
	private boolean isStart = false;
	
	public PartPlazma(FileHandle part, FileHandle image, float size) {
		this.part = part;
		this.image = image;
		this.size = size;
		this.tempSize = 1;
		pr = new ParticleEffect();
		pr.load(part, image);
		this.duration = pr.getEmitters().first().getDuration().getLowMax();
		if(duration > 100) {
			this.durationx = (duration - 100) / 1000;
		}
		if(duration <= 100) {
			this.durationx = duration / 2 / 1000;
		}
	}
	
	public void starter() {
		pr.start();
	}
	
	public void start() {
		isStart = true;
	}
	
	public void stop() {
		isStart = false;
	}
	
	public void draw(SpriteBatch batch, float x, float y, float speed, float sizePixels) {
		if(isStart == true) {
			pr.setPosition(x, y);
			this.x = tempSize * sizePixels / size;
			pr.scaleEffect(this.x / tempSize);
			tempSize = this.x;
			size = sizePixels;
			pr.draw(batch);
			pr.update(Gdx.graphics.getDeltaTime() * speed);
			delta = delta + Gdx.graphics.getDeltaTime() * speed;
			if(delta >= durationx) {
				delta = 0;
				pr.start();
			}
		}
	}
	
	public ParticleEffect getParticleEffect() {
		return pr;
	}
	
	public FileHandle getPart() {
		return part;
	}
	
	public FileHandle getImage() {
		return image;
	}
	
	public float getSize() {
		return size;
	}
	public float getTempSize() {
		return tempSize;
	}
	
	public void setSize(float size) {
		this.size = size;
	}
	
	public boolean isStart() {
		return isStart;
	}
	
	public float getDuration() {
		return duration;
	}
	
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public float getDurationX(){
		return durationx;
	}
	
	public void setDurationX(float durationx) {
		this.durationx = durationx;
	}
	
	public void dispose() {
		pr.dispose();
		pr = null;
		part = null;
		image = null;
	}

}
