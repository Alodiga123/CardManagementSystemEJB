package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.ejb.UtilsEJBLocal;
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
import com.cms.commons.models.BinSponsor;
import com.cms.commons.models.CardIssuanceType;
import com.cms.commons.models.CardStatus;
import com.cms.commons.models.City;
import com.cms.commons.models.StatusRequest;
import com.cms.commons.models.Country;
import com.cms.commons.models.Currency;
import com.cms.commons.models.Network;
import com.cms.commons.models.PersonClassification;
import com.cms.commons.models.ProgramHasNetwork;
import com.cms.commons.models.ProgramType;
import com.cms.commons.models.RequestType;
import com.cms.commons.models.SourceFunds;
import com.cms.commons.models.CollectionsRequest;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.EconomicActivity;
import com.cms.commons.models.Issuer;
import com.cms.commons.models.EdificationType;
import com.cms.commons.models.KindCard;
import com.cms.commons.models.Language;
import com.cms.commons.models.LegalPerson;
import com.cms.commons.models.LegalRepresentatives;
import com.cms.commons.models.OriginApplication;
import com.cms.commons.models.Permission;
import com.cms.commons.models.PermissionData;
import com.cms.commons.models.PermissionGroup;
import com.cms.commons.models.PermissionGroupData;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.ResponsibleNetworkReporting;
import com.cms.commons.models.Sequences;
import com.cms.commons.models.State;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;

