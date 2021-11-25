package io.davidarchanjo.code.exception;

import java.text.MessageFormat;

public class AppAlreadyExistException extends RuntimeException {

    public AppAlreadyExistException(String pattern, Object... args) {
        super(new MessageFormat(pattern).format(args));
    }

}
