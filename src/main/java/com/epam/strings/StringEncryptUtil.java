package com.epam.strings;

import com.epam.fp.CheckedFunction1;
import lombok.val;

import java.security.MessageDigest;

public interface StringEncryptUtil {

  String ALGORITHM = "MD5";

  @SuppressWarnings("SpellCheckingInspection")
  MessageDigest ENCRYPTOR =
    CheckedFunction1.<String, MessageDigest>of(MessageDigest::getInstance)
      .unchecked()
      .apply(ALGORITHM);

  static String encrypt(String s) {

    ENCRYPTOR.reset();

    byte[] bs = ENCRYPTOR.digest(s.getBytes());

    val stringBuilder = new StringBuilder();

    //hex encode the digest
    for (byte b : bs) {
      String hexVal = Integer.toHexString(0xFF & b);
      if (hexVal.length() == 1)
        stringBuilder.append("0");
      stringBuilder.append(hexVal);
    }

    return stringBuilder.toString();
  }
}
