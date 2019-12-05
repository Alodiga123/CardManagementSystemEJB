/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.ProgramEJB;
import com.alodiga.cms.commons.ejb.RequestEJB;
import com.alodiga.cms.commons.ejb.RequestEJBLocal;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.DisabledAccountException;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Address;
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.City;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.Country;
import com.cms.commons.models.Person;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.EdificationType;
import com.cms.commons.models.FamilyReferences;
import com.cms.commons.models.PersonClassification;
import com.cms.commons.models.PersonHasAddress;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.PhonePerson;
import com.cms.commons.models.PhoneType;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.Profession;
import com.cms.commons.models.Program;
import com.cms.commons.models.RequestType;
import com.cms.commons.models.Request;
import com.cms.commons.models.Sequences;
import com.cms.commons.models.State;
import com.cms.commons.models.StatusRequest;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.QueryConstants;
import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author jose
 */

@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.REQUEST_EJB, mappedName = EjbConstants.REQUEST_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class RequestEJBImp extends AbstractDistributionEJB implements RequestEJB, RequestEJBLocal {
    private static final Logger logger = Logger.getLogger(RequestEJBImp.class);
    private UtilsEJB utilsEJB = null;
    private ProgramEJB programEJB = null;
    private RequestEJB requestEJB = null;
    private PersonEJB personEJB = null;

    @Override
    public List<Request> getRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Request> requests = (List<Request>) listEntities(Request.class, request, logger, getMethodName());
        return requests;
    }

    @Override
    public Request loadRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Request requests = (Request) loadEntity(Request.class, request, logger, getMethodName());
        return requests;
    }

    @Override
    public Request saveRequest(Request request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (request == null) {
            throw new NullParameterException("requests", null);
        }
        return (Request) saveEntity(request);
    }

    @Override
    public Request saveRequestPersonData(int countryId, String email, int documentPersonTypeId, String identificationNumber, Date dueDateIdentification,
                                         String firstNames, String lastNames, String marriedLastName, String gender, String placeBirth, Date dateBirth, int familyResponsabilities,  
                                         int civilStatusId, int professionId, String roomPhone, String cellPhone, int countryAddress, int state, int city, int zipZone, int edificationType, String nameEdification,
                                         String tower, int floor, int streetType, String nameStreet, String Urbanization, String firstNamesFamilyOne, String lastNamesFamilyOne, String cellPhoneFamilyOne,
                                         String roomPhoneFamilyOne, String cityFamilyOne, String firstNamesFamilyTwo, String lastNamesFamilyTwo, String cellPhoneFamilyTwo, String roomPhoneFamilyTwo, String cityFamilyTwo) 
                                         throws EmptyListException, RegisterNotFoundException, NullParameterException, GeneralException {
 
        PersonType personTypeApp = new PersonType();
        int numberSequence = 0;
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        programEJB = (ProgramEJB) EJBServiceLocator.getInstance().get(EjbConstants.PROGRAM_EJB);
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);  
        
        //1. Persona que hace la solicitud
        //Clasificacion de la persona (Solicitante)
        EJBRequest request1 = new EJBRequest();
        Map params = new HashMap();
        request1.setParam(Constants.PERSON_CLASSIFICATION_APPLICANT);
        PersonClassification personClassification = utilsEJB.loadPersonClassification(request1);
        //país de la persona que hace la solicitud
        request1 = new EJBRequest();
        request1.setParam(countryId);
        Country country = utilsEJB.loadCountry(request1);
         //tipo de la persona que hace la solicitud
        request1 = new EJBRequest();
        params = new HashMap();
        params.put(Constants.COUNTRY_KEY, countryId);
        params.put(Constants.ORIGIN_APPLICATION_ID, Constants.ORIGIN_APPLICATION_ID);
        request1.setParams(params);
        List<PersonType> personTypes = utilsEJB.getPersonTypesByCountry(request1);
        for (PersonType p: personTypes) {
            if (p.getOriginApplicationId().getId() == Constants.ORIGIN_APPLICATION_ID) {
                personTypeApp = p;
            }
        }
        
        //Crea el person y lo guarda en BD
        Person applicant = new Person();
        applicant.setCountryId(country);
        applicant.setEmail(email);
        applicant.setPersonClassificationId(personClassification);
        applicant.setPersonTypeId(personTypeApp);
        applicant = utilsEJB.savePerson(applicant);
        
        //2. Solicitud de tarjeta         
        //programa asociado a la solicitud
        request1 = new EJBRequest();
        request1.setParam(Constants.PROGRAM_WALLET_APP_ID);
        Program program = programEJB.loadProgram(request1);
        //tipo de solicitud
        request1 = new EJBRequest();
        request1.setParam(Constants.REQUEST_TYPE_WALLET_APP_ID);
        RequestType requestType = utilsEJB.loadRequestType(request1);
        //tipo de producto de la solicitud
        request1 = new EJBRequest();
        request1.setParam(Constants.PRODUCT_TYPE_WALLET_APP_ID);
        ProductType productType = utilsEJB.loadProductType(request1);
        //colocar estatus de solicitud "EN PROCESO"
        request1 = new EJBRequest();
        request1.setParam(Constants.STATUS_REQUEST_IN_PROCESS);
        StatusRequest statusRequest = utilsEJB.loadStatusRequest(request1);
        
        //Obtiene el numero de secuencia para documento Request
        request1 = new EJBRequest();
        request1.setParam(Constants.SEQUENCES_REQUEST);
        List<Sequences> sequence = utilsEJB.getSequencesByDocumentType(request1);
        for (Sequences s : sequence) {
            if (s.getCurrentValue() > 1) {
                numberSequence = s.getCurrentValue();
            } else {
                numberSequence = s.getInitialValue();
            }
            s.setCurrentValue(s.getCurrentValue()+1);
            Sequences sequenceBD =  utilsEJB.saveSequences(s);
        }   
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String prefixNumberRequest = "APP-";
        String suffixNumberRequest = "-";
        suffixNumberRequest = suffixNumberRequest.concat(String.valueOf(year));
        String numberRequest = prefixNumberRequest;
        numberRequest = numberRequest.concat(String.valueOf(numberSequence));
        numberRequest = numberRequest.concat(suffixNumberRequest);
        
        //Crea el request y lo guarda en BD
        Request request = new Request();
        request.setRequestNumber(numberRequest);//APP-1-2019
        Date dateRequest = new Date();
        request.setRequestDate(dateRequest);
        request.setCountryId(country);
        request.setPersonId(applicant);
        request.setPersonTypeId(personTypeApp);
        request.setProgramId(program);
        request.setProductTypeId(productType);
        request.setRequestTypeId(requestType);
        request.setStatusRequestId(statusRequest);
        request = requestEJB.saveRequest(request);        
        
        //3. Datos basicos del solicitante
        //tipo de documento del solicitante
        request1 = new EJBRequest();
        params = new HashMap();
        request1.setParam(documentPersonTypeId);
        DocumentsPersonType documentPersonType = utilsEJB.loadDocumentsPersonType(request1);
        //estado civil del solicitante
        request1 = new EJBRequest();
        request1.setParam(civilStatusId);
        CivilStatus civilStatus = utilsEJB.loadCivilStatus(request1);
        //profesion del solicitante
        request1 = new EJBRequest();
        request1.setParam(professionId);
        Profession profession = utilsEJB.loadProfession(request1);
        
        //Guarda en BD el applicantNaturalPerson
        ApplicantNaturalPerson applicantNatural = new ApplicantNaturalPerson();
        applicantNatural.setPersonId(applicant);
        applicantNatural.setIdentificationNumber(identificationNumber);
        applicantNatural.setDueDateDocumentIdentification(dueDateIdentification);
        applicantNatural.setFirstNames(firstNames);
        applicantNatural.setLastNames(lastNames);
        applicantNatural.setMarriedLastName(marriedLastName);
        applicantNatural.setGender(gender); //pasar por parámetro M ó F
        applicantNatural.setPlaceBirth(placeBirth);
        applicantNatural.setDateBirth(dateBirth);
        applicantNatural.setFamilyResponsibilities(familyResponsabilities);
        applicantNatural.setCivilStatusId(civilStatus);
        applicantNatural.setProfessionId(profession);
        applicantNatural.setDocumentsPersonTypeId(documentPersonType);
        applicantNatural = requestEJB.saveApplicantNatural(applicantNatural);
        
        //4. Telefonos del solicitante
        //Guarda el telf. Celular en BD
        PhonePerson cellPhoneApplicant = new PhonePerson();
        cellPhoneApplicant.setNumberPhone(cellPhone);
        cellPhoneApplicant.setPersonId(applicant);
        request1 = new EJBRequest();
        request1.setParam(Constants.PHONE_TYPE_MOBILE);
        PhoneType mobilePhoneType = utilsEJB.loadPhoneType(request1);
        cellPhoneApplicant.setPhoneTypeId(mobilePhoneType);
        cellPhoneApplicant = personEJB.savePhonePerson(cellPhoneApplicant);
        //Guarda el telf. Habitacion en BD
        PhonePerson roomPhoneApplicant = new PhonePerson();
        roomPhoneApplicant.setNumberPhone(roomPhone);
        roomPhoneApplicant.setPersonId(applicant);
        request1 = new EJBRequest();
        request1.setParam(Constants.PHONE_TYPE_ROOM);
        PhoneType roomPhoneType = utilsEJB.loadPhoneType(request1);
        roomPhoneApplicant.setPhoneTypeId(roomPhoneType);
        roomPhoneApplicant = personEJB.savePhonePerson(roomPhoneApplicant);
           
        //5. Direccion del solicitante
        Address addressApplicant = new Address();
        //pais
        request1 = new EJBRequest();
        request1.setParam(countryAddress);
        Country countryAddressApplicant = utilsEJB.loadCountry(request1);
        //estado
        request1 = new EJBRequest();
        request1.setParam(state);
        State stateAddress = utilsEJB.loadState(request1);
        //ciudad
        request1 = new EJBRequest();
        request1.setParam(city);
        City cityAddress = utilsEJB.loadCity(request1);
        //zona postal
        request1 = new EJBRequest();
        request1.setParam(zipZone);
        ZipZone zipZoneAddress = utilsEJB.loadZipZone(request1);
        //tipos de edificacion
        request1 = new EJBRequest();
        request1.setParam(edificationType);
        EdificationType edificationTypeAddress = utilsEJB.loadEdificationType(request1);
        //tipos de calle
        request1 = new EJBRequest();
        request1.setParam(streetType);
        StreetType streetTypeAddress = utilsEJB.loadStreetType(request1);
        
        //Guarda la direccion en BD
        addressApplicant.setCityId(cityAddress);
        addressApplicant.setCountryId(countryAddressApplicant);
        addressApplicant.setEdificationTypeId(edificationTypeAddress);
        addressApplicant.setFloor(floor);
        addressApplicant.setNameEdification(nameEdification);
        addressApplicant.setNameStreet(nameStreet);
        addressApplicant.setStreetTypeId(streetTypeAddress);
        addressApplicant.setTower(tower);
        addressApplicant.setUrbanization(Urbanization);
        addressApplicant.setZipZoneId(zipZoneAddress);
        addressApplicant = utilsEJB.saveAddress(addressApplicant);
        PersonHasAddress personHasAddress = new PersonHasAddress();
        personHasAddress.setAddressId(addressApplicant);
        personHasAddress.setPersonId(applicant);
        personHasAddress = personEJB.savePersonHasAddress(personHasAddress);
           
        //4. Referencias Familiares
        FamilyReferences familyReferencesOne = new FamilyReferences();
        familyReferencesOne.setApplicantNaturalPersonId(applicantNatural);
        familyReferencesOne.setCellPhone(cellPhoneFamilyOne);
        familyReferencesOne.setCity(cityFamilyOne);
        familyReferencesOne.setLocalPhone(roomPhoneFamilyOne);
        familyReferencesOne.setFirstNames(firstNamesFamilyOne);
        familyReferencesOne.setLastNames(lastNamesFamilyOne);
        familyReferencesOne = personEJB.saveFamilyReferences(familyReferencesOne);
        
        FamilyReferences familyReferencesTwo = new FamilyReferences();
        familyReferencesTwo.setApplicantNaturalPersonId(applicantNatural);
        familyReferencesTwo.setCellPhone(cellPhoneFamilyTwo);
        familyReferencesTwo.setCity(cityFamilyTwo);
        familyReferencesTwo.setLocalPhone(roomPhoneFamilyTwo);
        familyReferencesTwo.setFirstNames(firstNamesFamilyTwo);
        familyReferencesTwo.setLastNames(lastNamesFamilyTwo);
        familyReferencesTwo = personEJB.saveFamilyReferences(familyReferencesTwo);
        
        return request;
    }

    @Override
    public ApplicantNaturalPerson saveApplicantNatural(ApplicantNaturalPerson applicantNatural) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (applicantNatural == null) {
            throw new NullParameterException("applicantNatural", null);
        }
        return (ApplicantNaturalPerson) saveEntity(applicantNatural);
    }
    
}
