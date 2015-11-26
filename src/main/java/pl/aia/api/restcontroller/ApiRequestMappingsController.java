package pl.aia.api.restcontroller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aia.api.model.ApiRequestMapping;
import pl.aia.api.service.ApiRequestMappingsService;

@RestController
@RequestMapping(value = "/api/apiRequestMappings")
public class ApiRequestMappingsController {

	@Autowired
	private ApiRequestMappingsService apiRequestMappingsService;

	@RequestMapping(value = "/search/{apiControllerId}", method = RequestMethod.GET)
	public ResponseEntity<Collection<ApiRequestMapping>> getRequestMappingsByApiControllerId(
			@PathVariable("apiControllerId") final Long apiControllerId) {
		final Collection<ApiRequestMapping> apiRequestMappings = apiRequestMappingsService
				.getApiRequestMappingsByApiControllerId(apiControllerId);
		return new ResponseEntity<Collection<ApiRequestMapping>>(apiRequestMappings, HttpStatus.OK);
	}

}
