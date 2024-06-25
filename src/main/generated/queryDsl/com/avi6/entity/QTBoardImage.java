package com.avi6.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTBoardImage is a Querydsl query type for TBoardImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTBoardImage extends EntityPathBase<TBoardImage> {

    private static final long serialVersionUID = 430970909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTBoardImage tBoardImage = new QTBoardImage("tBoardImage");

    public final StringPath imgName = createString("imgName");

    public final NumberPath<Long> inum = createNumber("inum", Long.class);

    public final StringPath path = createString("path");

    public final QTBoard tBoard;

    public final StringPath uuid = createString("uuid");

    public QTBoardImage(String variable) {
        this(TBoardImage.class, forVariable(variable), INITS);
    }

    public QTBoardImage(Path<? extends TBoardImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTBoardImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTBoardImage(PathMetadata metadata, PathInits inits) {
        this(TBoardImage.class, metadata, inits);
    }

    public QTBoardImage(Class<? extends TBoardImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tBoard = inits.isInitialized("tBoard") ? new QTBoard(forProperty("tBoard"), inits.get("tBoard")) : null;
    }

}

