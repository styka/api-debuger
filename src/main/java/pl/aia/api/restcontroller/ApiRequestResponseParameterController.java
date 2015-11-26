package pl.aia.api.restcontroller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aia.api.model.ApiRequestResponseParameter;
import pl.aia.api.service.ApiRequestResponseParametersService;

@RestController
@RequestMapping(value = "/api/apiRequestResponseParameter")
public class ApiRequestResponseParameterController {

	@Autowired
	private ApiRequestResponseParametersService apiRequestResponseParametersService;

	@RequestMapping(value = "/search/{apiRequestMappingId}", method = RequestMethod.GET)
	public ResponseEntity<Collection<ApiRequestResponseParameter>> getApiRequestResponseParametersByApiRequestMappingId(
			@PathVariable("apiRequestMappingId") final Long apiRequestMappingId) {
		final Collection<ApiRequestResponseParameter> apiParameters = apiRequestResponseParametersService
				.getApiRequestResponseParametersByApiRequestMappingId(apiRequestMappingId);
		return new ResponseEntity<Collection<ApiRequestResponseParameter>>(apiParameters, HttpStatus.OK);
	}

}
