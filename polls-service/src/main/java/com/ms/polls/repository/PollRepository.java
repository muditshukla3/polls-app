package com.ms.polls.repository;

import com.ms.polls.entity.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {

    Page<Poll> findByCreatedBy(Long userid, Pageable pageable);

    long countByCreatedBy(Long userId);
    List<Poll> findByIdIn(List<Long> pollIds);

    List<Poll> findByIdIn(List<Long> pollsIds, Sort sort);
}
