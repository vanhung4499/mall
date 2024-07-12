package com.union.mall.common.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dropdown option object
 *
 * @author vanhung4499
 */
@Schema(description = "Dropdown option object")
@Data
@NoArgsConstructor
public class Option<T> {

    public Option(T value, String label) {
        this.value = value;
        this.label = label;
    }

    public Option(T value, String label, List<Option> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

    @Schema(description = "Value of the option")
    private T value;

    @Schema(description = "Label of the option")
    private String label;

    @Schema(description = "List of child options")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option> children;

}
