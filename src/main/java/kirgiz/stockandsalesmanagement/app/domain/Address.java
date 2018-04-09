package kirgiz.stockandsalesmanagement.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @NotNull
    @Size(max = 80)
    @Column(name = "line_1", length = 80, nullable = false)
    private String line1;

    @Size(max = 80)
    @Column(name = "line_2", length = 80)
    private String line2;

    @Size(max = 80)
    @Column(name = "line_3", length = 80)
    private String line3;

    @Size(max = 80)
    @Column(name = "line_4", length = 80)
    private String line4;

    @Size(max = 10)
    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Size(max = 80)
    @Column(name = "state", length = 80)
    private String state;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @ManyToOne(optional = false)
    @NotNull
    private Addressclassification addressClassif;

    @ManyToOne
    private Country countryAddress;

    @ManyToMany(mappedBy = "addressthirds")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Third> thirdaddresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Address description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLine1() {
        return line1;
    }

    public Address line1(String line1) {
        this.line1 = line1;
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public Address line2(String line2) {
        this.line2 = line2;
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public Address line3(String line3) {
        this.line3 = line3;
        return this;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public Address line4(String line4) {
        this.line4 = line4;
        return this;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public Address state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public Address validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public Address validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public String getComments() {
        return comments;
    }

    public Address comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Addressclassification getAddressClassif() {
        return addressClassif;
    }

    public Address addressClassif(Addressclassification addressclassification) {
        this.addressClassif = addressclassification;
        return this;
    }

    public void setAddressClassif(Addressclassification addressclassification) {
        this.addressClassif = addressclassification;
    }

    public Country getCountryAddress() {
        return countryAddress;
    }

    public Address countryAddress(Country country) {
        this.countryAddress = country;
        return this;
    }

    public void setCountryAddress(Country country) {
        this.countryAddress = country;
    }

    public Set<Third> getThirdaddresses() {
        return thirdaddresses;
    }

    public Address thirdaddresses(Set<Third> thirds) {
        this.thirdaddresses = thirds;
        return this;
    }

    public Address addThirdaddress(Third third) {
        this.thirdaddresses.add(third);
        third.getAddressthirds().add(this);
        return this;
    }

    public Address removeThirdaddress(Third third) {
        this.thirdaddresses.remove(third);
        third.getAddressthirds().remove(this);
        return this;
    }

    public void setThirdaddresses(Set<Third> thirds) {
        this.thirdaddresses = thirds;
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
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", line3='" + getLine3() + "'" +
            ", line4='" + getLine4() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", state='" + getState() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
