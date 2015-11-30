package pl.aia.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import pl.aia.api.model.ApiRequestMapping;

@Service
public class ApiRequestMappingsService {

	private final Map<Long, ApiRequestMapping> apiRequestMappings = new HashMap<>();;
	private Long sequence = 1L;

	public Collection<ApiRequestMapping> getAll() {
		return Collections.unmodifiableCollection(apiRequestMappings.values());
	}

	public ApiRequestMapping create(final ApiRequestMapping apiRequestMapping) {
		apiRequestMapping.setId(sequence++);
		apiRequestMappings.put(apiRequestMapping.getId(), apiRequestMapping);
		final ApiRequestMapping result = new ApiRequestMapping();
		BeanUtils.copyProperties(apiRequestMapping, result);
		return result;
	}

}
