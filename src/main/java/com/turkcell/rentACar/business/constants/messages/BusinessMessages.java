package com.turkcell.rentACar.business.constants.messages;

import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;

public class BusinessMessages {
    public static final String TO = " to ";

    public class GlobalMessages{

        public static final String DATA_LISTED_SUCCESSFULLY = "Data Listed Successfully: ";
        public static final String DATA_ADDED_SUCCESSFULLY = "Data Added Successfully: ";
        public static final String DATA_UPDATED_SUCCESSFULLY = "Data updated successfully: ";
        public static final String DATA_DELETED_SUCCESSFULLY = "Data deleted successfully: ";
    }

    public class AdditionalServiceMessages{

        public static final String ADDITIONAL_SERVICE_ALREADY_EXISTS = "Following Additional Service is already exists: ";
        public static final String ADDITIONAL_SERVICE_NOT_FOUND = "There is no Additional Service with following id: ";
        public static final String ADDITIONAL_SERVICE_HAS_NO_CHANGES = "This Additional Service Update Has No Changes: ";
    }

    public class BrandMessages{

        public static final String BRAND_ALREADY_EXISTS = "Following Brand is already exists: ";
        public static final String BRAND_NOT_FOUND = "There is no Brand with following id: ";
        public static final String BRAND_HAS_NO_CHANGES = "This Brand Update Has No Changes: ";
    }

    public class CarDamageMessages{

        public static final String CAR_DAMAGE_ALREADY_EXISTS = "Following Car Damage is already exists: ";
        public static final String CAR_DAMAGE_NOT_FOUND = "There is no Car Damage with following id: ";
    }

    public class CarMaintenanceMessages{

        public static final String CAR_NOT_FOUND = "There is no Car with following id: ";
        public static final String CAR_RENT_NOT_FOUND = "There is no Car Rent operation with following id: ";
        public static final String CAR_IS_RENTED = "Maintenance is not possible! This car is rented from ";
        public static final String CAR_MAINTENANCE_NOT_FOUND = "There is no car maintenance with following id: ";
        public static final String NO_CHANGES_NO_NEED_TO_UPDATE = "Initial values are completely equal to update values, no need to update!";
        public static final String CAR_IS_ALREADY_AT_MAINTENANCE = "This car is already in maintenance";
    }

    public class CarMessages{

        public static final String CAR_NOT_FOUND = "There is no Car with following id: ";
        public static final String NO_CHANGES_NO_NEED_TO_UPDATE = "Initial values are completely equal to update values, no need to update!";
        public static final String BRAND_NOT_FOUND = "There is no Brand with following id: ";
        public static final String COLOR_NOT_FOUND = "There is no Color with following id: ";

    }

    public class ColorMessages{

        public static final String COLOR_ALREADY_EXISTS = "Following Color is already exists: ";
        public static final String COLOR_NOT_FOUND = "There is no Color with following id: ";
        public static final String COLOR_HAS_NO_CHANGES = "This Color Update Has No Changes: ";
    }

    public class CorporateCustomerMessages{

        public static final String CORPORATE_CUSTOMER_NOT_FOUND = "There is no Corporate Customer with following id: ";
        public static final String CORPORATE_CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
        public static final String CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS = "Following Tax Number is already exists: ";
    }

    public class CustomerMessages{

        public static final String CUSTOMER_NOT_FOUND = "There is no Customer with following id: ";
        public static final String CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
    }

    public class CityMessages{
        public static final String CITY_NOT_FOUND = "There is no City with following id:";
    }

    public class IndividualCustomerMessages{

        public static final String INDIVIDUAL_CUSTOMER_NOT_FOUND = "There is no Individual Customer with following id: ";
        public static final String INDIVIDUAL_CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
        public static final String INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS = "Following National Identity is already exists: ";
    }

    public class InvoiceMessages{

        public static final String RENTAL_CAR_NOT_FOUND = "There is no Rental Car with following id: ";
        public static final String INVOICE_NOT_FOUND = "There is no Invoice with following id: ";
    }

    public class CarRentalMessages{

        public static final String CITY_NOT_FOUND = "There is no City with following id: ";
        public static final String RENT_AND_RETURN_KILOMETER_NOT_VALID = "Return kilometer must be greater than Rent kilometer.";
        public static final String CAR_NOT_FOUND = "There is no Car with following id: ";
        public static final String RENTAL_CAR_NOT_FOUND = "There is no Rental Car with following id: ";
        public static final String NO_CHANGES_NO_NEED_TO_UPDATE = "Initial values are completely equal to update values, no need to update!";
        public static final String CUSTOMER_NOT_FOUND = "There is no Customer with following id: ";
        public static final String CAR_IS_AT_MAINTENANCE = "This car is in maintenance";
        public static final String CAR_IS_ALREADY_RENTED = "This car is already rented";
        public static final String CAR_RENTAL_ENDED_SUCCESSFULLY = "Car Rental Ended Successfully";
        public static final String RENTAL_DATES_ARE_NOT_CORRECT = "Rental dates are not correct";
        public static final String ADDITIONAL_RENT_FOR_DELIVERING_LATE = "New Car Rental Since Car Delivered Late";

    }

    public class UserMessages{

        public static final String USER_NOT_FOUND = "There is no Customer with following id: ";
        public static final String USER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
    }

    public class PaymentMessages{

        public static final String INVALID_PAYMENT = "Invalid payment operation!";
        public static final String PAYMENT_NOT_FOUND = "Payment Not Found";
        public static final String INSUFFICIENT_BALANCE = "Insufficient Balance";
    }
    public class CreditCardMessages{
        public static final String CREDIT_CARD_NOT_FOUND = "Credit Card Not Found";
        public static final String CUSTOMER_NOT_FOUND = "Customer Not Found";
    }


}