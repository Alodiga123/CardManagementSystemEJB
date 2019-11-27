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
import com.cms.commons.models.BinSponsor;
import com.cms.commons.models.CardIssuanceType;
import com.cms.commons.models.CardRequestType;
import com.cms.commons.models.CardStatus;
import com.cms.commons.models.CardType;
import com.cms.commons.models.City;
import com.cms.commons.models.StatusRequest;
import com.cms.commons.models.Country;
import com.cms.commons.models.Currency;
import com.cms.commons.models.Network;
import com.cms.commons.models.PersonClassification;
import com.cms.commons.models.Product;
import com.cms.commons.models.ProgramHasNetwork;
import com.cms.commons.models.ProgramType;
import com.cms.commons.models.RequestType;
import com.cms.commons.models.SourceFunds;
import com.cms.commons.models.CollectionsRequest;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.EconomicActivity;
import com.cms.commons.models.Issuer;
import com.cms.commons.models.EdificationType;
import com.cms.commons.models.LegalPerson;
import com.cms.commons.models.Person;
import com.cms.commons.models.LegalRepresentatives;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.Request;
import com.cms.commons.models.ResponsibleNetworkReporting;
import com.cms.commons.models.State;
import com.cms.commons.models.StreetType;
import com.cms.commons.models.ZipZone;
import com.cms.commons.util.EjbConstants;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;

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

    @Override
    public List<CardRequestType> getCardRequestType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRequestType> cardRequestsTypeList = (List<CardRequestType>) listEntities(CardRequestType.class, request, logger, getMethodName());
        return cardRequestsTypeList;
    }

    @Override
    public CardRequestType loadCardRequestType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardRequestType cardRequestType = (CardRequestType) loadEntity(CardRequestType.class, request, logger, getMethodName());
        return cardRequestType;
    }

    @Override
    public CardRequestType saveCardRequestType(CardRequestType cardRequestType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardRequestType == null) {
            throw new NullParameterException("cardRequestType", null);
        }
        return (CardRequestType) saveEntity(cardRequestType);
    }

    @Override
    public List<Country> getCountries(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Country> countryList = (List<Country>) listEntities(Country.class, request, logger, getMethodName());
        return countryList;
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
    public PersonClassification savePersonClassification(PersonClassification personclassification) throws NullParameterException, GeneralException {
        if (personclassification == null) {
            throw new NullParameterException("requestType", null);
        }
        return (PersonClassification) saveEntity(personclassification);
    }

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

    //Collections Requests
    @Override
    public List<CollectionsRequest> getCollectionsRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CollectionsRequest> collectionsRequest = (List<CollectionsRequest>) listEntities(CollectionsRequest.class, request, logger, getMethodName());
        return collectionsRequest;
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
//CardType

    @Override
    public List<CardType> getCardTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardType> cardType = (List<CardType>) listEntities(CardType.class, request, logger, getMethodName());
        return cardType;
    }

    @Override
    public CardType loadCardType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardType cardType = (CardType) loadEntity(CardType.class, request, logger, getMethodName());
        return cardType;
    }

    @Override
    public CardType saveCardType(CardType cardType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardType == null) {
            throw new NullParameterException("requestType", null);
        }
        return (CardType) saveEntity(cardType);
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

    @Override
    public List<Network> getNetworks(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Network> networks = (List<Network>) listEntities(Network.class, request, logger, getMethodName());
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
    public List<ProgramHasNetwork> getProgramHasNetwork(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramHasNetwork> programHasNetwork = (List<ProgramHasNetwork>) listEntities(ProgramHasNetwork.class, request, logger, getMethodName());
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

    @Override
    public List<Product> getProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Product> product = (List<Product>) listEntities(Product.class, request, logger, getMethodName());
        return product;
    }

    @Override
    public Product loadProduct(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Product product = (Product) loadEntity(Product.class, request, logger, getMethodName());
        return product;
    }

    @Override
    public Product saveProduct(Product product) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (product == null) {
            throw new NullParameterException("requestType", null);
        }
        return (Product) saveEntity(product);
    }  

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
    public List<DocumentsPersonType> getDocumentsPersonTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DocumentsPersonType> documentsPersonType = (List<DocumentsPersonType>) listEntities(DocumentsPersonType.class, request, logger, getMethodName());
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

    
    //Request
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
    
    
    //Address
    @Override
    public List<Address> getAddresses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Address> addresses = (List<Address>) listEntities(Address.class, request, logger, getMethodName());
        return addresses;
    }

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

    @Override
    public List<Person> getPersons(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person loadPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person savePerson(Person person) throws RegisterNotFoundException, NullParameterException, GeneralException {
                if (person == null) {
            throw new NullParameterException("person", null);
        }
        return (Person) saveEntity(person);

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
    public List<City> getCitiesByCounty(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<City> cities = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            //throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
            throw new NullParameterException("city", null);
        }
        cities = (List<City>) getNamedQueryResult(UtilsEJB.class, EjbConstants.CITIES_BY_COUNTY, request, getMethodName(), logger, "cities");
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
    
}
    
    

   
