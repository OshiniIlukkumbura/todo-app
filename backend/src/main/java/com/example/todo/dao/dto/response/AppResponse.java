package com.example.todo.dao.dto.response;
import com.example.todo.dao.enums.EApiStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppResponse<T> {

    private T data;
    private MetaResponse meta;
    private ErrorResponse error;
    private PagingResponse paging;

    // Generic success response
    public static <T> AppResponse<T> success(T data, String message) {
        return AppResponse.<T>builder()
                .data(data)
                .meta(MetaResponse.builder()
                        .status(EApiStatus.SUCCESS.getStatus())
                        .code(EApiStatus.SUCCESS.getCode())
                        .message(message != null ? message : EApiStatus.SUCCESS.getMessage())
                        .build())
                .build();
    }

    // Success response with pagination
    public static <T> AppResponse<T> successWithPaging(T data, String message, PagingResponse paging) {
        return AppResponse.<T>builder()
                .data(data)
                .meta(MetaResponse.builder()
                        .status(EApiStatus.SUCCESS.getStatus())
                        .code(EApiStatus.SUCCESS.getCode())
                        .message(message != null ? message : EApiStatus.SUCCESS.getMessage())
                        .build())
                .paging(paging)
                .build();
    }

    // Error response
    public static <T> AppResponse<T> failure(EApiStatus status, String errorCode, String errorMessage) {
        return AppResponse.<T>builder()
                .meta(MetaResponse.builder()
                        .status(status.getStatus())
                        .code(status.getCode())
                        .message(status.getMessage())
                        .build())
                .error(ErrorResponse.builder()
                        .code(errorCode)
                        .message(errorMessage)
                        .build())
                .build();
    }
}
