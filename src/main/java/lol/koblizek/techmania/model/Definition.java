package lol.koblizek.techmania.model;

public enum Definition {
    V,
    VT,
    VN,
    F,
    O,
    G,
    S,
    L,
    MTL,
    USEMTL,
    UNKNOWN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static Definition from(String def) {
        try {
            return valueOf(def.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
