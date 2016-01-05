/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2015 anseki
 * Licensed under the MIT license.
 */

package io.github.anseki.drgriffin;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.geom.Point2D;

class BezierParser {

    private static final double[] T_VALUES =
            { -0.1252, 0.1252, -0.3678, 0.3678, -0.5873, 0.5873,
                    -0.7699, 0.7699, -0.9041, 0.9041, -0.9816, 0.9816 };
    private static final double[] C_VALUES =
            { 0.2491, 0.2491, 0.2335, 0.2335, 0.2032, 0.2032,
                    0.1601, 0.1601, 0.1069, 0.1069, 0.0472, 0.0472 };

    private final double pathP1X;
    private final double pathP1Y;
    private final double pathC1X;
    private final double pathC1Y;
    private final double pathC2X;
    private final double pathC2Y;
    private final double pathP2X;
    private final double pathP2Y;

    private final double timingCx;
    private final double timingBx;
    private final double timingAx;
    private final double timingCy;
    private final double timingBy;
    private final double timingAy;
    private final Map<Double, Double> timingX2Y = new HashMap<>();
    private final List<Double> timingX2YKeys = new ArrayList<>();

    private final double allLen;
    private final long allMs; // all of time length

    BezierParser(double p1x, double p1y, double c1x, double c1y,
                 double c2x, double c2y, double p2x, double p2y,
                 double x1, double y1, double x2, double y2, double speed, int fps) { // speed: pixels/ms
        pathP1X = p1x;
        pathP1Y = p1y;
        pathC1X = c1x;
        pathC1Y = c1y;
        pathC2X = c2x;
        pathC2Y = c2y;
        pathP2X = p2x;
        pathP2Y = p2y;

        int msPf = 1000 / fps; // ms/frame
        allLen = getLenByT(1);
        allMs = (long)(allLen / speed);
        int frames = (int)(allMs / msPf);

        timingCx = 3 * x1;
        timingBx = 3 * (x2 - x1) - timingCx;
        timingAx = 1 - timingCx - timingBx;
        timingCy = 3 * y1;
        timingBy = 3 * (y2 - y1) - timingCy;
        timingAy = 1 - timingCy - timingBy;

        double stepX = 1d / (double) frames;
        double stepT = stepX / 10d; // precision
        double nextX = 0;
        for (double t = 0; t <= 1; t += stepT) {
            double x = getXByT(t);
            if (x >= nextX) {
                timingX2Y.put(nextX, getYByT(t));
                timingX2YKeys.add(nextX);
                nextX += stepX;
            }
        }
        Collections.sort(timingX2YKeys);
    }

    double getAllLen() {
        return allLen;
    }

    double getLenByTime(long time) {
        double x = (double) time / allMs;
        return x >= 1 ? allLen : getYByX(x) * allLen;
    }

    private double getLenByT(double t) {
        t = t > 1 ? 1 : t < 0 ? 0 : t;
        double z2 = t / 2;
        int n = 12;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            double ct = z2 * T_VALUES[i] + z2;
            double xBase = base3(ct, pathP1X, pathC1X, pathC2X, pathP2X);
            double yBase = base3(ct, pathP1Y, pathC1Y, pathC2Y, pathP2Y);
            double comb = xBase * xBase + yBase * yBase;
            sum += C_VALUES[i] * Math.sqrt(comb);
        }
        return z2 * sum;
    }

    private static double base3(double t, double p1, double p2, double p3, double p4) {
        double t1 = -3 * p1 + 9 * p2 - 9 * p3 + 3 * p4;
        double t2 = t * t1 + 6 * p1 - 12 * p2 + 6 * p3;
        return t * t2 - 3 * p1 + 3 * p2;
    }

    Point2D.Double getPointByLen(double len) {
        double t = getTByLen(len);
        double t1 = 1 - t;
        double t13 = Math.pow(t1, 3);
        double t12 = Math.pow(t1, 2);
        double t2 = t * t;
        double t3 = t2 * t;
        double x = t13 * pathP1X + t12 * 3 * t * pathC1X + t1 * 3 * t * t * pathC2X + t3 * pathP2X;
        double y = t13 * pathP1Y + t12 * 3 * t * pathC1Y + t1 * 3 * t * t * pathC2Y + t3 * pathP2Y;
        return new Point2D.Double(x, y);
    }

    private double getTByLen(double len) {
        double t = 1;
        double step = t / 2;
        double t2 = t - step;
        double l;
        double e = 0.01f;
        l = getLenByT(t2);
        while (Math.abs(l - len) > e) {
            step /= 2;
            t2 += (l < len ? 1 : -1) * step;
            l = getLenByT(t2);
        }
        return t2;
    }

    private double getXByT(double t) {
        return t * (timingCx + t * (timingBx + t * timingAx));
    }

    private double getYByT(double t) {
        return t * (timingCy + t * (timingBy + t * timingAy));
    }

    private double getYByX(double x) {
        if (x >= 1) { return 1; }
        if (timingX2Y.containsKey(x)) { return timingX2Y.get(x); }
        double x2key = timingX2YKeys.get(0);
        for (double nearX : timingX2YKeys) {
            x2key = nearX;
            if (x2key > x) { break; }
        }
        return timingX2Y.get(x2key);
    }
}
