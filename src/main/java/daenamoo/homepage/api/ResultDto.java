package daenamoo.homepage.api;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ResultDto {

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
