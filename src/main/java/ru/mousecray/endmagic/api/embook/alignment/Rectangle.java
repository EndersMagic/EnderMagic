package ru.mousecray.endmagic.api.embook.alignment;

public class Rectangle {
    public final Alignment x1, y1, x2, y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this(new Min(x1), new Min(y1), new Min(x2), new Min(y2));
    }

    public Rectangle(Alignment x1, Alignment y1, Alignment x2, Alignment y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean contains(int mouseX, int mouseY, int width, int height) {
        int x = x1.resolve(width);
        int y = y1.resolve(height);
        int x_2 = x2.resolve(width);
        int y_2 = y2.resolve(height);

        int _x = Math.min(x, x_2);
        int _x_2 = Math.max(x, x_2);
        int _y = Math.min(y, y_2);
        int _y_2 = Math.max(y, y_2);

        return mouseX >= _x && mouseX <= _x_2 && mouseY >= _y && mouseY <= _y_2;
    }

    @Override
    public String toString() {
        return "Rectangle(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")";
    }
}
