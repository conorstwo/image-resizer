package com.conorstwo;

import com.conorstwo.rest.model.ResizeImageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Holds end to end integration test of image resizing functionality
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImageResizerApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void fullIntegrationTest() throws Exception {
		//Get test image from class loader
		final BufferedImage testImage = getTestImage();
		//Check the original height and width of the image is correct
		assertEquals(testImage.getHeight(), 131);
		assertEquals(testImage.getWidth(), 385);
		//Convert test image to byte array
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(testImage, "jpg", baos);
		final byte[] originalImage = baos.toByteArray();
		final int newHeight = 100;
		final int newWidth = 100;

		final ResizeImageRequest resizeImageRequest = new ResizeImageRequest(originalImage, newHeight, newWidth);
		final ObjectMapper objectMapper = new ObjectMapper();
		final String requestBody = objectMapper.writeValueAsString(resizeImageRequest);

		MvcResult result = this.mockMvc.perform(post("/resizeImage").content(requestBody).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andReturn();

		final byte[] resizedImage = result.getResponse().getContentAsByteArray();
		final InputStream inputStream = new ByteArrayInputStream(resizedImage);
		final BufferedImage bufferedResizedImage = ImageIO.read(inputStream);
		assertEquals(bufferedResizedImage.getHeight(), newHeight);
		assertEquals(bufferedResizedImage.getWidth(), newWidth);
	}

	private BufferedImage getTestImage() throws IOException {
		final URL url = Thread.currentThread().getContextClassLoader().getResource("logo.jpg");
		final File file = new File(url.getPath());

		return ImageIO.read(file);
	}

}
