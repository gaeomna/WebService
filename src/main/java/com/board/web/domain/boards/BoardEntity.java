package com.board.web.domain.boards;


import com.board.web.domain.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")

public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @ColumnDefault("0")
    private Long viewCount;

    @ColumnDefault("0")
    private Long recommend;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public BoardEntity(Long id, Long viewCount, Long recommend, String title, String content, String writer) {
        this.id = id;
        this.viewCount = viewCount;
        this.recommend = recommend;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
