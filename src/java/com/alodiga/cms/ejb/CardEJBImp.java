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
import com.cms.commons.models.AccountCard;
import com.cms.commons.models.AccountProperties;
import com.cms.commons.models.AccountSegment;
import com.cms.commons.models.AccountType;
import com.cms.commons.models.AccountTypeHasProductType;
import com.cms.commons.models.Card;
import com.cms.commons.models.CardNumberCredential;
import com.cms.commons.models.RateByCard;
import com.cms.commons.models.SubAccountType;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.List;

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
    public List<AccountProperties> getAccountPropertiesByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountProperties> accountPropertiesByRequest = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        accountPropertiesByRequest = (List<AccountProperties>) getNamedQueryResult(AccountProperties.class, QueryConstants.REVIEW_REQUEST_BY_REQUEST, request, getMethodName(), logger, "accountPropertiesByRequest");
        return accountPropertiesByRequest;
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

    //AccountSegment
    @Override
    public List<AccountSegment> getAccountSegment(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountSegment> accountSegment = (List<AccountSegment>) listEntities(AccountSegment.class, request, logger, getMethodName());
        return accountSegment;
    }

    @Override
    public AccountSegment loadAccountSegment(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        AccountSegment accountSegment = (AccountSegment) loadEntity(AccountSegment.class, request, logger, getMethodName());
        return accountSegment;
    }

    @Override
    public AccountSegment saveAccountSegment(AccountSegment accountSegment) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (accountSegment == null) {
            throw new NullParameterException("accountSegment", null);
        }
        return (AccountSegment) saveEntity(accountSegment);
    }

    @Override
    public List<AccountSegment> getAccountSegmentByAccountProperties(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountSegment> accountSegmentList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_ACCOUNT_PROPERTIES_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ACCOUNT_PROPERTIES_ID), null);
        }
        accountSegmentList = (List<AccountSegment>) getNamedQueryResult(AccountSegment.class, QueryConstants.ACCOUNT_SEGMENT_BY_ACCOUNT_PROPERTIES, request, getMethodName(), logger, "accountSegmentList");
        return accountSegmentList;
    }

    //Card
    @Override
    public List<Card> getCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> card = (List<Card>) listEntities(Card.class, request, logger, getMethodName());
        return card;
    }
    
    @Override
    public List<Card> getCardByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardByProgramList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        cardByProgramList = (List<Card>) getNamedQueryResult(Card.class, QueryConstants.CARD_BY_PROGRAM, request, getMethodName(), logger, "cardByProgramList");
        return cardByProgramList;
    }
    
    
    @Override
    public List<Card> getCardByCardHolder(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardByCardHolderList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARDHOLDER)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARDHOLDER), null);
        }
        cardByCardHolderList = (List<Card>) getNamedQueryResult(Card.class, QueryConstants.CARD_BY_CARDHOLDER, request, getMethodName(), logger, "cardByCardHolderList");
        return cardByCardHolderList;
    }
    
    @Override
    public Card loadCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Card card = (Card) loadEntity(Card.class, request, logger, getMethodName());
        return card;
    }

    @Override
    public Card saveCard(Card card) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (card == null) {
            throw new NullParameterException("card", null);
        }
        return (Card) saveEntity(card);
    }

    //CardNumberCredential
    @Override
    public List<CardNumberCredential> getCardNumberCredential(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardNumberCredential> cardNumberCredential = (List<CardNumberCredential>) listEntities(CardNumberCredential.class, request, logger, getMethodName());
        return cardNumberCredential;
    }

    @Override
    public List<CardNumberCredential> getCardNumberCredentialByUse(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardNumberCredential> cardNumberCredentialByUse = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARD_NUMBER_IND_USE)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_NUMBER_IND_USE), null);
        }
        cardNumberCredentialByUse = (List<CardNumberCredential>) getNamedQueryResult(CardNumberCredential.class, QueryConstants.CARD_NUMBER_BY_USE, request, getMethodName(), logger, "cardNumberCredentialByUse");
        return cardNumberCredentialByUse;
    }

    @Override
    public CardNumberCredential loadCardNumberCredential(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardNumberCredential cardNumberCredential = (CardNumberCredential) loadEntity(CardNumberCredential.class, request, logger, getMethodName());
        return cardNumberCredential;
    }

    @Override
    public CardNumberCredential saveCardNumberCredential(CardNumberCredential cardNumberCredential) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardNumberCredential == null) {
            throw new NullParameterException("cardNumberCredential", null);
        }
        return (CardNumberCredential) saveEntity(cardNumberCredential);
    }

    @Override
    public List<RateByCard> getRateByCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByCard> rateByCardList = (List<RateByCard>) listEntities(RateByCard.class, request, logger, getMethodName());
        return rateByCardList;
    }

    @Override
    public RateByCard loadRateByCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RateByCard rateByCard = (RateByCard) loadEntity(RateByCard.class, request, logger, getMethodName());
        return rateByCard;
    }

    @Override
    public RateByCard saveRateByCard(RateByCard rateByCard) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (rateByCard == null) {
            throw new NullParameterException("rateByCard", null);
        }
        return (RateByCard) saveEntity(rateByCard); 
    }

    @Override
    public List<RateByCard> getRateByCardByCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByCard> rateByCardList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARD_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_ID), null);
        }
        rateByCardList = (List<RateByCard>) getNamedQueryResult(RateByCard.class, QueryConstants.RATE_BY_CARD_BY_CARD, request, getMethodName(), logger, "rateByCardList");
        return rateByCardList;
    }

    @Override
    public List<AccountCard> getAccountCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountCard> accountCard = (List<AccountCard>) listEntities(AccountCard.class, request, logger, getMethodName());
        return accountCard;
    }

    @Override
    public AccountCard loadAccountCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        AccountCard accountCard = (AccountCard) loadEntity(AccountCard.class, request, logger, getMethodName());
        return accountCard;
    }

    @Override
    public AccountCard saveAccountCard(AccountCard accountCard) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (accountCard == null) {
            throw new NullParameterException("accountCard", null);
        }
        return (AccountCard) saveEntity(accountCard);
    }
}
