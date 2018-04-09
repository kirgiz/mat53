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
 * A Third.
 */
@Entity
@Table(name = "third")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "third")
public class Third implements Serializable {

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

    @OneToMany(mappedBy = "warehousefrom")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Materialhistory> materialhistoryfroms = new HashSet<>();

    @OneToMany(mappedBy = "warehouseto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Materialhistory> materialhistorytos = new HashSet<>();

    @OneToMany(mappedBy = "warehouseOutg")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dashboard> warehouseOuts = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "third_addressthird",
               joinColumns = @JoinColumn(name="thirds_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="addressthirds_id", referencedColumnName="id"))
    private Set<Address> addressthirds = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Thirdclassification thirdClassif;

    @ManyToOne(optional = false)
    @NotNull
    private Civility civilityClassif;

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

    public Third code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Third name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public Third comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Materialhistory> getMaterialhistoryfroms() {
        return materialhistoryfroms;
    }

    public Third materialhistoryfroms(Set<Materialhistory> materialhistories) {
        this.materialhistoryfroms = materialhistories;
        return this;
    }

    public Third addMaterialhistoryfrom(Materialhistory materialhistory) {
        this.materialhistoryfroms.add(materialhistory);
        materialhistory.setWarehousefrom(this);
        return this;
    }

    public Third removeMaterialhistoryfrom(Materialhistory materialhistory) {
        this.materialhistoryfroms.remove(materialhistory);
        materialhistory.setWarehousefrom(null);
        return this;
    }

    public void setMaterialhistoryfroms(Set<Materialhistory> materialhistories) {
        this.materialhistoryfroms = materialhistories;
    }

    public Set<Materialhistory> getMaterialhistorytos() {
        return materialhistorytos;
    }

    public Third materialhistorytos(Set<Materialhistory> materialhistories) {
        this.materialhistorytos = materialhistories;
        return this;
    }

    public Third addMaterialhistoryto(Materialhistory materialhistory) {
        this.materialhistorytos.add(materialhistory);
        materialhistory.setWarehouseto(this);
        return this;
    }

    public Third removeMaterialhistoryto(Materialhistory materialhistory) {
        this.materialhistorytos.remove(materialhistory);
        materialhistory.setWarehouseto(null);
        return this;
    }

    public void setMaterialhistorytos(Set<Materialhistory> materialhistories) {
        this.materialhistorytos = materialhistories;
    }

    public Set<Dashboard> getWarehouseOuts() {
        return warehouseOuts;
    }

    public Third warehouseOuts(Set<Dashboard> dashboards) {
        this.warehouseOuts = dashboards;
        return this;
    }

    public Third addWarehouseOut(Dashboard dashboard) {
        this.warehouseOuts.add(dashboard);
        dashboard.setWarehouseOutg(this);
        return this;
    }

    public Third removeWarehouseOut(Dashboard dashboard) {
        this.warehouseOuts.remove(dashboard);
        dashboard.setWarehouseOutg(null);
        return this;
    }

    public void setWarehouseOuts(Set<Dashboard> dashboards) {
        this.warehouseOuts = dashboards;
    }

    public Set<Address> getAddressthirds() {
        return addressthirds;
    }

    public Third addressthirds(Set<Address> addresses) {
        this.addressthirds = addresses;
        return this;
    }

    public Third addAddressthird(Address address) {
        this.addressthirds.add(address);
        address.getThirdaddresses().add(this);
        return this;
    }

    public Third removeAddressthird(Address address) {
        this.addressthirds.remove(address);
        address.getThirdaddresses().remove(this);
        return this;
    }

    public void setAddressthirds(Set<Address> addresses) {
        this.addressthirds = addresses;
    }

    public Thirdclassification getThirdClassif() {
        return thirdClassif;
    }

    public Third thirdClassif(Thirdclassification thirdclassification) {
        this.thirdClassif = thirdclassification;
        return this;
    }

    public void setThirdClassif(Thirdclassification thirdclassification) {
        this.thirdClassif = thirdclassification;
    }

    public Civility getCivilityClassif() {
        return civilityClassif;
    }

    public Third civilityClassif(Civility civility) {
        this.civilityClassif = civility;
        return this;
    }

    public void setCivilityClassif(Civility civility) {
        this.civilityClassif = civility;
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
        Third third = (Third) o;
        if (third.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), third.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Third{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
