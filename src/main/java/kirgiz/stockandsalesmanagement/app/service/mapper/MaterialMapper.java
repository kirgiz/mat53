package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Material and its DTO MaterialDTO.
 */
@Mapper(componentModel = "spring", uses = {MaterialclassificationMapper.class, CurrencyMapper.class, LotMapper.class})
public interface MaterialMapper extends EntityMapper<MaterialDTO, Material> {

    @Mapping(source = "materialTypeDef.id", target = "materialTypeDefId")
    @Mapping(source = "materialTypeDef.name", target = "materialTypeDefName")
    @Mapping(source = "buycurrency.id", target = "buycurrencyId")
    @Mapping(source = "buycurrency.isoCode", target = "buycurrencyIsoCode")
    @Mapping(source = "sellcurrency.id", target = "sellcurrencyId")
    @Mapping(source = "sellcurrency.isoCode", target = "sellcurrencyIsoCode")
    @Mapping(source = "lotIdentifier.id", target = "lotIdentifierId")
    @Mapping(source = "lotIdentifier.code", target = "lotIdentifierCode")
    @Mapping(source = "materialClassif.id", target = "materialClassifId")
    @Mapping(source = "materialClassif.code", target = "materialClassifCode")
    @Mapping(source = "materialTypeCat.id", target = "materialTypeCatId")
    @Mapping(source = "materialTypeCat.name", target = "materialTypeCatName")
    MaterialDTO toDto(Material material); 

    @Mapping(source = "materialTypeDefId", target = "materialTypeDef")
    @Mapping(source = "buycurrencyId", target = "buycurrency")
    @Mapping(source = "sellcurrencyId", target = "sellcurrency")
    @Mapping(source = "lotIdentifierId", target = "lotIdentifier")
    @Mapping(source = "materialClassifId", target = "materialClassif")
    @Mapping(source = "materialTypeCatId", target = "materialTypeCat")
    Material toEntity(MaterialDTO materialDTO);

    default Material fromId(Long id) {
        if (id == null) {
            return null;
        }
        Material material = new Material();
        material.setId(id);
        return material;
    }
}
