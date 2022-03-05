package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.time.LocalDate;
import java.util.List;

public interface CarRentalService {
    DataResult<List<CarRentalListDto>> getAll();
    Result add(CreateCarRentalRequest createCarRentalRequest);
    Result update(UpdateCarRentalRequest updateCarRentalRequest);
    Result delete(DeleteCarRentalRequest deleteCarRentalRequest);
    DataResult<List<CarRentalListDto>> getByCarId(int id);
    boolean checkIfCarIsRented(int id, LocalDate localDate);
}