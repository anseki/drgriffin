/*
 * DrGriffin
 * https://github.com/anseki/drgriffin
 *
 * Copyright (c) 2015 anseki
 * Licensed under the MIT license.
 */

package io.github.anseki.drgriffin;

import java.awt.event.KeyEvent;

/**
 * <code>VK_*</code> from {@link java.awt.event.KeyEvent java.awt.event.KeyEvent}
 */
@SuppressWarnings({"unused", "WeakerAccess", "SpellCheckingInspection"})
public class KeyCode {

    /* Virtual key codes. */

    public static final int VK_ENTER          = KeyEvent.VK_ENTER;
    public static final int VK_BACK_SPACE     = KeyEvent.VK_BACK_SPACE;
    public static final int VK_TAB            = KeyEvent.VK_TAB;
    public static final int VK_CANCEL         = KeyEvent.VK_CANCEL;
    public static final int VK_CLEAR          = KeyEvent.VK_CLEAR;
    public static final int VK_SHIFT          = KeyEvent.VK_SHIFT;
    public static final int VK_CONTROL        = KeyEvent.VK_CONTROL;
    public static final int VK_ALT            = KeyEvent.VK_ALT;
    public static final int VK_PAUSE          = KeyEvent.VK_PAUSE;
    public static final int VK_CAPS_LOCK      = KeyEvent.VK_CAPS_LOCK;
    public static final int VK_ESCAPE         = KeyEvent.VK_ESCAPE;
    public static final int VK_SPACE          = KeyEvent.VK_SPACE;
    public static final int VK_PAGE_UP        = KeyEvent.VK_PAGE_UP;
    public static final int VK_PAGE_DOWN      = KeyEvent.VK_PAGE_DOWN;
    public static final int VK_END            = KeyEvent.VK_END;
    public static final int VK_HOME           = KeyEvent.VK_HOME;

    /**
     * Constant for the non-numpad <b>left</b> arrow key.
     * @see #VK_KP_LEFT
     */
    public static final int VK_LEFT           = KeyEvent.VK_LEFT;

    /**
     * Constant for the non-numpad <b>up</b> arrow key.
     * @see #VK_KP_UP
     */
    public static final int VK_UP             = KeyEvent.VK_UP;

    /**
     * Constant for the non-numpad <b>right</b> arrow key.
     * @see #VK_KP_RIGHT
     */
    public static final int VK_RIGHT          = KeyEvent.VK_RIGHT;

    /**
     * Constant for the non-numpad <b>down</b> arrow key.
     * @see #VK_KP_DOWN
     */
    public static final int VK_DOWN           = KeyEvent.VK_DOWN;

    /**
     * Constant for the comma key, ","
     */
    public static final int VK_COMMA          = KeyEvent.VK_COMMA;

    /**
     * Constant for the minus key, "-"
     * @since 1.2
     */
    public static final int VK_MINUS          = KeyEvent.VK_MINUS;

    /**
     * Constant for the period key, "."
     */
    public static final int VK_PERIOD         = KeyEvent.VK_PERIOD;

    /**
     * Constant for the forward slash key, "/"
     */
    public static final int VK_SLASH          = KeyEvent.VK_SLASH;

    /** VK_0 thru VK_9 are the same as ASCII '0' thru '9' (0x30 - 0x39) */
    public static final int VK_0              = KeyEvent.VK_0;
    public static final int VK_1              = KeyEvent.VK_1;
    public static final int VK_2              = KeyEvent.VK_2;
    public static final int VK_3              = KeyEvent.VK_3;
    public static final int VK_4              = KeyEvent.VK_4;
    public static final int VK_5              = KeyEvent.VK_5;
    public static final int VK_6              = KeyEvent.VK_6;
    public static final int VK_7              = KeyEvent.VK_7;
    public static final int VK_8              = KeyEvent.VK_8;
    public static final int VK_9              = KeyEvent.VK_9;

    /**
     * Constant for the semicolon key, ";"
     */
    public static final int VK_SEMICOLON      = KeyEvent.VK_SEMICOLON;

    /**
     * Constant for the equals key, "="
     */
    public static final int VK_EQUALS         = KeyEvent.VK_EQUALS;

