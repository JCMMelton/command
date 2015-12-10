package com.command;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Person extends Actor {
	
	public int 		team;
	public Texture 	text;
	public Pixmap  	pixmap;
	public Circle 	visionRange;
	public Circle 	fightRange;
	public Circle 	renderCircle;
	public float 	xDest;
	public float    yDest;
	public float 	xPos;
	public float 	yPos;
	public int 		visionRadius 	= 300;
	public int 		renderRadius 	= 10;
	public int 		fightRadius;
	public float 	runSpeed  		= 10.0f;
	public float 	walkSpeed 		= 5.0f;
	public boolean 	fighting 		= false;
	public float 	heath 			= 100.0f;
	
	public Person(int teamInit, int xInit, int yInit) {
		team = teamInit;
		xPos = xInit;
		yPos = yInit;
		fightRadius = (int)Math.round(renderRadius*1.4);
		
		visionRange = new Circle();
		visionRange.setRadius(visionRadius);
		visionRange.setX(xPos);
		visionRange.setY(yPos);
		
		fightRange = new Circle();
		fightRange.setRadius(fightRadius);
		fightRange.setX(xPos);
		fightRange.setY(yPos);
		
		renderCircle = new Circle();
		renderCircle.setRadius(renderRadius);
		renderCircle.setX(xPos);
		renderCircle.setY(yPos);
		
		pixmap = new Pixmap(1+renderRadius*2, 1+renderRadius*2, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.BLUE);

		pixmap.fillCircle(renderRadius, renderRadius, renderRadius);
		text = new Texture(pixmap);
		
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(text, xPos, yPos);
	}
	
	@Override
	public void act(float delta) {
		xPos += runSpeed * delta;
	}
	
	public boolean canReach(Person target) {
		return distCheck(target, this.fightRadius, target.fightRadius);
	}
	
	public boolean canTouch(Person target) {
		return distCheck(target, this.renderRadius, target.renderRadius);
	}
	
	public boolean canSee(Person target) {
		return distCheck(target, this.visionRadius, target.renderRadius);
	}
	
	public boolean distCheck(Person target, int radius1, int radius2) {
//		double xD = Math.abs(this.xPos - target.xPos);
//		double yD = Math.abs(this.yPos - target.yPos);
		return distCalc(this.xPos, this.yPos, target.xPos, target.yPos) < (radius1 + radius2);
	}
	
	public double distCalc(float x1, float y1, float x2, float y2) {
		double xD = Math.abs(x1 - x2);
		double yD = Math.abs(y1 - y2);
		return Math.sqrt((xD*xD) + (yD*yD));
	}
	
	public void update(Person target, float deltaTime) {
		if(canSee(target)) {
			moveTowards(target, deltaTime);
		}
	}
	
	public void update2(Array<Person> peeps, float deltaTime) {
		double shortest = 100000;
		Person target = null;
		for(Person peep: peeps) {
			if(peep.team != this.team) {
				double temp = distCalc(this.xPos, this.yPos, peep.xPos, peep.yPos);
				if(temp < shortest) {
					shortest = temp;
					target   = peep;
				}
			}
		}
		moveTowards(target, deltaTime);
	}
	
	public void setDest(Person target) {
		this.xDest = target.xPos;
		this.yDest = target.yPos;
	}
	
	public void runAway(Person target, float deltaTime) {
		if(fighting) {
			return;
		}
		if(this.canReach(target)) {
			fighting = true;
			return;
		}
		float x = target.xPos;
		float y = target.yPos;
		if(x > xPos) {
			xPos -= walkSpeed*deltaTime;
		} else 
		if(x < xPos) {
			xPos += walkSpeed*deltaTime;
		}
		if(y > yPos) {
			yPos -= walkSpeed*deltaTime;
		} else 
		if(y < yPos) {
			yPos += walkSpeed*deltaTime;
		}
	}
	
	public void moveTowardsDest(float deltaTime) {
//		float x = target.xPos;
//		float y = target.yPos;
//		if(this.canTouch(target)) {
//			return;
//		}
//		if(this.canReach(target)) {
//			fighting = true;
//			return;
//		} else {
			if(xDest > xPos) {
				xPos += runSpeed*deltaTime;
			} else 
			if(xDest < xPos) {
				xPos -= runSpeed*deltaTime;
			}
			if(yDest > yPos) {
				yPos += runSpeed*deltaTime;
			} else 
			if(yDest < yPos) {
				yPos -= runSpeed*deltaTime;
			}
//		}
	}
	
	public void moveTowards(Person target, float deltaTime) {
		float x = target.xPos;
		float y = target.yPos;
		if(this.canTouch(target)) {
			return;
		}
		if(this.canReach(target)) {
			fighting = true;
			return;
		} else {
			if(x > xPos) {
				xPos += runSpeed*deltaTime;
			} else 
			if(x < xPos) {
				xPos -= runSpeed*deltaTime;
			}
			if(y > yPos) {
				yPos += runSpeed*deltaTime;
			} else 
			if(y < yPos) {
				yPos -= runSpeed*deltaTime;
			}
		}
	}

}
