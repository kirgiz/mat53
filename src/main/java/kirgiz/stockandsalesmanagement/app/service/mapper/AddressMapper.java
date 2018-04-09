package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressclassificationMapper.class, CountryMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "addressClassif.id", target = "addressClassifId")
    @Mapping(source = "addressClassif.name", target = "addressClassifName")
    @Mapping(source = "countryAddress.id", target = "countryAddressId")
    @Mapping(source = "countryAddress.name", target = "countryAddressName")
    AddressDTO toDto(Address address); 

    @Mapping(source = "addressClassifId", target = "addressClassif")
    @Mapping(source = "countryAddressId", target = "countryAddress")
    @Mapping(target = "thirdaddresses", ignore = true)
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
