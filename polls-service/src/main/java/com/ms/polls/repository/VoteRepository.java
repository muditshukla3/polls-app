package com.ms.polls.repository;

import com.ms.polls.entity.ChoiceVoteCount;
import com.ms.polls.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("""
            SELECT NEW com.ms.polls.entity.ChoiceVoteCount(v.choice.id, count(v.id))\s
             FROM Vote v WHERE v.poll.id in :pollIds GROUP BY v.choice.id
            """)
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    @Query("""
            SELECT NEW com.ms.polls.entity.ChoiceVoteCount(v.choice.id, count(v.id))\s
             FROM Vote v WHERE v.poll.id = :pollId GROUP BY v.choice.id
            """)
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and v.poll.id in :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and v.poll.id =:pollId")
    List<Vote> findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("SELECT COUNT(v.id) FROM Vote v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.poll.id FROM Vote v WHERE v.user.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);

}
