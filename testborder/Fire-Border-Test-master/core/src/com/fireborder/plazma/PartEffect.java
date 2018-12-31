package com.fireborder.plazma;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PartEffect {
	
	PartPlazma plazma;
	PartPlazma smoke;
	
	public PartEffect(PartPlazma plazma, PartPlazma smoke) {
		this.plazma = plazma;
		this.smoke = smoke;
	}
	
	public void starter() {
		plazma.starter();
		smoke.starter();
	}
	
	public void start() {
		plazma.start();
		smoke.start();
	}
	
	public void stop() {
		plazma.stop();
		smoke.stop();
	}
	
	public void draw(SpriteBatch batch, float x, float y, float speed, float sizePixels) {
		plazma.draw(batch, x, y, speed, sizePixels);
		smoke.draw(batch, x, y, speed, sizePixels);
	}
	
	public PartPlazma getPlazma(){
		return plazma;
	}
	
	public PartPlazma getSmoke(){
		return smoke;
	}
	
	public void setPlazma(PartPlazma plazma){
		this.plazma = plazma;
	}
	
	public void setSmoke(PartPlazma smoke){
		this.smoke = smoke;
	}
	
	public void dispose() {
		plazma.dispose();
		smoke.dispose();
		plazma = null;
		smoke = null;
	}

}
