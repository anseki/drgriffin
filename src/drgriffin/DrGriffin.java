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
import java.awt.Robot;
import java.awt.geom.Point2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.event.InputEvent;

import java.awt.AWTException;
import java.awt.HeadlessException;

public class DrGriffin extends KeyCode {

    @SuppressWarnings("WeakerAccess")
    public static final int BUTTON_NONE = 0;
    @SuppressWarnings("WeakerAccess")
    public static final int BUTTON_1 = 1;
    @SuppressWarnings("WeakerAccess")
    public static final int BUTTON_2 = 2;
    @SuppressWarnings("WeakerAccess")
    public static final int BUTTON_3 = 3;

    private static final Map<Integer, Integer> BUTTONS = new HashMap<Integer, Integer>() {{
        put(BUTTON_1, InputEvent.BUTTON1_DOWN_MASK);
        put(BUTTON_2, InputEvent.BUTTON2_DOWN_MASK);
        put(BUTTON_3, InputEvent.BUTTON3_DOWN_MASK);
    }};

    @SuppressWarnings("WeakerAccess")
    public static final int KEY_NONE = VK_UNDEFINED;

    @SuppressWarnings("WeakerAccess")
    public static final String TF_LINEAR = "linear";
    @SuppressWarnings("unused")
    public static final String TF_EASE = "ease";
    @SuppressWarnings("WeakerAccess")
    public static final String TF_EASE_IN = "ease-in";
    @SuppressWarnings("WeakerAccess")
    public static final String TF_EASE_IN_OUT = "ease-in-out";
    @SuppressWarnings("WeakerAccess")
    public static final String TF_EASE_OUT = "ease-out";

    /**
     * <code>{@value}</code> pixels per millisecond as the <code>speed</code> value.
     */
    @SuppressWarnings("unused")
    public static final double SPEED_NORMAL = 0.3;

    /**
     * The <code>speed</code> value that is slower than {@link #SPEED_NORMAL}.
     */
    @SuppressWarnings("unused")
    public static final double SPEED_LOW1 = 0.1;

    /**
     * The <code>speed</code> value that is slower than {@link #SPEED_LOW1}.
     */
    @SuppressWarnings("unused")
    public static final double SPEED_LOW2 = 0.05;

    /**
     * The <code>speed</code> value that is faster than {@link #SPEED_NORMAL}.
     */
    @SuppressWarnings("unused")
    public static final double SPEED_HIGH1 = 0.5;

    /**
     * The <code>speed</code> value that is faster than {@link #SPEED_HIGH1}.
     */
    @SuppressWarnings("unused")
    public static final double SPEED_HIGH2 = 1.0;

    private static int fps = 60;

    private Robot robot;

    // default: ease
    private double timing1X = 0.25;
    private double timing1Y = 0.1;
    private double timing2X = 0.25;
    private double timing2Y = 1;

    private double timingSpeed = SPEED_NORMAL; // pixels/ms

    private double curX = -1;
    private double curY = -1;

    private int button = BUTTON_NONE;
    private int buttonMask;
    private int key = KEY_NONE;

    @SuppressWarnings("unused")
    public DrGriffin(int delay, double dx, double dy)
            throws AWTException, IllegalArgumentException, HeadlessException {
        this(delay, dx, dy, 0);
    }

    @SuppressWarnings("unused")
    public DrGriffin(int fps) throws AWTException, IllegalArgumentException, HeadlessException {
        this(0, -1, -1, fps);
    }

    @SuppressWarnings("unused")
    public DrGriffin() throws AWTException, IllegalArgumentException, HeadlessException {
        this(0, -1, -1, 0);
    }

    @SuppressWarnings("unused")
    public DrGriffin(int delay, double dx, double dy, int fps)
            throws AWTException, IllegalArgumentException, HeadlessException {
        robot = new Robot();

        if (fps > 0) { DrGriffin.fps = fps; }
        if (dx >= 0 && dy >= 0) {
            moveMouse(delay, dx, dy);
        }
    }

