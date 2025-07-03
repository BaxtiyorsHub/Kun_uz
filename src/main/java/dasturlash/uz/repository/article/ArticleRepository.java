package dasturlash.uz.repository.article;

import dasturlash.uz.entity.article.ArticleEntity;
import dasturlash.uz.mapper.ArticleFullInfo;
import dasturlash.uz.mapper.ArticleShortInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String>, JpaRepository<ArticleEntity, String>, PagingAndSortingRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findByIdAndVisibleIsTrue(String id);

    @Query("""
                select a.id as id,
                       case ?2
                           when 'UZ' then a.titleUz
                           when 'EN' then a.titleEn
                           when 'RU' then a.titleRu
                       end as title,
                       case ?2
                           when 'UZ' then a.descriptionUz
                           when 'EN' then a.descriptionEn
                           when 'RU' then a.descriptionRu
                       end as description,
                       a.imageId as imageId,
                       a.publishedDate as publishedDate
                 from ArticleEntity as a
                 where a.visible = true and a.status = 'PUBLISHED' and a.id <> ?1
                 order by a.viewCount desc
                 limit 4
            """)
    List<ArticleShortInfo> findMostRead4Articles(String articleId, String language);

    @Query("""
                select a.id as id,
                       case ?3
                           when 'UZ' then a.titleUz
                           when 'RU' then a.titleRu
                           when 'EN' then a.titleEn
                       end as title,
                       case ?3
                           when 'UZ' then a.descriptionUz
                           when 'RU' then a.descriptionRu
                           when 'EN' then a.descriptionEn
                       end as description,
                       a.imageId as imageId,
                       a.publishedDate as publishedDate
                from ArticleEntity a
                inner join ArticleSectionEntity ase on ase.articleId = a.id
                where ase.sectionId = ?1 and a.status = ?2 and a.visible = true
                order by a.publishedDate desc limit ?4
            """)
    List<ArticleShortInfo> find_n_ArticlesBySectionId(Integer sectionId, String status, String lang, int limit);

    @Query("""
                select a.id as id,
                       case ?3
                           when 'UZ' then a.titleUz
                           when 'RU' then a.titleRu
                           when 'EN' then a.titleEn
                       end as title,
                       case ?3
                           when 'UZ' then a.descriptionUz
                           when 'RU' then a.descriptionRu
                           when 'EN' then a.descriptionEn
                       end as description,
                       a.imageId as imageId,
                       a.publishedDate as publishedDate
                from ArticleEntity a
                inner join ArticleCategoryEntity ase on ase.articleId = a.id
                where ase.categoryId = ?1 and a.status = ?2 and a.visible = true
                order by a.publishedDate desc limit ?4
            """)
    List<ArticleShortInfo> find_n_ArticlesByCategoryId(Integer categoryId, String status, String lang, int limit);


    /**
     * @param articleId List
     * @return 12 articles with pageable
     */
    @Query("""
                select id as id,
                       case ?2
                           when 'UZ' then titleUz
                           when 'RU' then titleRu
                           when 'EN' then titleEn
                       end as title,
                       case ?2
                           when 'UZ' then descriptionUz
                           when 'RU' then descriptionRu
                           when 'EN' then descriptionEn
                       end as description,
                       imageId as imageId,
                       publishedDate as publishedDate
                from ArticleEntity where id not in ?1 and visible=true and
                status = 'PUBLISHED' order by createdDate desc limit 12
            """)
    List<ArticleShortInfo> find12Articles(List<String> articleId, String lang);

    @Query(value = """
            SELECT
                a.id as id,
                CASE ?2
                    WHEN 'UZ' THEN a.title_uz
                    WHEN 'EN' THEN a.title_en
                    WHEN 'RU' THEN a.title_ru
                END as title,
                CASE ?2
                    WHEN 'UZ' THEN a.description_uz
                    WHEN 'EN' THEN a.description_en
                    WHEN 'RU' THEN a.description_ru
                END as description,
                CASE ?2
                    WHEN 'UZ' THEN a.content_uz
                    WHEN 'EN' THEN a.content_en
                    WHEN 'RU' THEN a.content_ru
                END as content,
                a.image_id as imageId,
                a.shared_count as sharedCount,
                a.likes_count as likeCount,
                a.view_count as viewCount,
                a.published_date as publishedDate,
                a.region_id as regionId,
            
                -- categoryList: id + name by lang
                (
                    SELECT json_agg(json_build_object(
                        'id', c.id,
                        'name',
                            CASE ?2
                                WHEN 'UZ' THEN c.name_uz
                                WHEN 'EN' THEN c.name_en
                                WHEN 'RU' THEN c.name_ru
                            END
                    ))
                    FROM category c
                    JOIN article_category ac ON ac.category_id = c.id
                    WHERE ac.article_id = a.id
                ) as categoryList,
            
                -- sectionList: id + name by lang
                (
                    SELECT json_agg(json_build_object(
                        'id', s.id,
                        'name',
                            CASE ?2
                                WHEN 'UZ' THEN s.name_uz
                                WHEN 'EN' THEN s.name_en
                                WHEN 'RU' THEN s.name_ru
                            END
                    ))
                    FROM section s
                    JOIN article_section ase ON ase.section_id = s.id
                    WHERE ase.article_id = a.id
                ) as sectionList
            
            FROM article a
            WHERE a.region_id = ?1 AND a.status = 'PUBLISHED' AND a.visible = true
            """, nativeQuery = true)
    List<ArticleFullInfo> findByRegionId(Integer regionId, String language);

}
