package com.gitplex.server.web.component.diff.revision;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Nullable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.gitplex.server.model.CodeComment;
import com.gitplex.server.model.support.CommentPos;

public interface BlobCommentSupport extends Serializable {
	
	Collection<CodeComment> getComments();
	
	@Nullable CommentPos getMark();
	
	String getMarkUrl(CommentPos mark);
	
	@Nullable CodeComment getOpenComment();

	void onOpenComment(AjaxRequestTarget target, CodeComment comment);
	
	void onAddComment(AjaxRequestTarget target, CommentPos commentPos);
	
	Component getDirtyContainer();
	
}
