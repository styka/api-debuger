package pl.aia.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.stereotype.Service;

import pl.aia.api.model.ApiRequestGetParameter;

@Service
public class ApiRequestGetParametersService {

	private final Map<Long, ApiRequestGetParameter> apiRequestGetParameters = new HashMap<>();;
	private Long sequence = 1L;

	public Collection<ApiRequestGetParameter> getApiRequestGetParametersByApiRequestMappingId(
			final Long apiRequestMappingId) {
		final Collection<ApiRequestGetParameter> results = new HashSet<>();
		for (final ApiRequestGetParameter item : apiRequestGetParameters.values()) {
			if (apiRequestMappingId.equals(item.getApiRequestMappingId())) {
				results.add(item);
			}
		}
		return Collections.unmodifiableCollection(results);
	}

	public void create(final ApiRequestGetParameter apiRequestGetParameter) {
		apiRequestGetParameter.setId(sequence++);
		apiRequestGetParameters.put(apiRequestGetParameter.getId(), apiRequestGetParameter);
	}

	public Collection<ApiRequestGetParameter> getAll() {
		return Collections.unmodifiableCollection(apiRequestGetParameters.values());
	}

}
