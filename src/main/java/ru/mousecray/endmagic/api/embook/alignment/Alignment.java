package ru.mousecray.endmagic.api.embook.alignment;

import ru.mousecray.endmagic.api.embook.BookApi;

public class Alignment {
    public static int min(double v,int dimension){return (int) (dimension * v);}
    public static int max(double v,int dimension){return (int) (dimension * (1 + v));}
    public static int center(double v,int dimension){return (int) (dimension * (0.5 + v));}

    public static int left(double v){return min(v, BookApi.pageWidth);}
    public static int right(double v){return max(v, BookApi.pageWidth);}
    public static int centerX(double v){return center(v, BookApi.pageWidth);}

    public static int top(double v){return min(v, BookApi.pageHeight);}
    public static int bottom(double v){return max(v, BookApi.pageHeight);}
    public static int centerY(double v){return center(v, BookApi.pageHeight);}
}
