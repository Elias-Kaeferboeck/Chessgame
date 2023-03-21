package at.elias.chessgame.ui.screens;

import at.elias.chessgame.Chessgame;
import at.elias.chessgame.utility.Data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Chessgame game;

    OrthographicCamera camera;

    public MainMenuScreen(final Chessgame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Data.WIDTH, Data.HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Data.BACKGROUND_COLOR);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.setColor(0, 0, 0, 1);

        game.font.draw(game.batch, "Chess", 100, 150);
        game.font.draw(game.batch, "Click anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            System.exit(0);

        if (Gdx.input.isTouched()) {
            game.setScreen(new at.elias.chessgame.ui.screens.GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
