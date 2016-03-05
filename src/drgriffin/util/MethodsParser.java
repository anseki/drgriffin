/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2015 anseki
 * Licensed under the MIT license.
 */

package io.github.anseki.drgriffin.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

public class MethodsParser {
    private static final int TYPE_STRING = 1;
    private static final int TYPE_BOOLEAN = 2;
    private static final int TYPE_DOUBLE = 3;
    private static final int TYPE_INTEGER = 4;

    private static final Map<Class, Integer> CLASS_2_TYPE = new HashMap<Class, Integer>() {{
        put(String.class, TYPE_STRING);
        put(Boolean.class, TYPE_BOOLEAN);
        put(boolean.class, TYPE_BOOLEAN);
        put(Double.class, TYPE_DOUBLE);
        put(double.class, TYPE_DOUBLE);
        put(Integer.class, TYPE_INTEGER);
        put(int.class, TYPE_INTEGER);
    }};

    private final Object targetInstance;
    private final Map<String, List<MethodDeclaration>> methodDeclarationSet = new HashMap<>();
    private final Map<Integer, Map<String, Object>> namedValues =
            new HashMap<Integer, Map<String, Object>>() {{
                put(TYPE_STRING, new HashMap<>());
                put(TYPE_BOOLEAN, new HashMap<>());
                put(TYPE_DOUBLE, new HashMap<>());
                put(TYPE_INTEGER, new HashMap<>());
            }};

    public MethodsParser(Object targetInstance) throws ClassParseException, IllegalAccessException {
        this.targetInstance = targetInstance;

        // Parse Methods
        for (Method method : targetInstance.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                String methodName = method.getName();
                if (!methodDeclarationSet.containsKey(methodName)) {
                    methodDeclarationSet.put(methodName, new ArrayList<>());
                }
                methodDeclarationSet.get(methodName).add(new MethodDeclaration(this, method));
            }
        }
        for (String methodName : methodDeclarationSet.keySet()) {
            Collections.sort(methodDeclarationSet.get(methodName),
                    new MethodDeclaration.MethodDeclarationComparator());
        }

