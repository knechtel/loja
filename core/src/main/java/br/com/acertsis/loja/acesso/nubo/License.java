package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

public class License {
    @SerializedName("id")
    private Long id;
    @SerializedName("license_package")
    private LicensePackage licensePackage;
    @SerializedName("license_service_id")
    private Integer licenseServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LicensePackage getLicensePackage() {
        return licensePackage;
    }

    public void setLicensePackage(LicensePackage licensePackage) {
        this.licensePackage = licensePackage;
    }

    public Integer getLicenseServiceId() {
        return licenseServiceId;
    }

    public void setLicenseServiceId(Integer licenseServiceId) {
        this.licenseServiceId = licenseServiceId;
    }
}