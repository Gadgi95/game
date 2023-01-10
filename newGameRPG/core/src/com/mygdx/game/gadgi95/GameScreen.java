package com.mygdx.game.gadgi95;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.gadgi95.characters.GameCharacter;
import com.mygdx.game.gadgi95.characters.Hero;
import com.mygdx.game.gadgi95.characters.Monster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameScreen {
  private SpriteBatch batch;
  private BitmapFont font30;
  private Map map;
  private ItemsEmitter itemsEmitter;
  private Hero hero;

  private List<GameCharacter> allCharacters;
  private List<Monster> allMonsters;

  public List<Monster> getAllMonsters() {
    return allMonsters;
  }

  public Map getMap() {
    return map;
  }

  private Comparator<GameCharacter> drawOrderComparator;

  public GameScreen(SpriteBatch batch) {
    this.batch = batch;
  }

  public Hero getHero() {
    return hero;
  }

  public void create() {
    map = new Map();
    allCharacters = new ArrayList<>();
    allMonsters = new ArrayList<>();
    hero = new Hero(this);
    itemsEmitter = new ItemsEmitter();
    allCharacters.addAll(Arrays.asList(
        hero,
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this),
        new Monster(this)
    ));
    for (int i = 0; i < allCharacters.size(); i++) {
      if(allCharacters.get(i) instanceof Monster) {
        allMonsters.add((Monster) allCharacters.get(i));
      }
    }
    font30 = new BitmapFont(Gdx.files.internal("font30.fnt"));
    drawOrderComparator = new Comparator<GameCharacter>() {
      @Override
      public int compare(GameCharacter gameCharacter1, GameCharacter gameCharacter2) {
        return (int) (gameCharacter2.getPosition().y - gameCharacter1.getPosition().y);
      }
    };
  }

  public void render() {

    float dt = Gdx.graphics.getDeltaTime();
    update(dt);
    ScreenUtils.clear(1, 1, 1, 1);

    batch.begin();
    map.render(batch);
    Collections.sort(allCharacters, drawOrderComparator);
    for (int i = 0; i < allCharacters.size(); i++) {
      allCharacters.get(i).render(batch, font30);
    }
    itemsEmitter.render(batch);
    hero.renderHUD(batch, font30);
    batch.end();
  }

  public void update(float dt) {
    for (int i = 0; i < allCharacters.size(); i++) {
      allCharacters.get(i).update(dt);
    }
    for (int i = 0; i < allMonsters.size(); i++) {
      Monster currentMonster = allMonsters.get(i);
      if(!currentMonster.isAlive()) {
        allMonsters.remove(currentMonster);
        allCharacters.remove(currentMonster);
        itemsEmitter.generateRandomItem(currentMonster.getPosition().x,
            currentMonster.getPosition().y, 5, 0.6f);
        hero.killMonster(currentMonster);
      }
    }

    for (int i = 0; i < itemsEmitter.getItems().length; i++) {
      Item it = itemsEmitter.getItems()[i];
      if(it.isActive()) {
        float dst = hero.getPosition().dst(it.getPosition());
        if(dst < 24.0f) {
          hero.useItem(it);
        }
      }
    }
    itemsEmitter.update(dt);
  }
}
