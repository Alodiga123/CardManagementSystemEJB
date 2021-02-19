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
import com.cms.commons.models.CardRenewalRequest;
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
import com.cms.commons.models.KeyProperties;
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
import com.cms.commons.models.Profile;
import com.cms.commons.models.ProfileData;
import com.cms.commons.models.ResponsibleNetworkReporting;
import com.cms.commons.models.Sequences;
import com.cms.commons.models.State;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.Transaction;
import com.cms.commons.models.User;
import com.cms.commons.models.UserHasProfile;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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
    
    @Override
    public Currency loadCurrencyByCountry(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Currency> currencyList = null;
        Currency currency = null;        
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        try {
            currencyList = (List<Currency>) getNamedQueryResult(Country.class, QueryConstants.CURRENCY_BY_COUNTRY, request, getMethodName(), logger, "currencyList");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "country"), null);
        }
        currency = currencyList.get(0);
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
    
    @Override
    public List<LegalPerson> getLegalPersonByPersonClassification(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> legalPersonList = null;        
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }
        legalPersonList = (List<LegalPerson>) getNamedQueryResult(LegalPerson.class, QueryConstants.LEGAL_PERSON_BY_PERSON_CLASSIFICATION, request, getMethodName(), logger, "legalPersonList");
        return legalPersonList;
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
    public BinSponsor loadBinSponsor(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
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

    @Override
    public Network searchNetwork(String name) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Network network = new Network();
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT n FROM Network n ");
            sqlBuilder.append("WHERE n.name LIKE '").append(name).append("'");
            network = (Network) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, Network.class.getSimpleName(), "loadNetworkByName", Network.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return network;
    }

    @Override
    public List<Network> searchNetworkByCountry(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<Network> network = null;
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT n FROM Network n ");
            sqlBuilder.append("WHERE n.countryId IN (SELECT c.id FROM Country WHERE c.name LIKE '").append(name).append("')");
            network = (List<Network>) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getResultList();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return network;
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
    
    @Override
    public List<PersonType> getPersonTypeByCountryByOriginApplication(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypes = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_ORIGIN_APPLICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ORIGIN_APPLICATION_ID), null);
        }
       
        personTypes = (List<PersonType>) getNamedQueryResult(PersonType.class, QueryConstants.PERSON_TYPE_BY_COUNTRY_BY_ORIGIN_APPLICATION, request, getMethodName(), logger, "personTypeByCountryByOriginApplication");
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
        if (!params.containsKey(EjbConstants.PARAM_ORIGIN_APPLICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ORIGIN_APPLICATION_ID), null);
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
    
    public List<EconomicActivity> SearchDescription(String description) throws EmptyListException, GeneralException, NullParameterException {
        List<EconomicActivity> economicList = null;
        if (description == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM economicActivity e ");
            sqlBuilder.append("WHERE e.description LIKE").append(description).append("'%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            economicList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return economicList;
    }
    
        public List<EconomicActivity> getSearchEconomicActivity(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<EconomicActivity> economicActivityList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT e FROM EconomicActivity e ");
            sqlBuilder.append("WHERE e.description LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            economicActivityList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return economicActivityList;
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
    
    public List<Address> getAddressesById(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException{
         List<Address> addressById = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_ADDRESS_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ADDRESS_ID), null);
        }
        addressById = (List<Address>) getNamedQueryResult(UtilsEJB.class, QueryConstants.PERSON_HAS_ADDRESS_BY_PERSON, request, getMethodName(), logger, "addressById");
        return addressById;
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
    
    public List<City> getSearchCity(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<City> cityList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT c FROM City c ");
            sqlBuilder.append("WHERE c.name LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            cityList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return cityList;
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
    
    @Override
    public List<LegalRepresentatives> getLegalRepresentativesByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalRepresentatives> legalRepresentatives = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        legalRepresentatives = (List<LegalRepresentatives>) getNamedQueryResult(LegalRepresentatives.class, QueryConstants.LEGAL_REPRESENTATIVES_BY_PERSON, request, getMethodName(), logger, "legalRepresentatives");
        return legalRepresentatives;
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

    //Profile    
    public List<Profile> getProfile(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Profile> profileList = (List<Profile>) listEntities(Profile.class, request, logger, getMethodName());
        return profileList;
    }

    public Profile loadProfile(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Profile profile = (Profile) loadEntity(Profile.class, request, logger, getMethodName());
        return profile;
    }

    public Profile saveProfile(Profile profile) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (profile == null) {
            throw new NullParameterException("profile", null);
        }
        return (Profile) saveEntity(profile);
    }

    //ProfileData
    @Override
    public List<ProfileData> getProfileData(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProfileData> profileDataList = (List<ProfileData>) listEntities(ProfileData.class, request, logger, getMethodName());
        return profileDataList;
    }

    @Override
    public ProfileData loadProfileData(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProfileData profileData = (ProfileData) loadEntity(ProfileData.class, request, logger, getMethodName());
        return profileData;
    }

    @Override
    public ProfileData saveProfileData(ProfileData profileData) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (profileData == null) {
            throw new NullParameterException("profileData", null);
        }
        return (ProfileData) saveEntity(profileData);
    }

    @Override
    public List<LegalPerson> getLegalPersonByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> legalPersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        legalPersonList = (List<LegalPerson>) getNamedQueryResult(LegalPerson.class, QueryConstants.LEGAL_PERSON_BY_PERSON, request, getMethodName(), logger, "legalPersonList");
        return legalPersonList;
    }

    @Override
    public Country searchCountry(String name) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Country country = new Country();
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT c FROM Country c ");
            sqlBuilder.append("WHERE c.name LIKE '%").append(name).append("%'");
            country = (Country) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, Country.class.getSimpleName(), "loadCountryByName", Country.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return country;
    }
    
    @Override
    public List<Country> getSearchCountry(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<Country> countryList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT c FROM Country c ");
            sqlBuilder.append("WHERE c.name LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            countryList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return countryList;
    }

    @Override
    public State searchState(String name) throws RegisterNotFoundException, NullParameterException, GeneralException {
        State state = new State();
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT s FROM State s ");
            sqlBuilder.append("WHERE s.name LIKE '").append(name).append("'");
            state = (State) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, State.class.getSimpleName(), "loadStateByName", State.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return state;
    }

    @Override
    public List<State> getSearchState(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<State> stateList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM state s WHERE s.name LIKE '%name%'");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        stateList = (List<State>) query.setHint("toplink.refresh", "true").getResultList();
        return stateList;
    }

    @Override
    public PersonClassification searchPersonClassification(String description) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonClassification personclassification = new PersonClassification();
        try {
            if (description == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "description"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT p FROM PersonClassification p ");
            sqlBuilder.append("WHERE p.description LIKE '").append(description).append("'");
            personclassification = (PersonClassification) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, PersonClassification.class.getSimpleName(), "loadPersonClassificationByDescription", PersonClassification.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return personclassification;
    }

    @Override
    public List<PersonClassification> getSearchPersonClassification(String description) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonClassification> personclassificationList = null;
        if (description == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "description"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM personClassification p WHERE p.description LIKE '%");
        sqlBuilder.append(description);
        sqlBuilder.append("%'");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), PersonClassification.class);
        personclassificationList = (List<PersonClassification>) query.setHint("toplink.refresh", "true").getResultList();
        return personclassificationList;
    }

    @Override
    public Currency searchCurrency(String name) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Currency currency = new Currency();
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT c FROM Currency c ");
            sqlBuilder.append("WHERE c.name LIKE '").append(name).append("'");
            currency = (Currency) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, Currency.class.getSimpleName(), "loadCurrencyByName", Currency.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return currency;
    }


    @Override
    public List<Currency> getSearchCurrency(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<Currency> currencyList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM currency c ");
            sqlBuilder.append("WHERE c.name LIKE '%").append(name).append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(),Currency.class);
            currencyList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return currencyList;
    }

    @Override
    public RequestType searchRequestType(String description) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RequestType requestType = new RequestType();
        try {
            if (description == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "description"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT r FROM RequestType r ");
            sqlBuilder.append("WHERE r.description LIKE '").append(description).append("'");
            requestType = (RequestType) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, RequestType.class.getSimpleName(), "loadRequestTypeByDescription", RequestType.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return requestType;
    }

    @Override
    public List<RequestType> getSearchRequestType(String description) throws EmptyListException, GeneralException, NullParameterException {
        List<RequestType> requestsTypeList = null;
        if (description == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "description"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM requestType r WHERE r.description LIKE '%description%'");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        requestsTypeList = (List<RequestType>) query.setHint("toplink.refresh", "true").getResultList();
        return requestsTypeList;
    }

    @Override
    public List<UserHasProfile> getUserHasProfile(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<UserHasProfile> userHasProfileList = (List<UserHasProfile>) listEntities(UserHasProfile.class, request, logger, getMethodName());
        return userHasProfileList;
    }

    @Override
    public UserHasProfile saveUserHasProfile(UserHasProfile userHasProfile) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (userHasProfile == null) {
            throw new NullParameterException("userHasProfile", null);
        }
        return (UserHasProfile) saveEntity(userHasProfile);
    }

    @Override
    public UserHasProfile loadUserHasProfile(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserHasProfile> getUserHasProfileByUser(UserHasProfile userHasProfile) throws EmptyListException, GeneralException, NullParameterException {
        List<UserHasProfile> userHasProfileList = null;
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM user_has_profile where userId=")
                .append(userHasProfile.getUserId().getId().toString())
                .append(" and profileId=")
                .append(userHasProfile.getProfileId().getId().toString());
        try {
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            userHasProfileList = (List<UserHasProfile>) query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return userHasProfileList;
    }

    //PersonType
    @Override
    public List<PersonType> getPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypeList = (List<PersonType>) listEntities(PersonType.class, request, logger, getMethodName());
        return personTypeList;
    }
    
    
    //DocumentsPersonType
    @Override
    public DocumentsPersonType searchDocumentsPersonType(String description) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DocumentsPersonType documentsPersonType = new DocumentsPersonType();
        try {
            if (description == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "description"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT d FROM DocumentsPersonType d ");
            sqlBuilder.append("WHERE d.description LIKE '").append(description).append("'");
            documentsPersonType = (DocumentsPersonType) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, DocumentsPersonType.class.getSimpleName(), "loadDocumentsPersonTypeByDescription", DocumentsPersonType.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return documentsPersonType;
    }

    @Override
    public List<DocumentsPersonType> getSearchDocumentsPersonType(String description) throws EmptyListException, GeneralException, NullParameterException {
        List<DocumentsPersonType> documentsPersonTypeList = null;
        if (description == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "description"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM documentsPersonType d ");
        sqlBuilder.append("WHERE d.description LIKE '%").append(description).append("%'");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(),DocumentsPersonType.class);
        documentsPersonTypeList = (List<DocumentsPersonType>) query.setHint("toplink.refresh", "true").getResultList();
        return documentsPersonTypeList;
    }
    
     //Transaction
    @Override
    public List<Transaction> getTransaction(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Transaction> transactions = (List<Transaction>) listEntities(Transaction.class, request, logger, getMethodName());
        return transactions;
    }
    
    //KeyProperties
    @Override
    public List<KeyProperties> getKeyProperties(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<KeyProperties> keyPropertiesList = (List<KeyProperties>) listEntities(KeyProperties.class, request, logger, getMethodName());
        return keyPropertiesList;
    }

    @Override
    public KeyProperties saveKeyProperties(KeyProperties keyProperties) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (keyProperties == null) {
            throw new NullParameterException("keyProperties", null);
        }
        return (KeyProperties) saveEntity(keyProperties);
    }

    @Override
    public KeyProperties loadKeyProperties(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<KeyProperties> getKeyPropertiesByChannelAndProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<KeyProperties> keyPropertiesList= null; 
               
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }  
        
        if (!params.containsKey(EjbConstants.PARAM_CHANNEL_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_NAME), null);
        }  
        
          try {
            StringBuilder sqlBuilder = new StringBuilder("select * from keyProperties where channelId =");
            sqlBuilder.append(params.get(EjbConstants.PARAM_CHANNEL_ID));
            sqlBuilder.append(" AND productId =");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PRODUCT_ID));
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), KeyProperties.class);
            keyPropertiesList = (List<KeyProperties>) query.setHint("toplink.refresh", "true").getResultList();                 
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return keyPropertiesList;   
    }
    
    @Override
    public List<KeyProperties> getSearchKeyPropertiesByChannel(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<KeyProperties> keyProperties = null;
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }

            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM keyProperties k ");
            sqlBuilder.append("WHERE k.channelId IN (SELECT c.id FROM channel c WHERE c.name LIKE '").append(name).append("%')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), KeyProperties.class);
            keyProperties = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return keyProperties;
    }
    
    public List<KeyProperties> getSearchKeyPropertiesByProduct(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<KeyProperties> keyProperties = null;
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }

            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM keyProperties k ");
            sqlBuilder.append("WHERE k.productId IN (SELECT p.id FROM product p WHERE p.name LIKE '").append(name).append("%')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), KeyProperties.class);
            keyProperties = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return keyProperties;
    }
}
