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
 * A Materialclassification.
 */
@Entity
@Table(name = "materialclassification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "materialclassification")
public class Materialclassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @NotNull
    @Size(max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Size(max = 500)
    @Column(name = "comments", length = 500)
    private String comments;

    @OneToMany(mappedBy = "materialTypeDef")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Material> materialCategories = new HashSet<>();

    @OneToMany(mappedBy = "materialClassif")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Material> materialCategs = new HashSet<>();

    @OneToMany(mappedBy = "materialTypeCat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Material> materialCats = new HashSet<>();

    @OneToMany(mappedBy = "materialTypeDefDashboard")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dashboard> materialCategoryDashboards = new HashSet<>();

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

    public Materialclassification code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Materialclassification name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public Materialclassification comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Material> getMaterialCategories() {
        return materialCategories;
    }

    public Materialclassification materialCategories(Set<Material> materials) {
        this.materialCategories = materials;
        return this;
    }

    public Materialclassification addMaterialCategory(Material material) {
        this.materialCategories.add(material);
        material.setMaterialTypeDef(this);
        return this;
    }

    public Materialclassification removeMaterialCategory(Material material) {
        this.materialCategories.remove(material);
        material.setMaterialTypeDef(null);
        return this;
    }

    public void setMaterialCategories(Set<Material> materials) {
        this.materialCategories = materials;
    }

    public Set<Material> getMaterialCategs() {
        return materialCategs;
    }

    public Materialclassification materialCategs(Set<Material> materials) {
        this.materialCategs = materials;
        return this;
    }

    public Materialclassification addMaterialCateg(Material material) {
        this.materialCategs.add(material);
        material.setMaterialClassif(this);
        return this;
    }

    public Materialclassification removeMaterialCateg(Material material) {
        this.materialCategs.remove(material);
        material.setMaterialClassif(null);
        return this;
    }

    public void setMaterialCategs(Set<Material> materials) {
        this.materialCategs = materials;
    }

    public Set<Material> getMaterialCats() {
        return materialCats;
    }

    public Materialclassification materialCats(Set<Material> materials) {
        this.materialCats = materials;
        return this;
    }

    public Materialclassification addMaterialCat(Material material) {
        this.materialCats.add(material);
        material.setMaterialTypeCat(this);
        return this;
    }

    public Materialclassification removeMaterialCat(Material material) {
        this.materialCats.remove(material);
        material.setMaterialTypeCat(null);
        return this;
    }

    public void setMaterialCats(Set<Material> materials) {
        this.materialCats = materials;
    }

    public Set<Dashboard> getMaterialCategoryDashboards() {
        return materialCategoryDashboards;
    }

    public Materialclassification materialCategoryDashboards(Set<Dashboard> dashboards) {
        this.materialCategoryDashboards = dashboards;
        return this;
    }

    public Materialclassification addMaterialCategoryDashboard(Dashboard dashboard) {
        this.materialCategoryDashboards.add(dashboard);
        dashboard.setMaterialTypeDefDashboard(this);
        return this;
    }

    public Materialclassification removeMaterialCategoryDashboard(Dashboard dashboard) {
        this.materialCategoryDashboards.remove(dashboard);
        dashboard.setMaterialTypeDefDashboard(null);
        return this;
    }

    public void setMaterialCategoryDashboards(Set<Dashboard> dashboards) {
        this.materialCategoryDashboards = dashboards;
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
        Materialclassification materialclassification = (Materialclassification) o;
        if (materialclassification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialclassification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Materialclassification{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
