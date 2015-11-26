package pl.aia.api.model;

public class ApiRequestResponseParameter {

	private Long id;
	private String name;
	private String type;
	private Long apiRequestMappingId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApiRequestMappingId() {
		return apiRequestMappingId;
	}
	public void setApiRequestMappingId(Long apiRequestMappingId) {
		this.apiRequestMappingId = apiRequestMappingId;
	}
	
}
