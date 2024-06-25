package com.avi6.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDestinationEntity is a Querydsl query type for DestinationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDestinationEntity extends EntityPathBase<DestinationEntity> {

    private static final long serialVersionUID = 1738128645L;

    public static final QDestinationEntity destinationEntity = new QDestinationEntity("destinationEntity");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath local = createString("local");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final StringPath origin_lat = createString("origin_lat");

    public final StringPath origin_lng = createString("origin_lng");

    public final StringPath place_id = createString("place_id");

    public final StringPath travelModeEnum = createString("travelModeEnum");

    public QDestinationEntity(String variable) {
        super(DestinationEntity.class, forVariable(variable));
    }

    public QDestinationEntity(Path<? extends DestinationEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDestinationEntity(PathMetadata metadata) {
        super(DestinationEntity.class, metadata);
    }

}

