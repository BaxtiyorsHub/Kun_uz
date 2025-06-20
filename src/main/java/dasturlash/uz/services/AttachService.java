package dasturlash.uz.services;

import dasturlash.uz.entities.AttachEntity;
import dasturlash.uz.exp.AppBadExp;
import dasturlash.uz.repository.AttachRepository;
import dasturlash.uz.responseDto.AttachResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@Service
public class AttachService {

    @Value("${attachment.url1}")
    private String url1;

    private final AttachRepository attachRepository;

    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    public String saveToSystem(MultipartFile file) {
        try {
            File folder = new File("attaches");
            if (!folder.exists()) folder.mkdir();

            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<Resource> open(String fileName) {
        Path filePath = Paths.get("attaches/" + fileName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) throw new RuntimeException("File not found: " + fileName);
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public AttachResponseDTO upload(MultipartFile file) {
        if (file.isEmpty()) throw new AppBadExp("File not found");

        try {
            String pathFolder = getYmDString(); // 2025/06/09
            String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); // .jpg, .png, .mp4

            // create folder if not exists
            File folder = new File(url1 + "/" + pathFolder);
            if (!folder.exists()) folder.mkdirs();

            // save to
            byte[] bytes = file.getBytes();
            Path path = Paths.get(url1 + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/ 2025/06/09/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);

            // save to db
            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<Resource> open2(String id) { // d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        AttachEntity entity = getEntity(id);
        if (entity.getVisible().equals(false)) throw new AppBadExp("File not found");

        Path filePath = Paths.get(url1 + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        // attaches/2025/06/09/d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) throw new RuntimeException("File not found: " + filePath);

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) contentType = "application/octet-stream"; // Fallback content typ

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public Boolean deleteFile(String fileId) {
        if (fileId == null || fileId.isEmpty()) throw new AppBadExp("File not found (null)");

        Optional<AttachEntity> optionalAttach = attachRepository.findById(fileId);
        if (optionalAttach.isEmpty() || optionalAttach.get().getVisible().equals(false)) throw new AppBadExp("File not found");

        AttachEntity attach = optionalAttach.get();
        String filePath = attach.getPath();
        String fileName = attach.getId() + "." + attach.getExtension();

        Path fullPath = Paths.get(url1 + "/" + filePath + "/" + fileName).normalize();

        try {
            Files.delete(fullPath);
        } catch (IOException e) {
            throw new RuntimeException("Faylni o‘chirishda xatolik: " + e.getMessage());
        }

        attach.setVisible(false);
        attachRepository.save(attach);

        return true;
    }

    public Resource downloadFile(String id) {
        AttachEntity file = attachRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("File not found"));
        if (file.getVisible().equals(Boolean.FALSE)) throw new AppBadExp("File not found");

        // Faylning to‘liq yo‘lini yasaymiz: attaches/2025/06/15/UUID.jpg
        String fullPath = url1 + "/" + file.getPath() + "/" + file.getId();
        //             attaches   /      2025/6/15      /    askandklandklan

        Path path = Paths.get(fullPath).normalize();
        //             fullpath + jpg , ...
        try {
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists() || !resource.isReadable()) throw new RuntimeException("File not found or not readable: " + fullPath);

            return resource;
        } catch (Exception e) {
            throw new RuntimeException("File yuklab olishda xatolik: " + e.getMessage());
        }
    }

    private AttachEntity getEntity(String id) {
        return attachRepository.findById(id)
                .orElseThrow(() -> new AppBadExp("File not found"));
    }

    /*public String openURL(String fileName) {
        return url1 + "/open/" + fileName;
    }*/

    /*public String openURL(String fileName) {
        return url2 + "/attach/open/" + fileName;
    }*/

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "-" + month + "-" + day;
    }

    private String getExtension(String fileName) { // dasd.asdasd.zari.jpg
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    private AttachResponseDTO toDTO(AttachEntity entity) {
        AttachResponseDTO attachDTO = new AttachResponseDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        attachDTO.setUrl(openURL(entity.getId()));
        return attachDTO;
    }

    public String openURL(String fileName) {
        return url1 + "/open/" + fileName;
    }

    public PageImpl<AttachResponseDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<AttachEntity> entitiesFromDb = attachRepository.findAll(pageRequest);

        long totalElements = entitiesFromDb.getTotalElements();
        List<AttachEntity> results = entitiesFromDb.getContent();

        List<AttachResponseDTO> responseList = new LinkedList<>();
        for (AttachEntity entity : results) {
            responseList.add(toDTO(entity));
        }

        return new PageImpl<>(responseList,pageRequest,totalElements);
    }
}

