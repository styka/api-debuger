package pl.aia.api.model;

public class ApiRequestMapping {

	private Long id;
	private String value;
	private String method;
	private Long apiControllerId;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApiControllerId() {
		return apiControllerId;
	}

	public void setApiControllerId(Long apiControllerId) {
		this.apiControllerId = apiControllerId;
	}

}
