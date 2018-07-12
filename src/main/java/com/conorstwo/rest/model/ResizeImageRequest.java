package com.conorstwo.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Java representation of resize image JSON request
 */
public class ResizeImageRequest {

    @NotNull
    private final byte[] originalImage;
    @Min(0)
    private final int newHeight;
    @Min(0)
    private final int newWidth;

    public ResizeImageRequest(@JsonProperty("originalImage") final byte[] originalImage,
                              @JsonProperty("newHeight") final int newHeight,
                              @JsonProperty("newWidth") final int newWidth) {
        this.originalImage = originalImage;
        this.newHeight = newHeight;
        this.newWidth = newWidth;
    }


    public int getNewWidth() {
        return newWidth;
    }

    public int getNewHeight() {
        return newHeight;
    }

    public byte[] getOriginalImage() {
        return originalImage;
    }
}
