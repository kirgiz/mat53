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
 * A Addressclassification.
 */
@Entity
@Table(name = "addressclassification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "addressclassification")
public class Addressclassification implements Serializable {

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

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @OneToMany(mappedBy = "addressClassif")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Address> addressCategories = new HashSet<>();

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

    public Addressclassification code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Addressclassification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public Addressclassification comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Address> getAddressCategories() {
        return addressCategories;
    }

    public Addressclassification addressCategories(Set<Address> addresses) {
        this.addressCategories = addresses;
        return this;
    }

    public Addressclassification addAddressCategory(Address address) {
        this.addressCategories.add(address);
        address.setAddressClassif(this);
        return this;
    }

    public Addressclassification removeAddressCategory(Address address) {
        this.addressCategories.remove(address);
        address.setAddressClassif(null);
        return this;
    }

    public void setAddressCategories(Set<Address> addresses) {
        this.addressCategories = addresses;
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
        Addressclassification addressclassification = (Addressclassification) o;
        if (addressclassification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addressclassification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Addressclassification{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
