package com.shilen.app.workbench.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shilen.app.workbench.model.ag.AirGaugeInspection;
import com.shilen.app.workbench.model.ag.AirGaugeSample;

public interface AirGaugeMapper {

	@Select("SELECT id, inspectiontype, woid, comment, DATE_FORMAT(created, '%Y-%m-%d %H:%i:%s') created, custom1, custom2, serialNumber "
			+ "FROM operations.inspection "
			+ "WHERE UPPER(IFNULL(inspectiontype, '')) = 'AG' "
			+ "  AND UPPER(IFNULL(woid, '')) = UPPER(#{woid}) "
			+ "ORDER BY serialNumber, created DESC, id DESC")
	List<AirGaugeInspection> findByWorkOrder(@Param("woid") String woid);

	@Select("SELECT id, inspectiontype, woid, comment, DATE_FORMAT(created, '%Y-%m-%d %H:%i:%s') created, custom1, custom2, serialNumber "
			+ "FROM operations.inspection "
			+ "WHERE UPPER(IFNULL(inspectiontype, '')) = 'AG' "
			+ "  AND UPPER(IFNULL(serialNumber, '')) = UPPER(#{serialNumber}) "
			+ "ORDER BY woid, created DESC, id DESC")
	List<AirGaugeInspection> findBySerialNumber(@Param("serialNumber") String serialNumber);

	@Select("SELECT id, inspectiontype, woid, comment, DATE_FORMAT(created, '%Y-%m-%d %H:%i:%s') created, custom1, custom2, serialNumber "
			+ "FROM operations.inspection "
			+ "WHERE UPPER(IFNULL(inspectiontype, '')) = 'AG' "
			+ "  AND id = #{id}")
	AirGaugeInspection findInspectionById(@Param("id") int id);

	@Select("SELECT zposvalue, xvalue "
			+ "FROM operations.inspection_sample "
			+ "WHERE inspection_id = #{inspectionId} "
			+ "ORDER BY zposvalue")
	List<AirGaugeSample> findSamplesByInspectionId(@Param("inspectionId") int inspectionId);
}
