package dasturlash.uz.controllers;

import dasturlash.uz.responseDto.AttachResponseDTO;
import dasturlash.uz.services.AttachService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {

    private final AttachService attachService;

    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }

   /* @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return attachService.saveToSystem(file);
    }*/

    @GetMapping("/open/{fileId}")
    public ResponseEntity<Resource> open(@PathVariable String fileId) {
        return attachService.open2(fileId);
    }

    @PostMapping("/upload")
    public ResponseEntity<AttachResponseDTO> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.upload(file));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) {
        Resource file = attachService.downloadFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Boolean> delete(@PathVariable String fileId) {
        return ResponseEntity.ok(attachService.deleteFile(fileId));
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> pagination(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(attachService.pagination(page-1, size));
    }
}
