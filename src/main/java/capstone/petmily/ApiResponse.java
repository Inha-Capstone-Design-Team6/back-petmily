package capstone.petmily;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "message", "result"})
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final String message;
    private T result;

    public static <T> ApiResponse<T> onSuccess(T result) {

        return new ApiResponse<>(true, "Success", result);
    }

    public static <T> ApiResponse<T> onFailure(String message, T result) {
        return new ApiResponse<>(false, message, result);
    }
}
