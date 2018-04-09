package kirgiz.stockandsalesmanagement.app.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Materialhistory entity.
 */
public class MaterialhistoryDTO implements Serializable {

    private Long id;

    @Size(max = 20)
    private String code;

    @NotNull
    private LocalDate creationDate;

    private Double price;

    @Size(max = 500)
    private String comments;

    private Set<MaterialDTO> itemTransfereds = new HashSet<>();

    private Long transferClassifId;

    private String transferClassifName;

    private Long warehousefromId;

    private String warehousefromName;

    private Long warehousetoId;

    private String warehousetoName;

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<MaterialDTO> getItemTransfereds() {
        return itemTransfereds;
    }

    public void setItemTransfereds(Set<MaterialDTO> materials) {
        this.itemTransfereds = materials;
    }

    public Long getTransferClassifId() {
        return transferClassifId;
    }

    public void setTransferClassifId(Long transferclassificationId) {
        this.transferClassifId = transferclassificationId;
    }

    public String getTransferClassifName() {
        return transferClassifName;
    }

    public void setTransferClassifName(String transferclassificationName) {
        this.transferClassifName = transferclassificationName;
    }

    public Long getWarehousefromId() {
        return warehousefromId;
    }

    public void setWarehousefromId(Long thirdId) {
        this.warehousefromId = thirdId;
    }

    public String getWarehousefromName() {
        return warehousefromName;
    }

    public void setWarehousefromName(String thirdName) {
        this.warehousefromName = thirdName;
    }

    public Long getWarehousetoId() {
        return warehousetoId;
    }

    public void setWarehousetoId(Long thirdId) {
        this.warehousetoId = thirdId;
    }

    public String getWarehousetoName() {
        return warehousetoName;
    }

    public void setWarehousetoName(String thirdName) {
        this.warehousetoName = thirdName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialhistoryDTO materialhistoryDTO = (MaterialhistoryDTO) o;
        if(materialhistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialhistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialhistoryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", price='" + getPrice() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
