package notai.global.exception;

import lombok.Getter;
import notai.global.exception.errorCode.ErrorCode;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorResponse toResponse() {
        return ErrorResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .errorCode(errorCode.toString())
            .message(errorCode.getMessage())
            .build();
    }
}
