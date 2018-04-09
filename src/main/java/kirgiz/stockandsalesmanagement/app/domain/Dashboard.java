package kirgiz.stockandsalesmanagement.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Dashboard.
 */
@Entity
@Table(name = "dashboard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dashboard")
public class Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @Column(name = "profit_and_loss")
    private Double profitAndLoss;

    @Column(name = "number_of_items")
    private Long numberOfItems;

    @ManyToOne(optional = false)
    @NotNull
    private Third warehouseOutg;

    @ManyToOne(optional = false)
    @NotNull
    private Materialclassification materialTypeDefDashboard;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public Dashboard transferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
        return this;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public Double getProfitAndLoss() {
        return profitAndLoss;
    }

    public Dashboard profitAndLoss(Double profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
        return this;
    }

    public void setProfitAndLoss(Double profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public Long getNumberOfItems() {
        return numberOfItems;
    }

    public Dashboard numberOfItems(Long numberOfItems) {
        this.numberOfItems = numberOfItems;
        return this;
    }

    public void setNumberOfItems(Long numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Third getWarehouseOutg() {
        return warehouseOutg;
    }

    public Dashboard warehouseOutg(Third third) {
        this.warehouseOutg = third;
        return this;
    }

    public void setWarehouseOutg(Third third) {
        this.warehouseOutg = third;
    }

    public Materialclassification getMaterialTypeDefDashboard() {
        return materialTypeDefDashboard;
    }

    public Dashboard materialTypeDefDashboard(Materialclassification materialclassification) {
        this.materialTypeDefDashboard = materialclassification;
        return this;
    }

    public void setMaterialTypeDefDashboard(Materialclassification materialclassification) {
        this.materialTypeDefDashboard = materialclassification;
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
        Dashboard dashboard = (Dashboard) o;
        if (dashboard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dashboard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dashboard{" +
            "id=" + getId() +
            ", transferDate='" + getTransferDate() + "'" +
            ", profitAndLoss='" + getProfitAndLoss() + "'" +
            ", numberOfItems='" + getNumberOfItems() + "'" +
            "}";
    }
}
