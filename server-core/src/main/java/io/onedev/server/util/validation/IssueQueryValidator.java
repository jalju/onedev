package io.onedev.server.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.onedev.commons.utils.StringUtils;
import io.onedev.server.model.Project;
import io.onedev.server.web.editable.annotation.IssueQuery;

public class IssueQueryValidator implements ConstraintValidator<IssueQuery, String> {

	private String message;
	
	@Override
	public void initialize(IssueQuery constaintAnnotation) {
		message = constaintAnnotation.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
		if (value == null) {
			return true;
		} else {
			Project project = Project.get();
			try {
				io.onedev.server.search.entity.issue.IssueQuery.parse(project, value, true);
				return true;
			} catch (Exception e) {
				constraintContext.disableDefaultConstraintViolation();
				String message = this.message;
				if (message.length() == 0) {
					if (StringUtils.isNotBlank(e.getMessage()))
						message = e.getMessage();
					else
						message = "Malformed issue query";
				}
				
				constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
				return false;
			}
		}
	}
	
}