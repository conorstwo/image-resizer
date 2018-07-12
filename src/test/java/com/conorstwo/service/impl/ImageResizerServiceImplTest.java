package com.conorstwo.service.impl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ResizeImageServiceImpl}
 */
public class ImageResizerServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ResizeImageServiceImpl resizeImageService;

    @Before
    public void setUp() {
        resizeImageService = new ResizeImageServiceImpl();
    }

    @Test
    public void testResizeImage() throws IOException {
        //Get test image from class loader
        final BufferedImage testImage = getTestImage();
        //Check the original height and width of the image is correct
        assertEquals(testImage.getHeight(), 131);
        assertEquals(testImage.getWidth(), 385);
        //Convert test image to byte array
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(testImage, "jpg", baos);
        final byte[] originalImage = baos.toByteArray();

        //Resize image
        final byte[] resizedImage = resizeImageService.resizeImage(originalImage, 100, 100);
        //Convert back to buffered image
        final InputStream inputStream = new ByteArrayInputStream(resizedImage);
        final BufferedImage bufferedResizedImage = ImageIO.read(inputStream);
        assertEquals(bufferedResizedImage.getHeight(), 100);
        assertEquals(bufferedResizedImage.getWidth(), 100);
    }

    @Test
    public void testResizeImageInvalidImage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Image supplied to resizeImage was null");

        resizeImageService.resizeImage(null, 100, 100);
    }

    @Test
    public void testResizeImageNewHeightNegative() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("New height supplied to resizeImage was negative");

        resizeImageService.resizeImage(new byte[20], -15, 100);
    }

    @Test
    public void testResizeImageNewWidthtNegative() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("New width supplied to resizeImage was negative");

        resizeImageService.resizeImage(new byte[20], 100, -15);
    }

    /**
     * Retrieve the test image from src/test/resources
     *
     * @return the test image logo.jpg as {@link BufferedImage}
     * @throws IOException - if there is a problem reading the image
     */
    private BufferedImage getTestImage() throws IOException {
        final URL url = Thread.currentThread().getContextClassLoader().getResource("logo.jpg");
        final File file = new File(url.getPath());

        return ImageIO.read(file);
    }
}
