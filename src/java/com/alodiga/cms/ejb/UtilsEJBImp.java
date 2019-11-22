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
import com.cms.commons.models.CardRequestType;
import com.cms.commons.models.CardStatus;
import com.cms.commons.models.CollectionsRequest;
import com.cms.commons.models.Country;
import com.cms.commons.models.Currency;
import com.cms.commons.models.PersonClassification;
import com.cms.commons.models.RequestType;
import com.cms.commons.models.StatusRequest;
import com.cms.commons.models.CollectionsRequest;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.LegalPerson;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.Request;
import com.cms.commons.models.State;
import com.cms.commons.util.EjbConstants;
import java.util.List;
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

public class UtilsEJBImp extends AbstractDistributionEJB implements UtilsEJBLocal, UtilsEJB  {
    
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
    public List<DocumentsPersonType> getDocumentsPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
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

    @Override
    public List<Request> getRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Request> requests = (List<Request>) listEntities(DocumentsPersonType.class, request, logger, getMethodName());
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

 }
  