    /** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */
    public static final int VK_A              = KeyEvent.VK_A;
    public static final int VK_B              = KeyEvent.VK_B;
    public static final int VK_C              = KeyEvent.VK_C;
    public static final int VK_D              = KeyEvent.VK_D;
    public static final int VK_E              = KeyEvent.VK_E;
    public static final int VK_F              = KeyEvent.VK_F;
    public static final int VK_G              = KeyEvent.VK_G;
    public static final int VK_H              = KeyEvent.VK_H;
    public static final int VK_I              = KeyEvent.VK_I;
    public static final int VK_J              = KeyEvent.VK_J;
    public static final int VK_K              = KeyEvent.VK_K;
    public static final int VK_L              = KeyEvent.VK_L;
    public static final int VK_M              = KeyEvent.VK_M;
    public static final int VK_N              = KeyEvent.VK_N;
    public static final int VK_O              = KeyEvent.VK_O;
    public static final int VK_P              = KeyEvent.VK_P;
    public static final int VK_Q              = KeyEvent.VK_Q;
    public static final int VK_R              = KeyEvent.VK_R;
    public static final int VK_S              = KeyEvent.VK_S;
    public static final int VK_T              = KeyEvent.VK_T;
    public static final int VK_U              = KeyEvent.VK_U;
    public static final int VK_V              = KeyEvent.VK_V;
    public static final int VK_W              = KeyEvent.VK_W;
    public static final int VK_X              = KeyEvent.VK_X;
    public static final int VK_Y              = KeyEvent.VK_Y;
    public static final int VK_Z              = KeyEvent.VK_Z;

    /**
     * Constant for the open bracket key, "["
     */
    public static final int VK_OPEN_BRACKET   = KeyEvent.VK_OPEN_BRACKET;

    /**
     * Constant for the back slash key, "\"
     */
    public static final int VK_BACK_SLASH     = KeyEvent.VK_BACK_SLASH;

    /**
     * Constant for the close bracket key, "]"
     */
    public static final int VK_CLOSE_BRACKET  = KeyEvent.VK_CLOSE_BRACKET;

    public static final int VK_NUMPAD0        = KeyEvent.VK_NUMPAD0;
    public static final int VK_NUMPAD1        = KeyEvent.VK_NUMPAD1;
    public static final int VK_NUMPAD2        = KeyEvent.VK_NUMPAD2;
    public static final int VK_NUMPAD3        = KeyEvent.VK_NUMPAD3;
    public static final int VK_NUMPAD4        = KeyEvent.VK_NUMPAD4;
    public static final int VK_NUMPAD5        = KeyEvent.VK_NUMPAD5;
    public static final int VK_NUMPAD6        = KeyEvent.VK_NUMPAD6;
    public static final int VK_NUMPAD7        = KeyEvent.VK_NUMPAD7;
    public static final int VK_NUMPAD8        = KeyEvent.VK_NUMPAD8;
    public static final int VK_NUMPAD9        = KeyEvent.VK_NUMPAD9;
    public static final int VK_MULTIPLY       = KeyEvent.VK_MULTIPLY;
    public static final int VK_ADD            = KeyEvent.VK_ADD;

    /**
     * This constant is obsolete, and is included only for backwards
     * compatibility.
     * @see #VK_SEPARATOR
     */
    public static final int VK_SEPARATER      = KeyEvent.VK_SEPARATER;

    /**
     * Constant for the Numpad Separator key.
     * @since 1.4
     */
    public static final int VK_SEPARATOR      = KeyEvent.VK_SEPARATOR;

    public static final int VK_SUBTRACT       = KeyEvent.VK_SUBTRACT;
    public static final int VK_DECIMAL        = KeyEvent.VK_DECIMAL;
    public static final int VK_DIVIDE         = KeyEvent.VK_DIVIDE;
    public static final int VK_DELETE         = KeyEvent.VK_DELETE; /* ASCII DEL */
    public static final int VK_NUM_LOCK       = KeyEvent.VK_NUM_LOCK;
    public static final int VK_SCROLL_LOCK    = KeyEvent.VK_SCROLL_LOCK;

