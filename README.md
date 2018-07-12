Image Resizer service
This Spring Boot microservice allows you to resize an images on the fly by sending a simple REST request to /resizeImage.
The supplied image must be in a byte array format and the request new height and width should be positive integers.
Sample valid request:
`{
    "originalImage" : //original image as byte array,
    "newHeight" : 100,
    "newWidth" : 100
}`

Resources used:
Spring Boot initializr for setting up default spring boot project: https://start.spring.io/
MyKong tutorials on converting images to byte arrays and resizing using Graphics2D: https://www.mkyong.com/java/how-to-convert-bufferedimage-to-byte-in-java/ and https://www.mkyong.com/java/how-to-resize-an-image-in-java/

Future improvements:
Write end to end integration tests