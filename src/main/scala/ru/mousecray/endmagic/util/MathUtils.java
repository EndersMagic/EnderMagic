package ru.mousecray.endmagic.util;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MathUtils {

    public static Function<Double, Vec3d> bezierCurve(List<Vec3d> path) {
        //Можно развернуть точки так, чтобы они лежали в плоскости ZoY, заюзать двумерноые безье, и повернуть обратно
        Vec3d first = path.get(0);
        Vec3d last = path.get(path.size() - 1);
        float angle = (float) Math.atan2(last.x - first.x, last.z - first.z);

        List<Vec3d> rotatedPath = path.stream().map(v -> v.rotateYaw(-angle)).collect(Collectors.toList());

        return t -> {
            double z = 0;
            double y = 0;

            int n = rotatedPath.size() - 1;

            Vec3d vec = rotatedPath.get(0);
            double pow_1_minus_t_n = Math.pow((1 - t), n);
            z += vec.z * pow_1_minus_t_n;
            y += vec.y * pow_1_minus_t_n;

            double factorial_n = factorial(n);

            for (int index = 1; index < rotatedPath.size(); index++) {
                Vec3d item = rotatedPath.get(index);
                z += factorial_n / factorial(index) / factorial(n - index) * item.z * Math.pow((1 - t), n - index) * Math.pow(t, index);
                y += factorial_n / factorial(index) / factorial(n - index) * item.y * Math.pow((1 - t), n - index) * Math.pow(t, index);
            }
            return new Vec3d(vec.x, y, z).rotateYaw(angle);
        };
    }

    private static Int2IntMap factorialCache = new Int2IntOpenHashMap();

    private static double factorial(int num) {
        return factorialCache.computeIfAbsent(num, num1 -> factorial(num1, 1));
    }

    private static int factorial(int num, int acc) {
        if (num <= 1) {
            return acc;
        } else {
            return factorial(num - 1, acc * num);
        }
    }
}
