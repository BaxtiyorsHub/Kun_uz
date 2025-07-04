package dasturlash.uz.service.article;

import dasturlash.uz.dto.article.TagDTO;
import dasturlash.uz.entity.TagEntity;
import dasturlash.uz.repository.article.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Boolean create(String id, Integer i) {
        return null;
    }

    public Page<TagDTO> getList(String id, Integer i, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TagEntity> all = tagRepository
                .findAll(pageRequest);

        List<TagDTO> dtoList = all.getContent().stream().map(this::toDTO).toList();
        return new PageImpl<>(dtoList, pageRequest, all.getTotalElements());
    }

    private TagDTO toDTO(TagEntity tagEntity) {
        TagDTO dto = new TagDTO();
        dto.setId(tagEntity.getId());
        dto.setName(tagEntity.getName());
        return dto;
    }
}
