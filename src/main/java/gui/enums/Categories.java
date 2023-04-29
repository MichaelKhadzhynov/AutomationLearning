package gui.enums;

public enum Categories {
    ELEMENTS(0),
    FORMS(1),
    ALERT_FRAME_WINDOW(2),
    WIDGETS(3),
    INTERACTION(4),
    BOOKSTORE(5);

    private final int category;

    Categories(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }
}
