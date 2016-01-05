/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2015 anseki
 * Licensed under the MIT license.
 */

package io.github.anseki.drgriffin.util;

import io.github.anseki.drgriffin.DrGriffin;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("WeakerAccess")
public class RequestList {
    private final MethodsParser methodsParser;
    private final List<MethodsParser.MethodRequest> methodRequests = new ArrayList<>();
    final boolean valid;

    public RequestList(DrGriffin targetInstance, List requestElements)
            throws IllegalAccessException,
            MethodsParser.ClassParseException, MethodsParser.InvalidArgumentsForParameterException {
        methodsParser = new MethodsParser(targetInstance);
        valid = parseRequestElements(requestElements);
    }

    private boolean parseRequestElements(List requestElements)
            throws MethodsParser.InvalidArgumentsForParameterException {
        for (int i = 0; i < requestElements.size(); i++) {
            Object requestElement = requestElements.get(i);
            String requestFace = requestElement.toString(); // for log (requestElement might be changed)
            MethodsParser.MethodRequest methodRequest = getMethodRequest(requestElement);
            if (methodRequest == null) {
                System.err.printf("#%d Invalid element as a method: %s", i + 1, requestFace).println();
                return false;
            }
            methodRequests.add(methodRequest);
        }
        return true;
    }

    private MethodsParser.MethodRequest getMethodRequest(Object requestElement)
            throws MethodsParser.InvalidArgumentsForParameterException {
        MethodsParser.MethodRequest methodRequest;
        Map<String, Object> mapAsRequest = cast2Map(requestElement);
        if (mapAsRequest != null) {
            Object objMethod = mapAsRequest.containsKey("method") ? mapAsRequest.get("method") : null;
            if (objMethod == null || !(objMethod instanceof String)) {
                System.err.println("Mapping as a method has to include \"method\".");
                return null;
            }
            String methodName = (String) objMethod;

            Object objArgs = mapAsRequest.containsKey("args") ? mapAsRequest.get("args") : null;
            if (objArgs != null) {
                Map<String, Object> mapArguments = cast2Map(objArgs);
                if (mapArguments != null) {
                    methodRequest = methodsParser.findMethod(methodName, mapArguments);
                } else {
                    List<Object> listArguments = cast2List(objArgs);
                    if (listArguments == null) {
                        System.err.println("\"args\" element must be mapping or sequence structure.");
                        return null;
                    }
                    methodRequest = methodsParser.findMethod(methodName, listArguments);
                }
            } else {
                methodRequest = methodsParser.findMethod(methodName, mapAsRequest);
            }
        } else {
            List<Object> listAsRequest = cast2List(requestElement);
            if (listAsRequest == null) {
                System.err.println("Each element as a method must be mapping or sequence structure.");
                return null;
            }
            Object objMethod = listAsRequest.size() > 0 ? listAsRequest.remove(0) : null;
            if (objMethod == null || !(objMethod instanceof String)) {
                System.err.println("First element of sequence as a method must be a <methodName>.");
                return null;
            }
            methodRequest = methodsParser.findMethod((String) objMethod, listAsRequest);
        }
        if (methodRequest == null) {
            System.err.println("Method not found.");
        }
        return methodRequest;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> cast2Map(Object element) {
        try { return (Map<String, Object>) element; }
        catch (ClassCastException e) { return null; }
    }

    @SuppressWarnings("unchecked")
    private static List<Object> cast2List(Object element) {
        try { return (List<Object>) element; }
        catch (ClassCastException e) { return null; }
    }

    boolean exec() {
        int size = methodRequests.size();
        if (size < 1) {
            System.err.println("No method.");
            return false;
        }
        for (int i = 0; i < size; i++) {
            MethodsParser.MethodRequest methodRequest = methodRequests.get(i);
            System.out.printf("#%d %s", i + 1, methodRequest).println();
            try {
                methodRequest.exec();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
                    java.awt.HeadlessException e) { // Thrown by DrGriffin.
                System.err.println("Method failed.");
                System.err.println(e.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }
}
