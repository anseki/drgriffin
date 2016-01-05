/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2015 anseki
 * Licensed under the MIT license.
 */

package io.github.anseki.drgriffin.util;

import io.github.anseki.drgriffin.DrGriffin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.awt.AWTException;

import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

class Cli {

    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        if (args.length < 1){
            printUsage();
            System.exit(0);
        }

        switch (args[0]) {
            case "exec":
                if (args.length < 2){
                    System.err.println("No parameter: exec-file");
                    System.exit(1);
                }
                if (!exec(args[1])) {
                    System.exit(1);
                }
                break;
            case "pointer-xy":
                if (!pointerXY()) {
                    System.exit(1);
                }
                break;
            default:
                System.err.printf("Invalid command: %s", args[0]).println();
                printUsage();
                System.exit(1);
                break;
        }

        System.exit(0);
    }

    private static void printUsage() {
        System.err.println("Usage: drgriffin <command>[ <arg1>[ <arg2> ...]]");
        System.err.println("  commands and arguments:");
        System.err.println("    - exec path/to/methods.yml");
        System.err.println("      Execute methods written in specific file.");
        System.err.println("    - pointer-xy");
        System.err.println("      Get current coordinates of the mouse pointer.");
    }

    private static boolean exec(String path) {
        InputStream input;
        try {
            input = new FileInputStream(new File(path));
        } catch (java.io.FileNotFoundException e) {
            System.err.printf("Couldn't load file: %s", path).println();
            System.err.println(e.getLocalizedMessage());
            return false;
        }

        Yaml yaml = new Yaml(new SafeConstructor());
        List list;
        try {
            list = (List) yaml.load(input);
        } catch (ClassCastException e) {
            System.err.println("Top-level-structure must be sequence structure.");
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Couldn't parse YAML.");
            System.err.println(e.getLocalizedMessage());
            return false;
        }

        try {
            RequestList requestList = new RequestList(new DrGriffin(), list);
            if (!requestList.valid || !requestList.exec()) { return false; }
        } catch (IllegalAccessException | MethodsParser.ClassParseException | // parse error
                MethodsParser.InvalidArgumentsForParameterException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } catch (AWTException e) { // Thrown by DrGriffin.
            System.err.println("It's not able to control the mouse and keyboard.");
            System.err.println(e.getLocalizedMessage());
            return false;
        }

        System.out.println("Finished.");
        return true;
    }

    private static boolean pointerXY() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo == null) {
            System.err.println("Couldn't get pointer-xy.");
            return false;
        }

        Point point = pointerInfo.getLocation();
        String content = String.format("%d, %d", point.x, point.y);

        setClipboard(content);
        System.out.printf("[pointer-xy] (X, Y): %s", content).println();
        System.out.println("It was saved to clipboard.");

        return true;
    }

    private static void setClipboard(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(content);
        clipboard.setContents(selection, null);
    }
}