    /** Constant for the F1 function key. */
    public static final int VK_F1             = KeyEvent.VK_F1;

    /** Constant for the F2 function key. */
    public static final int VK_F2             = KeyEvent.VK_F2;

    /** Constant for the F3 function key. */
    public static final int VK_F3             = KeyEvent.VK_F3;

    /** Constant for the F4 function key. */
    public static final int VK_F4             = KeyEvent.VK_F4;

    /** Constant for the F5 function key. */
    public static final int VK_F5             = KeyEvent.VK_F5;

    /** Constant for the F6 function key. */
    public static final int VK_F6             = KeyEvent.VK_F6;

    /** Constant for the F7 function key. */
    public static final int VK_F7             = KeyEvent.VK_F7;

    /** Constant for the F8 function key. */
    public static final int VK_F8             = KeyEvent.VK_F8;

    /** Constant for the F9 function key. */
    public static final int VK_F9             = KeyEvent.VK_F9;

    /** Constant for the F10 function key. */
    public static final int VK_F10            = KeyEvent.VK_F10;

    /** Constant for the F11 function key. */
    public static final int VK_F11            = KeyEvent.VK_F11;

    /** Constant for the F12 function key. */
    public static final int VK_F12            = KeyEvent.VK_F12;

    /**
     * Constant for the F13 function key.
     * @since 1.2
     */
    /* F13 - F24 are used on IBM 3270 keyboard; use random range for constants. */
    public static final int VK_F13            = KeyEvent.VK_F13;

    /**
     * Constant for the F14 function key.
     * @since 1.2
     */
    public static final int VK_F14            = KeyEvent.VK_F14;

    /**
     * Constant for the F15 function key.
     * @since 1.2
     */
    public static final int VK_F15            = KeyEvent.VK_F15;

    /**
     * Constant for the F16 function key.
     * @since 1.2
     */
    public static final int VK_F16            = KeyEvent.VK_F16;

    /**
     * Constant for the F17 function key.
     * @since 1.2
     */
    public static final int VK_F17            = KeyEvent.VK_F17;

    /**
     * Constant for the F18 function key.
     * @since 1.2
     */
    public static final int VK_F18            = KeyEvent.VK_F18;

    /**
     * Constant for the F19 function key.
     * @since 1.2
     */
    public static final int VK_F19            = KeyEvent.VK_F19;

    /**
     * Constant for the F20 function key.
     * @since 1.2
     */
    public static final int VK_F20            = KeyEvent.VK_F20;

    /**
     * Constant for the F21 function key.
     * @since 1.2
     */
    public static final int VK_F21            = KeyEvent.VK_F21;

    /**
     * Constant for the F22 function key.
     * @since 1.2
     */
    public static final int VK_F22            = KeyEvent.VK_F22;

    /**
     * Constant for the F23 function key.
     * @since 1.2
     */
    public static final int VK_F23            = KeyEvent.VK_F23;

    /**
     * Constant for the F24 function key.
     * @since 1.2
     */
    public static final int VK_F24            = KeyEvent.VK_F24;

    public static final int VK_PRINTSCREEN    = KeyEvent.VK_PRINTSCREEN;
    public static final int VK_INSERT         = KeyEvent.VK_INSERT;
    public static final int VK_HELP           = KeyEvent.VK_HELP;
    public static final int VK_META           = KeyEvent.VK_META;

    public static final int VK_BACK_QUOTE     = KeyEvent.VK_BACK_QUOTE;
    public static final int VK_QUOTE          = KeyEvent.VK_QUOTE;

    /**
     * Constant for the numeric keypad <b>up</b> arrow key.
     * @see #VK_UP
     * @since 1.2
     */
    public static final int VK_KP_UP          = KeyEvent.VK_KP_UP;

    /**
     * Constant for the numeric keypad <b>down</b> arrow key.
     * @see #VK_DOWN
     * @since 1.2
     */
    public static final int VK_KP_DOWN        = KeyEvent.VK_KP_DOWN;

    /**
     * Constant for the numeric keypad <b>left</b> arrow key.
     * @see #VK_LEFT
     * @since 1.2
     */
    public static final int VK_KP_LEFT        = KeyEvent.VK_KP_LEFT;

