package io.davidarchanjo.code.exception;

import java.text.MessageFormat;

public class AppNotFoundException extends RuntimeException {

    public AppNotFoundException(String pattern, Object... args) {
        super(new MessageFormat(pattern).format(args));
    }

}
