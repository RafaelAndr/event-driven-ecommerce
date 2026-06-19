package rafaelandrade.ipurchases.invoicing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rafaelandrade.ipurchases.invoicing.bucket.BucketFile;
import rafaelandrade.ipurchases.invoicing.bucket.BucketService;

import java.io.InputStream;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buckets")
public class BucketController {

    private final BucketService service;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try ( InputStream is = file.getInputStream() ) {

            MediaType type = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
            var bucketFile = new BucketFile(file.getOriginalFilename(), is, type, file.getSize());
            service.upload(bucketFile);

            return ResponseEntity.ok("File sent successfully!");

        } catch (Exception e){
            return ResponseEntity.status(500).body("Error sending file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getUrl(@RequestParam String filename){
        try {
            String url = service.getUrl(filename);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body(url);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error getting url file: " + e.getMessage());
        }
    }
}
