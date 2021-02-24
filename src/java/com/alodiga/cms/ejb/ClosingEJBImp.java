package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.CardEJBLocal;
import com.alodiga.cms.commons.ejb.ClosingEJB;
import com.alodiga.cms.commons.ejb.ClosingEJBLocal;
import com.alodiga.cms.commons.ejb.PersonEJBLocal;
import com.alodiga.cms.commons.ejb.UtilsEJBLocal;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.CalendarDays;
import com.cms.commons.models.Card;
import com.cms.commons.models.CardRenewalRequest;
import com.cms.commons.models.CardRenewalRequestHasCard;
import com.cms.commons.models.DailyClosing;
import com.cms.commons.models.Issuer;
import com.cms.commons.models.OriginApplication;
import com.cms.commons.models.Sequences;
import com.cms.commons.models.StatusCardRenewalRequest;
import com.cms.commons.models.Transaction;
import com.cms.commons.models.TotalTransactionsAmountByDailyClosing;
import com.cms.commons.models.TransactionsManagement;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.EjbUtils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import javax.persistence.EntityTransaction;


@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.CLOSING_EJB, mappedName = EjbConstants.CLOSING_EJB)
@TransactionManagement(TransactionManagementType.BEAN)
public class ClosingEJBImp extends AbstractDistributionEJB implements ClosingEJB, ClosingEJBLocal {

    private static final Logger logger = Logger.getLogger(ClosingEJBImp.class);
    @EJB
    private UtilsEJBLocal utilsEJB;
    @EJB
    private PersonEJBLocal personEJB;
    @EJB
    private CardEJBLocal cardEJB;
    
