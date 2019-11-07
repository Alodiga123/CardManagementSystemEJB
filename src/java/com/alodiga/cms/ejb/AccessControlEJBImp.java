package com.alodiga.cms.ejb;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.alodiga.cms.commons.ejb.AccessControlEJB;
import com.alodiga.cms.commons.ejb.AccessControlEJBLocal;
import com.alodiga.cms.commons.exception.DisabledAccountException;
import com.alodiga.cms.commons.exception.DisabledUserException;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.InvalidPasswordException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Account;
import com.cms.commons.models.User;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import javax.persistence.NoResultException;
import javax.persistence.Query;


@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.ACCESS_CONTROL_EJB, mappedName = EjbConstants.ACCESS_CONTROL_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class AccessControlEJBImp extends AbstractDistributionEJB implements AccessControlEJB, AccessControlEJBLocal {

    private static final Logger logger = Logger.getLogger(AccessControlEJBImp.class);

    
    public User loadUserByLogin(String login) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = null;
        try {
            Query query = createQuery("SELECT u FROM User u WHERE u.login =:login AND u.enabled=TRUE");
            query.setParameter("login", login);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
        return user;
    }

    @Override
    public User validateUser(String login, String password) throws RegisterNotFoundException, NullParameterException, GeneralException, DisabledUserException, InvalidPasswordException {
        User user = null;
        try {
            user = loadUserByLogin(login);
            if (!user.getPassword().equals(password)) {
                throw new InvalidPasswordException(com.cms.commons.util.Constants.INVALID_PASSWORD_EXCEPTION);
            }
            if (!user.getEnabled()) {
                throw new DisabledUserException(com.cms.commons.util.Constants.DISABLED_USER_EXCEPTION);
            }
            return user;
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (RegisterNotFoundException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
    }

}
