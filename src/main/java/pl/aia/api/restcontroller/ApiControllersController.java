package pl.aia.api.restcontroller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aia.api.model.ApiController;
import pl.aia.api.service.ApiControllersService;

@RestController
@RequestMapping(value = "/api/apiControllers")
public class ApiControllersController {

	@Autowired
	private ApiControllersService apiControllersService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<ApiController>> getControllers() {
		final Collection<ApiController> apiControllers = apiControllersService.getAll();
		return new ResponseEntity<Collection<ApiController>>(apiControllers, HttpStatus.OK);
	}

}
