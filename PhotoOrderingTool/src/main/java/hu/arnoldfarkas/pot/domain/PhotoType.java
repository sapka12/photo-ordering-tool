package hu.arnoldfarkas.pot.domain;

public enum PhotoType {

    SIZE_9X13("9x13"),
    SIZE_10X15("10x15");
    private final String size;

    PhotoType(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
