package com.conorstwo.service;

/**
 * Interface for resizing images functionality
 */
public interface ResizeImageService {

    /**
     * Method to resize the supplied image to the new height and width specified
     *
     * @param originalImage - original image as byte array
     * @param newHeight - new height as positive int
     * @param newWidth - new width as positive int
     * @return - byte array of the resized image
     */
    byte[] resizeImage(byte[] originalImage, int newHeight, int newWidth);
}
