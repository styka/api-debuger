package pl.aia.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import pl.aia.api.model.ApiController;

@Service
public class ApiControllersService {

	private final Map<Long, ApiController> apiControllers = new HashMap<>();
	private Long sequence = 1L;

	public Collection<ApiController> getAll() {
		return Collections.unmodifiableCollection(apiControllers.values());
	}

	public ApiController create(final ApiController apiController) {
		apiController.setId(sequence++);
		apiControllers.put(apiController.getId(), apiController);
		final ApiController result = new ApiController();
		BeanUtils.copyProperties(apiController, result);
		return result;
	}

}
