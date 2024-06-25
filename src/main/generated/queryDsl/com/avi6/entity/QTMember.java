package com.avi6.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTMember is a Querydsl query type for TMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTMember extends EntityPathBase<TMember> {

    private static final long serialVersionUID = -705905502L;

    public static final QTMember tMember = new QTMember("tMember");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath email = createString("email");

    public final NumberPath<Long> memId = createNumber("memId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath wishList = createString("wishList");

    public QTMember(String variable) {
        super(TMember.class, forVariable(variable));
    }

    public QTMember(Path<? extends TMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTMember(PathMetadata metadata) {
        super(TMember.class, metadata);
    }

}

