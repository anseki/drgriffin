/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2015 anseki
 * Licensed under the MIT license.
 */

package io.github.anseki.drgriffin.util;

import io.github.anseki.drgriffin.DrGriffin;
import java.awt.AWTException;
import java.lang.reflect.InvocationTargetException;

import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class REPL {
    private static PrintStream out = null;
    private static PrintStream err = null;

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                out = new PrintStream(System.out, true, args[0]);
                err = new PrintStream(System.err, true, args[0]);
            } catch (UnsupportedEncodingException ignore) {}
        }
        if (out == null) { out = new PrintStream(System.out, true); }
        if (err == null) { err = new PrintStream(System.err, true); }

        MethodsParser methodsParser = null;
        try {
            methodsParser = new MethodsParser(new DrGriffin());
        } catch (AWTException | MethodsParser.ClassParseException | IllegalAccessException e) {
            responseError("", e.getLocalizedMessage());
            System.exit(1);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((line = reader.readLine()) != null) {

                List<String> arguments = new ArrayList<>(Arrays.asList(line.split("\\t", -1)));
                if (arguments.size() < 2) {
                    responseError("", "Invalid request.");
                    continue;
                }
                String requestId = arguments.remove(0);
                String methodName = arguments.remove(0);

                MethodsParser.MethodRequest methodRequest;
                try {
                    methodRequest = methodsParser.findMethod(methodName, arguments);
                } catch (MethodsParser.InvalidArgumentsForParameterException e) {
                    responseError(requestId, e.getLocalizedMessage());
                    continue;
                }
                if (methodRequest == null) {
                    responseError(requestId, "Unknown method.");
                    continue;
                }

                try {
                    methodRequest.exec();
                } catch (InvocationTargetException | IllegalAccessException e) {
                    responseError(requestId, e.getLocalizedMessage());
                    continue;
                }

                responseOk(requestId);
            }
            reader.close();
        } catch (IOException e) {
            responseError("", e.getLocalizedMessage());
            System.exit(1);
        }

        System.exit(0);
    }

    private static void responseError(String requestId, String message) {
        err.println(response(requestId, message));
    }

    private static void responseOk(String requestId) {
        out.println(response(requestId, "OK"));
    }

    private static String response(String requestId, String message) {
        return String.format("%s\t%s", requestId, message);
    }
}
