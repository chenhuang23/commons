package com.github.commons.utils.format;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64 {

    public static byte[] decode(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String encode(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
