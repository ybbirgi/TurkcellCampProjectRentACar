package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.creditCardDto.CreditCardDto;
import com.turkcell.rentACar.business.dtos.creditCardDto.CreditCardListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentACar.entities.concretes.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardManager implements CreditCardService {

    private final CreditCardDao creditCardDao;
    private final ModelMapperService modelMapperService;
    private final CustomerService customerService;

    @Autowired
    public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService, CustomerService customerService) {
        this.creditCardDao = creditCardDao;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<List<CreditCardListDto>> getAll() {

        List<CreditCard> result = this.creditCardDao.findAll();
        List<CreditCardListDto> response = result.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CreditCardListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException {
        CreditCard creditCard = this.modelMapperService.forDto().map(createCreditCardRequest, CreditCard.class);
        creditCard.setCustomer(this.customerService.getCustomerByCustomerId(customerId));
        this.creditCardDao.save(creditCard);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CreditCardDto> getById(int id) throws BusinessException {
        checkIfCreditCardExist(id);

        CreditCard result = this.creditCardDao.getById(id);

        CreditCardDto response = this.modelMapperService.forDto().map(result, CreditCardDto.class);

        return new SuccessDataResult<CreditCardDto>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);

    }

    @Override
    public DataResult<List<CreditCardListDto>> getByCustomerId(int id) throws BusinessException {

        List<CreditCard> result = this.creditCardDao.getAllByCustomer_CustomerId(id);

        List<CreditCardListDto> response = result.stream()
                .map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CreditCardListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);

    }

    @Override
    public Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {

        checkIfCreditCardExist(updateCreditCardRequest.getCreditCardId());

        CreditCard creditCard = this.modelMapperService.forDto().map(updateCreditCardRequest, CreditCard.class);
        this.creditCardDao.save(creditCard);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException {

        checkIfCreditCardExist(deleteCreditCardRequest.getCreditCardId());

        this.creditCardDao.deleteById(deleteCreditCardRequest.getCreditCardId());

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
    }

    public void checkIfCreditCardExist(int id) throws BusinessException{
        if(!this.creditCardDao.existsById(id))
            throw new BusinessException(BusinessMessages.CreditCardMessages.CREDIT_CARD_NOT_FOUND);
    }
}
