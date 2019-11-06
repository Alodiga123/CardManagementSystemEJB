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
import com.cms.commons.models.Country;
import com.cms.commons.models.RequestType;
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
        return (Country) saveEntity(country);    }
    
}
