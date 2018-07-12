package com.conorstwo.rest.controller;

import com.conorstwo.rest.model.ResizeImageRequest;
import com.conorstwo.service.ResizeImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link ImageResizerController}
 * Only tests validation of input fields as testing of image resizing is handled by {@link com.conorstwo.service.impl.ImageResizerServiceImplTest}
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ImageResizerController.class)
public class ImageResizerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResizeImageService mockResizeImageService;

    @Test
    public void testResizeImage() throws Exception {
        final byte[] originalImage = new byte[20];
        final int newHeight = 100;
        final int newWidth = 100;

        final ResizeImageRequest resizeImageRequest = new ResizeImageRequest(originalImage, newHeight, newWidth);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestBody = objectMapper.writeValueAsString(resizeImageRequest);

        Mockito.when(mockResizeImageService.resizeImage(originalImage, newHeight, newWidth)).thenReturn(new byte[10]);

        this.mockMvc.perform(post("/resizeImage").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(mockResizeImageService, times(1)).resizeImage(originalImage, newHeight, newWidth);
    }

    @Test
    public void testResizeImageInvalidImage() throws Exception {
        final byte[] originalImage = null;
        final int newHeight = 100;
        final int newWidth = 100;

        final ResizeImageRequest resizeImageRequest = new ResizeImageRequest(originalImage, newHeight, newWidth);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestBody = objectMapper.writeValueAsString(resizeImageRequest);

        Mockito.when(mockResizeImageService.resizeImage(originalImage, newHeight, newWidth)).thenReturn(new byte[10]);

        this.mockMvc.perform(post("/resizeImage").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        Mockito.verify(mockResizeImageService, times(0)).resizeImage(originalImage, newHeight, newWidth);
    }

    @Test
    public void testResizeImageInvalidNewHeight() throws Exception {
        final byte[] originalImage = new byte[20];
        final int newHeight = -100;
        final int newWidth = 100;

        final ResizeImageRequest resizeImageRequest = new ResizeImageRequest(originalImage, newHeight, newWidth);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestBody = objectMapper.writeValueAsString(resizeImageRequest);

        Mockito.when(mockResizeImageService.resizeImage(originalImage, newHeight, newWidth)).thenReturn(new byte[10]);

        this.mockMvc.perform(post("/resizeImage").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        Mockito.verify(mockResizeImageService, times(0)).resizeImage(originalImage, newHeight, newWidth);
    }

    @Test
    public void testResizeImageInvalidNewWidth() throws Exception {
        final byte[] originalImage = new byte[20];
        final int newHeight = 100;
        final int newWidth = -100;

        final ResizeImageRequest resizeImageRequest = new ResizeImageRequest(originalImage, newHeight, newWidth);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestBody = objectMapper.writeValueAsString(resizeImageRequest);

        Mockito.when(mockResizeImageService.resizeImage(originalImage, newHeight, newWidth)).thenReturn(new byte[10]);

        this.mockMvc.perform(post("/resizeImage").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        Mockito.verify(mockResizeImageService, times(0)).resizeImage(originalImage, newHeight, newWidth);
    }
}
