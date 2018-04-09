package kirgiz.stockandsalesmanagement.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Third entity.
 */
public class ThirdDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3)
    private String code;

    @NotNull
    @Size(max = 60)
    private String name;

    @Size(max = 500)
    private String comments;

    private Set<AddressDTO> addressthirds = new HashSet<>();

    private Long thirdClassifId;

    private String thirdClassifName;

    private Long civilityClassifId;

    private String civilityClassifName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<AddressDTO> getAddressthirds() {
        return addressthirds;
    }

    public void setAddressthirds(Set<AddressDTO> addresses) {
        this.addressthirds = addresses;
    }

    public Long getThirdClassifId() {
        return thirdClassifId;
    }

    public void setThirdClassifId(Long thirdclassificationId) {
        this.thirdClassifId = thirdclassificationId;
    }

    public String getThirdClassifName() {
        return thirdClassifName;
    }

    public void setThirdClassifName(String thirdclassificationName) {
        this.thirdClassifName = thirdclassificationName;
    }

    public Long getCivilityClassifId() {
        return civilityClassifId;
    }

    public void setCivilityClassifId(Long civilityId) {
        this.civilityClassifId = civilityId;
    }

    public String getCivilityClassifName() {
        return civilityClassifName;
    }

    public void setCivilityClassifName(String civilityName) {
        this.civilityClassifName = civilityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThirdDTO thirdDTO = (ThirdDTO) o;
        if(thirdDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thirdDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThirdDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
