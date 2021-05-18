package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.ClosingEJBLocal;
import com.alodiga.cms.commons.ejb.ClosingTimerEJB;
import com.alodiga.cms.commons.ejb.ClosingTimerEJBLocal;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.EjbUtils;

@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.CLOSING_TIMER_EJB, mappedName = EjbConstants.CLOSING_TIMER_EJB)
@TransactionManagement(TransactionManagementType.BEAN)
public class ClosingTimerEJBImp extends AbstractDistributionEJB implements ClosingTimerEJB, ClosingTimerEJBLocal {

    private static final Logger logger = Logger.getLogger(ClosingTimerEJBImp.class);

    @EJB
    private ClosingEJBLocal closingEJBLocal;
    @Resource
    private SessionContext ctx;
    Calendar initialExpiration;
    private Long timeoutInterval = 0L;


    private void cancelTimers() {
        try {
            if (ctx.getTimerService() != null) {
                Collection<Timer> timers = ctx.getTimerService().getTimers();
                if (timers != null) {
                    for (Timer timer : timers) {
                        timer.cancel();
                    }
                }
            }
        } catch (Exception e) {
            //
        }
    }

    private void createTimer() {
        ctx.getTimerService().createTimer(initialExpiration.getTime(), timeoutInterval, EjbConstants.CLOSING_TIMER_EJB);
}

    @Timeout
    public void execute(Timer timer) {
        try {
            logger.info("[ClosingTimerEJB] Ejecutando");
            System.out.println("[ClosingTimerEJB] Ejecutando");
            executeBilling();
            stop();
            start();

        } catch (Exception e) {
            logger.error("Error", e);
        }
    }


    private void executeBilling() throws Exception {

        try {
        	closingEJBLocal.closingDailyTransactionWallet(EjbUtils.getEndingDate(new Date()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void forceExecution() throws Exception {
        logger.info("Ejecuta forceExecution!!!!!!!!");
    }

    public Date getNextExecutionDate() {
        if (ctx.getTimerService() != null) {
            Collection<Timer> timers = ctx.getTimerService().getTimers();
            if (timers != null) {
                for (Timer timer : timers) {
                    return timer.getNextTimeout();
                }
            }
        }

        return null;
    }

    public void restart() throws Exception {
        stop();
        start();
        logger.info("[ClosingTimerEJB] Reiniciado");
    }

    private void setTimeoutInterval() throws Exception {
//    	PreferenceManager pManager = PreferenceManager.getInstance();
//    	String time = pManager.getPreferencesValueByClassificationIdAndPreferenceId(1L,PreferenceFieldEnum.WALLET_CLOSING_TIME.getId());
//    	String[] parts = time.split(":");
        initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR, 11);//Media entre zona horaria de California Y Florida - EN CA 12 am en FL seria las 4 am.
        initialExpiration.set(Calendar.MINUTE, 55);
        initialExpiration.set(Calendar.SECOND, 0);;
        initialExpiration.set(Calendar.MILLISECOND, 0);
        initialExpiration.set(Calendar.AM_PM, Calendar.PM);
        Long secondsInDay = 86400L;
        //secondsInDay = secondsInDay * 15;//Cada quince dias
        initialExpiration.add(Calendar.DAY_OF_MONTH, 0);//El timer comienza un dia despues que se inicializa.
        timeoutInterval = secondsInDay * 1000L;//Milisegundos
    }

    @SuppressWarnings("unchecked")
    public void start() throws Exception {
        setTimeoutInterval();
        createTimer();
        logger.info("[ClosingTimerEJB] Iniciado");
    }

    @SuppressWarnings("unchecked")
    public void stop() throws Exception {
        cancelTimers();
        logger.info("[ClosingTimerEJB] Detenido");
    }

    public Long getTimeoutInterval() {
        return timeoutInterval;
    }
        
    
}
