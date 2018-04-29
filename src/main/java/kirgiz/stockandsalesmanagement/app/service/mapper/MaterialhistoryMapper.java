package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.MaterialhistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Materialhistory and its DTO MaterialhistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {MaterialMapper.class, TransferclassificationMapper.class, ThirdMapper.class, CurrencyMapper.class})
public interface MaterialhistoryMapper extends EntityMapper<MaterialhistoryDTO, Materialhistory> {

    @Mapping(source = "transferClassif.id", target = "transferClassifId")
    @Mapping(source = "transferClassif.name", target = "transferClassifName")
    @Mapping(source = "warehousefrom.id", target = "warehousefromId")
    @Mapping(source = "warehousefrom.name", target = "warehousefromName")
    @Mapping(source = "warehouseto.id", target = "warehousetoId")
    @Mapping(source = "warehouseto.name", target = "warehousetoName")
    @Mapping(source = "outgccy.id", target = "outgccyId")
    @Mapping(source = "outgccy.isoCode", target = "outgccyIsoCode")
    MaterialhistoryDTO toDto(Materialhistory materialhistory); 

    @Mapping(source = "transferClassifId", target = "transferClassif")
    @Mapping(source = "warehousefromId", target = "warehousefrom")
    @Mapping(source = "warehousetoId", target = "warehouseto")
    @Mapping(source = "outgccyId", target = "outgccy")
    Materialhistory toEntity(MaterialhistoryDTO materialhistoryDTO);

    default Materialhistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        Materialhistory materialhistory = new Materialhistory();
        materialhistory.setId(id);
        return materialhistory;
    }
}
