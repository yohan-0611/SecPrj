package com.avi6.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTBoard is a Querydsl query type for TBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTBoard extends EntityPathBase<TBoard> {

    private static final long serialVersionUID = 937188318L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTBoard tBoard = new QTBoard("tBoard");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> bno = createNumber("bno", Long.class);

    public final StringPath content = createString("content");

    public final ListPath<TBoardImage, QTBoardImage> imageList = this.<TBoardImage, QTBoardImage>createList("imageList", TBoardImage.class, QTBoardImage.class, PathInits.DIRECT2);

    public final QTMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath title = createString("title");

    public final NumberPath<Integer> visitCount = createNumber("visitCount", Integer.class);

    public QTBoard(String variable) {
        this(TBoard.class, forVariable(variable), INITS);
    }

    public QTBoard(Path<? extends TBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTBoard(PathMetadata metadata, PathInits inits) {
        this(TBoard.class, metadata, inits);
    }

    public QTBoard(Class<? extends TBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QTMember(forProperty("member")) : null;
    }

}

