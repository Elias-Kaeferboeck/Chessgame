package at.elias.chessgame.ui.screens;

import at.elias.chessgame.Chessgame;
import at.elias.chessgame.utility.GameManager;
import at.elias.chessgame.figures.ChessPiece;
import at.elias.chessgame.figures.PieceMovement;
import at.elias.chessgame.figures.Tile;
import at.elias.chessgame.utility.Data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GameScreen implements Screen {

    final Chessgame game;

    OrthographicCamera camera;

    private ShapeRenderer renderer;

    private GameManager gameManager;

    public static List<ChessPiece> pieces;

    public GameScreen(final Chessgame game) {
        this.game = game;
        pieces = new ArrayList<>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Data.WIDTH, Data.HEIGHT);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        boolean beginColumnWhite = true;
        boolean isWhite;


        Tile.tiles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            beginColumnWhite = !beginColumnWhite;
            if (beginColumnWhite)
                isWhite = true;
            else
                isWhite = false;
            for (int j = 0; j < 8; j++) {
                Tile tile = new Tile(i + 1, j + 1);
                tile.setColor(isWhite ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                isWhite = !isWhite;
                Tile.tiles.add(tile);
            }
        }

        gameManager = new GameManager(this);

        resetBoard();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Data.BACKGROUND_COLOR);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        float lineSpacingX = Data.WIDTH / 10;
        float lineSpacingY = Data.HEIGHT / 10;


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            GameScreen screen = new GameScreen(game);
            gameManager.setActiveGameScreen(screen);
            game.setScreen(screen);
            dispose();
        }


        if (Gdx.input.justTouched()) {
            Vector3 pos = new Vector3();
            pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(pos);
            float inputX = pos.x;
            float inputY = pos.y;

            Tile touched = null;
            for (Tile t : Tile.tiles) {
                if (inputX > t.getActualX() && inputX < t.getActualX() + lineSpacingX && inputY > t.getActualY() && inputY < t.getActualY() + lineSpacingY) {
                    touched = t;
                    break;
                }
            }
            if (touched != null) {
                if (touched.isHighlighted()) {
                    ChessPiece piece = touched.getHasHighlighted();
                    piece.move(touched);
                } else {
                    boolean foundMatch = false;
                    for (ChessPiece piece : pieces) {
                        if (piece.getY() == touched.getY() && piece.getX() == touched.getX()) {
                            if (gameManager.isWhiteTurn() == piece.isWhite()) {
                                piece.select();
                                foundMatch = true;
                            }
                        }
                    }
                    if (!foundMatch)
                        ChessPiece.unselect();
                }
            }
        }


        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Tile tile : Tile.tiles) {
            renderer.setColor(tile.getColor());
            renderer.rect(tile.getActualX(), tile.getActualY(), Data.WIDTH / 10, Data.HEIGHT / 10);
        }
        renderer.end();

        game.batch.begin();
        game.font.setColor(0, 0, 0, 1);
        String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H"};

        for (int i = 1; i < 9; i++) {
            game.font.draw(game.batch, chars[i - 1], (i * lineSpacingX) + lineSpacingX / 2, lineSpacingY / 3 * 2.5f);
            game.font.draw(game.batch, chars[i - 1], (i * lineSpacingX) + lineSpacingX / 2, (lineSpacingY / 3) + Data.HEIGHT - lineSpacingY);
        }
        for (int j = 1; j < 9; j++) {
            game.font.draw(game.batch, j + "", lineSpacingX / 3 * 2, (j * lineSpacingY) + lineSpacingY / 2);
            game.font.draw(game.batch, j + "", lineSpacingX / 5 + Data.WIDTH - lineSpacingX, (j * lineSpacingY) + lineSpacingY / 2);
        }

        for (ChessPiece chessPiece : pieces) {
            game.batch.setColor(Color.WHITE);
            game.batch.draw(chessPiece.getTexture(), chessPiece.getActualX(), chessPiece.getActualY());
        }

        for (Tile t : Tile.tiles) {
            if (t.isHighlighted()) {
                game.batch.setColor(t.getColor() == Color.LIGHT_GRAY ? Data.HIGHLIGHTING_COLOR_BLACK : Data.HIGHLIGHTING_COLOR_WHITE);
                game.batch.draw(t.getTexture(), t.getActualX(), t.getActualY());
            }
        }


        if (gameManager.isWhiteTurn()) {
            if (gameManager.getTurnTimeWhite() >= 0) {
                gameManager.setTurnTimeWhite(gameManager.getTurnTimeWhite() + delta * 1000);
                Date date = new Date((long) gameManager.getTurnTimeWhite());
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormatted = formatter.format(date);
                game.font.setColor(Color.LIGHT_GRAY);
                game.font.draw(game.batch, dateFormatted, Data.WIDTH / 50, Data.HEIGHT - Data.HEIGHT / 45);
            }
        } else {
            if (gameManager.getTurnTimeBlack() >= 0) {
                gameManager.setTurnTimeBlack(gameManager.getTurnTimeBlack() + delta * 1000);
                Date date = new Date((long) gameManager.getTurnTimeBlack());
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormatted = formatter.format(date);
                game.batch.setColor(Color.DARK_GRAY);
                game.font.draw(game.batch, dateFormatted, Data.WIDTH / 10 * 8.5f, Data.HEIGHT - Data.HEIGHT / 45);
            }
        }
        game.batch.end();

        if (gameManager.getLastPieceBlink() == 0 || System.currentTimeMillis() - gameManager.getLastPieceBlink() > 500) {
            gameManager.setLastPieceBlink(System.currentTimeMillis());
            if (gameManager.isSelectedInUpPosition()) {
                gameManager.setSelectedInUpPosition(false);
            } else {
                gameManager.setSelectedInUpPosition(true);
            }
        }

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

    @Override
    public void resize(int width, int height) {

    }

    public void resetBoard() {

        for (int i = 0; i < 8; i++) {
            pieces.add(new ChessPiece("Pawn", i + 1, 2, true, "pawn", PieceMovement.STRAIGHT_LIMITED, game));
        }
        for (int i = 0; i < 8; i++) {
            pieces.add(new ChessPiece("Pawn", i + 1, 7, false, "pawn", PieceMovement.STRAIGHT_LIMITED, game));
        }

        pieces.add(new ChessPiece("Rook", 1, 1, true, "rook", PieceMovement.STRAIGHT_INFINITE, game));
        pieces.add(new ChessPiece("Rook", 8, 1, true, "rook", PieceMovement.STRAIGHT_INFINITE, game));
        pieces.add(new ChessPiece("Rook", 1, 8, false, "rook", PieceMovement.STRAIGHT_INFINITE, game));
        pieces.add(new ChessPiece("Rook", 8, 8, false, "rook", PieceMovement.STRAIGHT_INFINITE, game));

        pieces.add(new ChessPiece("Knight", 2, 1, true, "knight", PieceMovement.LSHAPE, game));
        pieces.add(new ChessPiece("Knight", 7, 1, true, "knight", PieceMovement.LSHAPE, game));
        pieces.add(new ChessPiece("Knight", 2, 8, false, "knight", PieceMovement.LSHAPE, game));
        pieces.add(new ChessPiece("Knight", 7, 8, false, "knight", PieceMovement.LSHAPE, game));

        pieces.add(new ChessPiece("Bishop", 3, 1, true, "bishop", PieceMovement.DIAGONAL_INFINITE, game));
        pieces.add(new ChessPiece("Bishop", 6, 1, true, "bishop", PieceMovement.DIAGONAL_INFINITE, game));
        pieces.add(new ChessPiece("Bishop", 3, 8, false, "bishop", PieceMovement.DIAGONAL_INFINITE, game));
        pieces.add(new ChessPiece("Bishop", 6, 8, false, "bishop", PieceMovement.DIAGONAL_INFINITE, game));

        pieces.add(new ChessPiece("King", 5, 1, true, "king", PieceMovement.ANY_DIRECTION_LIMITED, game));
        pieces.add(new ChessPiece("King", 4, 8, false, "king", PieceMovement.ANY_DIRECTION_LIMITED, game));

        pieces.add(new ChessPiece("Queen", 4, 1, true, "queen", PieceMovement.ANY_DIRECTION_INFINITE, game));
        pieces.add(new ChessPiece("Queen", 5, 8, false, "queen", PieceMovement.ANY_DIRECTION_INFINITE, game));

    }

}
