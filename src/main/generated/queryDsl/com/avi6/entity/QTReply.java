package com.avi6.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTReply is a Querydsl query type for TReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTReply extends EntityPathBase<TReply> {

    private static final long serialVersionUID = 951680994L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTReply tReply = new QTReply("tReply");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QTBoard board;

    public final QTMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> reviewNum = createNumber("reviewNum", Long.class);

    public final StringPath text = createString("text");

    public QTReply(String variable) {
        this(TReply.class, forVariable(variable), INITS);
    }

    public QTReply(Path<? extends TReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTReply(PathMetadata metadata, PathInits inits) {
        this(TReply.class, metadata, inits);
    }

    public QTReply(Class<? extends TReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QTBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QTMember(forProperty("member")) : null;
    }

}

