package com.conorstwo.service.impl;

import com.conorstwo.service.ResizeImageService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementation of {@link com.conorstwo.service.ResizeImageService} interface providing resizing functionality using {@link java.awt.Graphics2D}
 */
@Service
public class ResizeImageServiceImpl implements ResizeImageService {

    public byte[] resizeImage(final byte[] originalImage, final int newHeight, final int newWidth) {
        //Check input to method
        Preconditions.checkNotNull(originalImage, "Image supplied to resizeImage was null");
        Preconditions.checkArgument(newHeight > 0, "New height supplied to resizeImage was negative");
        Preconditions.checkArgument(newWidth > 0, "New width supplied to resizeImage was negative");
        try {
            //Convert byte array in to buffered image
            final InputStream inputStream = new ByteArrayInputStream(originalImage);
            final BufferedImage bufferedOriginalImage = ImageIO.read(inputStream);
            final int imageType = getImageType(bufferedOriginalImage);

            //Resize the image
            final BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, imageType);
            final Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(bufferedOriginalImage, 0, 0, newWidth, newHeight, null);
            graphics2D.dispose();

            //Convert resized image back to byte array and return
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException ioe) {
            //In real application would log/alert on error here
            throw new RuntimeException("Error resizing image", ioe);
        }
    }

    /**
     * Get the image type for the supplied buffered image
     * If the buffered image has type 0 then it is defaulted to {@link BufferedImage#TYPE_INT_ARGB}
     *
     * @param bufferedImage - input image
     * @return - type of the input image or {@link BufferedImage#TYPE_INT_ARGB} if type of input is 0
     */
    private int getImageType(final BufferedImage bufferedImage) {
        return bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
    }
}