    /**
     * Constant for the numeric keypad <b>right</b> arrow key.
     * @see #VK_RIGHT
     * @since 1.2
     */
    public static final int VK_KP_RIGHT       = KeyEvent.VK_KP_RIGHT;

    /* For European keyboards */
    /** @since 1.2 */
    public static final int VK_DEAD_GRAVE               = KeyEvent.VK_DEAD_GRAVE;
    /** @since 1.2 */
    public static final int VK_DEAD_ACUTE               = KeyEvent.VK_DEAD_ACUTE;
    /** @since 1.2 */
    public static final int VK_DEAD_CIRCUMFLEX          = KeyEvent.VK_DEAD_CIRCUMFLEX;
    /** @since 1.2 */
    public static final int VK_DEAD_TILDE               = KeyEvent.VK_DEAD_TILDE;
    /** @since 1.2 */
    public static final int VK_DEAD_MACRON              = KeyEvent.VK_DEAD_MACRON;
    /** @since 1.2 */
    public static final int VK_DEAD_BREVE               = KeyEvent.VK_DEAD_BREVE;
    /** @since 1.2 */
    public static final int VK_DEAD_ABOVEDOT            = KeyEvent.VK_DEAD_ABOVEDOT;
    /** @since 1.2 */
    public static final int VK_DEAD_DIAERESIS           = KeyEvent.VK_DEAD_DIAERESIS;
    /** @since 1.2 */
    public static final int VK_DEAD_ABOVERING           = KeyEvent.VK_DEAD_ABOVERING;
    /** @since 1.2 */
    public static final int VK_DEAD_DOUBLEACUTE         = KeyEvent.VK_DEAD_DOUBLEACUTE;
    /** @since 1.2 */
    public static final int VK_DEAD_CARON               = KeyEvent.VK_DEAD_CARON;
    /** @since 1.2 */
    public static final int VK_DEAD_CEDILLA             = KeyEvent.VK_DEAD_CEDILLA;
    /** @since 1.2 */
    public static final int VK_DEAD_OGONEK              = KeyEvent.VK_DEAD_OGONEK;
    /** @since 1.2 */
    public static final int VK_DEAD_IOTA                = KeyEvent.VK_DEAD_IOTA;
    /** @since 1.2 */
    public static final int VK_DEAD_VOICED_SOUND        = KeyEvent.VK_DEAD_VOICED_SOUND;
    /** @since 1.2 */
    public static final int VK_DEAD_SEMIVOICED_SOUND    = KeyEvent.VK_DEAD_SEMIVOICED_SOUND;

    /** @since 1.2 */
    public static final int VK_AMPERSAND                = KeyEvent.VK_AMPERSAND;
    /** @since 1.2 */
    public static final int VK_ASTERISK                 = KeyEvent.VK_ASTERISK;
    /** @since 1.2 */
    public static final int VK_QUOTEDBL                 = KeyEvent.VK_QUOTEDBL;
    /** @since 1.2 */
    public static final int VK_LESS                     = KeyEvent.VK_LESS;

    /** @since 1.2 */
    public static final int VK_GREATER                  = KeyEvent.VK_GREATER;
    /** @since 1.2 */
    public static final int VK_BRACELEFT                = KeyEvent.VK_BRACELEFT;
    /** @since 1.2 */
    public static final int VK_BRACERIGHT               = KeyEvent.VK_BRACERIGHT;

    /**
     * Constant for the "@" key.
     * @since 1.2
     */
    public static final int VK_AT                       = KeyEvent.VK_AT;

    /**
     * Constant for the ":" key.
     * @since 1.2
     */
    public static final int VK_COLON                    = KeyEvent.VK_COLON;

    /**
     * Constant for the "^" key.
     * @since 1.2
     */
    public static final int VK_CIRCUMFLEX               = KeyEvent.VK_CIRCUMFLEX;

    /**
     * Constant for the "$" key.
     * @since 1.2
     */
    public static final int VK_DOLLAR                   = KeyEvent.VK_DOLLAR;

    /**
     * Constant for the Euro currency sign key.
     * @since 1.2
     */
    public static final int VK_EURO_SIGN                = KeyEvent.VK_EURO_SIGN;

