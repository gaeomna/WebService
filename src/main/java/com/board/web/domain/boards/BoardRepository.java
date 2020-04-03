package com.board.web.domain.boards;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    List<BoardEntity> findByTitleContaining(String keyword);

    @Modifying
    @Query("UPDATE webservice w SET w.viewCount = w.viewCount + 1 WHERE w.id = :id")
    void viewCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE webservice w SET w.recommend = w.recommend + 1 WHERE w.id = :id")
    void recommending(@Param("id") Long id);
}
