package at.elias.chessgame.figures;

import at.elias.chessgame.Chessgame;
import at.elias.chessgame.ui.screens.LoseScreen;
import at.elias.chessgame.utility.GameManager;
import at.elias.chessgame.ui.screens.GameScreen;
import at.elias.chessgame.utility.Data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class ChessPiece {
    private String name;
    private Texture texture;
    private int x;
    private int y;
    private boolean isWhite;
    private boolean isSelected;

    private Chessgame game;

    private PieceMovement possibleMove;

    public ChessPiece(String name, int x, int y, boolean isWhite, String resourceLocation, PieceMovement movement, Chessgame game) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.setResourceLocation(resourceLocation);
        this.setPossibleMove(movement);
        this.isSelected = false;
        this.game = game;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPossibleMove(PieceMovement possibleMove) {
        this.possibleMove = possibleMove;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setResourceLocation(String resourceLocation) {
        setTexture(new Texture(Gdx.files.internal(resourceLocation + (isWhite() ? "" : "1") + ".png")));
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public PieceMovement getPossibleMove() {
        return possibleMove;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public float getActualX() {
        float lineSpacingX = Data.WIDTH / 10;
        return getX() * lineSpacingX;
    }

    public float getActualY() {
        float lineSpacingY = Data.HEIGHT / 10;
        return getY() * lineSpacingY + (isSelected() && GameManager.getInstance().isSelectedInUpPosition() ? 7 : 0);
    }

    public List<Tile> calculateHighlightedTiles(PieceMovement possibleMovement) {
        List<Tile> movementOptions = new ArrayList<>();
        switch (possibleMovement) {
            case LSHAPE:
                movementOptions.add(Tile.getTile(getX() + 2, getY() + 1));
                movementOptions.add(Tile.getTile(getX() - 2, getY() + 1));
                movementOptions.add(Tile.getTile(getX() + 2, getY() - 1));
                movementOptions.add(Tile.getTile(getX() - 2, getY() - 1));
                movementOptions.add(Tile.getTile(getX() + 1, getY() + 2));
                movementOptions.add(Tile.getTile(getX() - 1, getY() + 2));
                movementOptions.add(Tile.getTile(getX() + 1, getY() - 2));
                movementOptions.add(Tile.getTile(getX() - 1, getY() - 2));
                break;
            case ANY_DIRECTION_LIMITED:
                movementOptions.add(Tile.getTile(getX() + 1, getY()));
                movementOptions.add(Tile.getTile(getX() - 1, getY()));
                movementOptions.add(Tile.getTile(getX(), getY() + 1));
                movementOptions.add(Tile.getTile(getX(), getY() - 1));
                movementOptions.add(Tile.getTile(getX() + 1, getY() + 1));
                movementOptions.add(Tile.getTile(getX() - 1, getY() + 1));
                movementOptions.add(Tile.getTile(getX() + 1, getY() - 1));
                movementOptions.add(Tile.getTile(getX() - 1, getY() - 1));
                break;
            case STRAIGHT_LIMITED:
                boolean canMoveForward = true;
                if (isWhite()) {
                    for (ChessPiece piece : GameScreen.pieces) {
                        if (getX() + 1 == piece.getX() && getY() + 1 == piece.getY() && !piece.isWhite)
                            movementOptions.add(Tile.getTile(getX() + 1, getY() + 1));
                        if (getX() - 1 == piece.getX() && getY() + 1 == piece.getY() && !piece.isWhite())
                            movementOptions.add(Tile.getTile(getX() - 1, getY() + 1));
                        if (getX() == piece.getX() && getY() + 1 == piece.getY())
                            canMoveForward = false;
                    }
                    if (canMoveForward)
                        movementOptions.add(Tile.getTile(getX(), getY() + 1));
                    if (getY() == 2) {
                        boolean canMove = true;
                        for (ChessPiece piece : GameScreen.pieces) {
                            if (piece.getX() == getX() && piece.getY() == getY() + 1) canMove = false;
                            if (piece.getX() == getX() && piece.getY() == getY() + 2) canMove = false;
                        }
                        if (canMove) movementOptions.add(Tile.getTile(getX(), getY() + 2));
                    }
                } else {
                    for (ChessPiece piece : GameScreen.pieces) {
                        if (getX() + 1 == piece.getX() && getY() - 1 == piece.getY() && piece.isWhite)
                            movementOptions.add(Tile.getTile(getX() + 1, getY() - 1));
                        if (getX() - 1 == piece.getX() && getY() - 1 == piece.getY() && piece.isWhite)
                            movementOptions.add(Tile.getTile(getX() - 1, getY() - 1));
                        if (getX() == piece.getX() && getY() - 1 == piece.getY())
                            canMoveForward = false;
                    }
                    if (canMoveForward)
                        movementOptions.add(Tile.getTile(getX(), getY() - 1));
                    if (getY() == 7) {
                        boolean canMove = true;
                        for (ChessPiece piece : GameScreen.pieces) {
                            if (piece.getX() == getX() && piece.getY() == getY() - 1) canMove = false;
                            if (piece.getX() == getX() && piece.getY() == getY() - 2) canMove = false;
                        }
                        if (canMove) movementOptions.add(Tile.getTile(getX(), getY() - 2));
                    }
                }
                break;
            case STRAIGHT_INFINITE:
                for (int i = getX(); i < 8; i++) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == i + 1 && piece.getY() == getY()) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(i + 1, getY()));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(i + 1, getY()));
                }
                for (int i = getX(); i > 0; i--) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == i - 1 && piece.getY() == getY()) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(i - 1, getY()));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(i - 1, getY()));
                }
                for (int i = getY(); i < 8; i++) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == getX() && piece.getY() == i + 1) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(getX(), i + 1));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(getX(), i + 1));
                }
                for (int i = getY(); i > 0; i--) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == getX() && piece.getY() == i - 1) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(getX(), i - 1));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(getX(), i - 1));
                }
                break;
            case DIAGONAL_INFINITE:
                for (int i = 1; i < 8; i++) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == getX() + i && piece.getY() == getY() + i) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(getX() + i, getY() + i));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(getX() + i, getY() + i));
                }
                for (int i = 1; i < 8; i++) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == getX() - i && piece.getY() == getY() + i) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(getX() - i, getY() + i));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(getX() - i, getY() + i));
                }
                for (int i = 1; i < 8; i++) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == getX() + i && piece.getY() == getY() - i) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(getX() + i, getY() - i));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(getX() + i, getY() - i));
                }
                for (int i = 1; i < 8; i++) {
                    boolean canMove = true;
                    for (ChessPiece piece : GameScreen.pieces)
                        if (piece.getX() == getX() - i && piece.getY() == getY() - i) {
                            if (piece.isWhite() != isWhite()) {
                                movementOptions.add(Tile.getTile(getX() - i, getY() - i));
                            }
                            canMove = false;
                        }
                    if (!canMove) break;
                    movementOptions.add(Tile.getTile(getX() - i, getY() - i));
                }

                break;
            case ANY_DIRECTION_INFINITE:
                movementOptions.addAll(calculateHighlightedTiles(PieceMovement.STRAIGHT_INFINITE));
                movementOptions.addAll(calculateHighlightedTiles(PieceMovement.DIAGONAL_INFINITE));
                break;
            default:

                break;
        }
        List<Tile> cleanedOptions = new ArrayList<>();
        for (Tile t : movementOptions) {
            if (t != null) {
                boolean isClean = true;
                for (ChessPiece piece : GameScreen.pieces) {
                    if (t.getX() == piece.getX() && t.getY() == piece.getY() && isWhite() == piece.isWhite)
                        isClean = false;
                }
                if (isClean) cleanedOptions.add(t);
            }
        }
        return cleanedOptions;
    }

    public void select() {
        unselect();
        this.isSelected = true;
        for (Tile t : calculateHighlightedTiles(getPossibleMove()))
            if (t != null) {
                t.setHighlighted(true);
                t.setHasHighlighted(this);
            }
    }

    public static void unselect() {
        for (Tile t : Tile.tiles) {
            t.setHighlighted(false);
            t.setHasHighlighted(null);
        }
        for (ChessPiece piece : GameScreen.pieces)
            piece.isSelected = false;
    }

    public void move(Tile position) {
        if (!GameManager.getInstance().isWhiteTurn()) {
            if (GameManager.getInstance().getTurnTimeWhite() < 0) {
                GameManager.getInstance().setTurnTimeWhite(0);
            }
        } else {
            if (GameManager.getInstance().getTurnTimeBlack() < 0) {
                GameManager.getInstance().setTurnTimeBlack(0);
            }
        }

        this.x = position.getX();
        this.y = position.getY();
        unselect();

        ChessPiece defeated = null;
        for (ChessPiece piece : GameScreen.pieces) {
            if (isWhite() != piece.isWhite() && position.getX() == piece.getX() && position.getY() == piece.getY()) {
                defeated = piece;
            }
        }

        GameManager.getInstance().makeMove();

        if (getName().equalsIgnoreCase("pawn") && ((isWhite && getY() == 8) || ((!isWhite) && getY() == 1))) {
            setResourceLocation("queen");
            setPossibleMove(PieceMovement.ANY_DIRECTION_INFINITE);
        }

        if (defeated == null) {
            return;
        }
        if (defeated.getName().equalsIgnoreCase("king")) {
            GameManager.getInstance().setWinner(defeated.isWhite ? 2 : 1);
            game.setScreen(new LoseScreen(game));
        }
        GameScreen.pieces.remove(defeated);
    }

}
