package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.TransferclassificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transferclassification and its DTO TransferclassificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransferclassificationMapper extends EntityMapper<TransferclassificationDTO, Transferclassification> {

    

    @Mapping(target = "materialhistoryCategories", ignore = true)
    Transferclassification toEntity(TransferclassificationDTO transferclassificationDTO);

    default Transferclassification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transferclassification transferclassification = new Transferclassification();
        transferclassification.setId(id);
        return transferclassification;
    }
}
