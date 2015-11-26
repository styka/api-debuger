package pl.aia.api.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.stereotype.Service;

import pl.aia.api.model.ApiRequestResponseParameter;

@Service
public class ApiRequestResponseParametersService {

	private final Map<Long, ApiRequestResponseParameter> apiRequestResponseParameters = new HashMap<>();;
	private Long sequence = 1L;

	public Collection<ApiRequestResponseParameter> getApiRequestResponseParametersByApiRequestMappingId(
			final Long apiRequestMappingId) {
		final Collection<ApiRequestResponseParameter> results = new HashSet<>();
		for (final ApiRequestResponseParameter item : apiRequestResponseParameters.values()) {
			if (apiRequestMappingId.equals(item.getApiRequestMappingId())) {
				results.add(item);
			}
		}
		return Collections.unmodifiableCollection(results);
	}

	public void create(final ApiRequestResponseParameter apiRequestResponseParameter) {
		apiRequestResponseParameter.setId(sequence++);
		apiRequestResponseParameters.put(apiRequestResponseParameter.getId(), apiRequestResponseParameter);
	}

	public Collection<ApiRequestResponseParameter> getAll() {
		return Collections.unmodifiableCollection(apiRequestResponseParameters.values());
	}

}
