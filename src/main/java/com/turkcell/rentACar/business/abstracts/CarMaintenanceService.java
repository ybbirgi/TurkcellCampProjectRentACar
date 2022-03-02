package com.turkcell.rentACar.business.abstracts;



import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CarMaintenanceService {
    DataResult<List<CarMaintenanceListDto>> getAll();
    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);
    Result update(int id,UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
    Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);
    DataResult<List<CarMaintenanceListDto>> getByCarId(int id);
}
