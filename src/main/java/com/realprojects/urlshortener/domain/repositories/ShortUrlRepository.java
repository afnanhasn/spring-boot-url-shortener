package com.realprojects.urlshortener.domain.repositories;

import com.realprojects.urlshortener.domain.entities.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {//handling lazyloading
    @Query("select su from ShortUrl su left join fetch su.createdBy where su.isPrivate = false")
    Page<ShortUrl> findPublicShortUrls(Pageable pageable);

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shortKey);//searches record using shortkey
//Some basic crud type of operation using spring japa and not using any query
    Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

    @Modifying
    void deleteByIdInAndCreatedById(List<Long> ids, Long userId);

    @Query("select u from ShortUrl u left join fetch u.createdBy")
    Page<ShortUrl> findAllShortUrls(Pageable pageable);
}
