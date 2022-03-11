package com.turkcell.rentACar.business.abstracts;



import com.turkcell.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.time.LocalDate;
import java.util.List;

public interface CarMaintenanceService {
    DataResult<List<CarMaintenanceListDto>> getAll();
    Result sendToMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
    Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);
    DataResult<List<CarMaintenanceListDto>> getByCarId(int id);
    boolean checkIfCarIsInMaintenance(int id, LocalDate localDate);
    boolean checkIfCarIsInMaintenance(int id,LocalDate startDate,LocalDate endDate);
}
