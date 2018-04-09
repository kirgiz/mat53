package kirgiz.stockandsalesmanagement.app.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Lot entity.
 */
public class LotDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code;

    @NotNull
    @Size(max = 200)
    private String description;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private Long numberOfItems;

    @Size(max = 500)
    private String comments;

    private Double unitBuyPrice;

    private Long buycurrencylotId;

    private String buycurrencylotIsoCode;

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

    public Long getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Long numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Double getUnitBuyPrice() {
        return unitBuyPrice;
    }

    public void setUnitBuyPrice(Double unitBuyPrice) {
        this.unitBuyPrice = unitBuyPrice;
    }

    public Long getBuycurrencylotId() {
        return buycurrencylotId;
    }

    public void setBuycurrencylotId(Long currencyId) {
        this.buycurrencylotId = currencyId;
    }

    public String getBuycurrencylotIsoCode() {
        return buycurrencylotIsoCode;
    }

    public void setBuycurrencylotIsoCode(String currencyIsoCode) {
        this.buycurrencylotIsoCode = currencyIsoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LotDTO lotDTO = (LotDTO) o;
        if(lotDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lotDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LotDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", numberOfItems='" + getNumberOfItems() + "'" +
            ", comments='" + getComments() + "'" +
            ", unitBuyPrice='" + getUnitBuyPrice() + "'" +
            "}";
    }
}
