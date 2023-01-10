package com.mygdx.game.gadgi95.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.gadgi95.GameScreen;
import com.mygdx.game.gadgi95.Item;
import com.mygdx.game.gadgi95.Weapon;

/**
 * This class represents a hero character in the game. The hero has a name, level,
 * experience points, coins, maximum hit points, current hit points, a weapon, and
 * a position on the game map. The hero can attack monsters and move around the
 * map using keyboard controls. The hero also has a damage effect timer which
 * is used to display a visual effect when the hero takes damage.
 */

public class Hero extends GameCharacter {
  private String name;
  private int coins;
  private int level;
  private int exp;
  private int[] expTo = {0, 0, 100, 300, 600, 1000, 5000};

  public String getName() {
    return name;
  }

  public int getCoins() {
    return coins;
  }

  public int getLevel() {
    return level;
  }

  public int getExp() {
    return exp;
  }

  public int[] getExpTo() {
    return expTo;
  }


  /**
   * Creates a new Hero object with the default name "Gadgi95" and the default
   * stats and weapon. The hero's position is set to a random cell on the map
   * that is suitable for the hero to stand on.
   *
   * @param gameScreen the GameScreen object which the hero belongs to
   */

  public Hero(GameScreen gameScreen) {
    this.gameScreen = gameScreen;
    this.name = "Gadgi95";
    this.level = 1;
    this.texture = new Texture("skelet.png");
    this.textureHp = new Texture("barHP.png");
    this.position = new Vector2(200, 200);
    while (!gameScreen.getMap().isCellPossible(position)) {
      this.position.set(MathUtils.random(0, 1280), MathUtils.random(0, 720));
    }
    this.direction = new Vector2(0, 0);
    this.speed = 100.f;
    this.hpMax = 100;
    this.hp = this.hpMax;
    weapon = new Weapon("sword", 150.0f, 0.5f, 5.0f);
  }

  public void renderHUD(SpriteBatch batch, BitmapFont font30) {
    stringHelper.setLength(0);
    stringHelper.append("Skeleton: ").append(name).append("\n")
        .append("Level: ").append(level).append("\n")
        .append("Exp: ").append(exp).append(" / ").append(expTo[level + 1]).append("\n")
        .append("Coins: ").append(coins);
    font30.draw(batch,  stringHelper, 60, 700);
  }

  //  Логика поведения героя
  public void update(float dt) {

//  Поиск и атака ближайшего монстра
    Monster nearestMonster = null;
    float minDist = Float.MAX_VALUE;
    for (int i = 0; i < gameScreen.getAllMonsters().size(); i++) {
      float dst = gameScreen.getAllMonsters().get(i).getPosition().dst(this.position);
      if (dst < minDist) {
        minDist = dst;
        nearestMonster = gameScreen.getAllMonsters().get(i);
      }
    }
    if (nearestMonster != null && minDist < weapon.getAttackRadius()) {
      attackTimer += dt;
      if (attackTimer > weapon.getAttackPeriod()) {
        attackTimer = 0.0f;
        nearestMonster.takeDamage(weapon.getDamage());
      }
    }
    damageEffectTimer -= dt;
    if (damageEffectTimer < 0.0f) {
      damageEffectTimer = 0.0f;
    }

//    управление через нажатие кнопок
    direction.set(0, 0);

    if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
      direction.x = 1.0f;
    }
    if (Gdx.input.isKeyPressed(Keys.LEFT)) {
      direction.x = -1.0f;
    }
    if (Gdx.input.isKeyPressed(Keys.UP)) {
      direction.y = 1.0f;
    }
    if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      direction.y = -1.0f;
    }

    moveForward(dt);

//    Границы перемещения героя
    checkScreenBound();
  }

  public void killMonster(Monster monster) {
    exp += monster.hpMax * 5;
    if(exp >= expTo[level +1]) {
      level++;
      hpMax *= 1.1f;
      hp = hpMax;
      exp -= expTo[level];

    }
  }

  public void useItem(Item it) {
    switch (it.getType()) {
      case COINS:
        coins += MathUtils.random(1,5);
        break;
      case MEDKIT:
        hp += 5;
        if (hp > hpMax) {
          hp = hpMax;
        }
        break;
    }
    it.deactivate();
  }
}
