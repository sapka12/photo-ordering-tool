package hu.arnoldfarkas.pot.domain.model;

public enum PhotoType {

    SIZE_9X13(9, 13),
    SIZE_10X15(10, 15);
    private final int x;
    private final int y;

    PhotoType(int heightInCm, int widthInCm) {
        x = widthInCm;
        y = heightInCm;
    }

    public String getSize() {
        return y + "x" + x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