    /**
     * Constant for the "!" key.
     * @since 1.2
     */
    public static final int VK_EXCLAMATION_MARK         = KeyEvent.VK_EXCLAMATION_MARK;

    /**
     * Constant for the inverted exclamation mark key.
     * @since 1.2
     */
    public static final int VK_INVERTED_EXCLAMATION_MARK = KeyEvent.VK_INVERTED_EXCLAMATION_MARK;

    /**
     * Constant for the "(" key.
     * @since 1.2
     */
    public static final int VK_LEFT_PARENTHESIS         = KeyEvent.VK_LEFT_PARENTHESIS;

    /**
     * Constant for the "#" key.
     * @since 1.2
     */
    public static final int VK_NUMBER_SIGN              = KeyEvent.VK_NUMBER_SIGN;

    /**
     * Constant for the "+" key.
     * @since 1.2
     */
    public static final int VK_PLUS                     = KeyEvent.VK_PLUS;

    /**
     * Constant for the ")" key.
     * @since 1.2
     */
    public static final int VK_RIGHT_PARENTHESIS        = KeyEvent.VK_RIGHT_PARENTHESIS;

    /**
     * Constant for the "_" key.
     * @since 1.2
     */
    public static final int VK_UNDERSCORE               = KeyEvent.VK_UNDERSCORE;

    /**
     * Constant for the Microsoft Windows "Windows" key.
     * It is used for both the left and right version of the key.
     * @see java.awt.event.KeyEvent#getKeyLocation()
     * @since 1.5
     */
    public static final int VK_WINDOWS                  = KeyEvent.VK_WINDOWS;

    /**
     * Constant for the Microsoft Windows Context Menu key.
     * @since 1.5
     */
    public static final int VK_CONTEXT_MENU             = KeyEvent.VK_CONTEXT_MENU;

    /* for input method support on Asian Keyboards */

    /* not clear what this means - listed in Microsoft Windows API */
    public static final int VK_FINAL                    = KeyEvent.VK_FINAL;

    /** Constant for the Convert function key. */
    /* Japanese PC 106 keyboard, Japanese Solaris keyboard: henkan */
    public static final int VK_CONVERT                  = KeyEvent.VK_CONVERT;

    /** Constant for the Don't Convert function key. */
    /* Japanese PC 106 keyboard: muhenkan */
    public static final int VK_NONCONVERT               = KeyEvent.VK_NONCONVERT;

    /** Constant for the Accept or Commit function key. */
    /* Japanese Solaris keyboard: kakutei */
    public static final int VK_ACCEPT                   = KeyEvent.VK_ACCEPT;

    /* not clear what this means - listed in Microsoft Windows API */
    public static final int VK_MODECHANGE               = KeyEvent.VK_MODECHANGE;

    /* replaced by VK_KANA_LOCK for Microsoft Windows and Solaris;
       might still be used on other platforms */
    public static final int VK_KANA                     = KeyEvent.VK_KANA;

    /* replaced by VK_INPUT_METHOD_ON_OFF for Microsoft Windows and Solaris;
       might still be used for other platforms */
    public static final int VK_KANJI                    = KeyEvent.VK_KANJI;

    /**
     * Constant for the Alphanumeric function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: eisuu */
    public static final int VK_ALPHANUMERIC             = KeyEvent.VK_ALPHANUMERIC;

    /**
     * Constant for the Katakana function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: katakana */
    public static final int VK_KATAKANA                 = KeyEvent.VK_KATAKANA;

    /**
     * Constant for the Hiragana function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: hiragana */
    public static final int VK_HIRAGANA                 = KeyEvent.VK_HIRAGANA;

    /**
     * Constant for the Full-Width Characters function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: zenkaku */
    public static final int VK_FULL_WIDTH               = KeyEvent.VK_FULL_WIDTH;

    /**
     * Constant for the Half-Width Characters function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: hankaku */
    public static final int VK_HALF_WIDTH               = KeyEvent.VK_HALF_WIDTH;

    /**
     * Constant for the Roman Characters function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard: roumaji */
    public static final int VK_ROMAN_CHARACTERS         = KeyEvent.VK_ROMAN_CHARACTERS;

