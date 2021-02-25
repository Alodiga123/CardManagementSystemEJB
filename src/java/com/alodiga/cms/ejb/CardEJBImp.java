package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.CardEJB;
import com.alodiga.cms.commons.ejb.CardEJBLocal;
import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.InvalidQuestionException;
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
import com.cms.commons.models.CardStatus;
import com.cms.commons.models.CardStatusHasUpdateReason;
import com.cms.commons.models.CardRenewalRequest;
import com.cms.commons.models.CardRenewalRequestHasCard;
import com.cms.commons.models.DeliveryRequest;
import com.cms.commons.models.DeliveryRequetsHasCard;
import com.cms.commons.models.Issuer;
import com.cms.commons.models.NewCardIssueRequest;
import com.cms.commons.models.RateByCard;
import com.cms.commons.models.SecurityQuestion;
import com.cms.commons.models.Sequences;
import com.cms.commons.models.StatusAccount;
import com.cms.commons.models.StatusCardRenewalRequest;
import com.cms.commons.models.StatusDeliveryRequest;
import com.cms.commons.models.StatusNewCardIssueRequest;
import com.cms.commons.models.StatusUpdateReason;
import com.cms.commons.models.SubAccountType;
import com.cms.commons.models.SystemFuncionality;
import com.cms.commons.models.SystemFuncionalityHasSecurityQuestion;
import com.cms.commons.enumeraciones.StatusCardE;
import com.cms.commons.models.BonusCard;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EJBServiceLocator;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;
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
    private PersonEJB personEJB = null;
    private UtilsEJB utilsEJB = null;
    private CardEJB cardEJB = null;

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
        accountPropertiesByRequest = (List<AccountProperties>) getNamedQueryResult(AccountProperties.class, QueryConstants.ACCOUNT_PROPERTIES_BY_REQUEST, request, getMethodName(), logger, "accountPropertiesByRequest");
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
    
    public List<AccountProperties> getSearchAccountProperties(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountProperties> accountPropertiesList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT a FROM AccountProperties a ");
            sqlBuilder.append("WHERE a.identifier LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            accountPropertiesList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return accountPropertiesList;
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

    public List<AccountType> getSearchAccountType(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountType> accountTypeList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT a FROM AccountType a ");
            sqlBuilder.append("WHERE a.description LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            accountTypeList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return accountTypeList;
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
    
    public List<SubAccountType> getSearchSubAccountType(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<SubAccountType> subAccountTypeList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT s FROM SubAccountType s ");
            sqlBuilder.append("WHERE s.name LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            subAccountTypeList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
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
    public List<Card> getCardByProgramByStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardByProgramByStatus = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_CARD_STATUS)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_STATUS), null);
        }
        cardByProgramByStatus = (List<Card>) getNamedQueryResult(Card.class, QueryConstants.CARD_BY_PROGRAM_BY_STATUS, request, getMethodName(), logger, "cardByProgramByStatus");
        return cardByProgramByStatus;
    }

    @Override
    public List<Card> getCardByStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardByStatus = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARD_STATUS)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_STATUS), null);
        }
        cardByStatus = (List<Card>) getNamedQueryResult(Card.class, QueryConstants.CARD_BY_STATUS, request, getMethodName(), logger, "cardByStatus");
        return cardByStatus;
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
    public List<Card> getCardByCardNumber(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardByCardNumberList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARDNUMBER)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARDHOLDER), null);
        }
        cardByCardNumberList = (List<Card>) getNamedQueryResult(Card.class, QueryConstants.CARD_BY_CARDNUMBER, request, getMethodName(), logger, "cardByCardNumberList");
        return cardByCardNumberList;
    }

    @Override
    public List<Card> getCardByIndRenewal(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardByIndRenewalList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_IND_RENEWAL)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_IND_RENEWAL), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_CARD_STATUS)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_STATUS), null);
        }
        cardByIndRenewalList = (List<Card>) getNamedQueryResult(Card.class, QueryConstants.CARD_BY_IND_RENEWAL, request, getMethodName(), logger, "cardByIndRenewalList");
        return cardByIndRenewalList;
    }

    @Override
    public List<Card> getCardByIssuer(Integer issuerId) throws RegisterNotFoundException, EmptyListException, GeneralException, NullParameterException {
        //Se declara la lista de tarjetas a retornar
        List<Card> cardByIssuerList = new ArrayList<Card>();
        CardStatus cardStatus = null;

        //Se instancian los EJB
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        cardEJB = (CardEJB) EJBServiceLocator.getInstance().get(EjbConstants.CARD_EJB);

        //Se obtiene el estatus de la tarjeta ANULADA
        EJBRequest request1 = new EJBRequest();
        request1.setParam(Constants.CARD_STATUS_CANCELED);
        cardStatus = utilsEJB.loadCardStatus(request1);

        //Consulta para obtener optener la lista de tarjetas por emisor y por status Anulada
        StringBuilder sqlBuilder = new StringBuilder("SELECT c.* FROM card c, product p, issuer i ");
        sqlBuilder.append("WHERE c.productId = p.id AND p.issuerId = i.id AND c.cardStatusId = ?1 AND p.issuerId = ?2");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
        query.setParameter("1", cardStatus.getId());
        query.setParameter("2", issuerId);
        List<Card> cardList = (List<Card>) query.getResultList();

        //Se recorre la lista de tarjetas anuladas para el emisor seleccionado
        for (Card c : cardList) {
            //Consulta para verificar que la tarjeta no tenga asociada una solicitud de renovación automatica
            sqlBuilder = new StringBuilder("SELECT COUNT(p.id) FROM cardRenewalRequestHasCard p ");
            sqlBuilder.append("WHERE p.cardId = ?1");
            query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", c.getId());
            List resultList = (List) query.getResultList();

            //Si la tarjeta no tiene solicitud de renovación automática se agrega a la lista de tarjetas a retornar
            if ((Long) resultList.get(0) == 0) {
                cardByIssuerList.add(c);
            }
        }
        return cardByIssuerList;
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

    public Card validateQuestionCard(Long cardId, Date expirationDate, Date createDate, String ICVVMagneticStrip) throws RegisterNotFoundException, NullParameterException, GeneralException, InvalidQuestionException {
        Card card = null;
        try {
            card = loadValidateCard(cardId);
            if (!card.getExpirationDate().equals(expirationDate)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            if (!card.getIssueDate().equals(createDate)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            if (!card.getICVVMagneticStrip().equals(ICVVMagneticStrip)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            return card;
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
    }

    public Card loadValidateCard(Long cardId) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Card card = null;
        try {
            Query query = createQuery("SELECT c FROM Card c WHERE c.id = :cardId");
            query.setParameter("cardId", cardId);
            card = (Card) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
        return card;
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

    //RateByCard
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

    //AccountCard
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

    @Override
    public List<AccountCard> getAccountCardByProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AccountCard> accountCardList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_ID), null);
        }
        accountCardList = (List<AccountCard>) getNamedQueryResult(AccountCard.class, QueryConstants.ACCOUNT_CARD_BY_PRODUCT, request, getMethodName(), logger, "accountCardList");
        return accountCardList;
    }

    //StatusAccount
    @Override
    public List<StatusAccount> getStatusAccount(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusAccount> statusAccountList = (List<StatusAccount>) listEntities(StatusAccount.class, request, logger, getMethodName());
        return statusAccountList;
    }

    @Override
    public StatusAccount loadStatusAccount(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusAccount statusAccount = (StatusAccount) loadEntity(StatusAccount.class, request, logger, getMethodName());
        return statusAccount;
    }

    @Override
    public StatusAccount saveStatusAccount(StatusAccount statusAccount) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusAccount == null) {
            throw new NullParameterException("statusAccount", null);
        }
        return (StatusAccount) saveEntity(statusAccount);
    }

    //DeliveryRequest
    @Override
    public List<DeliveryRequest> getDeliveryRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DeliveryRequest> deliveryRequest = (List<DeliveryRequest>) listEntities(DeliveryRequest.class, request, logger, getMethodName());
        return deliveryRequest;
    }

    @Override
    public DeliveryRequest loadDeliveryRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DeliveryRequest deliveryRequest = (DeliveryRequest) loadEntity(DeliveryRequest.class, request, logger, getMethodName());
        return deliveryRequest;
    }

    @Override
    public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (deliveryRequest == null) {
            throw new NullParameterException("deliveryRequest", null);
        }
        return (DeliveryRequest) saveEntity(deliveryRequest);
    }
    
    public List<DeliveryRequest> getSearchDeliveryRequest(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<DeliveryRequest> deliveryRequestList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT d FROM DeliveryRequest d ");
            sqlBuilder.append("WHERE d.requestNumber LIKE '%").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            deliveryRequestList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return deliveryRequestList;
    }

    //DeliveryRequestHasCard
    @Override
    public List<DeliveryRequetsHasCard> getDeliveryRequestHasCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DeliveryRequetsHasCard> deliveryRequetsHasCard = (List<DeliveryRequetsHasCard>) listEntities(DeliveryRequetsHasCard.class, request, logger, getMethodName());
        return deliveryRequetsHasCard;
    }

    @Override
    public List<DeliveryRequetsHasCard> getCardByDeliveryRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DeliveryRequetsHasCard> deliveryRequetsHasCardList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_DELIVERY_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_DELIVERY_REQUEST_ID), null);
        }
        deliveryRequetsHasCardList = (List<DeliveryRequetsHasCard>) getNamedQueryResult(DeliveryRequetsHasCard.class, QueryConstants.CARD_BY_DELIVERY_REQUEST, request, getMethodName(), logger, "deliveryRequetsHasCardList");
        return deliveryRequetsHasCardList;
    }

    @Override
    public DeliveryRequetsHasCard loadDeliveryRequestHasCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DeliveryRequetsHasCard deliveryRequetsHasCard = (DeliveryRequetsHasCard) loadEntity(DeliveryRequetsHasCard.class, request, logger, getMethodName());
        return deliveryRequetsHasCard;
    }

    @Override
    public DeliveryRequetsHasCard saveDeliveryRequestHasCard(DeliveryRequetsHasCard deliveryRequetsHasCard) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (deliveryRequetsHasCard == null) {
            throw new NullParameterException("deliveryRequetsHasCard", null);
        }
        return (DeliveryRequetsHasCard) saveEntity(deliveryRequetsHasCard);
    }

    @Override
    public List<StatusDeliveryRequest> getStatusDeliveryRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusDeliveryRequest> statusDeliveryRequest = (List<StatusDeliveryRequest>) listEntities(StatusDeliveryRequest.class, request, logger, getMethodName());
        return statusDeliveryRequest;

    }

    @Override
    public StatusDeliveryRequest loadStatusDeliveryRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusDeliveryRequest statusDeliveryRequest = (StatusDeliveryRequest) loadEntity(StatusDeliveryRequest.class, request, logger, getMethodName());
        return statusDeliveryRequest;
    }

    @Override
    public StatusDeliveryRequest saveStatusDeliveryRequest(StatusDeliveryRequest statusDeliveryRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusDeliveryRequest == null) {
            throw new NullParameterException("statusDeliveryRequest", null);
        }
        return (StatusDeliveryRequest) saveEntity(statusDeliveryRequest);
    }

    //SecurityQuestion
    @Override
    public List<SecurityQuestion> getSecurityQuestion(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SecurityQuestion> securityQuestion = (List<SecurityQuestion>) listEntities(SecurityQuestion.class, request, logger, getMethodName());
        return securityQuestion;
    }

    @Override
    public SecurityQuestion loadSecurityQuestion(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SecurityQuestion securityQuestion = (SecurityQuestion) loadEntity(SecurityQuestion.class, request, logger, getMethodName());
        return securityQuestion;
    }

    @Override
    public SecurityQuestion saveSecurityQuestion(SecurityQuestion securityQuestion) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (securityQuestion == null) {
            throw new NullParameterException("securityQuestion", null);
        }
        return (SecurityQuestion) saveEntity(securityQuestion);
    }

    //SystemFuncionality
    @Override
    public List<SystemFuncionality> getSystemFuncionality(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SystemFuncionality> systemFuncionality = (List<SystemFuncionality>) listEntities(SystemFuncionality.class, request, logger, getMethodName());
        return systemFuncionality;
    }

    @Override
    public SystemFuncionality loadSystemFuncionality(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SystemFuncionality systemFuncionality = (SystemFuncionality) loadEntity(SystemFuncionality.class, request, logger, getMethodName());
        return systemFuncionality;
    }

    @Override
    public SystemFuncionality saveSystemFuncionality(SystemFuncionality systemFuncionality) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (systemFuncionality == null) {
            throw new NullParameterException("systemFuncionality", null);
        }
        return (SystemFuncionality) saveEntity(systemFuncionality);
    }

    //SystemFuncionalityHasSecurityQuestion
    @Override
    public List<SystemFuncionalityHasSecurityQuestion> getSystemFuncionalityHasSecurityQuestion(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SystemFuncionalityHasSecurityQuestion> systemFuncionalityHasSecurityQuestion = (List<SystemFuncionalityHasSecurityQuestion>) listEntities(SystemFuncionalityHasSecurityQuestion.class, request, logger, getMethodName());
        return systemFuncionalityHasSecurityQuestion;
    }

    @Override
    public SystemFuncionalityHasSecurityQuestion loadSystemFuncionalityHasSecurityQuestion(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SystemFuncionalityHasSecurityQuestion systemFuncionalityHasSecurityQuestion = (SystemFuncionalityHasSecurityQuestion) loadEntity(SystemFuncionalityHasSecurityQuestion.class, request, logger, getMethodName());
        return systemFuncionalityHasSecurityQuestion;
    }

    @Override
    public SystemFuncionalityHasSecurityQuestion saveSystemFuncionalityHasSecurityQuestion(SystemFuncionalityHasSecurityQuestion systemFuncionalityHasSecurityQuestion) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (systemFuncionalityHasSecurityQuestion == null) {
            throw new NullParameterException("systemFuncionalityHasSecurityQuestion", null);
        }
        return (SystemFuncionalityHasSecurityQuestion) saveEntity(systemFuncionalityHasSecurityQuestion);
    }

    //StatusUpdateReason
    @Override
    public List<StatusUpdateReason> getStatusUpdateReason(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusUpdateReason> statusUpdateReason = (List<StatusUpdateReason>) listEntities(StatusUpdateReason.class, request, logger, getMethodName());
        return statusUpdateReason;
    }

    @Override
    public StatusUpdateReason loadStatusUpdateReason(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusUpdateReason statusUpdateReason = (StatusUpdateReason) loadEntity(StatusUpdateReason.class, request, logger, getMethodName());
        return statusUpdateReason; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public StatusUpdateReason saveStatusUpdateReason(StatusUpdateReason statusUpdateReason) throws RegisterNotFoundException, NullParameterException, GeneralException{
        if (statusUpdateReason == null) {
            throw new NullParameterException("statusUpdateReason", null);
        }
        return (StatusUpdateReason) saveEntity(statusUpdateReason);
    }

    //CardRenewalRequest
    public List<CardRenewalRequest> getCardRenewalRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRenewalRequest> cardRenewalRequest = (List<CardRenewalRequest>) listEntities(CardRenewalRequest.class, request, logger, getMethodName());
        return cardRenewalRequest;
    }

    public CardRenewalRequest loadCardRenewalRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardRenewalRequest cardRenewalRequest = (CardRenewalRequest) loadEntity(CardRenewalRequest.class, request, logger, getMethodName());
        return cardRenewalRequest;
    }

    public CardRenewalRequest saveCardRenewalRequest(CardRenewalRequest cardRenewalRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardRenewalRequest == null) {
            throw new NullParameterException("cardRenewalRequest", null);
        }
        return (CardRenewalRequest) saveEntity(cardRenewalRequest);
    }

    @Override
    public List<CardRenewalRequest> createCardRenewalRequestByIssuer(Integer cardStatus) throws RegisterNotFoundException, EmptyListException, GeneralException, NullParameterException {
        //Se declara la lista de solicitudes a retornar
        List<CardRenewalRequest> cardRenewalRequestList = new ArrayList<CardRenewalRequest>();
        int issuerId = 0;

        //Se instancian los EJB
        utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
        cardEJB = (CardEJB) EJBServiceLocator.getInstance().get(EjbConstants.CARD_EJB);
        personEJB = (PersonEJB) EJBServiceLocator.getInstance().get(EjbConstants.PERSON_EJB);

        //Consulta para obtener el id del emisor para las tarjetas cuya fecha de renovación es igual a la fecha actual y estén activas
        StringBuilder sqlBuilder = new StringBuilder("SELECT i.id FROM card c, issuer i, product p ");
        sqlBuilder.append("WHERE c.productId = p.id AND p.issuerId = i.id AND c.cardStatusId = ?1 AND c.automaticRenewalDate = CURDATE() GROUP BY i.id");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", cardStatus);
        List result = (List) query.getResultList();

        //Obtener el estatus de la solicitud PENDIENTE
        EJBRequest request1 = new EJBRequest();
        request1.setParam(Constants.STATUS_CARD_RENEWAL_REQUEST_PENDING);
        StatusCardRenewalRequest statusCardRenewalRequest = cardEJB.loadStatusCardRenewalRequest(request1);

        //Se crean automáticamente las solicitudes de renovación de tarjeta por emisor
        for (int i = 0; i < result.size(); i++) {
            //Obtener el emisor
            request1 = new EJBRequest();
            request1.setParam(result.get(i));
            Issuer issuer = personEJB.loadIssuer(request1);
            issuerId = issuer.getId();

            //Obtiene el numero de secuencia para documento Request
            request1 = new EJBRequest();
            Map params = new HashMap();
            params.put(Constants.DOCUMENT_TYPE_KEY, Constants.DOCUMENT_TYPE_RENEWAL_REQUEST);
            request1.setParams(params);
            List<Sequences> sequence = utilsEJB.getSequencesByDocumentType(request1);
            String numberRequest = utilsEJB.generateNumberSequence(sequence, Constants.ORIGIN_APPLICATION_CMS_ID);

            //Se crea la solicitud de Renovación de Tarjeta y se guarda en BD
            CardRenewalRequest cardRenewalRequest = new CardRenewalRequest();
            cardRenewalRequest.setIssuerId(issuer);
            cardRenewalRequest.setRequestNumber(numberRequest);
            cardRenewalRequest.setCreateDate(new Timestamp(new Date().getTime()));
            cardRenewalRequest.setRequestDate(new Date());
            cardRenewalRequest.setStatusCardRenewalRequestId(statusCardRenewalRequest);
            cardRenewalRequest = cardEJB.saveCardRenewalRequest(cardRenewalRequest);

            //Consulta para obtener la lista de tarjetas por emisor cuya fecha de renovación es igual a la fecha actual y estén activas. 
            sqlBuilder = new StringBuilder("SELECT c.* FROM card c, issuer i, product p ");
            sqlBuilder.append("WHERE c.productId = p.id AND p.issuerId = i.id AND c.cardStatusId = ?1 AND i.id = ?2 AND c.automaticRenewalDate = CURDATE()");
            query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
            query.setParameter("1", cardStatus);
            query.setParameter("2", issuerId);
            List<Card> cardList = query.getResultList();

            //Asocia las tarjetas a la solicitud
            for (Card c : cardList) {
                CardRenewalRequestHasCard cardRenewalRequestHasCard = new CardRenewalRequestHasCard();
                cardRenewalRequestHasCard.setCardId(c);
                cardRenewalRequestHasCard.setCardRenewalRequestId(cardRenewalRequest);
                cardRenewalRequestHasCard.setCreateDate(new Timestamp(new Date().getTime()));
                cardRenewalRequestHasCard = cardEJB.saveCardRenewalRequestHasCard(cardRenewalRequestHasCard);
            }

            //Agregas la solicitud a la lista que retorna el servicio
            cardRenewalRequestList.add(cardRenewalRequest);
        }

        return cardRenewalRequestList;
    }

    @Override
    public List<CardRenewalRequest> getCardRenewalRequestByCurrentDate(Integer cardStatus) throws EmptyListException, GeneralException, NullParameterException {
        //Consulta para verificar si hay solicitudes de renovación generadas en la fecha actual
        StringBuilder sqlBuilder = new StringBuilder("SELECT c.* FROM cardRenewalRequest c WHERE DATE_FORMAT(c.createDate, '%Y-%m-%d') = CURDATE()");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), CardRenewalRequest.class);
        List<CardRenewalRequest> result = (List<CardRenewalRequest>) query.getResultList();
        return result;
    }
    
    public List<CardRenewalRequest> getSearchCardRenewalRequest(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRenewalRequest> cardRenewalRequestList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT c FROM CardRenewalRequest c");
            sqlBuilder.append("WHERE c.requestNumber LIKE '%").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            cardRenewalRequestList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return cardRenewalRequestList;
    }

    //CardRenewalRequestHasCard
    public List<CardRenewalRequestHasCard> getCardRenewalRequestHasCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRenewalRequestHasCard> cardRenewalRequestHasCard = (List<CardRenewalRequestHasCard>) listEntities(CardRenewalRequestHasCard.class, request, logger, getMethodName());
        return cardRenewalRequestHasCard;
    }

    public List<CardRenewalRequestHasCard> getCardRenewalRequestHasCardByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRenewalRequestHasCard> cardRenewalByRequestList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARD_RENEWEL_REQUEST)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_RENEWEL_REQUEST), null);
        }
        cardRenewalByRequestList = (List<CardRenewalRequestHasCard>) getNamedQueryResult(Card.class, QueryConstants.CARD_RENEWAL_BY_REQUEST, request, getMethodName(), logger, "cardRenewalByRequestList");
        return cardRenewalByRequestList;
    }

    @Override
    public List<CardRenewalRequestHasCard> getCardRenewalRequestByCard(Long cardId) throws EmptyListException, GeneralException, NullParameterException {
        //Consulta para verificar si hay solicitudes de renovación generadas en la fecha actual
        StringBuilder sqlBuilder = new StringBuilder("SELECT c.* FROM cardRenewalRequestHasCard c WHERE c.cardId.id = ?1");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString(), CardRenewalRequestHasCard.class);
        List<CardRenewalRequestHasCard> result = (List<CardRenewalRequestHasCard>) query.getResultList();
        return result;
    }

    public CardRenewalRequestHasCard loadCardRenewalRequestHasCard(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardRenewalRequestHasCard cardRenewalRequestHasCard = (CardRenewalRequestHasCard) loadEntity(CardRenewalRequestHasCard.class, request, logger, getMethodName());
        return cardRenewalRequestHasCard;
    }

    public CardRenewalRequestHasCard saveCardRenewalRequestHasCard(CardRenewalRequestHasCard cardRenewalRequestHasCard) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardRenewalRequestHasCard == null) {
            throw new NullParameterException("cardRenewalRequestHasCard", null);
        }
        return (CardRenewalRequestHasCard) saveEntity(cardRenewalRequestHasCard);
    }

    //StatusCardRenewalRequest
    public List<StatusCardRenewalRequest> getStatusCardRenewalRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusCardRenewalRequest> statusCardRenewalRequest = (List<StatusCardRenewalRequest>) listEntities(StatusCardRenewalRequest.class, request, logger, getMethodName());
        return statusCardRenewalRequest;
    }

    public StatusCardRenewalRequest loadStatusCardRenewalRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusCardRenewalRequest statusCardRenewalRequest = (StatusCardRenewalRequest) loadEntity(StatusCardRenewalRequest.class, request, logger, getMethodName());
        return statusCardRenewalRequest;
    }

    public StatusCardRenewalRequest saveStatusCardRenewalRequest(StatusCardRenewalRequest statusCardRenewalRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusCardRenewalRequest == null) {
            throw new NullParameterException("statusCardRenewalRequest", null);
        }
        return (StatusCardRenewalRequest) saveEntity(statusCardRenewalRequest);
    }

    //CardStatusHasUpdateReason
    @Override
    public List<CardStatusHasUpdateReason> getCardStatusHasUpdateReason(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException{
        List<CardStatusHasUpdateReason> cardStatusHasUpdateReason = (List<CardStatusHasUpdateReason>) listEntities(CardStatusHasUpdateReason.class, request, logger, getMethodName());
        return cardStatusHasUpdateReason;
    }
    
    @Override
    public List<CardStatusHasUpdateReason> getCardStatusByUpdateReason(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardStatusHasUpdateReason> cardStatusHasUpdateReasonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_STATUS_UPDATE_REASON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_STATUS_UPDATE_REASON_ID), null);
        }
        cardStatusHasUpdateReasonList = (List<CardStatusHasUpdateReason>) getNamedQueryResult(CardStatusHasUpdateReason.class, QueryConstants.CARD_STATUS_BY_REASON_UPDATE, request, getMethodName(), logger, "cardStatusHasUpdateReasonList");
        return cardStatusHasUpdateReasonList;
    }

    @Override
    public List<CardStatusHasUpdateReason> getUpdateReasonByCardStatus(String cardStatusId) throws EmptyListException, GeneralException, NullParameterException {
        List<CardStatusHasUpdateReason> cardStatusHasUpdateReasonList = null;
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM cardStatusHasUpdateReason where cardStatusId=")
                .append(cardStatusId)
                .append(" and indAllowTable=0");
        try {
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), CardStatusHasUpdateReason.class);
            cardStatusHasUpdateReasonList = (List<CardStatusHasUpdateReason>) query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            e.getMessage();
        }
        return cardStatusHasUpdateReasonList;
    }
    
    public List<CardStatusHasUpdateReason> getCardStatusHasUpdateReasonUnique(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException{
        List<CardStatusHasUpdateReason> cardStatusHasUpdateReasonUniqueList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_STATUS_UPDATE_REASON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_STATUS_UPDATE_REASON_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_CARD_STATUS)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CARD_STATUS), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_IND_ALLOW_TABLE)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_IND_ALLOW_TABLE), null);
        }
        cardStatusHasUpdateReasonUniqueList = (List<CardStatusHasUpdateReason>) getNamedQueryResult(CardStatusHasUpdateReason.class, QueryConstants.CARD_STATUS_BY_REASON_UNIQUE, request, getMethodName(), logger, "cardStatusHasUpdateReasonUniqueList");
        return cardStatusHasUpdateReasonUniqueList;
    }
    
    public CardStatusHasUpdateReason loadCardStatusHasUpdateReason(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException{
        CardStatusHasUpdateReason cardStatusHasUpdateReason = (CardStatusHasUpdateReason) loadEntity(CardStatusHasUpdateReason.class, request, logger, getMethodName());
        return cardStatusHasUpdateReason;
    }

    public CardStatusHasUpdateReason saveCardStatusHasUpdateReason(CardStatusHasUpdateReason cardStatusHasUpdateReason) throws RegisterNotFoundException, NullParameterException, GeneralException{
        if (cardStatusHasUpdateReason == null) {
            throw new NullParameterException("cardStatusHasUpdateReason", null);
        }
        return (CardStatusHasUpdateReason) saveEntity(cardStatusHasUpdateReason);
    }

    //NewCardIssueRequest
    public List<NewCardIssueRequest> getNewCardIssueRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NewCardIssueRequest> newCardIssueRequest = (List<NewCardIssueRequest>) listEntities(NewCardIssueRequest.class, request, logger, getMethodName());
        return newCardIssueRequest;
    }

    @Override
    public List<NewCardIssueRequest> getNewCardIssueRequestByCard(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NewCardIssueRequest> newCardIssueRequestByCardList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CARD_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_STATUS_UPDATE_REASON_ID), null);
        }
        newCardIssueRequestByCardList = (List<NewCardIssueRequest>) getNamedQueryResult(NewCardIssueRequest.class, QueryConstants.NEW_CARD_ISSUE_BY_CARD, request, getMethodName(), logger, "newCardIssueRequestByCardList");
        return newCardIssueRequestByCardList;
    }

    @Override
    public List<NewCardIssueRequest> createCardNewCardIssueRequest(Card cardId) throws RegisterNotFoundException, EmptyListException, GeneralException, NullParameterException {
        //Se declara la lista de solicitudes a retornar
        List<NewCardIssueRequest> newCardIssueRequestList = new ArrayList<NewCardIssueRequest>();
        int issuerId = 0;
        String numberRequest = "";
        
        try {
            //Se instancian los EJB
            utilsEJB = (UtilsEJB) EJBServiceLocator.getInstance().get(EjbConstants.UTILS_EJB);
            cardEJB = (CardEJB) EJBServiceLocator.getInstance().get(EjbConstants.CARD_EJB);

            //Se verifica si la tarjeta a emitir ya tiene asociada una solicitud de emisón de tarjeta
            StringBuilder sqlBuilder = new StringBuilder("SELECT p.* FROM newCardIssueRequest p ");
            sqlBuilder.append("WHERE p.cardId = ?1");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), NewCardIssueRequest.class);
            query.setParameter("1", cardId.getId());
            List<NewCardIssueRequest> resultList = (List<NewCardIssueRequest>) query.getResultList();

            //Se crea automáticamente la solicitud
            //Si la tarjeta no tiene solicitud anterior se agrega a la lista de tarjetas a retornar
            if (resultList.isEmpty()) {
                //Obtener el estatus de la solicitud PENDIENTE
                EJBRequest request1 = new EJBRequest();
                request1.setParam(Constants.STATUS_NEW_CARD_ISSUE_PENDING);
                StatusNewCardIssueRequest statusNewCardIssueRequest = cardEJB.loadStatusNewCardIssueRequest(request1);

                //Obtiene el numero de secuencia para documento Request
                EJBRequest request2 = new EJBRequest();
                Map params = new HashMap();
                params.put(Constants.DOCUMENT_TYPE_KEY, Constants.DOCUMENT_TYPE_NEW_CARD_ISSUE_REQUEST);
                request2.setParams(params);
                List<Sequences> sequence = utilsEJB.getSequencesByDocumentType(request2);
                numberRequest = utilsEJB.generateNumberSequence(sequence, Constants.ORIGIN_APPLICATION_CMS_ID);

                //Se guarda la solicitud de emisión de tarjeta en la BD
                NewCardIssueRequest newCardIssueRequest = new NewCardIssueRequest();
                newCardIssueRequest.setRequestNumber(numberRequest);
                newCardIssueRequest.setRequestDate(new Date());
                newCardIssueRequest.setStatusNewCardIssueRequestId(statusNewCardIssueRequest);
                newCardIssueRequest.setNewCardIssueDate(new Timestamp(new Date().getTime()));
                newCardIssueRequest.setCardId(cardId);
                newCardIssueRequest.setCreateDate(new Timestamp(new Date().getTime()));
                newCardIssueRequest = cardEJB.saveNewCardIssueRequest(newCardIssueRequest);
                newCardIssueRequestList.add(newCardIssueRequest);
            } else {
                for (NewCardIssueRequest r : resultList) {
                    newCardIssueRequestList.add(r);
                }
            }
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
        return newCardIssueRequestList;
    }

    public NewCardIssueRequest loadNewCardIssueRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        NewCardIssueRequest newCardIssueRequest = (NewCardIssueRequest) loadEntity(NewCardIssueRequest.class, request, logger, getMethodName());
        return newCardIssueRequest;
    }

    public NewCardIssueRequest saveNewCardIssueRequest(NewCardIssueRequest newCardIssueRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (newCardIssueRequest == null) {
            throw new NullParameterException("newCardIssueRequest", null);
        }
        return (NewCardIssueRequest) saveEntity(newCardIssueRequest);
    }

    //NewCardIssueRequest
    public List<StatusNewCardIssueRequest> getStatusNewCardIssueRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusNewCardIssueRequest> statusNewCardIssueRequest = (List<StatusNewCardIssueRequest>) listEntities(StatusNewCardIssueRequest.class, request, logger, getMethodName());
        return statusNewCardIssueRequest;
    }

    public StatusNewCardIssueRequest loadStatusNewCardIssueRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusNewCardIssueRequest statusNewCardIssueRequest = (StatusNewCardIssueRequest) loadEntity(StatusNewCardIssueRequest.class, request, logger, getMethodName());
        return statusNewCardIssueRequest;
    }

    public StatusNewCardIssueRequest saveStatusNewCardIssueRequest(StatusNewCardIssueRequest statusNewCardIssueRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusNewCardIssueRequest == null) {
            throw new NullParameterException("statusNewCardIssueRequest", null);
        }
        return (StatusNewCardIssueRequest) saveEntity(statusNewCardIssueRequest);
    }
    
    @Override
    public List<Card> getCardByEmail(String email) throws EmptyListException, GeneralException, NullParameterException {    
        List<Card> cards = new ArrayList<Card>();        
        if(email == null){
           throw new NullParameterException("email", null); 
        }         
        try{            
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM card c ");
            sqlBuilder.append("WHERE c.personCustomerId IN ");
            sqlBuilder.append("(SELECT n.id FROM person n WHERE n.email = '").append(email).append("')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
            cards = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
       return cards; 
    }
    
     @Override
    public List<Card> getCardByPhone(String phoneNumber) throws EmptyListException, GeneralException, NullParameterException {    
        List<Card> cards = new ArrayList<Card>();
        
        if(phoneNumber == null){
           throw new NullParameterException("phoneNumber ", null); 
        } 
        
        try{
            
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM card c ");
            sqlBuilder.append("WHERE c.personCustomerId IN ");
            sqlBuilder.append("(SELECT p.personId FROM phonePerson p WHERE p.numberPhone = '").append(phoneNumber).append("')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
            cards = query.setHint("toplink.refresh", "true").getResultList();
            
        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }

       return cards; 
    }
    
      public List<Card> getCardByIdentificationNumber(String identificationNumber) throws EmptyListException, GeneralException, NullParameterException {    
        List<Card> cards = new ArrayList<Card>();        
        if(identificationNumber == null){
           throw new NullParameterException("phoneNumber ", null); 
        }         
        try{  
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM card c ");
            sqlBuilder.append("WHERE c.personCustomerId IN ");
            sqlBuilder.append("(SELECT n.personId FROM naturalCustomer n WHERE n.identificationNumber = '").append(identificationNumber).append("')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
            cards = query.setHint("toplink.refresh", "true").getResultList();
            
        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }

       return cards; 
    }
      
    public List<Card> getCardByProgramAndTransactionalStatus(Long programId) throws EmptyListException, GeneralException, NullParameterException {
        List<Card> cardList = null;
        if (programId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "programId"), null);
        }
        try {            
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM card c ");
            sqlBuilder.append("WHERE c.programId = ").append(programId);
            sqlBuilder.append(" AND c.cardStatusId= ").append(StatusCardE.PERSON.getId());
            sqlBuilder.append(" OR c.cardStatusId= ").append(StatusCardE.PENPER.getId());
            sqlBuilder.append(" OR c.cardStatusId= ").append(StatusCardE.ENTREG.getId());
            sqlBuilder.append(" OR c.cardStatusId= ").append(StatusCardE.ACTIVA.getId());
            sqlBuilder.append(" OR c.cardStatusId= ").append(StatusCardE.BLOQUE.getId());
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
            cardList = (List<Card>) query.setHint("toplink.refresh", "true").getResultList();
        
        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return cardList;
    }
    
    @Override
    public List<Card> getCardByPerson(Long personId) throws EmptyListException, GeneralException, NullParameterException {    
        List<Card> cards = new ArrayList<Card>();        
        if(personId == null){
           throw new NullParameterException("personId", null); 
        }         
        try{            
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM card c ");
            sqlBuilder.append("WHERE c.personCustomerId IN ");
            sqlBuilder.append("(SELECT p.id FROM person p WHERE p.id = '").append(personId).append("')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Card.class);
            cards = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
       return cards; 
    }
    
    public BonusCard getBonusCard(Long cardId) throws RegisterNotFoundException, NullParameterException, GeneralException {
        BonusCard bonusCard = null;
        try {
            Query query = createQuery("SELECT b FROM BonusCard b WHERE b.cardId.id = :cardId");
            query.setParameter("cardId", cardId);
            bonusCard = (BonusCard) query.getSingleResult();
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
        return bonusCard;
    }


}
