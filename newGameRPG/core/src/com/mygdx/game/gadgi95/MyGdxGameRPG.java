package com.mygdx.game.gadgi95;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGameRPG extends ApplicationAdapter {
	/*
		=== Идеи ===
		1. + Движения по пикселям
		2. +Преграды
		3. Анимация
		4. Выстрелы (атака)
		5. +Хаотичное движение для монстра
		6. +Преследование монстром героя
		7. +Аптечки, монеты, зелья (все что можно поднять)
		8. +Параметры героя/монстра (ХП, крит шанс, скорость)
		9. Система уровней игры
		10. +-Опыт герою, через айтемы
		11. +Оружие
		12. Финальный босс
		13. +Драка с монтрами
		14. +Индикатор здоровья
		15. +Перенос на векторы
		16. Инвентарь
		17. +Отображение текста
		18. Хот бар
		**. Камера

	 */


	private SpriteBatch batch;
	private GameScreen gameScreen;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.gameScreen = new GameScreen(batch);
		this.gameScreen.create();
	}

	@Override
	public void render () {
		gameScreen.render();
	}

	public void update(float dt) {

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
