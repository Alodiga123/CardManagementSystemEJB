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
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Address;
import com.cms.commons.models.AddressType;
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.City;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.CollectionType;
import com.cms.commons.models.CollectionsRequest;
import com.cms.commons.models.Country;
import com.cms.commons.models.Person;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.EdificationType;
import com.cms.commons.models.FamilyReferences;
import com.cms.commons.models.KinShipApplicant;
import com.cms.commons.models.PersonClassification;
import com.cms.commons.models.PersonHasAddress;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.PhonePerson;
import com.cms.commons.models.PhoneType;
import com.cms.commons.models.PlastiCustomizingRequestHasCard;
import com.cms.commons.models.PlasticCustomizingRequest;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.Profession;
import com.cms.commons.models.Program;
import com.cms.commons.models.ReasonRejectionRequest;
import com.cms.commons.models.RequestType;
import com.cms.commons.models.Request;
import com.cms.commons.models.RequestHasCollectionsRequest;
import com.cms.commons.models.ResultPlasticCustomizingRequest;
import com.cms.commons.models.ReviewOFAC;
import com.cms.commons.models.ReviewRequest;
import com.cms.commons.models.ReviewRequestType;
import com.cms.commons.models.Sequences;
import com.cms.commons.models.State;
import com.cms.commons.models.StatusApplicant;
import com.cms.commons.models.StatusPlasticCustomizingRequest;
import com.cms.commons.models.StatusRequest;
import com.cms.commons.models.StatusResultPlasticCustomizing;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EJBServiceLocator;
import com.cms.commons.util.QueryConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
//
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
    public List<Request> getRequestsByStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Request> requestByStatusList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_STATUS_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_STATUS_REQUEST_ID), null);
        }
        requestByStatusList = (List<Request>) getNamedQueryResult(Request.class, QueryConstants.STATUS_REQUEST, request, getMethodName(), logger, "requestByStatusList");
        return requestByStatusList;
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
    public Long saveRequestPersonData(int countryId, String email, int documentPersonTypeId, String identificationNumber, Date dueDateIdentification,
            String firstNames, String lastNames, String marriedLastName, String gender, String placeBirth, Date dateBirth, int familyResponsabilities,
            int civilStatusId, int professionId, String roomPhone, String cellPhone, int countryAddress, int state, int city, int zipZone, int edificationType, String nameEdification,
            String tower, int floor, int streetType, String nameStreet, String Urbanization, String firstNamesFamilyOne, String lastNamesFamilyOne, String cellPhoneFamilyOne,
            String roomPhoneFamilyOne, String cityFamilyOne, String firstNamesFamilyTwo, String lastNamesFamilyTwo, String cellPhoneFamilyTwo, String roomPhoneFamilyTwo, String cityFamilyTwo)
            throws EmptyListException, RegisterNotFoundException, NullParameterException, GeneralException {

        PersonType personTypeApp = new PersonType();
        int numberSequence = 0;
        Long idApplicantNaturalPerson = 0L;
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        programEJB = (ProgramEJB) EJBServiceLocator.getInstance().get(EjbConstants.PROGRAM_EJB);
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);

        try {
            //1. Persona que hace la solicitud
            //Clasificacion de la persona (Solicitante)
            EJBRequest request1 = new EJBRequest();
            Map params = new HashMap();
            request1.setParam(Constants.PERSON_CLASSIFICATION_APPLICANT);
            PersonClassification personClassification = utilsEJB.loadPersonClassification(request1);
            //pais de la persona que hace la solicitud
            request1 = new EJBRequest();
            request1.setParam(countryId);
            Country country = utilsEJB.loadCountry(request1);
            //tipo de la persona que hace la solicitud
            request1 = new EJBRequest();
            params = new HashMap();
            params.put(Constants.COUNTRY_KEY, countryId);
            params.put(Constants.ORIGIN_APPLICATION_KEY, Constants.ORIGIN_APPLICATION_WALLET_ID);
            request1.setParams(params);
            List<PersonType> personTypes = utilsEJB.getPersonTypeByCountry(request1);
            for (PersonType p : personTypes) {
                if (p.getOriginApplicationId().getId() == Constants.ORIGIN_APPLICATION_WALLET_ID) {
                    personTypeApp = p;
                }
            }

            //Crea el person y lo guarda en BD
            Person applicant = new Person();
            applicant.setCountryId(country);
            applicant.setEmail(email);
            applicant.setPersonClassificationId(personClassification);
            applicant.setPersonTypeId(personTypeApp);
            applicant = personEJB.savePerson(applicant);

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
            params = new HashMap();
            params.put(Constants.DOCUMENT_TYPE_KEY, Constants.DOCUMENT_TYPE_REQUEST);
            request1.setParams(params);
            List<Sequences> sequence = utilsEJB.getSequencesByDocumentType(request1);
            String numberRequest = utilsEJB.generateNumberSequence(sequence, Constants.ORIGIN_APPLICATION_WALLET_ID);

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
            CivilStatus civilStatus = personEJB.loadCivilStatus(request1);
            //profesion del solicitante
            request1 = new EJBRequest();
            request1.setParam(professionId);
            Profession profession = personEJB.loadProfession(request1);

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
            applicantNatural = personEJB.saveApplicantNaturalPerson(applicantNatural);
            idApplicantNaturalPerson = applicantNatural.getId();

            //4. Telefonos del solicitante
            //Guarda el telf. Celular en BD
            PhonePerson cellPhoneApplicant = new PhonePerson();
            cellPhoneApplicant.setNumberPhone(cellPhone);
            cellPhoneApplicant.setPersonId(applicant);
            request1 = new EJBRequest();
            request1.setParam(Constants.PHONE_TYPE_MOBILE);
            PhoneType mobilePhoneType = personEJB.loadPhoneType(request1);
            cellPhoneApplicant.setPhoneTypeId(mobilePhoneType);
            cellPhoneApplicant = personEJB.savePhonePerson(cellPhoneApplicant);
            //Guarda el telf. Habitacion en BD
            PhonePerson roomPhoneApplicant = new PhonePerson();
            roomPhoneApplicant.setNumberPhone(roomPhone);
            roomPhoneApplicant.setPersonId(applicant);
            request1 = new EJBRequest();
            request1.setParam(Constants.PHONE_TYPE_ROOM);
            PhoneType roomPhoneType = personEJB.loadPhoneType(request1);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return idApplicantNaturalPerson;
    }

    @Override
     public ApplicantNaturalPerson saveCardComplementary(int countryId, String email, int documentPersonTypeId, String identificationNumber, Date dueDateIdentification,
                                                        String firstNames, String lastNames,  String gender, Date dateBirth, int civilStatusId,  
                                                        String cellPhone, int countryAddress, int state, int city, int zipZone,  
                                                        String nameStreet, String nameStreet2,Long applicantId, int kinShipApplicantId)
                                                        throws EmptyListException, RegisterNotFoundException, NullParameterException, GeneralException{
    
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB); 
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
        ApplicantNaturalPerson applicantCardComplementary = new ApplicantNaturalPerson();
        
        try {            
            //1. Persona (tarjeta Complementaria)
            //Clasificacion de la persona
            EJBRequest request1 = new EJBRequest();
            Map params = new HashMap();
            request1.setParam(Constants.PERSON_CLASSIFICATION_CARD_COMPLEMENTARY);
            PersonClassification personClassification = utilsEJB.loadPersonClassification(request1);
            //pais de la persona
            request1 = new EJBRequest();
            request1.setParam(countryId);
            Country country = utilsEJB.loadCountry(request1);
            //tipo de persona que hace la solicitud
            PersonType personTypeApp = personTypeWallet(countryId);

            //Crea el person y lo guarda en BD
            Person cardComplementaryPerson = new Person();
            cardComplementaryPerson.setCountryId(country);
            cardComplementaryPerson.setEmail(email);
            cardComplementaryPerson.setPersonClassificationId(personClassification);
            cardComplementaryPerson.setPersonTypeId(personTypeApp);
            cardComplementaryPerson = personEJB.savePerson(cardComplementaryPerson);

            //3. Datos basicos de persona asociada a tarjeta complementaria
            //tipo de documento
            request1 = new EJBRequest();
            params = new HashMap();
            request1.setParam(documentPersonTypeId);
            DocumentsPersonType documentPersonType = utilsEJB.loadDocumentsPersonType(request1);
            //estado civil del solicitante
            request1 = new EJBRequest();
            request1.setParam(civilStatusId);
            CivilStatus civilStatus = personEJB.loadCivilStatus(request1);
            //Solicitante Principal
            request1 = new EJBRequest();
            request1.setParam(applicantId);
            ApplicantNaturalPerson leadApplicant = personEJB.loadApplicantNaturalPerson(request1);
            //Parentesco con solicitante principal
            request1 = new EJBRequest();
            request1.setParam(kinShipApplicantId);
            KinShipApplicant kinShipApplicant = personEJB.loadKinShipApplicant(request1);

            //Solicitante de tarjeta complementaria
            applicantCardComplementary.setApplicantParentId(leadApplicant);
            applicantCardComplementary.setCivilStatusId(civilStatus);
            applicantCardComplementary.setDateBirth(dateBirth);
            applicantCardComplementary.setDocumentsPersonTypeId(documentPersonType);
            applicantCardComplementary.setDueDateDocumentIdentification(dueDateIdentification);
            applicantCardComplementary.setFirstNames(firstNames);
            applicantCardComplementary.setLastNames(lastNames);
            applicantCardComplementary.setGender(gender);
            applicantCardComplementary.setIdentificationNumber(identificationNumber);
            applicantCardComplementary.setKinShipApplicantId(kinShipApplicant);
            applicantCardComplementary.setPersonId(cardComplementaryPerson);
            applicantCardComplementary = personEJB.saveApplicantNaturalPerson(applicantCardComplementary);

            //4. Telefonos del solicitante de tarjeta complementaria
            //Guarda el telf. Celular en BD
            PhonePerson cellPhoneCardComplementary = new PhonePerson();
            cellPhoneCardComplementary.setNumberPhone(cellPhone);
            cellPhoneCardComplementary.setPersonId(cardComplementaryPerson);
            request1 = new EJBRequest();
            request1.setParam(Constants.PHONE_TYPE_MOBILE);
            PhoneType mobilePhoneType = personEJB.loadPhoneType(request1);
            cellPhoneCardComplementary.setPhoneTypeId(mobilePhoneType);
            cellPhoneCardComplementary = personEJB.savePhonePerson(cellPhoneCardComplementary);

            //5. Direccion del solicitante de tarjeta complementaria
            Address addressCardComplementary = new Address();
            //pais
            request1 = new EJBRequest();
            request1.setParam(countryAddress);
            Country countryAddressCardComplementary = utilsEJB.loadCountry(request1);
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
           //addressType
            request1 = new EJBRequest();
            request1.setParam(Constants.ADDRESS_TYPE_DELIVERY);
            AddressType addressType = utilsEJB.loadAddressType(request1);
            //Guarda la direccion en BD
            addressCardComplementary.setCityId(cityAddress);
            addressCardComplementary.setCountryId(countryAddressCardComplementary);
            addressCardComplementary.setAddressLine1(nameStreet);
            addressCardComplementary.setAddressLine2(nameStreet2);
            addressCardComplementary.setZipZoneId(zipZoneAddress);
            addressCardComplementary.setAddressTypeId(addressType);
            addressCardComplementary = utilsEJB.saveAddress(addressCardComplementary);
            PersonHasAddress personHasAddress = new PersonHasAddress();
            personHasAddress.setAddressId(addressCardComplementary);
            personHasAddress.setPersonId(cardComplementaryPerson);
            personHasAddress = personEJB.savePersonHasAddress(personHasAddress);
            
        } catch (Exception e) {
            e.printStackTrace();
        }   
        
        return applicantCardComplementary;
    }
	
	
	
    @Override
    public boolean existsApplicantNaturalPersonByEmail(String email) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> applicantNaturalPersons = new ArrayList<ApplicantNaturalPerson>();
        boolean exists = false;
        System.out.println("llego a validar email");
        if (email==null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "email"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT a FROM ApplicantNaturalPerson a, Person p WHERE a.personId.id = p.id AND p.email ='" + email+"'");
        Query query = null;
        try {
            query = createQuery(sqlBuilder.toString());
            applicantNaturalPersons = (List<ApplicantNaturalPerson>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!applicantNaturalPersons.isEmpty())
            exists = true;
        System.out.println("El email"+email+" :"+exists);
        return exists;
    }

    @Override
    public boolean existsApplicantNaturalPersonByPhoneNumber(String numberPhone) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> applicantNaturalPersons = new ArrayList<ApplicantNaturalPerson>();
        boolean exists = false;
        System.out.println("llego a validar numero");
        if (numberPhone==null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "numberPhone"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT a FROM ApplicantNaturalPerson a, PhonePerson p WHERE a.personId.id = p.personId.id AND p.numberPhone ='" + numberPhone+"'");
        Query query = null;
        try {
            query = createQuery(sqlBuilder.toString());
            applicantNaturalPersons = (List<ApplicantNaturalPerson>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!applicantNaturalPersons.isEmpty())
            exists = true;
        System.out.println("El numero"+numberPhone+" :"+exists);
        return exists;
    }

    @Override
    public PersonType personTypeWallet(int countryId) throws EmptyListException, RegisterNotFoundException, NullParameterException, GeneralException {

        PersonType personTypeApp = new PersonType();
        try {
            EJBRequest request1 = new EJBRequest();
            Map params = new HashMap();
            params.put(Constants.COUNTRY_KEY, countryId);
            params.put(Constants.ORIGIN_APPLICATION_KEY, Constants.ORIGIN_APPLICATION_WALLET_ID);
            request1.setParams(params);
            List<PersonType> personTypes = utilsEJB.getPersonTypeByCountry(request1);
            for (PersonType p : personTypes) {
                if (p.getOriginApplicationId().getId() == Constants.ORIGIN_APPLICATION_WALLET_ID) {
                    personTypeApp = p;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personTypeApp;
    }

    //Collections Requests
    @Override
    public List<CollectionsRequest> getCollectionsRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CollectionsRequest> collectionsRequest = (List<CollectionsRequest>) listEntities(CollectionsRequest.class, request, logger, getMethodName());
        return collectionsRequest;
    }

    @Override
    public List<CollectionsRequest> getCollectionsByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CollectionsRequest> CollectionsRequestByrequestsList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PERSON_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_TYPE_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_TYPE_ID), null);
        }
        CollectionsRequestByrequestsList = (List<CollectionsRequest>) getNamedQueryResult(CollectionsRequest.class, QueryConstants.COLLECTIONS_BY_REQUEST, request, getMethodName(), logger, "CollectionsRequestByrequestsList");
        return CollectionsRequestByrequestsList;
    }

    @Override
    public CollectionsRequest loadCollectionsRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CollectionsRequest collectionsRequest = (CollectionsRequest) loadEntity(CollectionsRequest.class, request, logger, getMethodName());
        return collectionsRequest;
    }

    @Override
    public CollectionsRequest saveCollectionRequest(CollectionsRequest collectionRequest) throws NullParameterException, GeneralException {
        if (collectionRequest == null) {
            throw new NullParameterException("collectionRequest", null);
        }
        return (CollectionsRequest) saveEntity(collectionRequest);
    }

    //RequestHasCollectionsRequest
    @Override
    public List<RequestHasCollectionsRequest> getRequestsHasCollectionsRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RequestHasCollectionsRequest> requestHasCollectionsRequest = (List<RequestHasCollectionsRequest>) listEntities(RequestHasCollectionsRequest.class, request, logger, getMethodName());
        return requestHasCollectionsRequest;
    }

    @Override
    public RequestHasCollectionsRequest loadRequestHasCollectionsRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RequestHasCollectionsRequest requestHasCollectionsRequest = (RequestHasCollectionsRequest) loadEntity(RequestHasCollectionsRequest.class, request, logger, getMethodName());
        return requestHasCollectionsRequest;
    }

    @Override
    public RequestHasCollectionsRequest saveRequestHasCollectionsRequest(RequestHasCollectionsRequest requestHasCollectionsRequest) throws NullParameterException, GeneralException {
        if (requestHasCollectionsRequest == null) {
            throw new NullParameterException("requestHasCollectionsRequest", null);
        }
        return (RequestHasCollectionsRequest) saveEntity(requestHasCollectionsRequest);
    }

    //CollectionType
    @Override
    public List<CollectionType> getCollectionType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CollectionType> collectionType = (List<CollectionType>) listEntities(CollectionType.class, request, logger, getMethodName());
        return collectionType;
    }

    @Override
    public List<CollectionType> getCollectionTypeByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CollectionType> collectionTypeByCountry = null;
        collectionTypeByCountry = (List<CollectionType>) getNamedQueryResult(UtilsEJB.class, QueryConstants.COLLECTION_TYPE_BY_COUNTRY, request, getMethodName(), logger, "collectionTypeByCountry");
        return collectionTypeByCountry;
    }

    @Override
    public CollectionType loadCollectionType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CollectionType collectionType = (CollectionType) loadEntity(CollectionType.class, request, logger, getMethodName());
        return collectionType;
    }

    @Override
    public CollectionType saveCollectionType(CollectionType collectionType) throws NullParameterException, GeneralException {
        if (collectionType == null) {
            throw new NullParameterException("collectionType", null);
        }
        return (CollectionType) saveEntity(collectionType);
    }

    //ReviewRequest
    @Override
    public List<ReviewRequest> getReviewRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ReviewRequest> reviewRequest = (List<ReviewRequest>) listEntities(ReviewRequest.class, request, logger, getMethodName());
        return reviewRequest;
    }

    @Override
    public List<ReviewRequest> getReviewRequestByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ReviewRequest> reviewRequest = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REQUEST_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_REVIEW_REQUEST_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REVIEW_REQUEST_TYPE_ID), null);
        }
        reviewRequest = (List<ReviewRequest>) getNamedQueryResult(ReviewRequest.class, QueryConstants.REVIEW_REQUEST_BY_REQUEST, request, getMethodName(), logger, "reviewRequest");
        return reviewRequest;
    }

    @Override
    public List<ReviewRequest> getReviewByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ReviewRequest> reviewByRequest = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REQUEST_ID), null);
        }
        reviewByRequest = (List<ReviewRequest>) getNamedQueryResult(ReviewRequest.class, QueryConstants.REVIEW_BY_REQUEST, request, getMethodName(), logger, "reviewByRequest");
        return reviewByRequest;
    }

    @Override
    public ReviewRequest loadReviewRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ReviewRequest reviewRequest = (ReviewRequest) loadEntity(ReviewRequest.class, request, logger, getMethodName());
        return reviewRequest;
    }

    @Override
    public ReviewRequest saveReviewRequest(ReviewRequest reviewRequest) throws NullParameterException, GeneralException {
        if (reviewRequest == null) {
            throw new NullParameterException("reviewRequest", null);
        }
        return (ReviewRequest) saveEntity(reviewRequest);
    }

    @Override
    public List<RequestHasCollectionsRequest> getRequestsHasCollectionsRequestByRequestByCollectionRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RequestHasCollectionsRequest> requestHasCollectionsRequestList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REQUEST_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_COLLECTION_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COLLECTION_REQUEST_ID), null);
        }
        requestHasCollectionsRequestList = (List<RequestHasCollectionsRequest>) getNamedQueryResult(RequestHasCollectionsRequest.class, QueryConstants.REQUEST_HAS_COLLECTION_REQUEST_BY_REQUEST_BY_COLLECTION_REQUEST, request, getMethodName(), logger, "requestHasCollectionsRequestList");
        return requestHasCollectionsRequestList;
    }

    @Override
    public List<StatusApplicant> getStatusApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusApplicant> statusApplicantList = (List<StatusApplicant>) listEntities(StatusApplicant.class, request, logger, getMethodName());
        return statusApplicantList;
    }

    @Override
    public StatusApplicant loadStatusApplicant(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusApplicant statusApplicant = (StatusApplicant) loadEntity(StatusApplicant.class, request, logger, getMethodName());
        return statusApplicant;
    }

    @Override
    public StatusApplicant saveStatusApplicant(StatusApplicant statusApplicant) throws NullParameterException, GeneralException {
        if (statusApplicant == null) {
            throw new NullParameterException("statusApplicant", null);
        }
        return (StatusApplicant) saveEntity(statusApplicant);
    }

    @Override
    public List<ReviewRequestType> getReviewRequestType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ReviewRequestType> reviewRequestTypeList = (List<ReviewRequestType>) listEntities(ReviewRequestType.class, request, logger, getMethodName());
        return reviewRequestTypeList;
    }

    @Override
    public ReviewRequestType loadReviewRequestType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ReviewRequestType reviewRequestType = (ReviewRequestType) loadEntity(ReviewRequestType.class, request, logger, getMethodName());
        return reviewRequestType;
    }

    @Override
    public ReviewRequestType saveReviewRequestType(ReviewRequestType reviewRequestType) throws NullParameterException, GeneralException {
        if (reviewRequestType == null) {
            throw new NullParameterException("reviewRequestType", null);
        }
        return (ReviewRequestType) saveEntity(reviewRequestType);
    }

    @Override
    public List<RequestHasCollectionsRequest> getRequestsHasCollectionsRequestByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RequestHasCollectionsRequest> requestHasCollectionsRequestList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REQUEST_ID), null);
        }
        requestHasCollectionsRequestList = (List<RequestHasCollectionsRequest>) getNamedQueryResult(RequestHasCollectionsRequest.class, QueryConstants.REQUEST_HAS_COLLECTION_REQUEST_BY_REQUEST, request, getMethodName(), logger, "requestHasCollectionsRequestList");
        return requestHasCollectionsRequestList;
    }

    @Override
    public List<ReasonRejectionRequest> getReasonRejectionRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReasonRejectionRequest loadReasonRejectionRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ReasonRejectionRequest reasonRejectionRequest = (ReasonRejectionRequest) loadEntity(ReasonRejectionRequest.class, request, logger, getMethodName());
        return reasonRejectionRequest;
    }

    @Override
    public ReasonRejectionRequest saveReasonRejectionRequest(ReasonRejectionRequest reasonRejectionRequest) throws NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ApplicantNaturalPerson saveRequestPersonData(int countryId, String email, Date dueDateIdentification, String firstNames, String lastNames, Date dateBirth, String cellPhone, int countryAddress, int state, int city, ZipZone postalZone, boolean recommendation, boolean promotion, boolean citizen, DocumentsPersonType documentsPersonType,
            String documentNumber, String gender, CivilStatus civilStatus,  String street,String street2, String number) throws EmptyListException, RegisterNotFoundException, NullParameterException, GeneralException {
        PersonType personTypeApp = new PersonType();
        ApplicantNaturalPerson applicantNatural = null;
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        programEJB = (ProgramEJB) EJBServiceLocator.getInstance().get(EjbConstants.PROGRAM_EJB);
        requestEJB = (RequestEJB) EJBServiceLocator.getInstance().get(EjbConstants.REQUEST_EJB);
        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);

        try {
            //1. Persona que hace la solicitud
            //Clasificacion de la persona (Solicitante)
            EJBRequest request1 = new EJBRequest();
            Map params = new HashMap();
            request1.setParam(Constants.PERSON_CLASSIFICATION_APPLICANT);
            PersonClassification personClassification = utilsEJB.loadPersonClassification(request1);
            //pais de la persona que hace la solicitud
            request1 = new EJBRequest();
            request1.setParam(countryId);
            Country country = utilsEJB.loadCountry(request1);
            //tipo de la persona que hace la solicitud
            request1 = new EJBRequest();
            params = new HashMap();
            params.put(Constants.COUNTRY_KEY, countryId);
            params.put(Constants.ORIGIN_APPLICATION_KEY, Constants.ORIGIN_APPLICATION_WALLET_ID);
            request1.setParams(params);
            List<PersonType> personTypes = utilsEJB.getPersonTypeByCountry(request1);
            for (PersonType p : personTypes) {
                if (p.getOriginApplicationId().getId() == Constants.ORIGIN_APPLICATION_WALLET_ID) {
                    personTypeApp = p;
                }
            }

            //Crea el person y lo guarda en BD
            Person applicant = new Person();
            applicant.setCountryId(country);
            applicant.setEmail(email);
            applicant.setPersonClassificationId(personClassification);
            applicant.setCreateDate(dueDateIdentification);
            applicant.setPersonTypeId(personTypeApp);
            applicant = personEJB.savePerson(applicant);

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
            params = new HashMap();
            params.put(Constants.DOCUMENT_TYPE_KEY, Constants.DOCUMENT_TYPE_REQUEST);
            request1.setParams(params);
            List<Sequences> sequence = utilsEJB.getSequencesByDocumentType(request1);
            String numberRequest = utilsEJB.generateNumberSequence(sequence, Constants.ORIGIN_APPLICATION_WALLET_ID);

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
            request.setCreateDate(dateRequest);
            request.setIndPersonNaturalRequest(true);
            request = requestEJB.saveRequest(request);

            //Guarda en BD el applicantNaturalPerson
            applicantNatural = new ApplicantNaturalPerson();
            applicantNatural.setPersonId(applicant);
            applicantNatural.setIdentificationNumber(documentNumber);
            applicantNatural.setDocumentsPersonTypeId(documentsPersonType);
            applicantNatural.setDueDateDocumentIdentification(dueDateIdentification);
            applicantNatural.setFirstNames(firstNames);
            applicantNatural.setLastNames(lastNames);
            applicantNatural.setMarriedLastName(civilStatus.getDescription());
            applicantNatural.setGender(gender); //pasar por parámetro M ó F
            applicantNatural.setDateBirth(dateBirth);
            applicantNatural.setCivilStatusId(civilStatus);
            applicantNatural.setPromotion(promotion);
            applicantNatural.setRecommendation(recommendation);
            applicantNatural.setCitizen(citizen);
            applicantNatural.setDateBirth(dateBirth);
            applicantNatural = personEJB.saveApplicantNaturalPerson(applicantNatural);
            applicantNatural.setRequest(request);
//            idApplicantNaturalPerson = applicantNatural.getId();

            //4. Telefonos del solicitante
            //Guarda el telf. Celular en BD
            PhonePerson cellPhoneApplicant = new PhonePerson();
            cellPhoneApplicant.setNumberPhone(cellPhone);
            cellPhoneApplicant.setPersonId(applicant);
            request1 = new EJBRequest();
            request1.setParam(Constants.PHONE_TYPE_MOBILE);
            PhoneType mobilePhoneType = personEJB.loadPhoneType(request1);
            cellPhoneApplicant.setPhoneTypeId(mobilePhoneType);
            cellPhoneApplicant = personEJB.savePhonePerson(cellPhoneApplicant);
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
            //addressType
            request1 = new EJBRequest();
            request1.setParam(Constants.ADDRESS_TYPE_DELIVERY);
            AddressType addressType = utilsEJB.loadAddressType(request1);
            //Guarda la direccion en BD
            addressApplicant.setCityId(cityAddress);
            addressApplicant.setCountryId(countryAddressApplicant);
            addressApplicant.setAddressLine1(street);
            addressApplicant.setAddressLine2(street2);
            addressApplicant.setNumber(number);
            addressApplicant.setZipZoneId(postalZone);
            addressApplicant.setAddressTypeId(addressType);
            addressApplicant = utilsEJB.saveAddress(addressApplicant);
            PersonHasAddress personHasAddress = new PersonHasAddress();
            personHasAddress.setAddressId(addressApplicant);
            personHasAddress.setPersonId(applicant);           
            personHasAddress = personEJB.savePersonHasAddress(personHasAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicantNatural;
    }

    @Override
    public List<ReviewOFAC> getReviewOFAC(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ReviewOFAC> getReviewOFACByApplicantByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ReviewOFAC> ReviewOFACList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REQUEST_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        ReviewOFACList = (List<ReviewOFAC>) getNamedQueryResult(ReviewOFAC.class, QueryConstants.REVIEW_OFAC_BY_APPLICANT_BY_REQUEST, request, getMethodName(), logger, "ReviewOFACList");
        return ReviewOFACList;
    }

    @Override
    public ReviewOFAC loadReviewOFAC(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReviewOFAC saveReviewOFAC(ReviewOFAC reviewOFAC) throws NullParameterException, GeneralException {
        if (reviewOFAC == null) {
            throw new NullParameterException("reviewOFAC", null);
        }
        return (ReviewOFAC) saveEntity(reviewOFAC);
    }

    //StatusRequest
    @Override
    public List<StatusRequest> getStatusRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StatusRequest loadStatusRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusRequest statusRequest = (StatusRequest) loadEntity(StatusRequest.class, request, logger, getMethodName());
        return statusRequest;
    }

    @Override
    public StatusRequest saveStatusRequest(StatusRequest statusRequest) throws NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //PlasticCustomizingRequest
    @Override
    public List<PlasticCustomizingRequest> getPlasticCustomizingRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PlasticCustomizingRequest> plasticCustomizingRequest = (List<PlasticCustomizingRequest>) listEntities(PlasticCustomizingRequest.class, request, logger, getMethodName());
        return plasticCustomizingRequest;
    }

    @Override
    public PlasticCustomizingRequest loadPlasticCustomizingRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PlasticCustomizingRequest plasticCustomizingRequest = (PlasticCustomizingRequest) loadEntity(PlasticCustomizingRequest.class, request, logger, getMethodName());
        return plasticCustomizingRequest;
    }

    @Override
    public PlasticCustomizingRequest savePlasticCustomizingRequest(PlasticCustomizingRequest plasticCustomizingRequest) throws NullParameterException, GeneralException {
        if (plasticCustomizingRequest == null) {
            throw new NullParameterException("plasticCustomizingRequest", null);
        }
        return (PlasticCustomizingRequest) saveEntity(plasticCustomizingRequest);
    }

    //PlastiCustomizingRequestHasCard
    @Override
    public List<PlastiCustomizingRequestHasCard> getPlastiCustomizingRequestHasCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PlastiCustomizingRequestHasCard> plastiCustomizingRequestHasCard = (List<PlastiCustomizingRequestHasCard>) listEntities(PlastiCustomizingRequestHasCard.class, request, logger, getMethodName());
        return plastiCustomizingRequestHasCard;
    }
    
    @Override
    public List<PlastiCustomizingRequestHasCard> getCardByPlastiCustomizingRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PlastiCustomizingRequestHasCard> PlastiCustomizingRequestHasCardList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PLASTIC_CUSTOMIZING_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PLASTIC_CUSTOMIZING_REQUEST_ID), null);
        }
        PlastiCustomizingRequestHasCardList = (List<PlastiCustomizingRequestHasCard>) getNamedQueryResult(PlastiCustomizingRequestHasCard.class, QueryConstants.CARD_BY_PLASTIC_CUSTOMIZING_REQUEST, request, getMethodName(), logger, "PlastiCustomizingRequestHasCardList");
        return PlastiCustomizingRequestHasCardList;
    }

    @Override
    public PlastiCustomizingRequestHasCard loadPlastiCustomizingRequestHasCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PlastiCustomizingRequestHasCard plastiCustomizingRequestHasCard = (PlastiCustomizingRequestHasCard) loadEntity(PlastiCustomizingRequestHasCard.class, request, logger, getMethodName());
        return plastiCustomizingRequestHasCard;
    }

    @Override
    public PlastiCustomizingRequestHasCard savePlastiCustomizingRequestHasCard(PlastiCustomizingRequestHasCard plastiCustomizingRequestHasCard) throws NullParameterException, GeneralException {
        if (plastiCustomizingRequestHasCard == null) {
            throw new NullParameterException("plastiCustomizingRequestHasCard", null);
        }
        return (PlastiCustomizingRequestHasCard) saveEntity(plastiCustomizingRequestHasCard);
    }

    //ResultPlasticCustomizingRequest
    @Override
    public List<ResultPlasticCustomizingRequest> getResultPlasticCustomizingRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ResultPlasticCustomizingRequest> resultPlasticCustomizingRequest = (List<ResultPlasticCustomizingRequest>) listEntities(ResultPlasticCustomizingRequest.class, request, logger, getMethodName());
        return resultPlasticCustomizingRequest;
    }

    @Override
    public ResultPlasticCustomizingRequest loadResultPlasticCustomizingRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ResultPlasticCustomizingRequest resultPlasticCustomizingRequest = (ResultPlasticCustomizingRequest) loadEntity(ResultPlasticCustomizingRequest.class, request, logger, getMethodName());
        return resultPlasticCustomizingRequest;
    }

    @Override
    public ResultPlasticCustomizingRequest saveResultPlasticCustomizingRequest(ResultPlasticCustomizingRequest resultPlasticCustomizingRequest) throws NullParameterException, GeneralException {
        if (resultPlasticCustomizingRequest == null) {
            throw new NullParameterException("resultPlasticCustomizingRequest", null);
        }
        return (ResultPlasticCustomizingRequest) saveEntity(resultPlasticCustomizingRequest);
    }

    //StatusResultPlasticCustomizing
    @Override
    public List<StatusResultPlasticCustomizing> getStatusResultPlasticCustomizing(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusResultPlasticCustomizing> statusResultPlasticCustomizing = (List<StatusResultPlasticCustomizing>) listEntities(StatusResultPlasticCustomizing.class, request, logger, getMethodName());
        return statusResultPlasticCustomizing;
    }

    @Override
    public List<StatusResultPlasticCustomizing> getStatusByDescription(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusResultPlasticCustomizing> statusResult = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(QueryConstants.PARAM_STATUS_DESCRIPTION)) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "position"), null);
        }
        statusResult = (List<StatusResultPlasticCustomizing>) getNamedQueryResult(StatusResultPlasticCustomizing.class, QueryConstants.STATUS_BY_DESCRIPTION, request, getMethodName(), logger, "statusResult");
        return statusResult;

    }
    
    @Override
    public List<StatusResultPlasticCustomizing> getStatusByPlasticManufacturer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusResultPlasticCustomizing> statusResult = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PLASTIC_MANUFACTURER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PLASTIC_MANUFACTURER_ID), null);            
        }
        statusResult = (List<StatusResultPlasticCustomizing>) getNamedQueryResult(StatusResultPlasticCustomizing.class, QueryConstants.STATUS_BY_PLASTIC_MANUFACTURER, request, getMethodName(), logger, "statusResult");
        return statusResult;
    }
    
    @Override
    public List<StatusResultPlasticCustomizing> getStatusByCardStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusResultPlasticCustomizing> statusResult = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARD_STATUS)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PLASTIC_MANUFACTURER_ID), null);            
        }
        statusResult = (List<StatusResultPlasticCustomizing>) getNamedQueryResult(StatusResultPlasticCustomizing.class, QueryConstants.STATUS_BY_CARD_STATUS, request, getMethodName(), logger, "statusResult");
        return statusResult;
    }
    
    @Override
    public StatusResultPlasticCustomizing loadStatusResultPlasticCustomizing(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusResultPlasticCustomizing statusResultPlasticCustomizing = (StatusResultPlasticCustomizing) loadEntity(StatusResultPlasticCustomizing.class, request, logger, getMethodName());
        return statusResultPlasticCustomizing;

    }

    @Override
    public StatusResultPlasticCustomizing saveStatusResultPlasticCustomizing(StatusResultPlasticCustomizing statusResultPlasticCustomizing) throws NullParameterException, GeneralException {
        if (statusResultPlasticCustomizing == null) {
            throw new NullParameterException("statusResultPlasticCustomizing", null);
        }
        return (StatusResultPlasticCustomizing) saveEntity(statusResultPlasticCustomizing);
    }

    //StatusPlasticCustomizingRequest
    @Override
    public List<StatusPlasticCustomizingRequest> getStatusPlasticCustomizingRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusPlasticCustomizingRequest> statusPlasticCustomizingRequest = (List<StatusPlasticCustomizingRequest>) listEntities(StatusPlasticCustomizingRequest.class, request, logger, getMethodName());
        return statusPlasticCustomizingRequest;
    }

    @Override
    public StatusPlasticCustomizingRequest loadStatusPlasticCustomizingRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusPlasticCustomizingRequest statusPlasticCustomizingRequest = (StatusPlasticCustomizingRequest) loadEntity(StatusPlasticCustomizingRequest.class, request, logger, getMethodName());
        return statusPlasticCustomizingRequest;
    }

    @Override
    public StatusPlasticCustomizingRequest saveStatusPlasticCustomizingRequest(StatusPlasticCustomizingRequest statusPlasticCustomizingRequest) throws NullParameterException, GeneralException {
        if (statusPlasticCustomizingRequest == null) {
            throw new NullParameterException("statusPlasticCustomizingRequest", null);
        }
        return (StatusPlasticCustomizingRequest) saveEntity(statusPlasticCustomizingRequest);
    }
    
}
