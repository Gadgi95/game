package com.mygdx.game.gadgi95.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.gadgi95.GameScreen;
import com.mygdx.game.gadgi95.Weapon;

public abstract class GameCharacter {

  GameScreen gameScreen;
  Texture texture;
  Texture textureHp;
  Vector2 position;
  Vector2 direction;
  Vector2 temp;
  float speed;

  float hp, hpMax;

  float damageEffectTimer;
  float attackTimer;

  Weapon weapon;

  StringBuilder stringHelper;

  public float getSpeed() {
    return speed;
  }

  public float getHp() {
    return hp;
  }

  public float getHpMax() {
    return hpMax;
  }

  public float getDamageEffectTimer() {
    return damageEffectTimer;
  }

  public float getAttackTimer() {
    return attackTimer;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public void setDamageEffectTimer(float damageEffectTimer) {
    this.damageEffectTimer = damageEffectTimer;
  }

  public void setPosition(Vector2 vector) {
    this.position = position;
  }

  public void setDirection(Vector2 direction) {
    this.direction = direction;
  }

  public GameCharacter() {
    temp = new Vector2(0, 0);
    stringHelper = new StringBuilder();
  }

  public boolean isAlive() {
    return hp > 0;
  }

  public Vector2 getPosition() {
    return position;
  }

  public abstract void update(float dt);

  public void render(SpriteBatch batch, BitmapFont font30) {

    if (damageEffectTimer > 0.0f) {
      batch.setColor(1, 1 - damageEffectTimer, 1 - damageEffectTimer, 1);
    }

    batch.draw(texture, position.x - 33, position.y - 40);
    batch.setColor(1, 1, 1, 1);

    batch.draw(textureHp, position.x + 8 - 33, position.y + 67 - 40, 0, 0, 80, 20, 1, 1, 0, 0, 0,
        80, 20, false, false);

    stringHelper.setLength(0);
    stringHelper.append((int)hp);

    if (hp < hpMax * 0.66f && hp > hpMax * 0.33f) {
      batch.setColor(1, 1, 0, 1);
    }
    else if (hp < hpMax * 0.33f) {
      batch.setColor(1, 0, 0, 1);
    }
    else {
      batch.setColor(0, 1, 0, 1);
    }

    batch.draw(textureHp, position.x + 8 - 33, position.y + 67 - 40, 0, 0, hp / hpMax * 80, 20, 1,
        1, 0, 0, 0, 80, 20, false, false);
    font30.draw(batch, stringHelper, position.x - 38, position.y + 45, 80, 1, false);
    batch.setColor(1, 1, 1, 1);
  }

  public void checkScreenBound() {
    //    Границы перемещения монстра
    if (position.x > 1280) {
      position.x = 1280.0f;
    }
    if (position.x < 0) {
      position.x = 0.0f;
    }
    if (position.y > 720) {
      position.y = 720.0f;
    }
    if (position.y < 0) {
      position.y = 0.0f;
    }
  }

  public void takeDamage(float amount) {
    hp -= amount;
    damageEffectTimer += 0.5f;
    if (damageEffectTimer > 1.0f) {
      damageEffectTimer = 1.0f;

    }
  }

  public void moveForward(float dt) {
    if(gameScreen.getMap().isCellPossible(temp.set(position)
        .mulAdd(direction, speed * dt))) {
      position.set(temp);
    }
    else if(gameScreen.getMap().isCellPossible(temp.set(position)
        .mulAdd(direction, speed * dt).set(temp.x, position.y))) {
      position.set(temp);
    }
    else if(gameScreen.getMap().isCellPossible(temp.set(position)
        .mulAdd(direction, speed * dt).set(position.x, temp.y))) {
      position.set(temp);
    }
  }
}
