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
import com.cms.commons.models.StatusRequest;
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
    public RequestType loadRequestType(EJBRequest RequestType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RequestType requestType = (RequestType) loadEntity(RequestType.class, RequestType, logger, getMethodName());
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
    public List<Country> getCountries(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Country> countrys = (List<Country>) listEntities(Country.class, request, logger, getMethodName());
        return countrys;
    }
    
    @Override
    public Country saveCountry(Country country) throws NullParameterException, GeneralException {
        if (country == null) {
            throw new NullParameterException("requestType", null);
        }
        return (Country) saveEntity(country);
    }
    
    //StatusRequest
    @Override
    public List<StatusRequest> getStatusRequests(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusRequest> statusRequest = (List<StatusRequest>) listEntities(StatusRequest.class, request, logger, getMethodName());
        return statusRequest;
    }
    
    @Override
    public StatusRequest saveStatusRequest (StatusRequest statusRequest) throws NullParameterException, GeneralException {
        if (statusRequest == null) {
            throw new NullParameterException("requestType", null);
        }
        return (StatusRequest) saveEntity(statusRequest);
    }

    @Override
    public List<CardRequestType> getCardRequestType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CardRequestType loadCardRequestType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CardRequestType saveCardRequestType(CardRequestType cardRequestType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
