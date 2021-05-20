package views.Windows;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;
import org.uqbar.arena.bindings.ValueTransformer;
import org.uqbar.commons.model.UserException;

public class LocalDateTransformer implements ValueTransformer<LocalDate, String> {
	public String pattern = "dd/MM/yyyy";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

	@Override
	public LocalDate viewToModel(String valueFromView) {
		try {
			return StringUtils.isBlank(valueFromView) ? null : LocalDate.parse(valueFromView,formatter);
		} catch (UnsupportedOperationException e) {
			throw new UserException("Debe INGRESAR una fecha en formato: " + this.pattern);
		}
	}

	@Override
	public String modelToView(LocalDate valueFromModel) {
		if (valueFromModel == null) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(valueFromModel);
	}

	@Override
	public Class<LocalDate> getModelType() {
		return LocalDate.class;
	}

	@Override
	public Class<String> getViewType() {
		return String.class;
	}

}