    @Override
    public DailyClosing closingDailyTransactionWallet(Date closingDate) throws GeneralException, NullParameterException {
        DailyClosing dailyClosing = new DailyClosing();
        try {
            if (!isHoliday(closingDate) && !EjbUtils.isWeekEnd(closingDate)) {
                dailyClosing.setClosingDate(new Date());// corresponde a la Fecha del Cierre
                dailyClosing.setClosingStartTime(new Date());// es la hora en que comienza el proceso de cierre.
                EJBRequest request = new EJBRequest();
                request.setParam(Constants.ORIGIN_APPLICATION_CMS_AUTHORIZE);
                OriginApplication originApplicationId = utilsEJB.loadOriginApplication(request);
                dailyClosing.setOriginApplicationId(originApplicationId);// Origen de Cierre: Billetera Móvil, Portal deNegocios, Alodiga Wallet Web
                Date oldClosingDate = OldClosingDate(closingDate);
                int totalTrasactions = TotalTransactionsCurrentDate(oldClosingDate, closingDate);
                Float transactionsAmount = TotalAmountCurrentDate(oldClosingDate, closingDate).floatValue();
                dailyClosing.setTotalTransactions(totalTrasactions);// cantidad total de transacciones del cierre diario
                dailyClosing.setTransactionsAmount(transactionsAmount);// Monto Total de las transacciones del cierre diario
                dailyClosing = saveDailyClosing(dailyClosing);
                request = new EJBRequest();
                List<Transaction> transactionTypes = utilsEJB.getTransaction(request);
                List<TotalTransactionsAmountByDailyClosing> details = new ArrayList<TotalTransactionsAmountByDailyClosing>();
                for (Transaction transaction : transactionTypes) {
                    int totalTrasactionsByTransactionType = TotalTransactionsCurrentDatebyTransactionType(oldClosingDate, closingDate, transaction.getId());  
                    Float transactionsAmountByTransactionType = TotalAmountCurrentDateByTransaction(oldClosingDate, closingDate, transaction.getId()).floatValue();
                    TotalTransactionsAmountByDailyClosing totalTransactionsAmountByDailyClosing = new TotalTransactionsAmountByDailyClosing();
                    if (transactionsAmountByTransactionType>0f || totalTrasactionsByTransactionType>0){
                        totalTransactionsAmountByDailyClosing.setCreateDate(new Date());
                        totalTransactionsAmountByDailyClosing.setDailyClosingId(dailyClosing);
                        totalTransactionsAmountByDailyClosing.setTotalTransactions(totalTrasactionsByTransactionType);
                        totalTransactionsAmountByDailyClosing.setTransactionsAmount(transactionsAmountByTransactionType);
                        totalTransactionsAmountByDailyClosing.setTransactionId(transaction);
                        //Guarda TotalTransactionsAmountByDailyClosing
                        totalTransactionsAmountByDailyClosing = saveTotalTransactionsAmountByDailyClosing(totalTransactionsAmountByDailyClosing);
                        details.add(totalTransactionsAmountByDailyClosing);
                    }
                }
                //llamar a Procedimiento almacenado para pasar de TransactionsManagement a TransactionsManagementHistory
                executeHistory(oldClosingDate, closingDate);
                //Agregar dailyClosingId a las transacciones
                addDailyClosingInTransaction(oldClosingDate, closingDate, dailyClosing);
                dailyClosing.setClosingEndTime(new Date());// es la hora en que finaliza el proceso de cierre
                dailyClosing = saveDailyClosing(dailyClosing);// actualizo el cierre con la hora de finalizacion
                //faltan enviar correo de notificacion con la informacion del cierre no estaba en las especificaciones
                // SendMailTherad sendMailTherad = new SendMailTherad("ES", transactionsAmount, totalTrasactions,"", "", Constants.SEND_TYPE_EMAIL_DAILY_CLOSING_WALLET);
                // sendMailTherad.run()
                
                //llamar al metodo para generar solicitud de tarjetas vencidas
                createCardRenewalRequestByIssuer(Constants.CARD_STATUS_ACTIVE);
     
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return dailyClosing;
    }
    
   
    private int TotalTransactionsCurrentDate(Date begginingDateTime, Date endingDateTime) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM transactionsManagement t WHERE t.createDate between ?1 AND ?2 AND t.dailyClosingId IS NULL AND (t.indClosed IS NULL OR t.indClosed = 0)");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.size();
    }
    
         
     private int TotalTransactionsCurrentDatebyTransactionType(Date begginingDateTime, Date endingDateTime, Integer transactionTypeId) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM transactionsManagement t WHERE t.createDate between ?1 AND ?2 AND t.transactionTypeId=?3 AND t.dailyClosingId IS NULL AND (t.indClosed IS NULL OR t.indClosed = 0)");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
        query.setParameter("3", transactionTypeId);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.size();
    }

    private void executeHistory(Date begginingDateTime, Date endingDateTime) {
        StringBuilder sqlBuilder = new StringBuilder("{call pasarTransactionesAHistoricos(?,?)}");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
        query.executeUpdate();

    }

