package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

public class LicensePackage {

    @SerializedName("id")
    private Long id;
    @SerializedName("license_service_id")
    private Integer licenseServiceId;
    @SerializedName("name")
    private String name;
    @SerializedName("visible")
    private Boolean visible;
    @SerializedName("description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLicenseServiceId() {
        return licenseServiceId;
    }

    public void setLicenseServiceId(Integer licenseServiceId) {
        this.licenseServiceId = licenseServiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
