package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.ClosingEJB;
import com.alodiga.cms.commons.ejb.ClosingEJBLocal;
import com.alodiga.cms.commons.ejb.UtilsEJBLocal;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.CalendarDays;
import com.cms.commons.models.DailyClosing;
import com.cms.commons.models.OriginApplication;
import com.cms.commons.util.Constants;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.EjbUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
                    addDailyClosingInTransaction(oldClosingDate, closingDate, dailyClosing);
                    dailyClosing.setClosingEndTime(new Date());// es la hora en que finaliza el proceso de cierre
                    dailyClosing = saveDailyClosing(dailyClosing);// actualizo el cierre con la hora de finalizacion
//                    SendMailTherad sendMailTherad = new SendMailTherad("ES", transactionsAmount, totalTrasactions,"", "", Constants.SEND_TYPE_EMAIL_DAILY_CLOSING_WALLET);
//                    sendMailTherad.run();
            }
        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        return dailyClosing;
    }
    
   
    private int TotalTransactionsCurrentDate(Date begginingDateTime, Date endingDateTime) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM transactionsManagement t WHERE t.creationDate between ?1 AND ?2 AND t.dailyClosingId IS NULL AND t.indClosed = 0");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.size();
    }


    private Double TotalAmountCurrentDate(Date begginingDateTime, Date endingDateTime) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.settlementTransactionAmount) FROM transactionsManagement t WHERE t.creationDate between ?1 AND ?2 AND t.dailyClosingId IS NULL AND t.indClosed = 0");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", begginingDateTime);
        query.setParameter("2", endingDateTime);
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
            StringBuilder sqlBuilder1 = new StringBuilder("UPDATE cardManagementSystem.transactionsManagement t SET t.dailyClosingId= "+dailyClosing.getId()+", t.indClosed = 1 WHERE t.creationDate between '"+begginingDate+"' AND '"+endingDate+"' AND t.dailyClosingId IS NULL AND t.indClosed = 0");
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

  
}
