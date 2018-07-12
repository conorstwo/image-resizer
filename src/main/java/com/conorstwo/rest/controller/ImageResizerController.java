package com.conorstwo.rest.controller;

import com.conorstwo.rest.model.ResizeImageRequest;
import com.conorstwo.service.ResizeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Controller class for image resizing functionality
 */
@Controller
public class ImageResizerController {

    private final ResizeImageService resizeImageService;

    @Autowired
    public ImageResizerController(final ResizeImageService resizeImageService) {
        this.resizeImageService = resizeImageService;
    }

    /**
     * Method to resize the original image to the new height and width specified
     *
     * @param resizeImageRequest - {@link ResizeImageRequest} containing the original image and the new height and width required
     * @return - resized image as a byte array
     */
    @RequestMapping(value = "/resizeImage", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public byte[] resizeImage(@RequestBody @Valid final ResizeImageRequest resizeImageRequest) {
        return resizeImageService.resizeImage(resizeImageRequest.getOriginalImage(), resizeImageRequest.getNewHeight(), resizeImageRequest.getNewWidth());
    }
}
