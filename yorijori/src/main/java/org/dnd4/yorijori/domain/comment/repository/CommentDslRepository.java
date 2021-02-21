package org.dnd4.yorijori.domain.comment.repository;

import static org.dnd4.yorijori.domain.comment.entity.QComment.comment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.dnd4.yorijori.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDslRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public CommentDslRepository(JPAQueryFactory queryFactory){
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    public List<Comment> findByParentId(Long pid, int offset, int limit){
        return queryFactory
                .selectFrom(comment)
                .where(
                        eqParentId(pid)
                ).offset(offset).limit(limit)
                .fetch();
    }

    private BooleanExpression eqParentId(Long pid) {
        if (pid == null) {
            return null;
        }

        return comment.parent.id.eq(pid);
    }
}
