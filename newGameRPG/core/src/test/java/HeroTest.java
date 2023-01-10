import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.gadgi95.GameScreen;
import com.mygdx.game.gadgi95.Weapon;
import com.mygdx.game.gadgi95.characters.Hero;
import com.mygdx.game.gadgi95.characters.Monster;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


// This class contains test cases for the Hero class
public class HeroTest {

/*
Test that the Hero is initialized with the correct default values
Create a new GameScreen object and a new Hero object
Check that the hero has the correct name
Check that the hero has the correct level
Check that the hero's position is a valid cell on the map
Check that the hero has the correct speed
Check that the hero has the correct maximum hp
Check that the hero has the correct current hp
Check that the hero has the correct weapon
 */


  @Test
  public void testDefaultInitialization() {
    GameScreen gameScreen = new GameScreen(new SpriteBatch());
    Hero hero = new Hero(gameScreen);
    assertEquals("Gadgi95", hero.getName());
    assertEquals(1, hero.getLevel());
    assertTrue(gameScreen.getMap().isCellPossible(hero.getPosition()));
    assertEquals(100.f, hero.getSpeed(), 0.001f);
    assertEquals(100, hero.getHpMax());
    assertEquals(hero.getHpMax(), hero.getHp());
    Weapon weapon = hero.getWeapon();
    assertEquals("sword", weapon.getName());
    assertEquals(150.0f, weapon.getDamage(), 0.001);
    assertEquals(0.5f, weapon.getAttackPeriod(), 0.001);
    assertEquals(5.0f, weapon.getAttackRadius(), 0.001);
  }

  @Test
  public void testAttack() {
    GameScreen gameScreen = new GameScreen(new SpriteBatch());
    Hero hero = new Hero(gameScreen);
    Monster monster = new Monster(gameScreen);
    double monsterHp = monster.getHp();
    hero.update(1.0f);
    assertEquals(monsterHp - hero.getWeapon().getDamage(), monster.getHp());
    assertEquals(0.0f, hero.getAttackTimer(), 0.001);
  }

  @Test
  public void testDamageEffectTimer() {
    Hero hero = new Hero(new GameScreen(new SpriteBatch()));
    hero.setDamageEffectTimer(1.0f);
    hero.update(2.0f);
    assertEquals(0.0f, hero.getDamageEffectTimer(), 0.001);
  }

  @Test
  public void testKeyboardControl() {
    GameScreen gameScreen = new GameScreen(new SpriteBatch());
    Hero hero = new Hero(gameScreen);
    hero.setPosition(new Vector2(200, 200));
    hero.setDirection(new Vector2(1.0f, 0.0f));
    hero.update(1.0f);
    assertEquals(201.0f, hero.getPosition().x, 0.001);
    hero.setDirection(new Vector2(-1.0f, 0.0f));
    hero.update(1.0f);
    assertEquals(200.0f, hero.getPosition().x, 0.001);
  }

}
