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
 * A Forexrates.
 */
@Entity
@Table(name = "forexrates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "forexrates")
public class Forexrates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "rate_date", nullable = false)
    private LocalDate rateDate;

    @NotNull
    @Column(name = "straigh_rate", nullable = false)
    private Double straighRate;

    @ManyToOne(optional = false)
    @NotNull
    private Currency rateForCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public Forexrates rateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
        return this;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }

    public Double getStraighRate() {
        return straighRate;
    }

    public Forexrates straighRate(Double straighRate) {
        this.straighRate = straighRate;
        return this;
    }

    public void setStraighRate(Double straighRate) {
        this.straighRate = straighRate;
    }

    public Currency getRateForCurrency() {
        return rateForCurrency;
    }

    public Forexrates rateForCurrency(Currency currency) {
        this.rateForCurrency = currency;
        return this;
    }

    public void setRateForCurrency(Currency currency) {
        this.rateForCurrency = currency;
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
        Forexrates forexrates = (Forexrates) o;
        if (forexrates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), forexrates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Forexrates{" +
            "id=" + getId() +
            ", rateDate='" + getRateDate() + "'" +
            ", straighRate='" + getStraighRate() + "'" +
            "}";
    }
}
