package kirgiz.stockandsalesmanagement.app.service.mapper;

import kirgiz.stockandsalesmanagement.app.domain.*;
import kirgiz.stockandsalesmanagement.app.service.dto.DashboardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dashboard and its DTO DashboardDTO.
 */
@Mapper(componentModel = "spring", uses = {ThirdMapper.class, MaterialclassificationMapper.class})
public interface DashboardMapper extends EntityMapper<DashboardDTO, Dashboard> {

    @Mapping(source = "warehouseOutg.id", target = "warehouseOutgId")
    @Mapping(source = "warehouseOutg.name", target = "warehouseOutgName")
    @Mapping(source = "materialTypeDefDashboard.id", target = "materialTypeDefDashboardId")
    @Mapping(source = "materialTypeDefDashboard.code", target = "materialTypeDefDashboardCode")
    DashboardDTO toDto(Dashboard dashboard); 

    @Mapping(source = "warehouseOutgId", target = "warehouseOutg")
    @Mapping(source = "materialTypeDefDashboardId", target = "materialTypeDefDashboard")
    Dashboard toEntity(DashboardDTO dashboardDTO);

    default Dashboard fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dashboard dashboard = new Dashboard();
        dashboard.setId(id);
        return dashboard;
    }
}
