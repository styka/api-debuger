package pl.aia.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.stereotype.Service;

import pl.aia.api.model.ApiRequestPostPutParameter;

@Service
public class ApiRequestPostPutParametersService {

	private final Map<Long, ApiRequestPostPutParameter> apiRequestPostPutParameters = new HashMap<>();;
	private Long sequence = 1L;

	public Collection<ApiRequestPostPutParameter> getApiRequestPostPutParametersByApiRequestMappingId(
			final Long apiRequestMappingId) {
		final Collection<ApiRequestPostPutParameter> results = new HashSet<>();
		for (final ApiRequestPostPutParameter item : apiRequestPostPutParameters.values()) {
			if (apiRequestMappingId.equals(item.getApiRequestMappingId())) {
				results.add(item);
			}
		}
		return Collections.unmodifiableCollection(results);
	}

	public void create(final ApiRequestPostPutParameter apiRequestPostPutParameter) {
		apiRequestPostPutParameter.setId(sequence++);
		apiRequestPostPutParameters.put(apiRequestPostPutParameter.getId(), apiRequestPostPutParameter);
	}

	public Collection<ApiRequestPostPutParameter> getAll() {
		return Collections.unmodifiableCollection(apiRequestPostPutParameters.values());
	}

}