/**
 * @author Jesus Gomez
 * @since 28/10/2019
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.UTILS_EJB, mappedName = EjbConstants.UTILS_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class UtilsEJBImp extends AbstractDistributionEJB implements UtilsEJBLocal, UtilsEJB {

    private static final Logger logger = Logger.getLogger(UtilsEJBImp.class);

    //RequestType
    @Override
    public List<RequestType> getRequestType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RequestType> requestsTypeList = (List<RequestType>) listEntities(RequestType.class, request, logger, getMethodName());
        return requestsTypeList;
    }

    @Override
    public RequestType loadRequestType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RequestType requestType = (RequestType) loadEntity(RequestType.class, request, logger, getMethodName());
        return requestType;
    }

    @Override
    public RequestType saveRequestType(RequestType requestType) throws NullParameterException, GeneralException {
        if (requestType == null) {
            throw new NullParameterException("requestType", null);
        }
        return (RequestType) saveEntity(requestType);
    }

    //Country
    @Override
    public List<Country> getCountries(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Country> countryList = (List<Country>) listEntities(Country.class, request, logger, getMethodName());
        return countryList;
    }

    @Override
    public Country loadCountry(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Country country = (Country) loadEntity(Country.class, request, logger, getMethodName());
        return country;
    }

    @Override
    public Country saveCountry(Country country) throws NullParameterException, GeneralException {
        if (country == null) {
            throw new NullParameterException("country", null);
        }
        return (Country) saveEntity(country);
    }

    //Status Request
    @Override
    public List<StatusRequest> getStatusRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusRequest> statusRequest = (List<StatusRequest>) listEntities(StatusRequest.class, request, logger, getMethodName());
        return statusRequest;
    }

    @Override
    public StatusRequest loadStatusRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusRequest statusRequest = (StatusRequest) loadEntity(StatusRequest.class, request, logger, getMethodName());
        return statusRequest;

    }

    @Override
    public StatusRequest saveStatusRequest(StatusRequest statusRequest) throws NullParameterException, GeneralException {
        if (statusRequest == null) {
            throw new NullParameterException("requestType", null);
        }
        return (StatusRequest) saveEntity(statusRequest);
    }

    //CardStatus
    @Override
    public List<CardStatus> getCardStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardStatus> cardStatus = (List<CardStatus>) listEntities(CardStatus.class, request, logger, getMethodName());
        return cardStatus;
    }

    @Override
    public CardStatus loadCardStatus(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardStatus cardStatus = (CardStatus) loadEntity(CardStatus.class, request, logger, getMethodName());
        return cardStatus;
    }

    @Override
    public CardStatus saveCardStatus(CardStatus cardStatus) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardStatus == null) {
            throw new NullParameterException("requestType", null);
        }
        return (CardStatus) saveEntity(cardStatus);
    }

    //Currency
    @Override
    public Currency saveCurrency(Currency currency) throws NullParameterException, GeneralException {
        if (currency == null) {
            throw new NullParameterException("requestType", null);
        }
        return (Currency) saveEntity(currency);
    }

    @Override
    public List<Currency> getCurrency(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Currency> currency = (List<Currency>) listEntities(Currency.class, request, logger, getMethodName());
        return currency;
    }

    @Override
    public Currency loadCurrency(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Currency currency = (Currency) loadEntity(Currency.class, request, logger, getMethodName());
        return currency;
    }

    //PersonClassification
    @Override
    public List<PersonClassification> getPersonClassification(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonClassification> personclassification = (List<PersonClassification>) listEntities(PersonClassification.class, request, logger, getMethodName());
        return personclassification;
    }

    @Override
    public PersonClassification loadPersonClassification(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonClassification personclassification = (PersonClassification) loadEntity(PersonClassification.class, request, logger, getMethodName());
        return personclassification;

    }
    @Override
    public PersonClassification savePersonClassification(PersonClassification personclassification) throws NullParameterException, GeneralException {
        if (personclassification == null) {
            throw new NullParameterException("requestType", null);
        }
        return (PersonClassification) saveEntity(personclassification);
    }

    

    //ProductType
    @Override
    public List<ProductType> getProductTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductType> productTypes = (List<ProductType>) listEntities(ProductType.class, request, logger, getMethodName());
        return productTypes;
    }

    @Override
    public ProductType loadProductType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProductType productType = (ProductType) loadEntity(ProductType.class, request, logger, getMethodName());
        return productType;
    }

    @Override
    public ProductType saveProductType(ProductType productType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (productType == null) {
            throw new NullParameterException("productType", null);
        }
        return (ProductType) saveEntity(productType);
    }

    //State
    @Override
    public List<State> getState(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<State> state = (List<State>) listEntities(State.class, request, logger, getMethodName());
        return state;
    }

    @Override
    public List<State> getStatesByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<State> states = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        states = (List<State>) getNamedQueryResult(UtilsEJB.class, QueryConstants.STATES_BY_COUNTRY, request, getMethodName(), logger, "states");
        return states;
    }

    @Override
    public State loadState(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        State state = (State) loadEntity(State.class, request, logger, getMethodName());
        return state;
    }

    @Override
    public State saveState(State state) throws NullParameterException, GeneralException {
        if (state == null) {
            throw new NullParameterException("state", null);
        }
        return (State) saveEntity(state);
    }

    //ProgramType
    @Override
    public List<ProgramType> getProgramType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramType> programType = (List<ProgramType>) listEntities(ProgramType.class, request, logger, getMethodName());
        return programType;
    }

    @Override
    public ProgramType loadProgramType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProgramType programType = (ProgramType) loadEntity(ProgramType.class, request, logger, getMethodName());
        return programType;
    }

    @Override
    public ProgramType saveProgramType(ProgramType programType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (programType == null) {
            throw new NullParameterException("requestType", null);
        }
        return (ProgramType) saveEntity(programType);
    }

    //BinSponsor
    @Override
    public List<BinSponsor> getBinSponsor(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<BinSponsor> binSponsor = (List<BinSponsor>) listEntities(BinSponsor.class, request, logger, getMethodName());
        return binSponsor;
    }

    @Override
    public BinSponsor loadBinSponsore(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        BinSponsor binSponsor = (BinSponsor) loadEntity(BinSponsor.class, request, logger, getMethodName());
        return binSponsor;
    }

    @Override
    public BinSponsor saveBinSponsor(BinSponsor binsponsor) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (binsponsor == null) {
            throw new NullParameterException("requestType", null);
        }
        return (BinSponsor) saveEntity(binsponsor);
    }

    //SourceFunds
    @Override
    public List<SourceFunds> getSourceFunds(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SourceFunds> sourceFunds = (List<SourceFunds>) listEntities(SourceFunds.class, request, logger, getMethodName());
        return sourceFunds;
    }

    @Override
    public SourceFunds loadSourceFunds(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SourceFunds sourceFunds = (SourceFunds) loadEntity(SourceFunds.class, request, logger, getMethodName());
        return sourceFunds;
    }

    @Override
    public SourceFunds saveSourceFunds(SourceFunds sourceFunds) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (sourceFunds == null) {
            throw new NullParameterException("requestType", null);
        }
        return (SourceFunds) saveEntity(sourceFunds);
    }

    //CardIssuanceType
    @Override
    public List<CardIssuanceType> getCardIssuanceTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardIssuanceType> cardIssuanceTypes = (List<CardIssuanceType>) listEntities(CardIssuanceType.class, request, logger, getMethodName());
        return cardIssuanceTypes;
    }

    @Override
    public CardIssuanceType loadCardIssuanceType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardIssuanceType cardIssuanceType = (CardIssuanceType) loadEntity(CardIssuanceType.class, request, logger, getMethodName());
        return cardIssuanceType;
    }

    @Override
    public CardIssuanceType saveCardIssuanceType(CardIssuanceType cardIssuanceType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardIssuanceType == null) {
            throw new NullParameterException("requestType", null);
        }
        return (CardIssuanceType) saveEntity(cardIssuanceType);
    }

    //Network
    @Override
    public List<Network> getNetworks(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Network> networks = (List<Network>) listEntities(Network.class, request, logger, getMethodName());
        return networks;
    }

    @Override
    public List<Network> getNetworkByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Network> networks = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        networks = (List<Network>) getNamedQueryResult(UtilsEJB.class, QueryConstants.NETWORK_BY_COUNTRY, request, getMethodName(), logger, "networks");
        return networks;
    }

    @Override
    public Network loadNetwork(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Network network = (Network) loadEntity(Network.class, request, logger, getMethodName());
        return network;
    }

    @Override
    public Network saveNetwork(Network network) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (network == null) {
            throw new NullParameterException("requestType", null);
        }
        return (Network) saveEntity(network);
    }

    //ProgramHasNetwork
    @Override
    public List<ProgramHasNetwork> getProgramHasNetwork(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramHasNetwork> programHasNetwork = (List<ProgramHasNetwork>) listEntities(ProgramHasNetwork.class, request, logger, getMethodName());
        return programHasNetwork;
    }

    @Override
    public List<ProgramHasNetwork> getProgramHasNetworkByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramHasNetwork> programHasNetwork = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        programHasNetwork = (List<ProgramHasNetwork>) getNamedQueryResult(UtilsEJB.class, QueryConstants.NETWORK_BY_PROGRAM, request, getMethodName(), logger, "programHasNetwork");
        return programHasNetwork;
    }

    @Override
    public ProgramHasNetwork loadProgramHasNetwork(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProgramHasNetwork programHasNetwork = (ProgramHasNetwork) loadEntity(ProgramHasNetwork.class, request, logger, getMethodName());
        return programHasNetwork;
    }

    @Override
    public ProgramHasNetwork saveProgramHasNetwork(ProgramHasNetwork programHasNetwork) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (programHasNetwork == null) {
            throw new NullParameterException("requestType", null);
        }
        return (ProgramHasNetwork) saveEntity(programHasNetwork);
    }

    //PersonType
    @Override
    public List<PersonType> getPersonTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypes = (List<PersonType>) listEntities(PersonType.class, request, logger, getMethodName());
        return personTypes;
    }

    @Override
    public PersonType loadPersonType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonType personTypes = (PersonType) loadEntity(PersonType.class, request, logger, getMethodName());
        return personTypes;
    }

    @Override
    public PersonType savePersonType(PersonType personType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (personType == null) {
            throw new NullParameterException("personType", null);
        }
        return (PersonType) saveEntity(personType);
    }

    @Override
    public List<PersonType> getPersonTypeByCountryByIndNaturalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypes = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_ORIGIN_APPLICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ORIGIN_APPLICATION_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_IND_NATURAL_PERSON)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_IND_NATURAL_PERSON), null);
        }
        personTypes = (List<PersonType>) getNamedQueryResult(PersonType.class, QueryConstants.PERSON_TYPE_BY_COUNTRY_BY_IND_NATURAL_PERSON, request, getMethodName(), logger, "personTypes");
        return personTypes;
    }
    
    @Override
    public List<PersonType> getPersonTypeByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypes = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_ORIGIN_APPLICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ORIGIN_APPLICATION_ID), null);
        }
        personTypes = (List<PersonType>) getNamedQueryResult(PersonType.class, QueryConstants.PERSON_TYPE_BY_COUNTRY, request, getMethodName(), logger, "personTypes");
        return personTypes;
    }

    //LegalPerson
    @Override
    public List<LegalPerson> getLegalPersons(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> legalPersons = (List<LegalPerson>) listEntities(LegalPerson.class, request, logger, getMethodName());
        return legalPersons;
    }

    @Override
    public LegalPerson loadLegalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalPerson legalPersons = (LegalPerson) loadEntity(LegalPerson.class, request, logger, getMethodName());
        return legalPersons;
    }

    @Override
    public LegalPerson saveLegalPerson(LegalPerson legalPersons) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalPersons == null) {
            throw new NullParameterException("legalPersons", null);
        }
        return (LegalPerson) saveEntity(legalPersons);
    }

    //DocumentsPersonType
    @Override
    public List<DocumentsPersonType> getDocumentsPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DocumentsPersonType> documentsPersonTypeList = (List<DocumentsPersonType>) listEntities(DocumentsPersonType.class, request, logger, getMethodName());
        return documentsPersonTypeList;
    }

    @Override
    public List<DocumentsPersonType> getDocumentsPersonByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DocumentsPersonType> documentsPersonType = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_IND_NATURAL_PERSON)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_IND_NATURAL_PERSON), null);
        }
        documentsPersonType = (List<DocumentsPersonType>) getNamedQueryResult(DocumentsPersonType.class, QueryConstants.DOCUMENTS_BY_COUNTRY, request, getMethodName(), logger, "documentsPersonType");
        return documentsPersonType;
    }

    @Override
    public DocumentsPersonType loadDocumentsPersonType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DocumentsPersonType documentsPersonType = (DocumentsPersonType) loadEntity(DocumentsPersonType.class, request, logger, getMethodName());
        return documentsPersonType;
    }

    @Override
    public DocumentsPersonType saveDocumentsPersonType(DocumentsPersonType documentsPersonType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (documentsPersonType == null) {
            throw new NullParameterException("documentsPersonType", null);
        }
        return (DocumentsPersonType) saveEntity(documentsPersonType);
    }

    //EconomicActivity
    @Override
    public List<EconomicActivity> getEconomicActivitys(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<EconomicActivity> economicActivitys = (List<EconomicActivity>) listEntities(EconomicActivity.class, request, logger, getMethodName());
        return economicActivitys;
    }

    @Override
    public EconomicActivity loadEconomicActivity(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        EconomicActivity economicActivity = (EconomicActivity) loadEntity(EconomicActivity.class, request, logger, getMethodName());
        return economicActivity;
    }

    @Override
    public EconomicActivity saveEconomicActivity(EconomicActivity economicActivity) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (economicActivity == null) {
            throw new NullParameterException("economicActivity", null);
        }
        return (EconomicActivity) saveEntity(economicActivity);
    }

    //Issuer
    @Override
    public List<Issuer> getIssuers(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Issuer> issuers = (List<Issuer>) listEntities(Issuer.class, request, logger, getMethodName());
        return issuers;
    }

    @Override
    public Issuer loadIssuer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Issuer issuer = (Issuer) loadEntity(Issuer.class, request, logger, getMethodName());
        return issuer;
    }

    @Override
    public Issuer saveIssuer(Issuer issuer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (issuer == null) {
            throw new NullParameterException("issuer", null);
        }
        return (Issuer) saveEntity(issuer);
    }

    //ResponsibleNetworkReporting
    @Override
    public List<ResponsibleNetworkReporting> getResponsibleNetworkReportings(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ResponsibleNetworkReporting> responsibleNetworkReportings = (List<ResponsibleNetworkReporting>) listEntities(ResponsibleNetworkReporting.class, request, logger, getMethodName());
        return responsibleNetworkReportings;
    }

    @Override
    public ResponsibleNetworkReporting loadResponsibleNetworkReporting(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ResponsibleNetworkReporting responsibleNetworkReporting = (ResponsibleNetworkReporting) loadEntity(ResponsibleNetworkReporting.class, request, logger, getMethodName());
        return responsibleNetworkReporting;
    }

    @Override
    public ResponsibleNetworkReporting saveResponsibleNetworkReporting(ResponsibleNetworkReporting responsibleNetworkReporting) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (responsibleNetworkReporting == null) {
            throw new NullParameterException("responsibleNetworkReporting", null);
        }
        return (ResponsibleNetworkReporting) saveEntity(responsibleNetworkReporting);
    }

    //Address
    @Override
    public List<Address> getAddresses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Address> addresses = (List<Address>) listEntities(Address.class, request, logger, getMethodName());
        return addresses;
    }

    @Override
    public Address loadAddress(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Address address = (Address) loadEntity(Address.class, request, logger, getMethodName());
        return address;
    }

    @Override
    public Address saveAddress(Address address) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (address == null) {
            throw new NullParameterException("address", null);
        }
        return (Address) saveEntity(address);
    }

    //EdificationType
    @Override
    public List<EdificationType> getEdificationTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<EdificationType> edificationTypes = (List<EdificationType>) listEntities(EdificationType.class, request, logger, getMethodName());
        return edificationTypes;
    }

    @Override
    public EdificationType loadEdificationType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        EdificationType edificationType = (EdificationType) loadEntity(EdificationType.class, request, logger, getMethodName());
        return edificationType;
    }

    @Override
    public EdificationType saveEdificationType(EdificationType edificationType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (edificationType == null) {
            throw new NullParameterException("edificationType", null);
        }
        return (EdificationType) saveEntity(edificationType);
    }

    //City
    @Override
    public List<City> getCitys(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<City> city = (List<City>) listEntities(City.class, request, logger, getMethodName());
        return city;
    }

    @Override
    public List<City> getCitiesByState(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<City> cities = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_STATE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_STATE_ID), null);
        }
        cities = (List<City>) getNamedQueryResult(UtilsEJB.class, QueryConstants.CITIES_BY_STATE, request, getMethodName(), logger, "cities");
        return cities;
    }

    @Override
    public City loadCity(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        City city = (City) loadEntity(City.class, request, logger, getMethodName());
        return city;
    }

    @Override
    public City saveCity(City city) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (city == null) {
            throw new NullParameterException("city", null);
        }
        return (City) saveEntity(city);
    }

    //StreetType
    @Override
    public List<StreetType> getStreetTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StreetType> streetTypes = (List<StreetType>) listEntities(StreetType.class, request, logger, getMethodName());
        return streetTypes;
    }

    @Override
    public StreetType loadStreetType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StreetType streetType = (StreetType) loadEntity(StreetType.class, request, logger, getMethodName());
        return streetType;
    }

    @Override
    public StreetType saveStreetType(StreetType streetType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (streetType == null) {
            throw new NullParameterException("streetType", null);
        }
        return (StreetType) saveEntity(streetType);
    }

    //ZipZone
    @Override
    public List<ZipZone> getZipZones(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ZipZone> zipZones = (List<ZipZone>) listEntities(ZipZone.class, request, logger, getMethodName());
        return zipZones;
    }

    @Override
    public List<ZipZone> getZipZoneByCities(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ZipZone> zipZones = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CITY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CITY_ID), null);
        }
        zipZones = (List<ZipZone>) getNamedQueryResult(UtilsEJB.class, QueryConstants.ZIPZONE_BY_CITY, request, getMethodName(), logger, "zipZones");
        return zipZones;
    }

    @Override
    public ZipZone loadZipZone(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ZipZone zipZone = (ZipZone) loadEntity(ZipZone.class, request, logger, getMethodName());
        return zipZone;
    }

    @Override
    public ZipZone saveZipZone(ZipZone zipZone) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (zipZone == null) {
            throw new NullParameterException("zipZone", null);
        }
        return (ZipZone) saveEntity(zipZone);
    }

    //AddressType
    @Override
    public List<AddressType> getAddressType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AddressType> addressType = (List<AddressType>) listEntities(AddressType.class, request, logger, getMethodName());
        return addressType;
    }

    @Override
    public AddressType loadAddressType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        AddressType addressType = (AddressType) loadEntity(AddressType.class, request, logger, getMethodName());
        return addressType;
    }

    @Override
    public AddressType saveAddressType(AddressType addressType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (addressType == null) {
            throw new NullParameterException("addressType", null);
        }
        return (AddressType) saveEntity(addressType);
    }

    //LegalRepresentatives
    @Override
    public List<LegalRepresentatives> getLegalRepresentativeses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalRepresentatives> legalRepresentativeses = (List<LegalRepresentatives>) listEntities(LegalRepresentatives.class, request, logger, getMethodName());
        return legalRepresentativeses;
    }

    @Override
    public LegalRepresentatives loadLegalRepresentatives(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalRepresentatives legalRepresentatives = (LegalRepresentatives) loadEntity(LegalRepresentatives.class, request, logger, getMethodName());
        return legalRepresentatives;
    }

    @Override
    public LegalRepresentatives saveLegalRepresentatives(LegalRepresentatives legalRepresentatives) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalRepresentatives == null) {
            throw new NullParameterException("legalRepresentatives", null);
        }
        return (LegalRepresentatives) saveEntity(legalRepresentatives);
    }

    //Sequences
    @Override
    public List<Sequences> getSequences(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Sequences> sequences = (List<Sequences>) listEntities(Sequences.class, request, logger, getMethodName());
        return sequences;

    }

    @Override
    public Sequences loadSequences(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Sequences sequence = (Sequences) loadEntity(Sequences.class, request, logger, getMethodName());
        return sequence;
    }

    @Override
    public Sequences saveSequences(Sequences sequence) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (sequence == null) {
            throw new NullParameterException("sequence", null);
        }
        return (Sequences) saveEntity(sequence);
    }

    @Override
    public List<Sequences> getSequencesByDocumentType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Sequences> sequence = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_DOCUMENT_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_DOCUMENT_TYPE_ID), null);
        }
        sequence = (List<Sequences>) getNamedQueryResult(UtilsEJB.class, QueryConstants.SEQUENCES_BY_DOCUMENT_TYPE, request, getMethodName(), logger, "sequence");
        return sequence;
    }

    @Override
    public String generateNumberSequence(List<Sequences> sequence, int originApplication) throws GeneralException, RegisterNotFoundException, NullParameterException {
        int numberSequence = 0;
        String prefixNumberSequence = "";
        String acronym = "";
        for (Sequences s : sequence) {
            if (s.getOriginApplicationId().getId() == originApplication) {
                if (s.getCurrentValue() > 1) {
                    numberSequence = s.getCurrentValue();
                } else {
                    numberSequence = s.getInitialValue();
                }
                acronym = s.getDocumentTypeId().getAcronym();
                s.setCurrentValue(s.getCurrentValue() + 1);
                saveSequences(s);
            }
        }
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        switch (originApplication) {
            case Constants.ORIGIN_APPLICATION_CMS_ID:
                prefixNumberSequence = "CMS-";
                break;
            case Constants.ORIGIN_APPLICATION_WALLET_ID:
                prefixNumberSequence = "APP-";
                break;
            default:
                break;
        }
        prefixNumberSequence = prefixNumberSequence.concat(acronym);
        String suffixNumberSequence = "-";
        suffixNumberSequence = suffixNumberSequence.concat(String.valueOf(year));
        String numberSequenceDoc = prefixNumberSequence;
        numberSequenceDoc = numberSequenceDoc.concat("-");
        numberSequenceDoc = numberSequenceDoc.concat(String.valueOf(numberSequence));
        numberSequenceDoc = numberSequenceDoc.concat(suffixNumberSequence);
        return numberSequenceDoc;
    }

    //OriginApplication
    @Override
    public List<OriginApplication> getOriginApplication(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<OriginApplication> originApplication = (List<OriginApplication>) listEntities(OriginApplication.class, request, logger, getMethodName());
        return originApplication;
    }

    @Override
    public OriginApplication loadOriginApplication(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OriginApplication saveOriginApplication(OriginApplication originApplication) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //KindCard
    @Override
    public List<KindCard> getKindCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<KindCard> kindCardList = (List<KindCard>) listEntities(KindCard.class, request, logger, getMethodName());
        return kindCardList;
    }

    @Override
    public KindCard loadKindCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KindCard saveKindCard(KindCard kindCard) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //PermissionGroup
    @Override
    public List<PermissionGroup> getPermissionGroup(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<PermissionGroup> permissionGroupList = (List<PermissionGroup>) listEntities(PermissionGroup.class, request, logger, getMethodName());
        return permissionGroupList;
    }

    @Override
    public PermissionGroup loadPermissionGroup(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PermissionGroup permissionGroup = (PermissionGroup) loadEntity(PermissionGroup.class, request, logger, getMethodName());
        return permissionGroup;
    }

    @Override
    public PermissionGroup savePermissionGroup(PermissionGroup permissionGroup) throws RegisterNotFoundException, NullParameterException, GeneralException {
         if (permissionGroup == null) {
            throw new NullParameterException("permissionGroup", null);
        }
        return (PermissionGroup) saveEntity(permissionGroup);
    }
    
    
    //PermissionGroupData
    @Override
    public List<PermissionGroupData> getPermissionGroupData(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PermissionGroupData> permissionGroupDataList = (List<PermissionGroupData>) listEntities(PermissionGroupData.class, request, logger, getMethodName());
        return permissionGroupDataList;
    }
    
    @Override
    public PermissionGroupData loadPermissionGroupData(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PermissionGroupData permissionGroupData = (PermissionGroupData) loadEntity(PermissionGroupData.class, request, logger, getMethodName());
        return permissionGroupData;
    }

    @Override
    public PermissionGroupData savePermissionGroupData(PermissionGroupData permissionGroupData) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (permissionGroupData == null) {
            throw new NullParameterException("permissionGroupData", null);
        }
        return (PermissionGroupData) saveEntity(permissionGroupData);
    }
    
    //Language
    @Override
    public List<Language> getLanguage(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Language> languageList = (List<Language>) listEntities(Language.class, request, logger, getMethodName());
        return languageList;
    }

    @Override
    public Language loadLanguage(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Language language = (Language) loadEntity(Language.class, request, logger, getMethodName());
        return language;
    }

    @Override
    public Language saveLanguage(Language language) throws RegisterNotFoundException, NullParameterException, GeneralException {
         if (language == null) {
            throw new NullParameterException("language", null);
        }
        return (Language) saveEntity(language);
    }

    //Permission
    @Override
    public List<Permission> getPermission(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Permission> permissionList = (List<Permission>) listEntities(Permission.class, request, logger, getMethodName());
        return permissionList;
    }

    @Override
    public Permission loadPermission(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Permission permission = (Permission) loadEntity(Permission.class, request, logger, getMethodName());
        return permission;
    }

    @Override
    public Permission savePermission(Permission permission) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (permission == null) {
            throw new NullParameterException("permission", null);
        }
        return (Permission) saveEntity(permission);
    }
    
    //PermissionData
    @Override
    public List<PermissionData> getPermissionData(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PermissionData> permissionDataList = (List<PermissionData>) listEntities(PermissionData.class, request, logger, getMethodName());
        return permissionDataList;
    }

    @Override
    public PermissionData loadPermissionData(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PermissionData permission = (PermissionData) loadEntity(PermissionData.class, request, logger, getMethodName());
        return permission;
    }

    @Override
    public PermissionData savePermissionData(PermissionData permissionData) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (permissionData == null) {
            throw new NullParameterException("permissionData", null);
        }
        return (PermissionData) saveEntity(permissionData);
    }

}    
