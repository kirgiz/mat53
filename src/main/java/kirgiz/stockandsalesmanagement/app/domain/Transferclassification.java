package kirgiz.stockandsalesmanagement.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Transferclassification.
 */
@Entity
@Table(name = "transferclassification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transferclassification")
public class Transferclassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "code", length = 3, nullable = false)
    private String code;

    @NotNull
    @Size(max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_outgoing_transfer", nullable = false)
    private Boolean isOutgoingTransfer;

    @NotNull
    @Column(name = "is_incoming_transfer", nullable = false)
    private Boolean isIncomingTransfer;

    @NotNull
    @Column(name = "is_internal_transfer", nullable = false)
    private Boolean isInternalTransfer;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @OneToMany(mappedBy = "transferClassif")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Materialhistory> materialhistoryCategories = new HashSet<>();

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

    public Transferclassification code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Transferclassification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsOutgoingTransfer() {
        return isOutgoingTransfer;
    }

    public Transferclassification isOutgoingTransfer(Boolean isOutgoingTransfer) {
        this.isOutgoingTransfer = isOutgoingTransfer;
        return this;
    }

    public void setIsOutgoingTransfer(Boolean isOutgoingTransfer) {
        this.isOutgoingTransfer = isOutgoingTransfer;
    }

    public Boolean isIsIncomingTransfer() {
        return isIncomingTransfer;
    }

    public Transferclassification isIncomingTransfer(Boolean isIncomingTransfer) {
        this.isIncomingTransfer = isIncomingTransfer;
        return this;
    }

    public void setIsIncomingTransfer(Boolean isIncomingTransfer) {
        this.isIncomingTransfer = isIncomingTransfer;
    }

    public Boolean isIsInternalTransfer() {
        return isInternalTransfer;
    }

    public Transferclassification isInternalTransfer(Boolean isInternalTransfer) {
        this.isInternalTransfer = isInternalTransfer;
        return this;
    }

    public void setIsInternalTransfer(Boolean isInternalTransfer) {
        this.isInternalTransfer = isInternalTransfer;
    }

    public String getComments() {
        return comments;
    }

    public Transferclassification comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Materialhistory> getMaterialhistoryCategories() {
        return materialhistoryCategories;
    }

    public Transferclassification materialhistoryCategories(Set<Materialhistory> materialhistories) {
        this.materialhistoryCategories = materialhistories;
        return this;
    }

    public Transferclassification addMaterialhistoryCategory(Materialhistory materialhistory) {
        this.materialhistoryCategories.add(materialhistory);
        materialhistory.setTransferClassif(this);
        return this;
    }

    public Transferclassification removeMaterialhistoryCategory(Materialhistory materialhistory) {
        this.materialhistoryCategories.remove(materialhistory);
        materialhistory.setTransferClassif(null);
        return this;
    }

    public void setMaterialhistoryCategories(Set<Materialhistory> materialhistories) {
        this.materialhistoryCategories = materialhistories;
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
        Transferclassification transferclassification = (Transferclassification) o;
        if (transferclassification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transferclassification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transferclassification{" +
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
