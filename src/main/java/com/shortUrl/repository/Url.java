package com.shortUrl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rafael.alves on 20/01/2019.
 */
public interface Url extends JpaRepository<com.shortUrl.model.Url, Long> {
}
