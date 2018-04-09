package kirgiz.stockandsalesmanagement.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Civility entity.
 */
public class CivilityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3)
    private String code;

    @NotNull
    @Size(max = 60)
    private String name;

    @Size(max = 500)
    private String comments;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CivilityDTO civilityDTO = (CivilityDTO) o;
        if(civilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), civilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CivilityDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
