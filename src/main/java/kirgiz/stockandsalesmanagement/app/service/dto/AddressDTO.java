package kirgiz.stockandsalesmanagement.app.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String description;

    @NotNull
    @Size(max = 80)
    private String line1;

    @Size(max = 80)
    private String line2;

    @Size(max = 80)
    private String line3;

    @Size(max = 80)
    private String line4;

    @Size(max = 10)
    private String zipCode;

    @Size(max = 80)
    private String state;

    private LocalDate validFrom;

    private LocalDate validTo;

    @Size(max = 500)
    private String comments;

    private Long addressClassifId;

    private String addressClassifName;

    private Long countryAddressId;

    private String countryAddressName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getAddressClassifId() {
        return addressClassifId;
    }

    public void setAddressClassifId(Long addressclassificationId) {
        this.addressClassifId = addressclassificationId;
    }

    public String getAddressClassifName() {
        return addressClassifName;
    }

    public void setAddressClassifName(String addressclassificationName) {
        this.addressClassifName = addressclassificationName;
    }

    public Long getCountryAddressId() {
        return countryAddressId;
    }

    public void setCountryAddressId(Long countryId) {
        this.countryAddressId = countryId;
    }

    public String getCountryAddressName() {
        return countryAddressName;
    }

    public void setCountryAddressName(String countryName) {
        this.countryAddressName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if(addressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", line3='" + getLine3() + "'" +
            ", line4='" + getLine4() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", state='" + getState() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
