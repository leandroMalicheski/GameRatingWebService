package br.com.gamerating.dao;

import br.com.gamerating.bean.Comment;

public interface CommentHistoryDAO {

	void addEditInfo(Comment newCommentInfo, Comment oldCommentInfo);

}
