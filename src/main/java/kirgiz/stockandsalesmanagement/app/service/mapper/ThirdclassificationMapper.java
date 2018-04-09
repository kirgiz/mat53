package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.ThirdclassificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Thirdclassification and its DTO ThirdclassificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThirdclassificationMapper extends EntityMapper<ThirdclassificationDTO, Thirdclassification> {

    

    @Mapping(target = "thirdCategories", ignore = true)
    Thirdclassification toEntity(ThirdclassificationDTO thirdclassificationDTO);

    default Thirdclassification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Thirdclassification thirdclassification = new Thirdclassification();
        thirdclassification.setId(id);
        return thirdclassification;
    }
}
