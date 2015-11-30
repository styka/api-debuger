package pl.aia.api.model;

public class ApiRequestMapping {

	private Long id;
	private String value;
	private String method;
	private String source;

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

}
