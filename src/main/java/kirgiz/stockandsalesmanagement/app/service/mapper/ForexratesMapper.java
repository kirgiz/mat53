package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.ForexratesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Forexrates and its DTO ForexratesDTO.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ForexratesMapper extends EntityMapper<ForexratesDTO, Forexrates> {

    @Mapping(source = "rateForCurrency.id", target = "rateForCurrencyId")
    @Mapping(source = "rateForCurrency.name", target = "rateForCurrencyName")
    ForexratesDTO toDto(Forexrates forexrates); 

    @Mapping(source = "rateForCurrencyId", target = "rateForCurrency")
    Forexrates toEntity(ForexratesDTO forexratesDTO);

    default Forexrates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Forexrates forexrates = new Forexrates();
        forexrates.setId(id);
        return forexrates;
    }
}
