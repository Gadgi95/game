package com.mygdx.game.gadgi95;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.gadgi95.Item.Type;

public class FlyingText {

  private Vector2 position;
  private Vector2 velosity;
  private StringBuilder text;
  private float time;
  private float timeMax;
  private boolean active;

  public Vector2 getPosition() {
    return position;
  }

  public boolean isActive() {
    return active;
  }

  public void deactivate() {
    this.active = false;
  }

  public FlyingText() {
    this.position = new Vector2(0, 0);
    this.velosity = new Vector2(0, 0);
    this.text = new StringBuilder();
    this.timeMax = 5.0f;
    this.time = 0.0f;
    this.active = false;
  }

  public void setup(float x, float y, StringBuilder text) {
    this.position.set(x, y);
    this.velosity.set(20, -40);
    this.text.setLength(0);
    this.text.append(text);
    this.time = 0.0f;
    this.active = true;
  }

  public void update(float dt) {
    time += dt;
    position.mulAdd(velosity, dt);
    if(time > timeMax) {
      deactivate();
    }
  }

}