        // Parse const Fields
        for (Field field : targetInstance.getClass().getFields()) { // These are public fields.
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                Class fieldClass = field.getType();
                if (!CLASS_2_TYPE.containsKey(fieldClass)) {
                    // This unknown type of signature exist in the class.
                    throw new ClassParseException(
                            "\"" + field.getName() + "\" parameter is unknown type: " + fieldClass);
                }
                namedValues.get(CLASS_2_TYPE.get(fieldClass)).put(field.getName(), field.get(null));
            }
        }
    }

    public MethodRequest findMethod(String methodName, Object arguments) throws InvalidArgumentsForParameterException {
        if (!methodDeclarationSet.containsKey(methodName)) { return null; }
        List<MethodDeclaration> methodDeclarations = methodDeclarationSet.get(methodName);
        MethodsParser.MethodRequest methodRequest = chooseMethod(methodDeclarations, arguments, false);
        if (methodRequest != null) { return methodRequest; }
        // Retry those forced
        return chooseMethod(methodDeclarations, arguments, true);
    }

    private MethodRequest chooseMethod(List<MethodDeclaration> methodDeclarations, Object arguments, boolean force)
            throws InvalidArgumentsForParameterException {
        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            List<ParameterValue> parameterValues = methodDeclaration.getMatchedParameterValues(arguments, force);
            if (parameterValues != null) {
                return new MethodRequest(this, methodDeclaration.method, parameterValues);
            }
        }
        return null;
    }

    static class MethodDeclaration {
        private final Method method;
        private final List<ParameterDeclaration> parameterDeclarations = new ArrayList<>();
        private final String parametersSignature;

        MethodDeclaration(MethodsParser methodsParser, Method method) throws ClassParseException {
            this.method = method;
            List<String> parameterNames = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                ParameterDeclaration parameterDeclaration = new ParameterDeclaration(methodsParser, parameter);
                parameterDeclarations.add(parameterDeclaration);
                parameterNames.add(parameterDeclaration.name);
            }
            parametersSignature = getParametersSignature(parameterNames);
        }

        private static String getParametersSignature(List<String> parameterNames) {
            List<String> names = new ArrayList<>(parameterNames);
            Collections.sort(names);
            return String.join(",", names);
        }

        @SuppressWarnings("unchecked")
        List<ParameterValue> getMatchedParameterValues(Object arguments, boolean force)
                throws InvalidArgumentsForParameterException {
            try {
                if (arguments instanceof Map) {
                    return getMatchedParameterValues((Map<String, Object>) arguments, force);
                } else if (arguments instanceof List) {
                    return getMatchedParameterValues((List<Object>) arguments, force);
                }
            } catch (ClassCastException e) {
                throw new InvalidArgumentsForParameterException(
                        "Class must be Map<String, Object> or List<Object>.");
            }
            throw new InvalidArgumentsForParameterException("Class must be Map or List.");
        }

        private List<ParameterValue> getMatchedParameterValues(Map<String, Object> arguments, boolean force) {
            List<String> parameterNames = (new ArrayList<>(arguments.keySet()))
                    .stream().filter(e -> !e.equals("method")).collect(Collectors.toList());
            if (!parametersSignature.equals(getParametersSignature(parameterNames))) { return null; }

            List<ParameterValue> parameterValues = new ArrayList<>();
            for (ParameterDeclaration parameterDeclaration : parameterDeclarations) {
                ParameterValue parameterValue =
                        parameterDeclaration.getParameterValue(arguments.get(parameterDeclaration.name), force);
                if (parameterValue == null) { return null; }
                parameterValues.add(parameterValue);
            }
            return parameterValues;
        }

        private List<ParameterValue> getMatchedParameterValues(List<Object> arguments, boolean force) {
            int size = parameterDeclarations.size();
            if (arguments.size() != size) { return null; }

            List<ParameterValue> parameterValues = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                ParameterDeclaration parameterDeclaration = parameterDeclarations.get(i);
                ParameterValue parameterValue = parameterDeclaration.getParameterValue(arguments.get(i), force);
                if (parameterValue == null) { return null; }
                parameterValues.add(parameterValue);
            }
            return parameterValues;
        }

        private static class ParameterDeclaration {
            private static final Map<Integer, Integer> TYPE_2_PRIORITY = new HashMap<Integer, Integer>() {{
                // How stricter converting
                put(TYPE_STRING, 1);    // any
                put(TYPE_BOOLEAN, 2);   // any ("true": true / others: false)
                put(TYPE_DOUBLE, 3);    // [\d\.]
                put(TYPE_INTEGER, 4);   // \d
            }};

            private final MethodsParser methodsParser;
            private final int typeId;
            private final int priority;
            private final String name;

            ParameterDeclaration(MethodsParser methodsParser, Parameter parameter) throws ClassParseException {
                Class parameterClass = parameter.getType();
                if (!CLASS_2_TYPE.containsKey(parameterClass)) {
                    // This unknown type of signature exist in the class.
                    throw new ClassParseException(
                            "\"" + parameter.getName() + "\" parameter is unknown type: " + parameterClass);
                }
                if (!parameter.isNamePresent()) {
                    // command: `javac -parameters`
                    throw new ClassParseException("\"-parameters\" option is required.");
                }
                this.methodsParser = methodsParser;
                typeId = CLASS_2_TYPE.get(parameterClass);
                priority = TYPE_2_PRIORITY.get(typeId);
                name = parameter.getName();
            }

            ParameterValue getParameterValue(Object argumentValue, boolean force) {
                ParameterValue parameterValue = null;
                switch (typeId) {
                    case TYPE_STRING:
                        parameterValue = argumentValue instanceof String ? (
                                methodsParser.namedValues.get(TYPE_STRING).containsKey(argumentValue) ?
                                        new ParameterValue(TYPE_STRING,
                                                methodsParser.namedValues.get(TYPE_STRING).get(argumentValue),
                                                (String) argumentValue) :
                                        new ParameterValue(TYPE_STRING, argumentValue)
                        ) : force ? new ParameterValue(TYPE_STRING,
                                // Accept null as String only.
                                argumentValue != null ? argumentValue.toString() : null) : null;
                        break;
                    case TYPE_BOOLEAN:
                        if (argumentValue instanceof Boolean) {
                            parameterValue = new ParameterValue(TYPE_BOOLEAN, argumentValue);
                            break;
                        } else if (argumentValue instanceof String) {
                            argumentValue = ((String) argumentValue).trim();
                            if (methodsParser.namedValues.get(TYPE_BOOLEAN).containsKey(argumentValue)) {
                                parameterValue = new ParameterValue(TYPE_BOOLEAN,
                                        methodsParser.namedValues.get(TYPE_BOOLEAN).get(argumentValue),
                                        (String) argumentValue);
                                break;
                            }
                            if ("true".equals(((String) argumentValue).toLowerCase())) {
                                parameterValue = new ParameterValue(TYPE_BOOLEAN, true);
                                break;
                            } else if ("false".equals(((String) argumentValue).toLowerCase())) {
                                parameterValue = new ParameterValue(TYPE_BOOLEAN, false);
                                break;
                            }
                        }
                        parameterValue = force && argumentValue != null ?
                                new ParameterValue(TYPE_BOOLEAN, Boolean.parseBoolean(argumentValue.toString())) :
                                null;
                        break;
                    case TYPE_DOUBLE:
                        if (argumentValue instanceof Double) {
                            parameterValue = new ParameterValue(TYPE_DOUBLE, argumentValue);
                            break;
                        } else if (argumentValue instanceof Number) {
                            parameterValue = new ParameterValue(TYPE_DOUBLE,
                                    Double.parseDouble(argumentValue.toString()));
                            break;
                        } else if (argumentValue instanceof String) {
                            argumentValue = ((String) argumentValue).trim();
                            if (methodsParser.namedValues.get(TYPE_DOUBLE).containsKey(argumentValue)) {
                                parameterValue = new ParameterValue(TYPE_DOUBLE,
                                        methodsParser.namedValues.get(TYPE_DOUBLE).get(argumentValue),
                                        (String) argumentValue);
                                break;
                            }
                        }
                        if (!force) { break; }
                        try {
                            parameterValue = new ParameterValue(TYPE_DOUBLE,
                                    Double.parseDouble(argumentValue.toString()));
                        } catch (NullPointerException | NumberFormatException ignore) {}
                        break;
                    case TYPE_INTEGER:
                        if (argumentValue instanceof Integer) {
                            parameterValue = new ParameterValue(TYPE_INTEGER, argumentValue);
                            break;
                        } else if (argumentValue instanceof Number) {
                            parameterValue = new ParameterValue(TYPE_INTEGER,
                                    Integer.parseInt(argumentValue.toString()));
                            break;
                        } else if (argumentValue instanceof String) {
                            argumentValue = ((String) argumentValue).trim();
                            if (methodsParser.namedValues.get(TYPE_INTEGER).containsKey(argumentValue)) {
                                parameterValue = new ParameterValue(TYPE_INTEGER,
                                        methodsParser.namedValues.get(TYPE_INTEGER).get(argumentValue),
                                        (String) argumentValue);
                                break;
                            }
                        }
                        if (!force) { break; }
                        try {
                            parameterValue = new ParameterValue(TYPE_INTEGER,
                                    Integer.parseInt(argumentValue.toString()));
                        } catch (NullPointerException | NumberFormatException ignore) {}
                        break;
                }
                return parameterValue;
            }
        }

        static class MethodDeclarationComparator implements Comparator<MethodDeclaration> {
            @Override
            public int compare(MethodDeclaration o1, MethodDeclaration o2) {
                int cmp = Integer.compare(o2.parameterDeclarations.size(), o1.parameterDeclarations.size());
                if (cmp != 0) { return cmp; }
                for (int i = 0; i < o1.parameterDeclarations.size(); i++) {
                    cmp = Integer.compare(o2.parameterDeclarations.get(i).priority,
                            o1.parameterDeclarations.get(i).priority);
                    if (cmp != 0) { return cmp; }
                }
                return 0;
            }
        }
    }

    private static class ParameterValue {
        final Object value;
        final String face;

        ParameterValue(int typeId, Object value) {
            this(typeId, value, null);
        }

        ParameterValue(int typeId, Object value, String face) {
            this.value = value;
            this.face = face != null ? face :
                    typeId == TYPE_STRING && value != null ? "\"" + value + "\"" : String.valueOf(value);
        }

        @Override
        public String toString() { return face; }
    }

    public static class MethodRequest {
        private final MethodsParser methodsParser;
        private final Method method;
        private final Object[] parameterValueArray;
        private final String face;

        public MethodRequest(MethodsParser methodsParser, Method method, List<ParameterValue> parameterValues) {
            this.methodsParser = methodsParser;
            this.method = method;
            parameterValueArray = parameterValues.stream().map(parameter -> parameter.value).toArray();
            face = method.getName() + "(" +
                    String.join(", ",
                            parameterValues.stream().map(ParameterValue::toString).toArray(String[]::new)) +
                    ")";
        }

        public void exec()
                throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            method.invoke(methodsParser.targetInstance, parameterValueArray);
        }

        @Override
        public String toString() { return face; }
    }

    public static class InvalidArgumentsForParameterException extends Exception {
        public InvalidArgumentsForParameterException(String message) {
            super(message);
        }
    }

    public static class ClassParseException extends Exception {
        ClassParseException(String message) {
            super(message);
        }
    }
}
