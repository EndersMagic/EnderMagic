package ru.mousecray.endmagic.util;

public class Truple<F, I, G>
{
    public F first;
    public I second;
    public G third;

    public Truple(F first, I second, G third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString()
    {
        return first.toString() + " " + second.toString() + " " + third.toString();
    }
}
