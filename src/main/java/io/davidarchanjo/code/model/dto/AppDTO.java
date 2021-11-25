package io.davidarchanjo.code.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AppDTO {

    @Nullable
    private Long id;

    @NotEmpty
    @Size(max = 20)
    @JsonProperty("appName")
    private String name;

    @NotEmpty
    @JsonProperty("devName")
    private String author;

    @NotEmpty
    @JsonProperty("appVersion")
    private String version;

    @Builder
    public AppDTO(String name, String version, String author) {
        this.name = name;
        this.author = author;
        this.version = version;
    }

}
