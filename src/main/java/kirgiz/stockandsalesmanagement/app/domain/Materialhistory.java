package kirgiz.stockandsalesmanagement.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Materialhistory.
 */
@Entity
@Table(name = "materialhistory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "materialhistory")
public class Materialhistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "price")
    private Double price;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "materialhistory_item_transfered",
               joinColumns = @JoinColumn(name="materialhistories_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="item_transfereds_id", referencedColumnName="id"))
    private Set<Material> itemTransfereds = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Transferclassification transferClassif;

    @ManyToOne(optional = false)
    @NotNull
    private Third warehousefrom;

    @ManyToOne(optional = false)
    @NotNull
    private Third warehouseto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Materialhistory code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Materialhistory creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Double getPrice() {
        return price;
    }

    public Materialhistory price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getComments() {
        return comments;
    }

    public Materialhistory comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Material> getItemTransfereds() {
        return itemTransfereds;
    }

    public Materialhistory itemTransfereds(Set<Material> materials) {
        this.itemTransfereds = materials;
        return this;
    }

    public Materialhistory addItemTransfered(Material material) {
        this.itemTransfereds.add(material);
        return this;
    }

    public Materialhistory removeItemTransfered(Material material) {
        this.itemTransfereds.remove(material);
        return this;
    }

    public void setItemTransfereds(Set<Material> materials) {
        this.itemTransfereds = materials;
    }

    public Transferclassification getTransferClassif() {
        return transferClassif;
    }

    public Materialhistory transferClassif(Transferclassification transferclassification) {
        this.transferClassif = transferclassification;
        return this;
    }

    public void setTransferClassif(Transferclassification transferclassification) {
        this.transferClassif = transferclassification;
    }

    public Third getWarehousefrom() {
        return warehousefrom;
    }

    public Materialhistory warehousefrom(Third third) {
        this.warehousefrom = third;
        return this;
    }

    public void setWarehousefrom(Third third) {
        this.warehousefrom = third;
    }

    public Third getWarehouseto() {
        return warehouseto;
    }

    public Materialhistory warehouseto(Third third) {
        this.warehouseto = third;
        return this;
    }

    public void setWarehouseto(Third third) {
        this.warehouseto = third;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Materialhistory materialhistory = (Materialhistory) o;
        if (materialhistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialhistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Materialhistory{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", price='" + getPrice() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
