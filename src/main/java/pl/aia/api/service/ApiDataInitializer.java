package pl.aia.api.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pl.aia.api.model.ApiRequestGetParameter;
import pl.aia.api.model.ApiRequestMapping;
import pl.aia.api.model.ApiRequestPostPutParameter;
import pl.aia.api.model.ApiRequestResponseParameter;

@Service
public class ApiDataInitializer {

	private static final String COLLECTION_TYPE = "org.springframework.http.ResponseEntity<java.util.Collection<";
	private static final String SIMPLY_TYPE = "org.springframework.http.ResponseEntity<";

	public static final Pattern PARAMETER_PATTERN = Pattern.compile("\\{.*?\\}");

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ApiRequestMappingsService apiRequestMappingsService;

	@Autowired
	private ApiRequestGetParametersService apiRequestGetParametersService;

	@Autowired
	private ApiRequestPostPutParametersService apiRequestPostPutParametersService;

	@Autowired
	private ApiRequestResponseParametersService apiRequestResponseParametersService;

	@PostConstruct
	private void init() {
		for (final String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
			final Object bean = applicationContext.getBean(beanDefinitionName);
			if (isRestController(bean)) {
				createControllerRequestMappings(bean);
			}
		}
		logApiRegistry();
	}

	private void logApiRegistry() {
		final Gson gson = new Gson();
		System.out.println(gson.toJson(apiRequestMappingsService.getAll()));
		System.out.println(gson.toJson(apiRequestGetParametersService.getAll()));
		System.out.println(gson.toJson(apiRequestPostPutParametersService.getAll()));
		System.out.println(gson.toJson(apiRequestResponseParametersService.getAll()));
	}

	private boolean isRestController(final Object bean) {
		return bean.getClass().getAnnotation(RestController.class) != null;
	}

	private void createControllerRequestMappings(final Object controller) {
		final String baseRequestMappingValue = getRequestMappingValue(controller);
		for (final Method method : controller.getClass().getMethods()) {
			if (hasRequestMappingAnnotation(method)) {
				final ApiRequestMapping apiRequestMapping = createApiRequestMapping(baseRequestMappingValue, method);
				createApiRequestGetParameters(method, apiRequestMapping);
				createApiRequestPostPutParameters(method, apiRequestMapping);
				createApiRequestResponseParameters(method, apiRequestMapping);
			}
		}
	}

	// org.springframework.http.ResponseEntity<java.util.Collection<pl.aia.api.model.ApiController>>
	private void createApiRequestResponseParameters(final Method method, final ApiRequestMapping apiRequestMapping) {
		final String returnType = method.getGenericReturnType().getTypeName();
		String className = "";
		if (returnType.startsWith(COLLECTION_TYPE)) {
			className = returnType.substring(COLLECTION_TYPE.length(), returnType.length() - 2);
		} else if (returnType.startsWith(SIMPLY_TYPE)) {
			className = returnType.substring(SIMPLY_TYPE.length(), returnType.length() - 1);
		}
		try {
			final Class<?> clazz = Class.forName(className);
			for (final Field field : clazz.getDeclaredFields()) {
				final ApiRequestResponseParameter apiRequestResponseParameter = new ApiRequestResponseParameter();
				apiRequestResponseParameter.setName(field.getName());
				apiRequestResponseParameter.setType(field.getType().getName());
				apiRequestResponseParameter.setApiRequestMappingId(apiRequestMapping.getId());
				apiRequestResponseParametersService.create(apiRequestResponseParameter);
			}
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void createApiRequestPostPutParameters(final Method method, final ApiRequestMapping apiRequestMapping) {
		for (final Parameter parameter : method.getParameters()) {
			final RequestBody declaredAnnotationRequestBody = parameter.getDeclaredAnnotation(RequestBody.class);
			if (declaredAnnotationRequestBody != null) {
				final Class<?> type = parameter.getType();
				for (final Field field : type.getDeclaredFields()) {
					final ApiRequestPostPutParameter apiRequestPostPutParameter = new ApiRequestPostPutParameter();
					apiRequestPostPutParameter.setName(field.getName());
					apiRequestPostPutParameter.setType(field.getType().getName());
					apiRequestPostPutParameter.setApiRequestMappingId(apiRequestMapping.getId());
					apiRequestPostPutParametersService.create(apiRequestPostPutParameter);
				}
			}
		}
	}

	private boolean hasRequestMappingAnnotation(final Method method) {
		return method.getAnnotation(RequestMapping.class) != null;
	}

	private String getRequestMappingValue(final Object controller) {
		if (hasRequestMappingAnnotation(controller)) {
			return controller.getClass().getAnnotation(RequestMapping.class).value()[0];
		} else {
			return "";
		}
	}

	private boolean hasRequestMappingAnnotation(final Object controller) {
		return controller.getClass().getAnnotation(RequestMapping.class) != null;
	}

	private ApiRequestMapping createApiRequestMapping(final String baseRequestMappingValue, final Method method) {
		final RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
		final ApiRequestMapping apiRequestMapping = new ApiRequestMapping();
		apiRequestMapping.setSource(method.toString());
		apiRequestMapping.setMethod(methodRequestMapping.method()[0].toString());
		apiRequestMapping.setValue(getRequestMappingValue(baseRequestMappingValue, methodRequestMapping));
		return apiRequestMappingsService.create(apiRequestMapping);
	}

	private String getRequestMappingValue(final String baseRequestMappingValue,
			final RequestMapping methodRequestMapping) {
		final String methodRequestMappingValue = methodRequestMapping.value().length > 0
				? methodRequestMapping.value()[0] : "";
		return baseRequestMappingValue + methodRequestMappingValue;
	}

	private void createApiRequestGetParameters(final Method method, final ApiRequestMapping apiRequestMapping) {
		final Matcher parameterMatcher = PARAMETER_PATTERN.matcher(apiRequestMapping.getValue());
		while (parameterMatcher.find()) {
			final String parameterNameWithBrackets = parameterMatcher.group(0);
			final String parameterName = parameterNameWithBrackets.substring(1, parameterNameWithBrackets.length() - 1);
			final ApiRequestGetParameter apiRequestGetParameter = new ApiRequestGetParameter();
			apiRequestGetParameter.setName(parameterName);
			apiRequestGetParameter.setType(getParameterType(parameterName, method));
			apiRequestGetParameter.setApiRequestMappingId(apiRequestMapping.getId());
			apiRequestGetParametersService.create(apiRequestGetParameter);
		}
	}

	private String getParameterType(final String parameterName, final Method method) {
		for (final Parameter item : method.getParameters()) {
			final PathVariable pathVariable = item.getAnnotation(PathVariable.class);
			if (pathVariable == null) {
				continue;
			}
			if (parameterName.equals(pathVariable.value())) {
				return item.getType().getCanonicalName();
			}
		}
		throw new RuntimeException("PathVariable Annotation for parameter '" + parameterName + "' not found in method: "
				+ method.getName());
	}

}
