package pl.aia.api.restcontroller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aia.api.model.ApiRequestGetParameter;
import pl.aia.api.service.ApiRequestGetParametersService;

@RestController
@RequestMapping(value = "/api/apiRequestGetParameter")
public class ApiRequestGetParameterController {

	@Autowired
	private ApiRequestGetParametersService apiRequestParametersService;

	@RequestMapping(value = "/search/{apiRequestMappingId}", method = RequestMethod.GET)
	public ResponseEntity<Collection<ApiRequestGetParameter>> getApiRequestGetParametersByApiRequestMappingId(
			@PathVariable("apiRequestMappingId") final Long apiRequestMappingId) {
		final Collection<ApiRequestGetParameter> apiParameters = apiRequestParametersService
				.getApiRequestGetParametersByApiRequestMappingId(apiRequestMappingId);
		return new ResponseEntity<Collection<ApiRequestGetParameter>>(apiParameters, HttpStatus.OK);
	}

}
