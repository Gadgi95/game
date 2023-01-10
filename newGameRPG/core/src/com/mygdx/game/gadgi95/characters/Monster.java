package com.mygdx.game.gadgi95.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.gadgi95.GameScreen;
import com.mygdx.game.gadgi95.Weapon;

public class Monster extends GameCharacter{

  private float moveTimer;
  private float activityRadius;



  public Monster(GameScreen gameScreen) {
    this.texture = new Texture("Worm.png");
    this.textureHp = new Texture("barHP.png");
    this.position = new Vector2(MathUtils.random(0, 1280), MathUtils.random(0, 720));
    while (!gameScreen.getMap().isCellPossible(position)) {
      this.position.set(MathUtils.random(0, 1280), MathUtils.random(0, 720));
    }
    this.direction = new Vector2(0, 0);
    this.speed = 40.0f;
    this.activityRadius = 200.0f;
    this.gameScreen = gameScreen;
    this.hpMax = 40;
    this.hp = this.hpMax;
    weapon = new Weapon("Rust Sword", 40.0f, 0.8f, 5.0f);
  }

//  Создание текстуры монстра и смещение координат на центр изображения
  @Override
  public void update(float dt) {
    damageEffectTimer -= dt;
    if(damageEffectTimer < 0.0f) {
      damageEffectTimer = 0.0f;
    }

    /*
    Погоня за героем в активном радиусе монстра, в остальных случаях
    рандомное передвижение монстра
    + проверка на наличие стен
     */

    float dst = gameScreen.getHero().getPosition().dst(this.position);
    if(dst < activityRadius) {
      direction.set(gameScreen.getHero().getPosition()).sub(this.position).nor();
    }
    else {
      moveTimer -= dt;
      if(moveTimer < 0.0f) {
        moveTimer = MathUtils.random(2.5f, 4.0f);
        direction.set(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f));
        direction.nor();
      }
    }

    moveForward(dt);

    if(dst < weapon.getAttackRadius()) {
      attackTimer += dt;
      if(attackTimer > weapon.getAttackPeriod()) {
        attackTimer = 0.0f;
        gameScreen.getHero().takeDamage(weapon.getDamage());
      }
    }

    checkScreenBound();
  }
}
