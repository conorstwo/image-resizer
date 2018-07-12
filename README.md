# Image Resizer service

This Spring Boot microservice allows you to resize an images on the fly by sending a simple REST request to /resizeImage.

The supplied image must be in a byte array format and the request new height and width should be positive integers.

## How to run the application

To run the application first clone this repository then use maven to build the artifact using

`mvn clean install`

Then run the jar found in the target directory using

`java -jar image-resizer-0.0.1-SNAPSHOT.jar`

The application can then be found on localhost at port 8080 (this can be overridden by adding a server.port property in src/main/resources/application.properties)

Alternatively you can use ImageResizerApplicationTests to see an end to end demonstration of the app resizing an image

## Sample valid request:

`{
    "originalImage" : //original image as byte array,
    "newHeight" : 100,
    "newWidth" : 100
}`

## Resources used:

Spring Boot initializr for setting up default spring boot project: https://start.spring.io/

MyKong tutorials on converting images to byte arrays and resizing using Graphics2D: https://www.mkyong.com/java/how-to-convert-bufferedimage-to-byte-in-java/ and https://www.mkyong.com/java/how-to-resize-an-image-in-java/