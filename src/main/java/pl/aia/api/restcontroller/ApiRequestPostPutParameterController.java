package pl.aia.api.restcontroller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aia.api.model.ApiRequestPostPutParameter;
import pl.aia.api.service.ApiRequestPostPutParametersService;

@RestController
@RequestMapping(value = "/api/apiRequestPostPutParameter")
public class ApiRequestPostPutParameterController {

	@Autowired
	private ApiRequestPostPutParametersService apiRequestPostPutParametersService;

	@RequestMapping(value = "/search/{apiRequestMappingId}", method = RequestMethod.GET)
	public ResponseEntity<Collection<ApiRequestPostPutParameter>> getApiRequestPostPutParametersByApiRequestMappingId(
			@PathVariable("apiRequestMappingId") final Long apiRequestMappingId) {
		final Collection<ApiRequestPostPutParameter> apiParameters = apiRequestPostPutParametersService
				.getApiRequestPostPutParametersByApiRequestMappingId(apiRequestMappingId);
		return new ResponseEntity<Collection<ApiRequestPostPutParameter>>(apiParameters, HttpStatus.OK);
	}

}