    private Double TotalAmountCurrentDate(Date begginingDateTime, Date endingDateTime) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.settlementTransactionAmount) FROM transactionsManagement t WHERE t.createDate between ?1 AND ?2 AND t.dailyClosingId IS NULL AND (t.indClosed IS NULL OR t.indClosed = 0)");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.get(0) != null ? (Double) result.get(0) : 0f;
    }
    
    private Double TotalAmountCurrentDateByTransaction(Date begginingDateTime, Date endingDateTime,Integer transactionTypeId) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.settlementTransactionAmount) FROM transactionsManagement t WHERE t.createDate between ?1 AND ?2 AND t.transactionTypeId = ?3 AND t.dailyClosingId IS NULL AND (t.indClosed IS NULL OR t.indClosed = 0)");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
        query.setParameter("3", transactionTypeId);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.get(0) != null ? (Double) result.get(0) : 0f;
    }
    
    private boolean isHoliday (Date currentDate)throws GeneralException, NullParameterException {
    	boolean holiday = false;
    	CalendarDays calendarDays = new CalendarDays();
        try {
            if (currentDate == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "holiday"), null);
            }	
            Query query = createQuery("SELECT c FROM CalendarDays c WHERE c.holidayDate = ?1");
            query.setParameter("1", currentDate);
            calendarDays = (CalendarDays) query.setHint("toplink.refresh", "true").getSingleResult();
            holiday = true;
        } catch (NoResultException ex) {
        	 holiday = false;
        } catch (Exception ex) {
            System.out.println("currentDate: " + currentDate);
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
    	return holiday;
    }
    
    private Date OldClosingDate(Date closingDate) {   
        DailyClosing dailyClosing = new DailyClosing();
        try {
            Query query = createQuery("SELECT d FROM DailyClosing d WHERE d.closingDate < ?1 order by d.id desc");
            query.setParameter("1", closingDate);
            query.setMaxResults(1);
            dailyClosing = (DailyClosing) query.setHint("toplink.refresh", "true").getSingleResult();
        } catch (NoResultException ex) {
            return EjbUtils.getBeginningDate(new Date());     
        } catch (Exception ex) {
           ex.printStackTrace();           
        }
        return dailyClosing.getClosingDate();
    }
    
    private void addDailyClosingInTransaction(Date begginingDateTime, Date endingDateTime,DailyClosing dailyClosing) throws GeneralException, NullParameterException {
        if (dailyClosing == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "dailyClosing"), null);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String begginingDate = formatter.format(begginingDateTime);
        String endingDate = formatter.format(endingDateTime);
        try {
            StringBuilder sqlBuilder1 = new StringBuilder("UPDATE cardManagementSystem.transactionsManagement t SET t.dailyClosingId= "+dailyClosing.getId()+", t.indClosed = 1 WHERE t.createDate between '"+begginingDate+"' AND '"+endingDate+"' AND t.dailyClosingId IS NULL AND (t.indClosed IS NULL OR t.indClosed = 0)");
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Query query1 = entityManager.createNativeQuery(sqlBuilder1.toString());
            query1.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }
    
    private DailyClosing saveDailyClosing(DailyClosing dailyClosing) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (dailyClosing == null) {
            throw new NullParameterException("dailyClosing", null);
        }
        return (DailyClosing) saveEntity(dailyClosing);
    }  
    
    private TotalTransactionsAmountByDailyClosing saveTotalTransactionsAmountByDailyClosing(TotalTransactionsAmountByDailyClosing totalTransactionsAmountByDailyClosing) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (totalTransactionsAmountByDailyClosing == null) {
            throw new NullParameterException("totalTransactionsAmountByDailyClosing", null);
        }
        return (TotalTransactionsAmountByDailyClosing) saveEntity(totalTransactionsAmountByDailyClosing);
    }  

   
    public List<CardRenewalRequest> createCardRenewalRequestByIssuer(Integer cardStatus) throws RegisterNotFoundException, EmptyListException, GeneralException, NullParameterException {
        //Se declara la lista de solicitudes a retornar
        List<CardRenewalRequest> cardRenewalRequestList = new ArrayList<CardRenewalRequest>();
        int issuerId = 0;

        //Consulta para obtener el id del emisor para las tarjetas cuya fecha de renovación es igual a la fecha actual y estén activas
        StringBuilder sqlBuilder = new StringBuilder("SELECT i.id FROM card c, issuer i, product p ");
        sqlBuilder.append("WHERE c.productId = p.id AND p.issuerId = i.id AND c.cardStatusId = ?1 AND c.automaticRenewalDate <= CURDATE() AND c.indRenewal = 0 GROUP BY i.id");
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
            sqlBuilder.append("WHERE c.productId = p.id AND p.issuerId = i.id AND c.cardStatusId = ?1 AND i.id = ?2 AND c.automaticRenewalDate <= CURDATE() AND c.indRenewal = 0");
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
                
                //actualizar el campo indRenawel para indicar que la tarjeta sera renovada
                c.setIndRenewal(true);
                c.setUpdateDate(new Timestamp(new Date().getTime()));
                cardEJB.saveCard(c);
            }

            //Agregas la solicitud a la lista que retorna el servicio
            cardRenewalRequestList.add(cardRenewalRequest);
        }
        return cardRenewalRequestList;
    }

  
}
