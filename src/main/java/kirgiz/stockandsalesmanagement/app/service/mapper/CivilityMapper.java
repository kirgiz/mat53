package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.CivilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Civility and its DTO CivilityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CivilityMapper extends EntityMapper<CivilityDTO, Civility> {

    

    @Mapping(target = "thirdCivilities", ignore = true)
    Civility toEntity(CivilityDTO civilityDTO);

    default Civility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Civility civility = new Civility();
        civility.setId(id);
        return civility;
    }
}
