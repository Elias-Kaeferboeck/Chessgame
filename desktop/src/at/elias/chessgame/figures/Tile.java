package at.elias.chessgame.figures;

import at.elias.chessgame.utility.Data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class Tile {
    private int x;
    private int y;

    private float actualX;
    private float actualY;

    private Color color;
    private boolean highlighted;

    private Texture selectionMarker;

    private ChessPiece hasHighlighted;

    public static List<Tile> tiles;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;

        this.highlighted = false;

        float lineSpacingX = Data.WIDTH / 10;
        float lineSpacingY = Data.HEIGHT / 10;

        this.actualX = x * lineSpacingX;
        this.actualY = y * lineSpacingY;

        this.hasHighlighted = null;

        this.selectionMarker = new Texture(Gdx.files.internal("selection_marker.png"));
    }

    public Color getColor() {
        return color;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public float getActualX() {
        return actualX;
    }

    public float getActualY() {
        return actualY;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Texture getTexture() {
        return selectionMarker;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public ChessPiece getHasHighlighted() {
        return hasHighlighted;
    }

    public void setHasHighlighted(ChessPiece hasHighlighted) {
        this.hasHighlighted = hasHighlighted;
    }

    public static Tile getTile(int x, int y) {
        if (x < 1 || x > 8 || y < 1 || y > 8)
            return null;
        for (Tile t : tiles) {
            if (t.getX() == x && t.getY() == y)
                return t;
        }
        return null;
    }
}
