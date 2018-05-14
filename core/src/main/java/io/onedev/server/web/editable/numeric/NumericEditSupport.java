package io.onedev.server.web.editable.numeric;

import java.lang.reflect.Method;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import io.onedev.server.util.editable.annotation.IssueChoice;
import io.onedev.server.web.editable.EditSupport;
import io.onedev.server.web.editable.EmptyValueLabel;
import io.onedev.server.web.editable.PropertyContext;
import io.onedev.server.web.editable.PropertyDescriptor;
import io.onedev.server.web.editable.PropertyEditor;
import io.onedev.server.web.editable.PropertyViewer;

@SuppressWarnings("serial")
public class NumericEditSupport implements EditSupport {

	@Override
	public PropertyContext<?> getEditContext(Class<?> beanClass, String propertyName) {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(beanClass, propertyName);

		Method propertyGetter = propertyDescriptor.getPropertyGetter();
		Class<?> propertyClass = propertyGetter.getReturnType();
		if ((propertyClass == int.class || propertyClass == long.class 
				|| propertyClass == Integer.class || propertyClass == Long.class) 
				&& propertyGetter.getAnnotation(IssueChoice.class) == null) {
			return new PropertyContext<Number>(propertyDescriptor) {

				@Override
				public PropertyViewer renderForView(String componentId, final IModel<Number> model) {
					return new PropertyViewer(componentId, this) {

						@Override
						protected Component newContent(String id, PropertyDescriptor propertyDescriptor) {
							if (model.getObject() != null) {
								return new Label(id, model.getObject().toString());
							} else {
								return new EmptyValueLabel(id, propertyDescriptor.getPropertyGetter());
							}
						}
						
					};
				}

				@Override
				public PropertyEditor<Number> renderForEdit(String componentId, IModel<Number> model) {
					return new NumericPropertyEditor(componentId, this, model);
				}
				
			};
		} else {
			return null;
		}
		
	}

}