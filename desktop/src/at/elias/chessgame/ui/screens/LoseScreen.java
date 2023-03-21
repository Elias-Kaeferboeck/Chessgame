package at.elias.chessgame.ui.screens;

import at.elias.chessgame.Chessgame;
import at.elias.chessgame.utility.Data;
import at.elias.chessgame.utility.GameManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoseScreen implements Screen {

    final Chessgame game;

    OrthographicCamera camera;

    public LoseScreen(final Chessgame game) {
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

        game.font.draw(game.batch, "The Game is Over! Winner: " + (GameManager.getInstance().getWinner() == 1 ? "White" : "Black"), Data.WIDTH / 20, Data.HEIGHT / 2);

        game.batch.end();
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
