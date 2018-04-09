package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.CurrencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Currency and its DTO CurrencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurrencyMapper extends EntityMapper<CurrencyDTO, Currency> {

    

    @Mapping(target = "companyBaseCurrencies", ignore = true)
    @Mapping(target = "currencyRates", ignore = true)
    @Mapping(target = "materialBuyCurrencies", ignore = true)
    @Mapping(target = "materialSellCurrencies", ignore = true)
    @Mapping(target = "lotBuyCurrencies", ignore = true)
    Currency toEntity(CurrencyDTO currencyDTO);

    default Currency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Currency currency = new Currency();
        currency.setId(id);
        return currency;
    }
}