    // Omitted: speed
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param c1x A X coordinate of a first control point when the mouse pointer follows a curve.
     * @param c1y A Y coordinate of a first control point when the mouse pointer follows a curve.
     * @param c2x A X coordinate of a second control point when the mouse pointer follows a curve.
     * @param c2y A Y coordinate of a second control point when the mouse pointer follows a curve.
     * @param t1x A X coordinate of a first control point of the timing-function.
     * @param t1y A Y coordinate of a first control point of the timing-function.
     * @param t2x A X coordinate of a second control point of the timing-function.
     * @param t2y A Y coordinate of a second control point of the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double c1x, double c1y, double c2x, double c2y,
                          double t1x, double t1y, double t2x, double t2y)
            throws IllegalArgumentException, HeadlessException {
        moveMouse(delay, dx, dy, c1x, c1y, c2x, c2y, 0, t1x, t1y, t2x, t2y);
    }

    // Omitted: c*
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param speed An average speed that specifies a number of pixels as the movement distance per millisecond.
     * @param t1x A X coordinate of a first control point of the timing-function.
     * @param t1y A Y coordinate of a first control point of the timing-function.
     * @param t2x A X coordinate of a second control point of the timing-function.
     * @param t2y A Y coordinate of a second control point of the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double speed, double t1x, double t1y, double t2x, double t2y)
            throws IllegalArgumentException, HeadlessException {
        initCurXY();
        moveMouse(delay, dx, dy, curX, curY, dx, dy, speed, t1x, t1y, t2x, t2y);
    }

    // Omitted: speed, c*
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param t1x A X coordinate of a first control point of the timing-function.
     * @param t1y A Y coordinate of a first control point of the timing-function.
     * @param t2x A X coordinate of a second control point of the timing-function.
     * @param t2y A Y coordinate of a second control point of the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double t1x, double t1y, double t2x, double t2y)
            throws IllegalArgumentException, HeadlessException {
        moveMouse(delay, dx, dy, 0, t1x, t1y, t2x, t2y);
    }

    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param c1x A X coordinate of a first control point when the mouse pointer follows a curve.
     * @param c1y A Y coordinate of a first control point when the mouse pointer follows a curve.
     * @param c2x A X coordinate of a second control point when the mouse pointer follows a curve.
     * @param c2y A Y coordinate of a second control point when the mouse pointer follows a curve.
     * @param speed An average speed that specifies a number of pixels as the movement distance per millisecond.
     * @param t1x A X coordinate of a first control point of the timing-function.
     * @param t1y A Y coordinate of a first control point of the timing-function.
     * @param t2x A X coordinate of a second control point of the timing-function.
     * @param t2y A Y coordinate of a second control point of the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double c1x, double c1y, double c2x, double c2y,
                          double speed, double t1x, double t1y, double t2x, double t2y)
            throws IllegalArgumentException, HeadlessException {
        initCurXY();
        if (speed > 0) {
            timingSpeed = speed;
        }
        if (t1x >= 0 && t1x <= 1 && t1y >= 0 && t1y <= 1 &&
                t2x >= 0 && t2x <= 1 && t2y >= 0 && t2y <= 1) { // *y also must be in range.
            timing1X = t1x;
            timing1Y = t1y;
            timing2X = t2x;
            timing2Y = t2y;
        }

        int msPf = 1000 / fps; // ms/frame
        BezierParser bezierParser = new BezierParser(curX, curY, c1x, c1y, c2x, c2y, dx, dy,
                timing1X, timing1Y, timing2X, timing2Y, timingSpeed, fps);
        double allLen = bezierParser.getAllLen();

        if (delay > 0) {
            robot.delay(delay);
        }

        long startTime = System.nanoTime();
        while (true) {
            long curTime = (System.nanoTime() - startTime) / 1000000;
            double len = bezierParser.getLenByTime(curTime);
            if (len > allLen) { break; }
            Point2D.Double point = bezierParser.getPointByLen(len);
            curX = point.getX();
            curY = point.getY();
            robot.mouseMove((int) curX, (int) curY);
            if (len >= allLen) { break; }
            robot.delay(msPf);
        }
    }

    // timing string
    // Omitted: speed
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param c1x A X coordinate of a first control point when the mouse pointer follows a curve.
     * @param c1y A Y coordinate of a first control point when the mouse pointer follows a curve.
     * @param c2x A X coordinate of a second control point when the mouse pointer follows a curve.
     * @param c2y A Y coordinate of a second control point when the mouse pointer follows a curve.
     * @param timing A keyword that specifies the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double c1x, double c1y, double c2x, double c2y,
                          String timing)
            throws IllegalArgumentException, HeadlessException {
        moveMouse(delay, dx, dy, c1x, c1y, c2x, c2y, 0, timing);
    }

    // timing string
    // Omitted: c*
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param speed An average speed that specifies a number of pixels as the movement distance per millisecond.
     * @param timing A keyword that specifies the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double speed, String timing)
            throws IllegalArgumentException, HeadlessException {
        initCurXY();
        moveMouse(delay, dx, dy, curX, curY, dx, dy, speed, timing);
    }

    // timing string
    // Omitted: speed, c*
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param timing A keyword that specifies the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          String timing)
            throws IllegalArgumentException, HeadlessException {
        moveMouse(delay, dx, dy, 0, timing);
    }

    // timing string
    /**
     * Move the mouse pointer.
     * @param delay A waiting time until the start of the moving.
     * @param dx A X coordinate of the destination point.
     * @param dy A Y coordinate of the destination point.
     * @param c1x A X coordinate of a first control point when the mouse pointer follows a curve.
     * @param c1y A Y coordinate of a first control point when the mouse pointer follows a curve.
     * @param c2x A X coordinate of a second control point when the mouse pointer follows a curve.
     * @param c2y A Y coordinate of a second control point when the mouse pointer follows a curve.
     * @param speed An average speed that specifies a number of pixels as the movement distance per millisecond.
     * @param timing A keyword that specifies the timing-function.
     */
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy,
                          double c1x, double c1y, double c2x, double c2y,
                          double speed, String timing)
            throws IllegalArgumentException, HeadlessException {
        if (timing != null) {
            switch (timing) {
                case TF_LINEAR:
                    timing1X = 0.0;
                    timing1Y = 0.0;
                    timing2X = 1.0;
                    timing2Y = 1.0;
                    break;
                case TF_EASE_IN:
                    timing1X = 0.42;
                    timing1Y = 0.0;
                    timing2X = 1.0;
                    timing2Y = 1.0;
                    break;
                case TF_EASE_IN_OUT:
                    timing1X = 0.42;
                    timing1Y = 0.0;
                    timing2X = 0.58;
                    timing2Y = 1.0;
                    break;
                case TF_EASE_OUT:
                    timing1X = 0.0;
                    timing1Y = 0.0;
                    timing2X = 0.58;
                    timing2Y = 1.0;
                    break;
                default: // ease
                    timing1X = 0.25;
                    timing1Y = 0.1;
                    timing2X = 0.25;
                    timing2Y = 1;
                    break;
            }
        }
        moveMouse(delay, dx, dy, c1x, c1y, c2x, c2y, speed, -1d, 0d, 0d, 0d);
    }

    // Direct move
    @SuppressWarnings("unused")
    public void moveMouse(int delay, double dx, double dy)
            throws IllegalArgumentException, HeadlessException {
        if (delay > 0) {
            robot.delay(delay);
        }

        curX = dx;
        curY = dy;
        robot.mouseMove((int) curX, (int) curY);
    }

    private void initCurXY() throws HeadlessException {
        if (curX < 0 || curY < 0) {
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            if (pointerInfo == null) {
                curX = 0;
                curY = 0;
            } else {
                Point point = pointerInfo.getLocation();
                curX = point.getX();
                curY = point.getY();
            }
        }
    }

    @SuppressWarnings("unused")
    public void pressButton(int delay, int button) throws IllegalArgumentException {
        pressButton(delay, button, false);
    }

    @SuppressWarnings("unused")
    public void pressButton(int delay) throws IllegalArgumentException {
        pressButton(delay, BUTTON_NONE, false);
    }

    @SuppressWarnings("unused")
    public void pressButton(int delay, int button, boolean andRelease) throws IllegalArgumentException {
        if (delay > 0) {
            robot.delay(delay);
        }

        if (button != this.button) {
            if (this.button > BUTTON_NONE) {
                robot.mouseRelease(buttonMask);
                this.button = BUTTON_NONE;
            }
            if (BUTTONS.containsKey(button)) {
                buttonMask = BUTTONS.get(button);
                robot.mousePress(buttonMask);
                this.button = button;

                if (andRelease) {
                    pressButton(1, BUTTON_NONE, false);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void pressKey(int delay, int key, boolean andRelease) throws IllegalArgumentException {
        pressKey(delay, key, andRelease, false);
    }

    @SuppressWarnings("unused")
    public void pressKey(int delay, int key) throws IllegalArgumentException {
        pressKey(delay, key, false, false);
    }

    @SuppressWarnings("unused")
    public void pressKey(int delay) throws IllegalArgumentException {
        pressKey(delay, KEY_NONE, false, false);
    }

    @SuppressWarnings("unused")
    public void pressKey(int delay, int key, boolean andRelease, boolean release)
            throws IllegalArgumentException {
        if (delay > 0) {
            robot.delay(delay);
        }

        if (key > KEY_NONE) {
            if (release) {
                robot.keyRelease(key);
                if (key == this.key) {
                    this.key = KEY_NONE;
                }
            } else {
                robot.keyPress(key);
                this.key = key;

                if (andRelease) {
                    pressKey(1, KEY_NONE, false, false);
                }
            }
        } else if (this.key > KEY_NONE) {
            robot.keyRelease(this.key);
            this.key = KEY_NONE;
        }
    }
}
