package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.CardEJB;
import com.alodiga.cms.commons.ejb.CardEJBLocal;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.AccountProperties;
import com.cms.commons.models.AccountType;
import com.cms.commons.models.AccountTypeHasProductType;
import com.cms.commons.models.SubAccountType;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Yoan Leon
 * @since 12/02/2020
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.CARD_EJB, mappedName = EjbConstants.CARD_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class CardEJBImp extends AbstractDistributionEJB implements CardEJBLocal, CardEJB {

    private static final Logger logger = Logger.getLogger(CardEJBImp.class);

   
    //AccountProperties
    @Override
    public List<AccountProperties> getAccountProperties(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountProperties> accountProperties = (List<AccountProperties>) listEntities(AccountProperties.class, request, logger, getMethodName());
        return accountProperties;
    }

    @Override
    public AccountProperties loadAccountProperties(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        AccountProperties accountProperties = (AccountProperties) loadEntity(AccountProperties.class, request, logger, getMethodName());
        return accountProperties;
    }

    @Override
    public AccountProperties saveAccountProperties(AccountProperties accountProperties) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (accountProperties == null) {
            throw new NullParameterException("accountProperties", null);
        }
        return (AccountProperties) saveEntity(accountProperties);
    }
    
    //AccountType
    @Override
    public List<AccountType> getAccountType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountType> accountType = (List<AccountType>) listEntities(AccountType.class, request, logger, getMethodName());
        return accountType;
    }

    @Override
    public AccountType loadAccountType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
         AccountType accountType = (AccountType) loadEntity(AccountType.class, request, logger, getMethodName());
        return accountType;
    }

    @Override
    public AccountType saveAccountType(AccountType accountType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (accountType == null) {
            throw new NullParameterException("accountType", null);
        }
        return (AccountType) saveEntity(accountType);
    }
    
    //AccountTypeHasProductType
    @Override
    public List<AccountTypeHasProductType> getAccountTypeHasProductType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountTypeHasProductType> accountTypeHasProductType = (List<AccountTypeHasProductType>) listEntities(AccountTypeHasProductType.class, request, logger, getMethodName());
        return accountTypeHasProductType;
    }

    @Override
    public List<AccountTypeHasProductType> getAccountTypeHasProductTypeByProductType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountTypeHasProductType> accountTypeHasProductTypeList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_TYPE_ID), null);
        }
        accountTypeHasProductTypeList = (List<AccountTypeHasProductType>) getNamedQueryResult(AccountTypeHasProductType.class, QueryConstants.ACCOUNT_TYPE_HAS_PRODUCT_TYPE_BY_PRODUCT_TYPE, request, getMethodName(), logger, "accountTypeHasProductTypeList");
        return accountTypeHasProductTypeList;
    }


    @Override
    public AccountTypeHasProductType loadAccountTypeHasProductType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        AccountTypeHasProductType accountTypeHasProductType = (AccountTypeHasProductType) loadEntity(AccountTypeHasProductType.class, request, logger, getMethodName());
        return accountTypeHasProductType;
    }

    @Override
    public AccountTypeHasProductType saveAccountTypeHasProductType(AccountTypeHasProductType accountTypeHasProductType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (accountTypeHasProductType == null) {
            throw new NullParameterException("accountTypeHasProductType", null);
        }
        return (AccountTypeHasProductType) saveEntity(accountTypeHasProductType);
    }
    
    //SubAccountType
    @Override
    public List<SubAccountType> getSubAccountType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SubAccountType> subAccountType = (List<SubAccountType>) listEntities(SubAccountType.class, request, logger, getMethodName());
        return subAccountType;
    }

    @Override
    public SubAccountType loadSubAccountType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SubAccountType subAccountType = (SubAccountType) loadEntity(SubAccountType.class, request, logger, getMethodName());
        return subAccountType;
    }

    @Override
    public SubAccountType saveSubAccountType(SubAccountType subAccountType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (subAccountType == null) {
            throw new NullParameterException("subAccountType", null);
        }
        return (SubAccountType) saveEntity(subAccountType);
    }

    @Override
    public List<SubAccountType> getSubAccountTypeByAccountType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SubAccountType> subAccountTypeList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_ACCOUNT_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ACCOUNT_TYPE_ID), null);
        }
        subAccountTypeList = (List<SubAccountType>) getNamedQueryResult(SubAccountType.class, QueryConstants.SUB_ACCOUNT_TYPE_BY_ACCOUNT_TYPE, request, getMethodName(), logger, "subAccountTypeList");
        return subAccountTypeList;
    }

        
}
