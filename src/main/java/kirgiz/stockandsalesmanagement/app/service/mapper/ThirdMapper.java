package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.ThirdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Third and its DTO ThirdDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, ThirdclassificationMapper.class, CivilityMapper.class})
public interface ThirdMapper extends EntityMapper<ThirdDTO, Third> {

    @Mapping(source = "thirdClassif.id", target = "thirdClassifId")
    @Mapping(source = "thirdClassif.name", target = "thirdClassifName")
    @Mapping(source = "civilityClassif.id", target = "civilityClassifId")
    @Mapping(source = "civilityClassif.name", target = "civilityClassifName")
    ThirdDTO toDto(Third third); 

    @Mapping(target = "materialhistoryfroms", ignore = true)
    @Mapping(target = "materialhistorytos", ignore = true)
    @Mapping(target = "warehouseOuts", ignore = true)
    @Mapping(source = "thirdClassifId", target = "thirdClassif")
    @Mapping(source = "civilityClassifId", target = "civilityClassif")
    Third toEntity(ThirdDTO thirdDTO);

    default Third fromId(Long id) {
        if (id == null) {
            return null;
        }
        Third third = new Third();
        third.setId(id);
        return third;
    }
}