    /**
     * Constant for the All Candidates function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard - VK_CONVERT + ALT: zenkouho */
    public static final int VK_ALL_CANDIDATES           = KeyEvent.VK_ALL_CANDIDATES;

    /**
     * Constant for the Previous Candidate function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard - VK_CONVERT + SHIFT: maekouho */
    public static final int VK_PREVIOUS_CANDIDATE       = KeyEvent.VK_PREVIOUS_CANDIDATE;

    /**
     * Constant for the Code Input function key.
     * @since 1.2
     */
    /* Japanese PC 106 keyboard - VK_ALPHANUMERIC + ALT: kanji bangou */
    public static final int VK_CODE_INPUT               = KeyEvent.VK_CODE_INPUT;

    /**
     * Constant for the Japanese-Katakana function key.
     * This key switches to a Japanese input method and selects its Katakana input mode.
     * @since 1.2
     */
    /* Japanese Macintosh keyboard - VK_JAPANESE_HIRAGANA + SHIFT */
    public static final int VK_JAPANESE_KATAKANA        = KeyEvent.VK_JAPANESE_KATAKANA;

    /**
     * Constant for the Japanese-Hiragana function key.
     * This key switches to a Japanese input method and selects its Hiragana input mode.
     * @since 1.2
     */
    /* Japanese Macintosh keyboard */
    public static final int VK_JAPANESE_HIRAGANA        = KeyEvent.VK_JAPANESE_HIRAGANA;

    /**
     * Constant for the Japanese-Roman function key.
     * This key switches to a Japanese input method and selects its Roman-Direct input mode.
     * @since 1.2
     */
    /* Japanese Macintosh keyboard */
    public static final int VK_JAPANESE_ROMAN           = KeyEvent.VK_JAPANESE_ROMAN;

    /**
     * Constant for the locking Kana function key.
     * This key locks the keyboard into a Kana layout.
     * @since 1.3
     */
    /* Japanese PC 106 keyboard with special Windows driver - eisuu + Control; Japanese Solaris keyboard: kana */
    public static final int VK_KANA_LOCK                = KeyEvent.VK_KANA_LOCK;

    /**
     * Constant for the input method on/off key.
     * @since 1.3
     */
    /* Japanese PC 106 keyboard: kanji. Japanese Solaris keyboard: nihongo */
    public static final int VK_INPUT_METHOD_ON_OFF      = KeyEvent.VK_INPUT_METHOD_ON_OFF;

    /* for Sun keyboards */
    /** @since 1.2 */
    public static final int VK_CUT                      = KeyEvent.VK_CUT;
    /** @since 1.2 */
    public static final int VK_COPY                     = KeyEvent.VK_COPY;
    /** @since 1.2 */
    public static final int VK_PASTE                    = KeyEvent.VK_PASTE;
    /** @since 1.2 */
    public static final int VK_UNDO                     = KeyEvent.VK_UNDO;
    /** @since 1.2 */
    public static final int VK_AGAIN                    = KeyEvent.VK_AGAIN;
    /** @since 1.2 */
    public static final int VK_FIND                     = KeyEvent.VK_FIND;
    /** @since 1.2 */
    public static final int VK_PROPS                    = KeyEvent.VK_PROPS;
    /** @since 1.2 */
    public static final int VK_STOP                     = KeyEvent.VK_STOP;

    /**
     * Constant for the Compose function key.
     * @since 1.2
     */
    public static final int VK_COMPOSE                  = KeyEvent.VK_COMPOSE;

    /**
     * Constant for the AltGraph function key.
     * @since 1.2
     */
    public static final int VK_ALT_GRAPH                = KeyEvent.VK_ALT_GRAPH;

    /**
     * Constant for the Begin key.
     * @since 1.5
     */
    public static final int VK_BEGIN                    = KeyEvent.VK_BEGIN;

    /**
     * This value is used to indicate that the keyCode is unknown.
     * KEY_TYPED events do not have a keyCode value; this value
     * is used instead.
     */
    public static final int VK_UNDEFINED      = KeyEvent.VK_UNDEFINED;

}
