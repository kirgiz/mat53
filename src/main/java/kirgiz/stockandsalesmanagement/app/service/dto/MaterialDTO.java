package kirgiz.stockandsalesmanagement.app.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Material entity.
 */
public class MaterialDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate creationDate;

    @Size(max = 500)
    private String comments;

    private Long materialTypeDefId;

    private String materialTypeDefName;

    private Long buycurrencyId;

    private String buycurrencyIsoCode;

    private Long sellcurrencyId;

    private String sellcurrencyIsoCode;

    private Long lotIdentifierId;

    private String lotIdentifierCode;

    private Long materialClassifId;

    private String materialClassifCode;

    private Long materialTypeCatId;

    private String materialTypeCatName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getMaterialTypeDefId() {
        return materialTypeDefId;
    }

    public void setMaterialTypeDefId(Long materialclassificationId) {
        this.materialTypeDefId = materialclassificationId;
    }

    public String getMaterialTypeDefName() {
        return materialTypeDefName;
    }

    public void setMaterialTypeDefName(String materialclassificationName) {
        this.materialTypeDefName = materialclassificationName;
    }

    public Long getBuycurrencyId() {
        return buycurrencyId;
    }

    public void setBuycurrencyId(Long currencyId) {
        this.buycurrencyId = currencyId;
    }

    public String getBuycurrencyIsoCode() {
        return buycurrencyIsoCode;
    }

    public void setBuycurrencyIsoCode(String currencyIsoCode) {
        this.buycurrencyIsoCode = currencyIsoCode;
    }

    public Long getSellcurrencyId() {
        return sellcurrencyId;
    }

    public void setSellcurrencyId(Long currencyId) {
        this.sellcurrencyId = currencyId;
    }

    public String getSellcurrencyIsoCode() {
        return sellcurrencyIsoCode;
    }

    public void setSellcurrencyIsoCode(String currencyIsoCode) {
        this.sellcurrencyIsoCode = currencyIsoCode;
    }

    public Long getLotIdentifierId() {
        return lotIdentifierId;
    }

    public void setLotIdentifierId(Long lotId) {
        this.lotIdentifierId = lotId;
    }

    public String getLotIdentifierCode() {
        return lotIdentifierCode;
    }

    public void setLotIdentifierCode(String lotCode) {
        this.lotIdentifierCode = lotCode;
    }

    public Long getMaterialClassifId() {
        return materialClassifId;
    }

    public void setMaterialClassifId(Long materialclassificationId) {
        this.materialClassifId = materialclassificationId;
    }

    public String getMaterialClassifCode() {
        return materialClassifCode;
    }

    public void setMaterialClassifCode(String materialclassificationCode) {
        this.materialClassifCode = materialclassificationCode;
    }

    public Long getMaterialTypeCatId() {
        return materialTypeCatId;
    }

    public void setMaterialTypeCatId(Long materialclassificationId) {
        this.materialTypeCatId = materialclassificationId;
    }

    public String getMaterialTypeCatName() {
        return materialTypeCatName;
    }

    public void setMaterialTypeCatName(String materialclassificationName) {
        this.materialTypeCatName = materialclassificationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialDTO materialDTO = (MaterialDTO) o;
        if(materialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
