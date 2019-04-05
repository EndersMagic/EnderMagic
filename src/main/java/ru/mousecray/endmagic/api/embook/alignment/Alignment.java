package ru.mousecray.endmagic.api.embook.alignment;

public interface Alignment {
    default int resolve(int dimension) {
        return (int) resolve1(dimension);
    }

    float resolve1(int dimension);

    default Alignment add(float v) {
        return new AdditionAlignment(this, v);
    }

    class AdditionAlignment implements Alignment {
        private Alignment base;
        private float addition;

        public AdditionAlignment(Alignment base, float addition) {

            this.base = base;
            this.addition = addition;
        }

        @Override
        public float resolve1(int dimension) {
            return (int) (base.resolve(dimension) + dimension * addition);
        }
    }
}
