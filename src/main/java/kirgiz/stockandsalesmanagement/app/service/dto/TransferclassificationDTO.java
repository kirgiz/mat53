package kirgiz.stockandsalesmanagement.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Transferclassification entity.
 */
public class TransferclassificationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3)
    private String code;

    @NotNull
    @Size(max = 60)
    private String name;

    @NotNull
    private Boolean isOutgoingTransfer;

    @NotNull
    private Boolean isIncomingTransfer;

    @NotNull
    private Boolean isInternalTransfer;

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

    public Boolean isIsOutgoingTransfer() {
        return isOutgoingTransfer;
    }

    public void setIsOutgoingTransfer(Boolean isOutgoingTransfer) {
        this.isOutgoingTransfer = isOutgoingTransfer;
    }

    public Boolean isIsIncomingTransfer() {
        return isIncomingTransfer;
    }

    public void setIsIncomingTransfer(Boolean isIncomingTransfer) {
        this.isIncomingTransfer = isIncomingTransfer;
    }

    public Boolean isIsInternalTransfer() {
        return isInternalTransfer;
    }

    public void setIsInternalTransfer(Boolean isInternalTransfer) {
        this.isInternalTransfer = isInternalTransfer;
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

        TransferclassificationDTO transferclassificationDTO = (TransferclassificationDTO) o;
        if(transferclassificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transferclassificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransferclassificationDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isOutgoingTransfer='" + isIsOutgoingTransfer() + "'" +
            ", isIncomingTransfer='" + isIsIncomingTransfer() + "'" +
            ", isInternalTransfer='" + isIsInternalTransfer() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
