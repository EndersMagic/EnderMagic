package ru.mousecray.endmagic.api.embook;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Rectangle {
    public final int x1, y1, x2, y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = min(x1, x2);
        this.x2 = max(x1, x2);
        this.y1 = min(y1, y2);
        this.y2 = max(y1, y2);
    }

    public boolean contains(int mouseX, int mouseY) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    @Override
    public String toString() {
        return "Rectangle(" + x1 + ", " + y1 + ", " + x2 + ", " + y2 + ")";
    }
}
