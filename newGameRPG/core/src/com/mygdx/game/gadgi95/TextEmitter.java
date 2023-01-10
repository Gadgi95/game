package com.mygdx.game.gadgi95;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.gadgi95.Item.Type;

public class TextEmitter {

  private Item[] items;
  private Texture texture;
  private TextureRegion[] regions;

  public Item[] getItems() {
    return items;
  }

  public TextEmitter() {
    items = new Item[50];
    for (int i = 0; i < items.length; i++) {
      items[i] = new Item();
    }
    texture = new Texture("items.png");
    regions = new TextureRegion(texture).split(20,20)[0];
  }

  public void render(SpriteBatch batch) {

    for (int i = 0; i < items.length; i++) {
      if(items[i].isActive()) {
        batch.draw(regions[items[i].getType().index], items[i].getPosition().x -15, items[i].getPosition().y -15);
      }
    }
  }

  public void generateRandomItem(float x, float y, int count, float probability) {
    for (int j = 0; j < count; j++) {
      float dice = MathUtils.random(0, 0.5f);
      if (dice <= probability) {
        int type = MathUtils.random (0, Type.values().length -1);
        for (int i = 0; i < items.length; i++) {
          if(!items[i].isActive()) {
            items[i].setup(x, y, Item.Type.values()[type]);
            break;
          }
        }
      }
    }
  }

  public void update(float dt) {
    for (int i = 0; i < items.length; i++) {
      if(items[i].isActive()) {
        items[i].update(dt);
      }
    }
  }
}
